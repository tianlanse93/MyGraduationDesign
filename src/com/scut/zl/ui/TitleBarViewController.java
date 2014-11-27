package com.scut.zl.ui;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.scut.zl.R;
import com.scut.zl.core.application.BaseViewController;
/**
 *
 */
/**
 * Created by minghao_zl on 14/11/19.
 */
public class TitleBarViewController extends BaseViewController{

    private TextView tv_title_bar;
    private ImageButton btn_title_back;
    public TitleBarViewController(Context context){
        super(context);
        initView();
    }

    private void initView(){
        tv_title_bar = (TextView) ((Activity)mContext).findViewById(R.id.tv_title_bar);
        btn_title_back = (ImageButton) ((Activity)mContext).findViewById(R.id.btn_title_back);
        btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
    }

    public void setTitle(String txt){
        if( !TextUtils.isEmpty(txt) && null != tv_title_bar ) {
            tv_title_bar.setText(txt);
        }
    }

    public void setTitle(int txt_id){
        if( null != tv_title_bar ) {
            tv_title_bar.setText(txt_id);
        }
    }

    @Override
    public View getView(){
        return mView;
    }
}


