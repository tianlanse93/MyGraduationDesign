package com.scut.zl.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scut.zl.R;
import com.scut.zl.core.application.BaseViewController;

/**
 * Created by minghao_zl on 14/11/21.
 */
public class InputViewController extends BaseViewController {

    //input数目
    private int mCount;

    private View v_input_singles[];
    private TextView tv_hint[];
    private EditText et_input[];

    LayoutInflater inflater;
    public InputViewController(Context context) {
        super(context);
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    public InputViewController(Context context,ViewGroup parent) {
        super(context);
        inflater = LayoutInflater.from(mContext);
        mView = parent;
//        initView();
    }

    private void initView(){
        mView = new LinearLayout(mContext);
        mView.setHovered(false);
        mView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    public void setHint( String... hint ){
        this.mCount = hint.length;
        v_input_singles = new View[mCount];
        tv_hint = new TextView[mCount];
        et_input = new EditText[mCount];

        for( int i = 0; i < mCount; i++ ){
            v_input_singles[i] = inflater.inflate(R.layout.layout_input,null);
            tv_hint[i] = (TextView)v_input_singles[i].findViewById(R.id.tv_input_hint);
            et_input[i] = (EditText)v_input_singles[i].findViewById(R.id.et_input);

            tv_hint[i].setText(hint[i]);
            v_input_singles[i].setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100) );
            ((LinearLayout)mView).addView(v_input_singles[i]);
        }
    }

    public void setHint( int... resId ){
        this.mCount = resId.length;
        v_input_singles = new View[mCount];
        tv_hint = new TextView[mCount];
        et_input = new EditText[mCount];

        for( int i = 0; i < mCount; i++ ){
            v_input_singles[i] = inflater.inflate(R.layout.layout_input,null);
            tv_hint[i] = (TextView)v_input_singles[i].findViewById(R.id.tv_input_hint);
            et_input[i] = (EditText)v_input_singles[i].findViewById(R.id.et_input);

            tv_hint[i].setText(resId[i]);
            v_input_singles[i].setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100) );
            ((LinearLayout)mView).addView(v_input_singles[i]);
        }
    }

    @Override
    public View getView(){
        return mView;
    }
}
