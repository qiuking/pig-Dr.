package com.ustcerqiu.pigdoc;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatusDetailActivity extends BaseMinorClass{
    //属性区域
    //mCom.mRateBarData barData;
    //
    private List<mCom.mRateBarData> dataList;
    private List<List<String>> tableData;
    private int total_count;
///////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //定义启动办法,和所需要的数据，方便其它程序转调;
    public static void actionStart(Context context, String titleName){
        Intent intent = new Intent(context, StatusDetailActivity.class);
        intent.putExtra("titleName", titleName); //传入标题，必须
        //intent.putExtra("parcelableItemList", parcelableItemList); //传入对应项的item相关数据
        context.startActivity(intent);
    }


    //定义获取 多个图 的数据方法
    private List<mCom.HorizontalRateBar> getBarPicDataList(){
        List<mCom.HorizontalRateBar> picList = new ArrayList<>();
        mCom.HorizontalRateBar pic;
        pic = new mCom.HorizontalRateBar( dataList, "母猪胎龄结构", true ); //true显示标题
        picList.add(pic);
        return picList ;
    } //getBarPicDataList

    //get the data of a single table
    private mCom.mTablePic getSingleTableData(){
        //TODO
        //mTablePic have parameters, they are title showTitle titleRow showTitleRow totalRow show totalRow tableDataList addAnimation
        List<String> column_title = new ArrayList<>();
        column_title.add("胎龄");
        column_title.add("数量");
        column_title.add("实际比例%");
        int columnNum = column_title.size(); //generate the number of column

        mCom.mTablePic tablePic = new mCom.mTablePic(); //new instance
        tablePic.setTitle("母猪胎龄结构表", true ); //show title;
        List<String> row = new ArrayList<>();
        for(int i=0; i<columnNum; i++){
            row.add(column_title.get(i));
        }
        tablePic.setTitleRow(row, true); //set title row


        row = new ArrayList<>();
        row.add("全部");
        row.add(String.valueOf(total_count));
        row.add("100");
        tablePic.setTotalRow(row, true); // set total row


        tablePic.setTableDataList(tableData);
        tablePic.setAddAnimation(true); //使用动画
        return tablePic;
    }//getSingleTableData


    //parse the data from internet to dataList
    private void parseGestationalAge(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String gestational_age_reports = jsonObject.getString("gestational_age_reports");
            JSONArray jsonArray = new JSONArray(gestational_age_reports);
            dataList = new ArrayList<>();
            mCom.mRateBarData barData;
            tableData = new ArrayList<>();
            total_count = 0;
            for(int i=0;i<jsonArray.length();i++)
            {
                List<String> row = new ArrayList<>();
                jsonObject = jsonArray.getJSONObject(i);
                int gestational_age = jsonObject.getInt("gestational_age");
                int count = jsonObject.getInt("count");
                double rate = jsonObject.getDouble("rate");
                barData = new mCom.mRateBarData("胎龄" + gestational_age + "胎", rate, "");
                dataList.add(barData);
                row.add(gestational_age+"胎");
                row.add(String.valueOf(count));
                row.add(String.format("%.2f",rate*100));
                total_count+=count;
                tableData.add(row);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }//


    //show the results to view
    private void showResponse(final String response, final LinearLayout picTableGroup){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseGestationalAge(response);
                // 一组图加入
                // LinearLayout picTableGroup;
                List<mCom.HorizontalRateBar> picList = getBarPicDataList();
                for (mCom.HorizontalRateBar pic : picList) {
                    pic.insertInto(picTableGroup, true); //使用动画true
                }
                getSingleTableData().insertTableTo(picTableGroup);
            }
        });
    }//

 ///--##############################################################################-----/////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);

        String titleName = "猪博士";
        //Intent中信息处理
        Intent intent = getIntent(); //getIntent 用以取得启动此活动的intent
        if(intent != null){ //intent中有数据; 如不符合一下数据类型，就直接bug了；
            titleName = intent.getStringExtra("titleName");
        }//if  intent

        //设置标题行的显示标题，替换掉layout中的默认标题
        ((TextView) this.findViewById(R.id.title_name_textView)).setText(titleName);  //this仅仅是强调本活动的调用环境
        //设置返回键的操作
        (findViewById(R.id.button_back)).setOnClickListener(new View.OnClickListener() {  //无需限制view的具体形式，所以没有强制类型转化
            @Override
            public void onClick(View v) {
                //定义返回前的操作，返回数据到前一活动用 setResult方法
                finish(); //
                overridePendingTransition(R.anim.translate_in_back, R.anim.translate_out_back);  //添加返回切换动画
            }
        });

        //处理中间部分


        String address = "http://180.76.148.62:8888/v1/gestational_age_reports";
        HttpUtil.sendHttpRequest(address, new com.ustcerqiu.pigdoc.HttpCallbackListener() {
            @Override
            public void onFinish(String response) { //主线程中运行
                LinearLayout picTableGroup = (LinearLayout) findViewById(R.id.group_pics_tables);
                showResponse(response, picTableGroup);//
            }

            @Override
            public void onError(Exception e) {

            }
        });




    }//onCreate


}//end activity
