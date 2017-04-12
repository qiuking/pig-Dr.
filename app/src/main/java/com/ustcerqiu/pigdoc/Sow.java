package com.ustcerqiu.pigdoc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by kkwang on 3/11/2017.
 */

public class Sow {
    private String id;
    private String ear_tag;
    private String ear_lack;
    private String dormitory;
    private String category;
    private String gestational_age;
    private String accum_return;
    private String state;
    private String state_day;
    private String birthday;
    private String entryday;
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getEarTag(){
        return ear_tag;
    }
    public void setEarTag(String ear_tag){
        this.ear_tag = ear_tag;
    }
    public String getEarLack(){
        return ear_lack;
    }
    public void setEarLack(String ear_lack){
        this.ear_lack = ear_lack;
    }
    public String getDormitory(){
        return dormitory;
    }
    public void setDormitory(String dormitory){
        this.dormitory = dormitory;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getGestationalAge(){
        return gestational_age;
    }
    public void setGestationalAge(String gestational_age){
        this.gestational_age = gestational_age;
    }
    public String getAccumReturn(){
        return accum_return;
    }
    public void setAccumReturn(String accum_return){
        this.accum_return = accum_return;
    }
    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getStateDay(){
        return state_day;
    }
    public void setStateDay(String state_day){
        this.state_day = state_day;
    }
    public String getBirthday(){
        return birthday;
    }
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public String getEntryday(){
        return entryday;
    }
    public void setEntryday(String entryday){
        this.entryday = entryday;
    }


    //sow与json数据间的转化
    //jsonData是一个纯的json数组，并且里面的key和Sow中属性名称相同；
    static public List<Sow> jsonToSowList(String jsonData){
        Gson gson = new Gson();
        List<Sow> SowList = null;
        try{
            SowList = gson.fromJson(jsonData, new TypeToken<List<Sow>>(){}.getType());
        }catch (Exception err){
            err.printStackTrace();
        }
        return SowList;
    } //jsonToSowList



}//sow
