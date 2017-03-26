package com.ustcerqiu.pigdoc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by ustcerQ on 2017/3/12.
 * 此方法用以纪录全局通用的方法和类
 */

public class mCom {
    //属性定义区


//////////////////////////////////////////////////////

//################# 功能块1-0 ####################
    //定义view的点击事件选择，基于内部的 ParcelableItem 类
    //基于item内部的 imageId funcName funcParams来设置执行不同的方法或活动；活动区分基于 inputList的种类
    //view通过点击事件确认被点击的view，对应的数据为item
    static public void doClickFunc(View v, ParcelableItem item){
        String[] funcP = item.getFuncParams(); //第一项为functype，用以识别不同的功能；
        String type = funcP[0];
        Context context = v.getContext(); //执行上下文
        String[] funcList = context.getResources().getStringArray(R.array.detai_func_type_array); //这行如此写，是为了让引用 和 标记的string不会出现拼写错误
        //不好引用常量，所以用if做
        if( type.equals( funcList[ 0 ] )){ //第一行功能类型标记
            //自定义的活动等操作内容，用以替换下面的信息显示
            Toast.makeText(context, "暂无权限0：" + item.getName(), Toast.LENGTH_SHORT).show();
        }else if( type.equals( funcList[ 1 ] )){
            //TODO
            Toast.makeText(context, "暂无权限1：" + item.getName(), Toast.LENGTH_SHORT).show();
        }else if( type.equals( funcList[ 2 ] )) {
            //TODO
            Toast.makeText(context, "暂无权限2：" + item.getName(), Toast.LENGTH_SHORT).show();
        }
        else if( type.equals( funcList[ 3 ] )) {
            //TODO
            Toast.makeText(context, "暂无权限3：" + item.getName(), Toast.LENGTH_SHORT).show();
        }
        else if( type.equals( funcList[ 4 ] )) {
            //TODO
            Toast.makeText(context, "暂无权限4：" + item.getName(), Toast.LENGTH_SHORT).show();
        }else if( type.equals( funcList[ 5 ] )) {
            //TODO
            Toast.makeText(context, "暂无权限5：" + item.getName(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "超出功能设置，超出功能定义范围" + item.getName(), Toast.LENGTH_SHORT).show();
        } //if
    }//doClickFunc


//################# 功能块1-1 ####################
    //定义view的点击事件选择，基于funcName 和 funcParams; 其中funcParams第一项为活动类型, funcName为备选参数(名称或标记名称)
    //功能选择列表基于 通用功能列表，便于全局统一。同时取消对自定义类的依赖
    //view通过点击事件自动传入被点击的view
    static public void doClickFuncSelect(View v, String funcName, String[] funcParams){
        //funcParams//第一项为functype，用以识别不同的功能；
        String type = funcParams[0];
        Context context = v.getContext(); //执行上下文
        String[] funcList = context.getResources().getStringArray(R.array.com_detail_func_types); //这行如此写，是为了让引用 和 标记的string不会出现拼写错误
        //不好引用常量，所以用if做
        if( type.equals( funcList[ 0 ] )){
            //第一行功能类型标记, 一般认为没有动作 定义。取消点击属性
            v.setClickable(false);
            //Toast.makeText(context, "暂无权限0：" + funcName, Toast.LENGTH_SHORT).show();
        }else if( type.equals( funcList[ 1 ] )){
            //TODO
            Toast.makeText(context, "暂无权限1：" + funcName, Toast.LENGTH_SHORT).show();
        }else if( type.equals( funcList[ 2 ] )) {
            //TODO
            Toast.makeText(context, "暂无权限2：" + funcName, Toast.LENGTH_SHORT).show();
        }
        else if( type.equals( funcList[ 3 ] )) {
            //TODO
            Toast.makeText(context, "暂无权限3：" + funcName, Toast.LENGTH_SHORT).show();
        }
        else if( type.equals( funcList[ 4 ] )) {
            //TODO
            Toast.makeText(context, "暂无权限4：" + funcName, Toast.LENGTH_SHORT).show();
        }else if( type.equals( funcList[ 5 ] )) {
            //TODO
            Toast.makeText(context, "暂无权限5：" + funcName, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "超出功能设置，超出功能定义范围" + funcName, Toast.LENGTH_SHORT).show();
        } //if
    }//doClickFuncSelect

//################# 功能块2 ####################
    //定义活动之间数据的传递类，主要针对功能列表，运用背景为 点击按钮出现功能列表页，以及 点击列表项，进入不同的详细功能页
    //定义 item 各项所需的数据的类型,采用 parcelable 接口，以便对象和数组通过intent传递；
    static class ParcelableItem implements Parcelable {
        //定义各行数据所需要的数据
        private int imageId; //图片id
        private String name; //子项中功能名称
        private String[] funcParams; //子项中标记 功能类型的字符串数组，第一项是type标记，后面可选，可用以区分进一步功能页面的加载内容模板等信息
    /////////////////////////////

    //定义设置属性的构造方法; 给外部数据生成对象实例用
        public ParcelableItem(int imageId, String name, String[] funcParams){
            this.imageId = imageId;
            this.name = name;
            this.funcParams = funcParams;
        }
        //定义获取属性值方法
        public int getImageId(){return imageId;}
        public String getName(){return name;}
        public String[] getFuncParams(){return funcParams;}

        //实现parcelable的接口, 定义content描述，定义writeToParcel的流序列（网络数据不建议，无法保证连续性，此时可用serializable接口）,
        // 定义create的静态接口变量   必须
        @Override
        public int describeContents() {
            return 0;
        }

        //定义如何将数据写入 数据流 ；主要是顺序
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            //写入流的顺序, 此后需要按此顺序读出
            dest.writeInt(imageId);
            dest.writeString(name);
            //数组需要纪录数组长度，读取时候需要先根据长度定义数组指针实例；
            dest.writeInt(funcParams.length);
            dest.writeStringArray(funcParams);
        }

        //构造函数，用以将parcelable中数据读出到 实例中; 对应上面的writeToParcel顺序
        public ParcelableItem(Parcel in){
            imageId = in.readInt();
            name = in.readString();
            int length = in.readInt();  //读取数组长度
            funcParams = new String[length]; //给数组指针 赋内部项目指针
            in.readStringArray(funcParams); //读取数组， 数组需要大小。list不需要设置大小
        }

        //必须定义的 CREATOR 域，表明了创建方法
        public static final Parcelable.Creator<mCom.ParcelableItem> CREATOR = new Creator<mCom.ParcelableItem>() {
            @Override
            public mCom.ParcelableItem createFromParcel(Parcel source) {
                return new mCom.ParcelableItem(source);  //定义对象的读取建立方式，即，将数据流中数据构造实例
            }

            @Override  //此序列化的读法，适用于array list 等，内部实现就是这样的。此处我们前后文都用array是为了提高性能
            public mCom.ParcelableItem[] newArray(int size) {
                return new mCom.ParcelableItem[size];  //定义数据流数组的读取建立方式，将数据流生成 数据数组； 每个元素使用上面对象的实例创建方式；
            }
        };
    }// class ItemData


//################# 功能块3 ####################
//定义次活动所调用recyclerView的通用基础 adapter ; 主要适用于各种  数组array 或 [] 数据，
//由于所需要的数据 以及 布局 以及 替换方法和动作需要改变，布局可以作为id传入，数据需要自定义类型，
// 替换方法和动作通过写入 继承于  使用自定义类型的泛类adapter的 子类的方法实现上，来实现
    //定义 通用comAdapter ; 基于ViewHolder，内有抽象方法需要实现，且使用泛类
    static abstract public class comAdapter< T > extends RecyclerView.Adapter<ViewHolder>{
        //属性
        private T[] mItemList;  //明确使用的数据, 根据实际类型来
        private int itemLayoutId;  //明确使用的子项布局id
        private int holderInitCount = 0; //纪录创建顺序 序号
        /////////////////////////////////////////////////

