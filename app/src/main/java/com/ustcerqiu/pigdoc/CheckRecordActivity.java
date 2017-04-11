package com.ustcerqiu.pigdoc;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

//this activity deals with the intents of check the record date
public class CheckRecordActivity extends BaseMinorClass implements View.OnClickListener {
    //属性区域
    int touchX0;
    int touchY0;
    boolean scrollToBottom = false;
    boolean isLoading = false;
    int oldMotionEnevtState;
    private List<PigCard> pigCardList;
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
            case R.id.group_selected: //select the group
                final TextView selectedGroup = (TextView) findViewById(R.id.group_selected_text);
                final int nowId = 0;
                final String[] dpValues = {"全  部","怀孕","哺乳","断奶空怀","反情空怀","B超鉴定空怀","流产空怀","后备母猪","后备第一次反情","后备第二次反情","公猪","已离场公猪"};
                selectedGroup.setText(dpValues[nowId]);
                mCom.mDialog mDialog = mCom.generateBottomNumberPickerDialog(this, dpValues, nowId , new mCom.mDialogReturnListener(){
                    @Override
                    public void setNegativeButton(mCom.mDialog dialog, NumberPickerView pickerView) {
                    }//do nothing special

                    @Override
                    public void setPositiveButton(mCom.mDialog dialog, NumberPickerView pickerView) {
                        int selectedId = pickerView.getValue();
                        if(selectedId == nowId ) return; //没有改变，就不变?????
                        selectedGroup.setText(dpValues[selectedId]);
                    }
                } );
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
    public class PigCardAdapter extends mCom.comListAdapter<PigCard>{
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
        public PigCardAdapter(List<PigCard> pigCardList, int pigCardLayoutId){
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

        //set the search icons
        LinearLayout selectGroupIcon = (LinearLayout) findViewById(R.id.group_selected);
        selectGroupIcon.setOnClickListener(this);

        //setOnClickListener (whole page left)
        (findViewById(R.id.button_back)).setOnClickListener(this);

        String address = "http://180.76.148.62:8888/v1/sows";
        HttpUtil.sendHttpRequest(address, new com.ustcerqiu.pigdoc.HttpCallbackListener() {
            @Override
            public void onFinish(String response) { //主线程中运行
                showResponse(response);//
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }//onCreate


    // parse sow
    private void parseSow(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String sow_info = jsonObject.getString("sows");
           JSONArray jsonArray = new JSONArray(sow_info);
            Gson gson = new Gson();
            pigCardList = new ArrayList<>();
            PigCard pigCard; // = new PigCard();
            for(int i=0;i<jsonArray.length();i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Sow sow = gson.fromJson(jsonObject.toString(), Sow.class);
                pigCard = new PigCard(sow.getEarTag(), "母猪", sow.getCategory(), sow.getGestationalAge(), sow.getBirthday(), sow.getDormitory(), sow.getState(),"无");
                pigCardList.add(pigCard); }
          //  Gson gson = new Gson();
          //  List<Sow> sowList = gson.fromJson(sow_info, new TypeToken<List<Sow>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
    }//parse sow

    // update UI
    private void updateUI()
    {
        //hide the loading progress
        FrameLayout waitLoading = (FrameLayout) findViewById(R.id.wait_loading);
        waitLoading.setVisibility(View.GONE); //hide
        //update the pig cards list
        final RecyclerView cardParentRecycleView = (RecyclerView) findViewById(R.id.pig_cards_list_RecyclerView);
        PigCardAdapter adapter = new PigCardAdapter(pigCardList, R.layout.pig_card_outline_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardParentRecycleView.setLayoutManager(layoutManager); //set layout parameters
        cardParentRecycleView.setAdapter(adapter);
        //add 增加动作监控
        final LinearLayout scrollFooter = (LinearLayout) findViewById(R.id.scroll_loading_footer);
        final LinearLayout footerLoadProgress = (LinearLayout) findViewById(R.id.footer_loading_progress);
        final TextView footerLoadText = (TextView) findViewById(R.id.footer_loading_content);
        final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) scrollFooter.getLayoutParams();

        cardParentRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        cardParentRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {
                Boolean result = false;
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN: //按下时候
                        if(scrollToBottom) touchY0 = (int) e.getRawY(); //取此时刻的手指触摸点
                        break;
                    case MotionEvent.ACTION_MOVE: //拖动时候
                        //若已经滚动到最下面
                        if(scrollToBottom){
                            int touchY1 = (int) e.getRawY(); //当前触摸点
                            int dy = touchY0 - touchY1;
                            //if(dy<0) dy -= 1; //下滑过程中，加快边框变化，避免出现滚动； 此法无效
                            touchY0 = touchY1; //纪录
                            int height = lp.height + dy;
                            if(height<=0){
                                height = 0;  //避免出现负数高度值
                                scrollToBottom = false; //设置未滚动到底部，恢复状态
                            }
                            lp.height = height;
                            scrollFooter.setLayoutParams(lp);
                            //设置对应字体的大小
                            float textsize = height/12.0f;
                            if(textsize>40) textsize = 40.0f;
                            footerLoadText.setTextSize(textsize);
                        }else{ //之前的上一次动作查询中，还并未到底部
                            int bottomLeft = recyclerView.computeVerticalScrollRange()- recyclerView.computeVerticalScrollExtent() - recyclerView.computeVerticalScrollOffset();
                            if(bottomLeft <= 0 ){
                                touchY0 = (int) e.getRawY(); //取此时刻的手指触摸点
                                scrollToBottom = true; //设置滚动到底部
                            }
                        }//if
                        break;
                    case MotionEvent.ACTION_UP: //抬起
                        if(lp.height>0){
                            ValueAnimator va = ValueAnimator.ofInt(lp.height, 0);
                            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    int h = (int) animation.getAnimatedValue();
                                    lp.height=h;
                                    scrollFooter.setLayoutParams(lp);
                                }
                            });
                            va.setDuration(450);
                            va.start();
                            if(lp.height>250) {
                                footerLoadProgress.setVisibility(View.VISIBLE); //高度最低限制
                                footerLoadText.setVisibility(View.GONE);
                                if(! isLoading) {
                                    //TODO 请求新数据
                                }
                            }//if

                        }//if
                        scrollToBottom = false; //设置未滚动到底部，恢复状态
                        break;
                }//switch
                return result;
            }//onInterceptTouchEvent

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }// update UI

    //show the results to view
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseSow(response);
                updateUI();
            }
        });
    }//


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Log.e("touch", ""+ ev.getRawY());
        //touchY = (int) ev.getRawY();
        return super.dispatchTouchEvent(ev);
    }
}//end
