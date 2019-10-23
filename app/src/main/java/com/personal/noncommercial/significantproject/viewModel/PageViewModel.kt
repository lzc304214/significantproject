package com.personal.noncommercial.significantproject.viewModel

import android.view.View
import com.personal.noncommercial.significantproject.BR
import com.personal.noncommercial.significantproject.R
import com.personal.noncommercial.significantproject.moudle.bean.Person
import com.personal.noncommercial.significantproject.view.pagemanager.PageLoadManager
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass


/**
 * @author :lizhengcao
 * @date :2019/10/23
 * E-mail:lizc@bsoft.com.cn
 *
 * @类说明
 */
class PageViewModel {
    lateinit var manager: PageLoadManager<Any>
    fun initManager() {

        val itemBind = OnItemBindClass<Any>().map(Person::class.java, { itemBinding, position, item ->
            itemBinding.clearExtras().set(BR.item, R.layout.item_adapter_page)
                   /* .bindExtra(BR.listener, listener)*/
        })
        manager = PageLoadManager<Any>(object : PageLoadManager.LoadListener<Any> {
            override fun loadingData(isRefresh: Boolean, pageNo: Int, pageSize: Int, callback: PageLoadManager.LoadCallbcak<Any>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }, itemBind)


    }

    val listener = View.OnClickListener { }
}