        //构造函数，明确了所引用的数据和布局，相对于直接调用活动中的属性，相当于便于人阅读了;
        public comAdapter( T[] itemList, int itemLayoutId){
            mItemList = itemList;
            this.itemLayoutId = itemLayoutId;
        }

        //属性读取
        public T[] getItems(){return mItemList;}

    //定义生成view时，需要的方法，包括纪录view中的锚点;
    // view是对应的组件，holder是用此组件生成的holder
        abstract public void setOnCreateViewHolder(ViewHolder holder, View v);

    //定义view展示时，所需做的处理方法
    //holder为对应的holder，position为当前序号，item是当前所需的数据；此处信息有多余，可不全用
        abstract public void setOnBindViewHolder( ViewHolder holder, int position, T item);

        //定义创建适配器中的viewholder实例的方法; 返回实例，供adapter内部调用
        //parent即为调用方，也就是setAdapter的实例，此处应为对应的RecyclerView的布局组件
        @Override   //适配器会根据调用者尺寸，计算出需要的holder的个数，每个holder都保存有一个独立id的视图，滚动就是将不同视图内容更改，锚点基于视图
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);  //读取布局文件，生成view组件，不加入；
            ViewHolder holder = new ViewHolder(view, holderInitCount); //申明全局，使得虚拟函数可以调用 //会在每个view第一次出现时候运行。为每一个创键一次
            holderInitCount++; //count从0开始数
            //定义点击动作，此处可避免反复定义； 因为view仔滚动中动态的进入或消失，会不停调用onbindViewholder
            //int posittion = holder.getAdapterPosition(); 无法获取位置;
            setOnCreateViewHolder(holder, view);
            return holder;
        }//onCreateViewHolder

        //重构 单个item进入视野时的执行方法，也就是view在加入视图之前所做的事情
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            T item = mItemList[position]; //取出对应的数据
            setOnBindViewHolder(holder, position, item);
        }//onBindViewHolder

        @Override
        public int getItemCount() {
            return mItemList.length; //此条会反复执行？？？？
        }
    }//class mAdapter
    //定义ViewHolder，用于此处通用的adapter
    static public class ViewHolder extends RecyclerView.ViewHolder{
        private int init_count;
        public ViewHolder(View v, int init_count){
            super(v);
            this.init_count = init_count;
        }
        public int getInitPosition(){return init_count;}
    }// class mAdapter


