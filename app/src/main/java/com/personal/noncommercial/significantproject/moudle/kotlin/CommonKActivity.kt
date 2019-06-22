package com.personal.noncommercial.significantproject.moudle.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.personal.noncommercial.significantproject.R

import com.personal.noncommercial.significantproject.moudle.base.BaseActivity
import com.personal.noncommercial.significantproject.moudle.bean.Person
import java.util.ArrayList


/**
 * @author :lizhengcao
 * @date :2019/4/14
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */
class CommonKActivity : AppCompatActivity() {

    private var books: List<String>? = null
    private var person: List<Person>? = null
    private var p: Person? = null
    private var bookName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_k_common)
        var age: Int
        var agender: String
        books = ArrayList<String>()
        person = ArrayList<Person>()
        books.apply { }
        /*person?.forEach { p ->
            bookName = p?.name
        }*/
        person?.forEach { p ->
            bookName = p.name
            p.age
            p.gender
        }
        for (p in person as ArrayList<Person>) {

        }
        p?.gender?.toUpperCase()
        p?.age
    }

    override fun onResume() {
        super.onResume()
    }
}
