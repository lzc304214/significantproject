package com.personal.noncommercial.significantproject.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.utils.DensityUtils;


/**
 * Created by qfxl on 2016/3/24.
 */
public class CustomDialog extends Dialog {


    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    //用Builder模式来构造Dialog
    public static class Builder {
        private Context mContext;
        private View contentView;
        private String title;
        private String message;
        private String positiveText;
        private String negativeText;
        private OnClickListener positiviOnclickListener;
        private OnClickListener negativeOnclickListener;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setContentView(View contentView) {//设置dialog的主view
            this.contentView = contentView;
            return this;
        }

        public Builder setTitle(String title) {//设置dialog的标题
            this.title = title;
            return this;
        }

        public Builder setMessage(String msg) {//设置dialig的内容
            this.message = msg;
            return this;
        }

        public Builder setPositiveButton(String text, OnClickListener positiviOnclickListener) {//dialog的确认按钮
            this.positiveText = text;
            this.positiviOnclickListener = positiviOnclickListener;
            return this;
        }

        public Builder setNegativeButton(String text, OnClickListener negativeOnclickListener) {//dialog的取消按钮
            this.negativeText = text;
            this.negativeOnclickListener = negativeOnclickListener;
            return this;
        }

        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public CustomDialog build() {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog mCustomDialog = new CustomDialog(mContext, R.style.CustomDialog);//默认调用带style的构造
            mCustomDialog.setCanceledOnTouchOutside(false);//默认点击布局外不能取消dialog

            View view = mInflater.inflate(R.layout.custom_dialog, null);

//    		GradientDrawable myGrad = (GradientDrawable) contentView
//    				.getBackground();
//    		if (myGrad != null){
//    			myGrad.setColor(Color.parseColor("#99000000"));
//    		}

           /* 改变宽度的方式
           ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    DensityUtils.dp2px(300), LinearLayout.LayoutParams.WRAP_CONTENT);
            mCustomDialog.setContentView(view, layoutParams);*/

            mCustomDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));//！！！
            if (!TextUtils.isEmpty(title)) {
                TextView titleView = (TextView) view.findViewById(R.id.tv_title);
                titleView.setText(title);
            }
            if (!TextUtils.isEmpty(positiveText)) {//这是确认按钮
                Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
                btn_confirm.setText(positiveText);
                if (positiviOnclickListener != null) {
                    btn_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiviOnclickListener.onClick(mCustomDialog, BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                view.findViewById(R.id.btn_confirm).setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(negativeText)) {//这是取消按钮逻辑处理
                Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                btn_cancel.setText(negativeText);
                if (negativeOnclickListener != null) {
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeOnclickListener.onClick(mCustomDialog, BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                view.findViewById(R.id.btn_cancel).setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(message)) {
                TextView messageView = (TextView) view.findViewById(R.id.tv_message);
                messageView.setText(message);//显示的内容
            } else if (contentView != null) {//如果内容区域要显示其他的View的话
                LinearLayout mContentLayout = (LinearLayout) view.findViewById(R.id.content);
                mContentLayout.removeAllViews();
                mContentLayout.addView(contentView);
            }

            mCustomDialog.setContentView(view);
            return mCustomDialog;
        }
    }
}
