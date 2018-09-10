package com.personal.noncommercial.significantproject.factory;


import android.support.v4.app.Fragment;

import com.personal.noncommercial.significantproject.moudle.base.BaseFragment;
import com.personal.noncommercial.significantproject.moudle.fragment.HomeFragment;
import com.personal.noncommercial.significantproject.moudle.fragment.MessageFragment;
import com.personal.noncommercial.significantproject.moudle.fragment.MyFragment;
import com.personal.noncommercial.significantproject.moudle.fragment.QueryFragment;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :lizhengcao
 * @date :2018/3/29
 * E-mail:lizc@bsoft.com.cn
 * @类说明 fragment的工厂模式
 */

public class FragmentFactory {

    public static final int HOME_FRAGMENT_POSITION = 0;
    public static final int QUERY_FRAGMENT_POSITION = 1;
    public static final int MESSAGE_FRAGMENT_POSITION = 2;
    public static final int MY_FRAGMENT_POSITION = 3;
    public static final int MAIN_TAB_FRAGMENT_COUNT = 4;

    private static Map<Integer, BaseFragment> map = new HashMap<>();

    public static BaseFragment getMainTabInstance(int position) {
        BaseFragment fragment = map.get(position);

        if (fragment == null) {
            //当前位置的map集合中无碎片
            switch (position) {
                case HOME_FRAGMENT_POSITION:
                    fragment = HomeFragment.getInstance();
                    break;
                case QUERY_FRAGMENT_POSITION:
                    fragment = QueryFragment.getInstance();
                    break;
                case MESSAGE_FRAGMENT_POSITION:
                    fragment = MessageFragment.getInstance();
                    break;
                case MY_FRAGMENT_POSITION:
                    fragment = MyFragment.getInstance();
                    break;
                default:
                    break;
            }

            map.put(position, fragment);
        }

        return fragment;
    }
}
