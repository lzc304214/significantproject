package com.personal.noncommercial.significantproject.view.pagemanager

import android.databinding.ObservableArrayList
import android.os.Handler
import android.view.View
import com.personal.noncommercial.significantproject.BR
import com.personal.noncommercial.significantproject.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import java.lang.Exception


/**
 * @author :lizhengcao
 * @date :2019/10/22
 * E-mail:lizc@bsoft.com.cn
 *
 * @类说明
 */
class PageLoadManager<Item> {
    private var pageNo = 1
    private var pageSize = 10
    var smartRefreshLayout: SmartRefreshLayout? = null
    private var enableRefresh = true
    private var enableLoadMore = true

    // 用于刷新列表的adapter
    var adapter = BindingRecyclerViewAdapter<Any>()
    // 可以合并数据的list(包含其他布局的集合)
    var itemData = MergeObservableList<Any>()
    //  给recyclerView添加items（只包含列表数据的集合）
    var items = ObservableArrayList<Item>()
    // 给recyclerView添加ItemBinding
    var onItemBind: OnItemBindClass<Any>? = null
    //加载数据监听
    private var loadListener: LoadListener<Item>? = null
    // 网络异常刷新按钮点击
    private var refreshListener = View.OnClickListener { refresh() }

    private var isLoading: Boolean = false
    private var emptyViewType: Int = 0
    private var noMoreViewType: Int = 0

    @JvmOverloads
    constructor(listener: LoadListener<Item>, itemBind: OnItemBindClass<Any>,
                isLoading: Boolean = false, emptyViewType: Int = EmptyView.EMPTY_DEFALUT,
                noMoreViewType: Int = NoMoreView.NOMORE_DEFAULT) {
        this.isLoading = isLoading
        this.emptyViewType = emptyViewType
        this.noMoreViewType = noMoreViewType
        if (isLoading) {
            /*itemBind.map(LoadingView::class.java, BR.loading, R.layout.layout_loading_view)*/
        }

        if (emptyViewType != 0) {
            itemBind.map(EmptyView::class.java, BR.empty,R.layout.layout_empty_view)
        }
        if (noMoreViewType != 0) {
            itemBind.map(NoMoreView::class.java,BR.noMore,R.layout.item_footer_no_more)
        }

         itemBind.map(ErrorView::class.java,{ itemBinding, position, item ->
             itemBinding.clearExtras().set(BR.error,R.layout.layout_error_view)
                     .bindExtra(BR.listener,refreshListener)
         })
        this.loadListener = listener
        this.onItemBind = itemBind
        initData()
    }

    private fun initData() {
        if (isLoading) {
            /*itemData.insertItem(loadingView())*/
        }
        Handler().post {
            refresh()
        }
    }

    fun initSmartRefreshLayout(smartRefreshLayout: SmartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout
        smartRefreshLayout.setEnableRefresh(false)
        smartRefreshLayout.setEnableLoadMore(false)
        smartRefreshLayout.setOnRefreshListener { refresh() }
        smartRefreshLayout.setOnLoadMoreListener { loadMore() }
    }

    fun setEnableRefresh(enabled: Boolean) {
        enableRefresh = enabled
        smartRefreshLayout?.setEnableRefresh(enabled)
    }

    fun setEnableLoadMore(enabled: Boolean) {
        enableLoadMore = enabled
        smartRefreshLayout?.setEnableLoadMore(enabled)
    }

    /**
     * 下拉刷新
     */
    fun refresh() {
        pageNo = 1
        //恢复没有更多数据的状态
        smartRefreshLayout?.setNoMoreData(false)
        loadData(true)
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        loadData(false)
    }

    /**
     * 获取数据
     */
    private fun loadData(isRefresh: Boolean) {
        loadListener?.loadingData(isRefresh, pageNo, pageSize, object : LoadCallbcak<Item> {
            override fun onSuccess(rowCount: Int, items: List<Item>) {
                loadFinish()
                pageNo++
                if (isRefresh) {

                }
            }

            override fun onSuccessNoPage(items: List<Item>, isShowNoMore: Boolean) {
                loadFinish()
                // 无分页加载，尾部不展示更多
                setDataNoPage(items, isShowNoMore)
            }

            override fun onFailed(exception: Exception) {
                loadFinish()
                loadError(exception)
            }
        })
    }

    /**
     * 无分页加载设置数据
     */
    fun setDataNoPage(data: List<Item>, isShowNoMore: Boolean) {
        smartRefreshLayout?.setEnableLoadMore(false)
        smartRefreshLayout?.finishRefresh(true)
        if (data.isNotEmpty()) {
            items.clear()
            items.addAll(data)
            itemData.removeAll()
            itemData.insertList(items)
            if (isShowNoMore) {
                itemData.insertItem(NoMoreView(noMoreViewType))
            }
        } else {
            itemData.removeAll()
            itemData.insertItem(EmptyView(emptyViewType))
        }
        notifyDataSetChanged()
    }

    /**
     *  下拉刷新成功
     */
    fun refreshSuccess(rowCount: Int, data: List<Item>) {
        smartRefreshLayout?.finishRefresh(true)
        if (data.isNotEmpty()) {
            items.clear()
            items.addAll(data)
            itemData.removeAll()
            itemData.insertList(items)

            if (smartRefreshLayout == null || data.size < pageSize || items.size >= rowCount) {
                itemData.insertItem(NoMoreView(noMoreViewType))
                smartRefreshLayout?.finishLoadMoreWithNoMoreData()
            } else {
                smartRefreshLayout?.setEnableLoadMore(true)
            }
        } else {
            itemData.removeAll()
            itemData.insertItem(NoMoreView(noMoreViewType))
            smartRefreshLayout?.finishLoadMoreWithNoMoreData()
        }
        notifyDataSetChanged()
    }

    /**
     * 加载更多成功
     */
    fun loadMoreSuccess(rowCount: Int, data: List<Item>) {
        smartRefreshLayout?.finishLoadMore(true)
        if (data.isNotEmpty()) {
            items.addAll(data)

            if (data.size < pageSize || items.size >= rowCount) {
                itemData.insertItem(NoMoreView(noMoreViewType))
                smartRefreshLayout?.finishLoadMoreWithNoMoreData()
            }
        } else {
            itemData.insertItem(NoMoreView(noMoreViewType))
            smartRefreshLayout?.finishLoadMoreWithNoMoreData()
        }
        notifyDataSetChanged()
    }

    /**
     * 数据加载失败
     */
    private fun loadError(exception: Exception) {
        smartRefreshLayout?.apply {
            finishRefresh(true)
            finishLoadMore(true)
            setEnableLoadMore(false)
        }

        // TODO 网络异常界面
        if ("".equals("")) {
            itemData.insertItem(ErrorView(ErrorView.ERROR_NETWORK))
        } else {
            itemData.insertItem(ErrorView(ErrorView.ERROR_DATA))
        }

    }

    /**
     * 接口执行完毕（无论成功还是失败）
     */
    fun loadFinish() {
        smartRefreshLayout?.setEnableRefresh(enableRefresh)
        smartRefreshLayout?.setEnableLoadMore(enableLoadMore)
    }

    /**
     * 刷新列表
     */
    fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    interface LoadListener<Item> {
        fun loadingData(isRefresh: Boolean, pageNo: Int, pageSize: Int, callback: LoadCallbcak<Item>)
    }

    interface LoadCallbcak<Item> {
        fun onSuccess(rowCount: Int, items: List<Item>)

        fun onSuccessNoPage(items: List<Item>, isShowNoMore: Boolean = false)

        fun onFailed(exception: Exception)
    }
}