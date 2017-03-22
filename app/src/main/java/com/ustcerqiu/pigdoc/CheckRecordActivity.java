package com.ustcerqiu.pigdoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//this activity deals with the intents of check the record date
public class CheckRecordActivity extends BaseMinorClass implements View.OnClickListener {
    //属性区域
    //
    //

    ///////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //This method is to define the interface (the parameters needed) to start this activity.
    static public void actionStart(Context context, String titleName){
        Intent intent = new Intent(context, CheckRecordActivity.class);
        //define the parameters needed to be transfer to this activity
        intent.putExtra("titleName", titleName);
        //TODO the others
        context.startActivity(intent);
    }//actionsStart


    //this deals with the whole onClick actions in this page/view;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_back:
                finish(); //
                overridePendingTransition(R.anim.translate_in_back, R.anim.translate_out_back);  //添加返回切换动画
                break;
            default:
                break;
        }

    }//onClick


    ///--##############################################################################-----/////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_record);
        //deal with the intent
        String titleName = "猪博士"; //the default name
        Intent intent = getIntent(); //Try to get the intent
        if(intent != null){
            titleName = intent.getStringExtra("titleName");
        }
        //设置标题行的显示标题，替换掉layout中的默认标题
        ((TextView) this.findViewById(R.id.title_name_textView)).setText(titleName);  //this仅仅是强调本活动的调用环境

        //spinner selector
        List<String> spinnerList = new ArrayList<>();
        String[] spinnerItems = {"王晶晶", "王二精", "2晶晶", "6各日", "晶晶王"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        for(String item : spinnerItems) spinnerList.add(item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(adapter);


        //setOnClickListener (whole page)
        (findViewById(R.id.button_back)).setOnClickListener(this);

    }//onCreate
}//end
