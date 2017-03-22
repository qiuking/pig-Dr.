package com.ustcerqiu.pigdoc;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ustcerQ on 2017/3/22.
 */

public class BaseMinorClass extends AppCompatActivity {

    //定义所有返回建的操作
    @Override
    public void onBackPressed() {
        //重写点击安卓返回键后的动作
        // super.onBackPressed();
        finish(); //
        overridePendingTransition(R.anim.translate_in_back, R.anim.translate_out_back);  //添加返回切换动画
    }
}//end
