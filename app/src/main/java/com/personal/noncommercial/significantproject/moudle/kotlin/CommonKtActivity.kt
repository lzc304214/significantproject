package com.personal.noncommercial.significantproject.moudle.kotlin

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.personal.noncommercial.significantproject.R
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity
import com.personal.noncommercial.significantproject.moudle.bean.Person


/**
 * @author :lizhengcao
 * @date :2019/4/14
 * E-mail:lizc@bsoft.com.cn
 *
 * @类说明
 */
class CommonKtActivity : BaseActivity() {


    override fun initOnCreate(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRootLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return R.layout.activity_kt_commmon
    }

    fun getBasicData(param1: Int, p2: String, p3: List<Person>): String {
        p3.forEach { p ->
            p.gender
            p.age
            p.hobby
        }
        when {
        }
        return ""

    }

}