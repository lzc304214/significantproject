package com.personal.noncommercial.significantproject.view.pagemanager

import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.personal.noncommercial.significantproject.R
import kotlinx.android.synthetic.main.activity_guide.view.*
import kotlinx.android.synthetic.main.adapter_item_parent.view.*


/**
 * @author :lizhengcao
 * @date :2019/10/22
 * E-mail:lizc@bsoft.com.cn
 *
 * @类说明
 */
class ErrorView(type: Int) {
    val message = ObservableField<String>()
    val src = ObservableInt()

    init {
        when (type) {
            ERROR_DATA -> {
                message.set("数据加载失败")
                src.set(R.drawable.angel)
            }
            ERROR_NETWORK -> {
                message.set("网络异常，请检查网络")
                src.set(R.drawable.bell)
            }
        }
    }

    companion object {
        // 网络异常
        val ERROR_NETWORK = 1000
        // 加载数据失败
        val ERROR_DATA = 10001
    }
}