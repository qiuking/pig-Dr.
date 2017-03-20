package com.ustcerqiu.pigdoc;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StatusDetailActivity extends BaseClass {
    //属性区域
    //mCom.mRateBarData barData;
    //

///////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //定义启动办法,和所需要的数据，方便其它程序转调;
    public static void actionStart(Context context, String titleName){
        Intent intent = new Intent(context, StatusDetailActivity.class);
        intent.putExtra("titleName", titleName); //传入标题，必须
        //intent.putExtra("parcelableItemList", parcelableItemList); //传入对应项的item相关数据
        context.startActivity(intent);
    }

    //定义获取单个图的数据的方法
    private List<mCom.mRateBarData> getBarDataList(){
        List<mCom.mRateBarData> dataList = new ArrayList<>();
        mCom.mRateBarData barData;
        //TODO
        int num = 6;
        for(int i=0; i<num+1; i++){
            barData = new mCom.mRateBarData("胎龄"+i+"年", ((int)(Math.random()*1001))/1000.0, "王二晶晶"  );
            dataList.add(barData);
        }
        return dataList;
    }//getBarDataList

    //定义获取 多个图 的数据方法
    private List<mCom.HorizontalRateBar> getBarPicDataList(){
        List<mCom.HorizontalRateBar> picList = new ArrayList<>();
        //TODO
        List<mCom.mRateBarData> barDataList;
        mCom.HorizontalRateBar pic;
        for(int i=0; i<2; i++){
            barDataList = getBarDataList(); //获取一张图
            pic = new mCom.HorizontalRateBar( barDataList, "养殖场编号-"+i, true ); //true显示标题
            picList.add(pic);
        }
        return picList ;
    } //getBarPicDataList

    //get the data of a single table
    private mCom.mTablePic getSingleTableData(){
        //TODO
        //mTablePic have parameters, they are title showTitle titleRow showTitleRow totalRow show totalRow tableDataList addAnimation
        int columnNum =(int) (Math.random()*15)+2; //generate the number of column
        mCom.mTablePic tablePic = new mCom.mTablePic(); //new instance
        tablePic.setTitle("the random title"+(int)(Math.random()*15), true ); //show title;
        List<String> row = new ArrayList<>();
        for(int i=0; i<columnNum; i++){
            row.add("第"+i+"列");
        }
        tablePic.setTitleRow(row, true); //set title row

        row = new ArrayList<>();
        for(int i=0; i<columnNum; i++){
            row.add("总"+i);
        }
        tablePic.setTotalRow(row, true); // set total row

        int rowNum = (int) (Math.random()*10)+1;
        List<List<String>> tableData = new ArrayList<>();
        for(int i=0; i<rowNum; i++){
            row = new ArrayList<>();
            for(int j=0; j<columnNum; j++){
                row.add(String.format("%3.1f",Math.random()*100));
            }
            tableData.add(row);
        }//for
        tablePic.setTableDataList(tableData);
        tablePic.setAddAnimation(true); //使用动画
        return tablePic;
    }//getSingleTableData

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


        //处理中间栏目
        //单个图加入
       // List<mCom.mRateBarData> barDataList;
      //  barDataList = getBarDataList(); //获取数据
      //  LinearLayout barGroup = (LinearLayout) findViewById(R.id.collect_bars_linear_layout);
     //   mCom.insertRateBars(barGroup, barDataList, 4, 2, true); //要接收插入的组件，所需数据，name一行字汉子数，info栏目的显示宽度（汉子数目）

        // 一组图加入
        LinearLayout picTableGroup = (LinearLayout) findViewById(R.id.group_pics_tables);
        List<mCom.HorizontalRateBar> picList = getBarPicDataList();
        for (mCom.HorizontalRateBar pic : picList){
            pic.insertInto(picTableGroup, true); //使用动画true
        }

        //insert tables
        for(int i=0; i<2; i++){ //control the number of the tables
            getSingleTableData().insertTableTo(picTableGroup);   //sample of how to use the table insert method
        }







    }//onCreate



}//end activity
