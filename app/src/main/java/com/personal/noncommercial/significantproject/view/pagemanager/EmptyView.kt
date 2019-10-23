package com.personal.noncommercial.significantproject.view.pagemanager

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.personal.noncommercial.significantproject.R


/**
 * @author :lizhengcao
 * @date :2019/10/22
 * E-mail:lizc@bsoft.com.cn
 *
 * @类说明
 */
class EmptyView(type: Int) {
    val message = ObservableField<String>()
    val src = ObservableInt()
    val myBalance = ObservableBoolean(false)

    init {
        when (type) {
            EMPTY_MESSAGE -> {
                message.set("暂无消息")
                src.set(R.drawable.bell)
            }
            EMPTY_BALANCE -> {
                message.set("平衡")
                myBalance.set(true)
            }
            else -> {
                message.set("暂无数据")
                src.set(R.drawable.angel)
            }
        }
    }

    companion object {
        const val EMPTY_DEFALUT = 1000
        const val EMPTY_MESSAGE = 10001
        const val EMPTY_BALANCE = 10002
    }
}