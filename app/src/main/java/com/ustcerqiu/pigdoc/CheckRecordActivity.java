package com.ustcerqiu.pigdoc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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


    //define the data class and the RecycleView adapter for the pig information card outlines.
    //the data needed
    public class PigCard{
        String number, sex, type, numYear, birthday, house, status, otherInfo;
        public PigCard(){ } //是否有默认无参数构造函数. 不设置就无
        public PigCard(String number, String sex, String type, String numYear, String birthday, String house, String status, String otherInfo){
            this.number = number;
            this.sex = sex;
            this.type = type;
            this.numYear = numYear;
            this.birthday = birthday;
            this.house = house;
            this.status = status;
            this.otherInfo = otherInfo;
        }
    }//pigCard

    //the adapter for the pigCard list
    public class PigCardAadpter extends mCom.comListAdapter<PigCard>{
        // the private List<T> mItemList 和 private int itemLayoutId  is in the parent；注意在构造函数中 itemLayoutId的赋值
        //unKnown the number of the holders, so use the list to store the listview holder content.
        private List<TextView> numberList = new ArrayList<>();
        private List<TextView> sexList = new ArrayList<>();
        private List<TextView> typeList = new ArrayList<>();
        private List<TextView> numYearList = new ArrayList<>();
        private List<TextView> birthdayList = new ArrayList<>();
        private List<TextView> houseList = new ArrayList<>();
        private List<TextView> statusList = new ArrayList<>();
        private List<TextView> otherInfoList = new ArrayList<>();

        //////////////////////////////////////
        //构造方法. set the data and the layout
        // R.layout.pig_card_outline_item
        public PigCardAadpter(List<PigCard> pigCardList, int pigCardLayoutId){
            super(pigCardList, pigCardLayoutId);
        }

        @Override
        public void setOnCreateViewHolder(mCom.ViewHolder holder, View v) {
            numberList.add( (TextView) v.findViewById(R.id.pig_number_text) );
            sexList.add( (TextView) v.findViewById(R.id.pig_sex) );
            typeList.add( (TextView) v.findViewById(R.id.pig_type) );
            numYearList.add( (TextView) v.findViewById(R.id.pig_num_year) );
            birthdayList.add( (TextView) v.findViewById(R.id.pig_birthday) );
            houseList.add( (TextView) v.findViewById(R.id.pig_house) );
            statusList.add( (TextView) v.findViewById(R.id.pig_status) );
            otherInfoList.add( (TextView) v.findViewById(R.id.pig_info_other) );
            //TODO onclick action, and so on.
        }

        @Override
        public void setOnBindViewHolder(mCom.ViewHolder holder, int position, PigCard item) {
            int viewHolderPositon = holder.getInitPosition(); //this position record the view tag position
            //set data to view in holder
            numberList.get(viewHolderPositon).setText(item.number);
            sexList.get(viewHolderPositon).setText(item.sex);
            typeList.get(viewHolderPositon).setText(item.type);
            numYearList.get(viewHolderPositon).setText(item.numYear);
            birthdayList.get(viewHolderPositon).setText(item.birthday);
            houseList.get(viewHolderPositon).setText(item.house);
            statusList.get(viewHolderPositon).setText(item.status);
            otherInfoList.get(viewHolderPositon).setText(item.otherInfo);
        }
    }//pigCardAdapter


    //method to get data need to generate the list
    public List<PigCard> getPigCardList(){
        //TODO 完成获取数据的方法
        List<PigCard> pigCardList = new ArrayList<>();
        PigCard pigCard; // = new PigCard();
        for(int i=0; i<20; i++){
            pigCard = new PigCard("SBx123"+i, "母猪", "大约克", "3胎", "2017-03-26", "LHY02栋0"+i*3, "怀孕待产","饲养员：王2晶晶");
            pigCardList.add(pigCard);
        }
        return pigCardList;
    }// getPigCarList



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

        //generate the pig cards list
        RecyclerView cardParentRecycleView = (RecyclerView) findViewById(R.id.pig_cards_list_RecyclerView);
        List<PigCard> pigCardList = getPigCardList(); // get the data of the pig card
        PigCardAadpter adpter = new PigCardAadpter(pigCardList, R.layout.pig_card_outline_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardParentRecycleView.setLayoutManager(layoutManager); //set layout parameters
        cardParentRecycleView.setAdapter(adpter);


     /*   //spinner selector
        List<String> spinnerList = new ArrayList<>();
        String[] spinnerItems = {"王晶晶", "王二精", "2晶晶", "6各日", "晶晶王"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        for(String item : spinnerItems) spinnerList.add(item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(adapter);
*/
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
