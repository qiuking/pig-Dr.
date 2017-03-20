package com.ustcerqiu.pigdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ustcerQ on 2017/2/28.
 */

public class BaseClass extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }//onPause



    //定义所有返回建的操作
    @Override
    public void onBackPressed() {
        //重写点击安卓返回键后的动作
        // super.onBackPressed();
        finish(); //
        overridePendingTransition(R.anim.translate_in_back, R.anim.translate_out_back);  //添加返回切换动画
    }


}//base class
