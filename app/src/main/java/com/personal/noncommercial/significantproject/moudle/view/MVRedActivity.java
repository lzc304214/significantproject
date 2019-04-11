package com.personal.noncommercial.significantproject.moudle.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.databinding.ActivityMvRedBinding;
import com.personal.noncommercial.significantproject.moudle.bean.User;
import com.personal.noncommercial.significantproject.utils.ToastUtils;
import com.personal.noncommercial.significantproject.viewModel.VMModel;


/**
 * @author :lizhengcao
 * @date :2019/4/6
 * E-mail:lizc@bsoft.com.cn
 * @类说明 dataBinding的测试activity
 */
public class MVRedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvRedBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mv_red);
        User user = new User("张三", 24, true, 1);
        VMModel vmModel = new VMModel(this);
        binding.setUser(user);
        binding.setVmmodel(vmModel);
    }


}
