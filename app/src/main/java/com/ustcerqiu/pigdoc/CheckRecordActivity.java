package com.ustcerqiu.pigdoc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//this activity deals with the intents of check the record date
public class CheckRecordActivity extends BaseMinorClass implements View.OnClickListener,
        NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener, NumberPicker.Formatter {
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

        Dialog dialog = new Dialog(this, R.style.mDialogTheme); // theme
        dialog.setContentView(R.layout.alert_dialog_1);
        //dialog.setTitle("wokao");
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        layoutParams.width = dm.widthPixels;
        dialogWindow.setAttributes(layoutParams);
        dialogWindow.getDecorView().setPadding(0,0,0,0); //占满控件
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM); //布局位置

        NumberPicker valuepicker = (NumberPicker) dialogWindow.findViewById(R.id.num_picker);  //注意在dialogWindow中找，否则找不到出错
        String[] city = {"  立水桥立水桥立水桥立水桥立水桥立水桥  ","  霍营立水桥立水桥  ","  回龙观立水桥立水桥  ","  龙泽立水桥  ","  西二旗立水桥  ","  上地立水桥  "};
        valuepicker.setFormatter(this);
        valuepicker.setOnValueChangedListener(this);
        valuepicker.setOnScrollListener(this);
        valuepicker.setDisplayedValues(city);
        valuepicker.setMinValue(0);
        valuepicker.setMaxValue(city.length-1);
        valuepicker.setValue(4);
        //valuepicker.setWrapSelectorWheel(false);  //是否循环
        valuepicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //避免点击后可修改状态



        //setOnClickListener (whole page)
        (findViewById(R.id.button_back)).setOnClickListener(this);

    }//onCreate

    @Override //Formatter
    public String format(int value) {
        return "0"+value;
    }

    @Override //OnScrollListener
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch(scrollState){
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING: //滑动惯性过程种
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE: //没滑动
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: //触摸滑动中
                break;
            default:
                break;
        }
    }

    @Override //OnValueChangeListener
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        //TODO
    }
}//end
