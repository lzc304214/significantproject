package com.personal.noncommercial.significantproject.view.pagemanager

import android.databinding.ObservableField


/**
 * @author :lizhengcao
 * @date :2019/10/22
 * E-mail:lizc@bsoft.com.cn
 *
 * @类说明
 */
class NoMoreView(type: Int) {

    val message = ObservableField<String>()

    init {
        when (type) {
            NOMORE_RECODE -> {
                message.set("没有兑换记录了")
            }
            else -> {
                message.set("没有更多了")
            }
        }
    }

    companion object {
        const val NOMORE_DEFAULT = 1000 // 默认没有更多了
        const val NOMORE_RECODE = 10001 // 没有兑换记录了
    }
}