//################# 功能块4 ####################
//定义次活动所调用recyclerView的通用基础 adapter ; 主要适用于各种 List 列表数据，
//由于所需要的数据 以及 布局 以及 替换方法和动作需要改变，布局可以作为id传入，数据需要自定义类型，
// 替换方法和动作通过写入 继承于  使用自定义类型的泛类adapter的 子类的方法实现上，来实现
    //定义 通用comListAdapter ; 基于基于ViewHolder，内有抽象方法需要实现，且使用泛类
    static abstract public class comListAdapter< T > extends RecyclerView.Adapter<ViewHolder>{
        //属性
        private List<T> mItemList;  //明确使用的数据, 根据实际类型来
        private int itemLayoutId;  //明确使用的子项布局id
        private int holderInitCount = 0; //纪录创建顺序 序号
        /////////////////////////////////////////////////

        //构造函数，明确了所引用的数据和布局，相对于直接调用活动中的属性，相当于便于人阅读了;
        public comListAdapter( List<T> itemList, int itemLayoutId){
            mItemList = itemList;
            this.itemLayoutId = itemLayoutId;
        }

        //属性读取
        public List<T> getItems(){return mItemList;}

        //定义生成view时，需要的方法，包括纪录view中的锚点;
        // view是对应的组件，holder是用此组件生成的holder
        abstract public void setOnCreateViewHolder(ViewHolder holder, View v);

        //定义view展示时，所需做的处理方法
        //holder为对应的holder，position为当前序号，item是当前所需的数据；此处信息有多余，可不全用
        abstract public void setOnBindViewHolder( ViewHolder holder, int position, T item);

        //定义创建适配器中的viewholder实例的方法; 返回实例，供adapter内部调用
        //parent即为调用方，也就是setAdapter的实例，此处应为对应的RecyclerView的布局组件
        @Override   //适配器会根据调用者尺寸，计算出需要的holder的个数，每个holder都保存有一个独立id的视图，滚动就是将不同视图内容更改，锚点基于视图
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);  //读取布局文件，生成view组件，不加入；
            ViewHolder holder = new ViewHolder(view, holderInitCount); //申明全局，使得虚拟函数可以调用 //会在每个view第一次出现时候运行。为每一个创键一次
            holderInitCount++; //count从0开始数
            //定义点击动作，此处可避免反复定义； 因为view仔滚动中动态的进入或消失，会不停调用onbindViewholder
            //int posittion = holder.getAdapterPosition(); 无法获取位置;
            setOnCreateViewHolder(holder, view);
            return holder;
        }//onCreateViewHolder

        //重构 单个item进入视野时的执行方法，也就是view在加入视图之前所做的事情
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            T item = mItemList.get(position); //取出对应的数据
            setOnBindViewHolder(holder, position, item);
        }//onBindViewHolder

        @Override
        public int getItemCount() {
            return mItemList.size(); //此条会反复执行,每次有数据进出都会执行很多遍，当mItemList一直在增加时候，就有用了。
        }
    }//class mListAdapter


