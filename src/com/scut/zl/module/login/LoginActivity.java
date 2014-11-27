package com.scut.zl.module.login;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.scut.zl.R;
import com.scut.zl.core.application.BaseActivity;
import com.scut.zl.ui.InputViewController;
import com.scut.zl.ui.TitleBarViewController;


public class LoginActivity extends BaseActivity {

    TitleBarViewController titleBarViewController;
    InputViewController inputViewController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initTitleBar();
        initInputItems();
    }

    private void initTitleBar(){
        titleBarViewController = new TitleBarViewController(this);
        titleBarViewController.setTitle("登陆");
    }

    private void initInputItems(){
        LinearLayout ll_input = (LinearLayout) findViewById(R.id.ll_input);
        inputViewController = new InputViewController(this,ll_input);
        inputViewController.setHint("账号","密码","新密码");
    }
}
