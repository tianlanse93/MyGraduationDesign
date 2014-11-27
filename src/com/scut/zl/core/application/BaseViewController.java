package com.scut.zl.core.application;

import android.content.Context;
import android.view.View;

/**
 * Created by minghao_zl on 14/11/19.
 */
public class BaseViewController implements IController{

    protected Context mContext;
    protected View mView;

    public BaseViewController( Context context ){
        this.mContext = context;
    }

    @Override
    public View getView() {
        return mView;
    }
}