//################# 功能块5 ####################
//定义一种标准的横向进度比例条 竖排列表的方法
//传入参数 所要填入的布局veiwGroup, 数据列表、名称一行字符数，数据最后一个信息的显示字符宽度（<5), 以及是否使用动画。
// 其中数据列表中单项数据必须包含 名称，进度比例，进度说明文字
    static public void insertRateBars(ViewGroup parent,  List<mRateBarData> rateBarDataList, int nameLengthNm, int infoLengNm, Boolean addAnimation){
        View view; //每个生成的view都会有一个id，更改时候需要几个条子，就得几个view？测试
        TextView text;
        ImageView image;
        ClipDrawable clipDrawable;
        float scale = parent.getContext().getResources().getDisplayMetrics().density; //dp与px的换算系数
        if(infoLengNm > 7) infoLengNm = 7; //设置最多显示7个汉子字符
        int lengthPx = (int)(infoLengNm * scale * 14 +3*scale+ 0.5f); //每个字符给予 14dp宽度; 需要显示的宽度字符
        if(nameLengthNm > 10) nameLengthNm = 10;
        int nameLengthPx = (int)(nameLengthNm * scale * 14+3*scale + 0.5f);
        //获取view的布局参数，参数中有长宽属性
        LinearLayout.LayoutParams layoutParams;

        int rateNum;
        int timePart = 2000/rateBarDataList.size();
        int i=0;
        for( mRateBarData barData : rateBarDataList){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rate_bar_item_layout, parent, false);
            text = (TextView) view.findViewById(R.id.bar_name);
            text.setText(barData.name);
            //设置 name 的显示宽度
            //获取view的布局参数，参数中有长宽属性
            layoutParams = (LinearLayout.LayoutParams) text.getLayoutParams();//取控件textView当前的布局参数
            layoutParams.width = nameLengthPx;
            text.setLayoutParams(layoutParams);
            rateNum = (int) (barData.rate * 10000);
            image = (ImageView) view.findViewById(R.id.bar_rate);
            clipDrawable = (ClipDrawable) image.getBackground();   //一定要擦背景吗？ 擦写前端是否可以? no
            clipDrawable.setLevel(rateNum+5); //避免不出颜色
            text = (TextView) view.findViewById(R.id.bar_rate_percent);
            text.setText( String.format(Locale.getDefault()," %.1f%%", barData.rate*100).toUpperCase() ); //按当地习惯使用字母
            text = (TextView) view.findViewById(R.id.bar_info);
            text.setText(barData.info);
            //设置info的显示宽度
            //获取view的布局参数，参数中有长宽属性
            layoutParams = (LinearLayout.LayoutParams) text.getLayoutParams();//取控件textView当前的布局参数
            layoutParams.width = lengthPx;
            text.setLayoutParams(layoutParams);
            if(addAnimation){
                //加入parent，并执行动画
                itemAnimation(parent, view ,i*timePart);
            }else{
                parent.addView(view);
            }
             i++; //用以动画延时参数
        }//for

    }//insertRateBars
    public static void itemAnimation(final ViewGroup parent, final View v, int startOffsetMs){
        //添加动画
        Handler handler = new Handler();
        //AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(1000); //时间
        translateAnimation.setStartOffset(startOffsetMs);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        parent.addView(v);
        v.startAnimation(translateAnimation);  //只有一个动画，所以就简单处理了
        //animationSet.addAnimation(translateAnimation);
        //final TranslateAnimation animation = translateAnimation;
        /*Runnable runnable =new Runnable() {
            @Override
            public void run() {
                parent.addView(v);
                v.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, durationMs); */
    }//

    //定义所需横向柱状条的单个的 数据类
    static public class mRateBarData{
        String name;
        double rate;  //默认小数为双精度
        String info;
        public mRateBarData(String name, double rate, String info){  //构造方法
            this.name = name;
            this.rate = rate;
            this.info = info;
        }
    }//class mRateData



