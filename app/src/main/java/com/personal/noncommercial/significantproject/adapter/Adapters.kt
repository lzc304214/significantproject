package com.personal.noncommercial.significantproject.adapter

import android.databinding.BindingAdapter
import com.personal.noncommercial.significantproject.view.pagemanager.PageLoadManager
import com.scwang.smartrefresh.layout.SmartRefreshLayout


/**
 * @author :lizhengcao
 * @date :2019/10/23
 * E-mail:lizc@bsoft.com.cn
 *
 * @类说明
 */
object Adapters {

    @BindingAdapter("pageLoadManager")
    @JvmStatic
    fun <ItemType> pageManagerAttachRecyclerView(view: SmartRefreshLayout, listViewModel: PageLoadManager<ItemType>) {
        listViewModel.initSmartRefreshLayout(view)
    }
}