//################# 功能块6 ####################
//定义一 类，该类的实例 内部包含一个横向柱状图的 mRateBarData数据的list，并包含有插入该图的方法
//具体属性为：mRateBarData的name, rate, info, 还有 title 是否显示title的是showTitle
// 具体属方法为：
    static public class HorizontalRateBar{
        //定义每一条数据所需要的参数 其余与参数与父类相同
        String title;  //表格标题
        Boolean showTitle = false;
        List<mRateBarData> barDataList;
        int maxNameWordsLength; //最长的行名称的长度，以中文字符长度计算，不足进1
        int maxInfoWordsLength; //最长的信息名的长度

        //构造函数
        public HorizontalRateBar(List<mRateBarData> ibarDataList, String title, Boolean showTitle){
            this.title = title;
            this.showTitle = showTitle;
            this.barDataList = ibarDataList;
            int maxlen1 = 0;
            int maxlen2 =0;
            int temp;
            for(mRateBarData data : barDataList){
                temp = getWordsLength( data.name );
                if(temp > maxlen1) maxlen1 = temp;
                temp = getWordsLength(data.info);
                if(temp > maxlen2) maxlen2 = temp;
            }
            maxNameWordsLength = maxlen1;
            maxInfoWordsLength = maxlen2;
        }

        //设置标题, 和是否显示标题,单独更改，需要直接访问属性值
        public void setTitle(String title, Boolean isShowTitle){
            this.title = title;
            showTitle = isShowTitle;
        }

        //设置方法，来显示多个图表，需要两个layout为基础，一个是包含框，一个是每个图表的包含框
        public void insertInto(ViewGroup parent, Boolean addAnimation){
            //读入包含整个图表的布局
            View chartView = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_pic_of_bars, parent, false);
            LinearLayout picLayout = (LinearLayout) chartView.findViewById(R.id.part_pic_of_bars_linearlayout); //用以包含整个图表的布局
            TextView titleText = (TextView) picLayout.findViewById(R.id.part_title);
            if(showTitle){
                titleText.setText(title);
            }else{
                titleText.setVisibility(View.GONE); //完全不显示,位置也不留
            }
            parent.addView(picLayout); //插入整张图进入，
            insertRateBars(picLayout, barDataList, maxNameWordsLength, maxInfoWordsLength, addAnimation); //将柱状图插入绘图区域， name一行字符，info一行的字符
        } //insertInto
    }//end class HorizentalBar

    //计算字符串的字符长度，中文2，英文1，
    public static int getWordsLength(String s) {
        int length = 0;
        String chinese = "[\u4e00-\u9fa5]";
        String temp;
        for (char c : s.toCharArray()) {
            temp = "" + c; //String.valueOf(c)
            if (temp.matches(chinese)) {
                length += 2;
            } else {
                length++;
            }
        }//for
        return (int) Math.ceil(length/2.0); //进位取整数
    }


//################# 功能块7 ####################
//定义一 类，该类的实例 内部包含一个的表格数据，并包含有插入不同数目的表格的方法
//具体属性为：, 还有 title ，标题行，汇总行，是否显示title的是showTitle, 是否使用动画
//是否显示 标题行的 showTitleLine 是否显示 汇总行的 showTotal, total行的数局需要后台设置，前台并不计算
    static public class mTablePic {
        String title; //表格大标题
        Boolean showTitle = false; //是否显示标题
        private List<String> titleRow; //表格的标题行
        Boolean showTitleRow = false; //是否显示titleRow行，各行的标题
        List<String> totalRow; //汇总行，位于表格数局第一行
        Boolean showTotalRow = false; //是否显示 汇总 行
        List<List<String>> tableDataList; //每一行是一个数组ar，所有行组成一个表格数据list
        Boolean addAnimation = false; //是否显示动画，默认不显示
    ////////////////////////////////////////////////////////////
        //不用特殊的构造函数
        //设置大标题
        public void setTitle(String title, Boolean showOrNot){
            this.title = title;
            this.showTitle = showOrNot;
        }
        //设置标题行
        public void setTitleRow(List<String> titleRow, Boolean showOrNot){
            this.titleRow = titleRow;
            this.showTitleRow = showOrNot;
        }
        //设置汇总行
        public void setTotalRow(List<String> totalRow, Boolean showOrNot){
            this.totalRow = totalRow;
            this.showTotalRow = showOrNot;
        }
        //设置内部数据
        public void setTableDataList(List<List<String>> tableDataList ){
            this.tableDataList = tableDataList;
        }
        //设置是否需要动画
        public void setAddAnimation(Boolean addOrNot){
            this.addAnimation = addOrNot;
        }
    ////////////////////////////////////////////////////////////////////////////////
        //将每行数据生成表格的标题行或汇总行 type为标识是 name行（0），还是 汇总行（1）,普通行(2)；两者处理方式略有不同
        public void insertIntoRow(ViewGroup parentRow, List<String> dataList,  int type ){
            //用数据生成表格的各个 表格单元，然后插入 行控件中
            View cellView;
            TextView text;
            LinearLayout cellLayout;
            Context context = parentRow.getContext();
            float scale = context.getResources().getDisplayMetrics().density; //dp与px的换算系数
            //如果是标题行，则行高度要重新设置
            if( type == 0 ) parentRow.setMinimumHeight((int)(28*scale));
            int i = 0;
            //加入各个单元格
            for (String data : dataList){
                cellView = LayoutInflater.from(parentRow.getContext()).inflate(R.layout.part_table_cell, parentRow, false ); //插入视图到parent,?先插入再改是否可以？
                text = (TextView) cellView.findViewById(R.id.part_table_cell_text);
                cellLayout = (LinearLayout) cellView.findViewById(R.id.part_table_cell);
                text.setText(data); //输入数据
                if(type == 0 || type == 1){  //倘若是菜单栏
                    //更改背景色，更改文字颜色
                    cellLayout.setBackgroundColor(Color.parseColor("#e68901"));
                    text.setTextColor(Color.parseColor("#000000"));  //error  if input #fff
                }
                if(i==0) {
                    //获取view的布局参数，更改对应值后，返回。用于设置第一列显示1.5倍宽度，默认为2
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cellLayout.getLayoutParams();//取控件textView当前的布局参数
                    layoutParams.weight = 3;
                    cellLayout.setLayoutParams(layoutParams);
                }
                parentRow.addView(cellView);
                i++; //用以动画延时参数
            }//for
        }// insert intoRow

        //use the data inthe instance to create the table pic and then insert it into the collector parent.
        public void insertTableTo(ViewGroup parent){
            //create the whole table, and insert it to the viewGroup parent
            //load the layout of the whole table (share the layout files with part_pic_of_bars
            LinearLayout rowLayout;
            Context context = parent.getContext();
            View tableView = LayoutInflater.from(context).inflate(R.layout.part_pic_of_bars,parent, false); // first insert into the layout, then change the content ?
            TextView tableTitle = (TextView) tableView.findViewById(R.id.part_title);
            LinearLayout picParent = (LinearLayout) tableView.findViewById(R.id.part_pic_of_bars_linearlayout);
            // deal with the table tile
            if(showTitle) {
                tableTitle.setText(this.title);
            }else{
                tableTitle.setVisibility(View.GONE);
            }
            //if show the title row , create the title row, other wise do nothing
            if(showTitleRow){
                View titleRowView = LayoutInflater.from(context).inflate(R.layout.part_table_row_collector_linearlayout, picParent, false);
                rowLayout = (LinearLayout) titleRowView.findViewById(R.id.part_table_row);
                insertIntoRow(rowLayout, this.titleRow, 0);
                picParent.addView(titleRowView);
            }
            // if show the total row, create the total row, otherwise do nothing.
            if(showTotalRow){
                View titleRowView = LayoutInflater.from(context).inflate(R.layout.part_table_row_collector_linearlayout, picParent, false);
                rowLayout = (LinearLayout) titleRowView.findViewById(R.id.part_table_row);
                insertIntoRow(rowLayout, this.totalRow, 1);
                picParent.addView(titleRowView);
            }
            //create the normal rows and insert them into the collector parent
            int i = 0;
            int timePart = 2000/this.tableDataList.size();
            for(List<String> rowData : tableDataList){
                View titleRowView = LayoutInflater.from(context).inflate(R.layout.part_table_row_collector_linearlayout, picParent, false);
                rowLayout = (LinearLayout) titleRowView.findViewById(R.id.part_table_row);
                insertIntoRow(rowLayout, rowData, 2);
                if(addAnimation){
                    itemAnimation(picParent, titleRowView ,i*timePart);
                }else{
                    picParent.addView(titleRowView);
                }
                i++;
            }
            parent.addView(tableView);
        } //insertTableTo

    //定义，将多个表插入到图表图中
    //this method is not defined here. when need to insert some table list, u can use the method insertTableTo in the for loop
    }// class mTablePic



//################# 功能块8 ####################
//定义一method，输出一个Dialog dialog，实现弹出底部滚动选择对话框的功能
    //displayValues 用以替换默认的选项数字，选择结果仍是数字，但界面显示为设置的字符串
    //defaultPostion 用以标识打开时候定位所在，默认所处的位置
    static public Dialog generateBottomNumpickerDialog(Activity activity, String[] displayValues, int defaultPosition){
        Dialog dialog = new Dialog(activity, R.style.mDialogTheme); // generate the dialog theme
        dialog.setContentView(R.layout.dialog_1);  //set layout file
        dialog.setCanceledOnTouchOutside(false);  //can not be canceled by click on outside space
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        layoutParams.width = dm.widthPixels;
        dialogWindow.setAttributes(layoutParams);
        dialogWindow.getDecorView().setPadding(0,0,0,0); //占满控件
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM); //布局位置

    /*    NumberPicker valuePicker = (NumberPicker) dialogWindow.findViewById(R.id.num_picker);  //注意在dialogWindow中找，否则找不到出错
        valuePicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return ""+value;
            }
        });
        valuePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //TODO
            }
        });
        valuePicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
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
        });
        valuePicker.setDisplayedValues(displayValues);
        valuePicker.setMinValue(0);
        valuePicker.setMaxValue(displayValues.length-1);
        valuePicker.setValue(defaultPosition);
        valuePicker.setWrapSelectorWheel(false);  //是否循环
        valuePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //避免点击后可修改状态
        //dialog.cancel();
        */

        NumberPickerView valuePicker = (NumberPickerView) dialogWindow.findViewById(R.id.number_picker_view);
        valuePicker.setDisplayedValues(displayValues);
        valuePicker.setMinValue(0);
        valuePicker.setMaxValue(displayValues.length-1);
        valuePicker.setValue(0);


        return dialog;
    }// generateBottomNumpickerDialog





}//end mCom
