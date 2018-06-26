package com.example.android.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.android.todolist.sync.WaterReminderFirebaseJobService;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;
import java.util.HashMap;

public class AddLesson extends AppCompatActivity{


    /*
            注意：這是第2個用到的class，接下來用到WaterReminderFirebaseJobService.class
       */
    //設置變數
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    Context context;
    PendingIntent pending_intent;
    String lesson_name,lesson_day,lesson_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("進來了","第2個class：AddLesson ");//設置Log，方便在debug模式顯示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lesson);

        //初始化下拉選單
        Spinner spinner = (Spinner)findViewById(R.id.select_lesson_day);
        final String[] lunch = {"星期一", "星期二", "星期三", "星期四","星期五","星期六","星期日"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner.setAdapter(lunchList);

        //設定下拉選單的Listener，並存回lesson_day
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lesson_day=lunch[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //把context指向this
        this.context=this;
        //初始化AlarmManager
        alarm_manager =(AlarmManager) getSystemService(ALARM_SERVICE);
        //初始化timePicker
        alarm_timepicker=(TimePicker)findViewById(R.id.timePicker);

        //初始化新增時間的Button
        final Button alarm_on=(Button) findViewById(R.id.alarm_on);

        //初始化取消的Button
        final Button cancel=(Button) findViewById(R.id.cancel);

        //把Calender實體化
        final Calendar calendar=Calendar.getInstance();



        /************** Start 「設置時間」的OnClickListener **************/
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //設定JobDispatcher
                Driver driver = new GooglePlayDriver(context);
                FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

                //初始化edit_lesson_name，並存回lesson_name
                lesson_name=((EditText) findViewById(R.id.edit_lesson_name)).getText().toString();


                //從timePicker get 分和秒的數值
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                //條件判斷，讓minute減五分鐘
                if (minute < 5){
                    minute=60+minute-5;
                    hour=hour-1;
                }else {
                    minute=minute-5;
                }
                if(hour<0){
                    hour=hour+24;
                }

                //設置calendar
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);


                //轉換成String
                String hour_string= String.valueOf(alarm_timepicker.getHour());
                String minute_string=String.valueOf(alarm_timepicker.getMinute());

                //幫minute補0
                if(alarm_timepicker.getMinute() < 10){
                    minute_string="0"+minute_string;
                }

                //把設定的時間存回lesson_time
                lesson_time=hour_string + ":" + minute_string;


                //把課程名稱、星期、時間存回MainActivity的list
                setList(lesson_name,lesson_day,lesson_time);

                /*************Start schedule這個課程*************/
                //計算「所設時間」離「目前時間」相差多少seconds
                int triggerTime =(int)calendar.getTimeInMillis()/1000-(int)Calendar.getInstance().getTimeInMillis()/1000;
                Log.e("triggerTime",String.valueOf(triggerTime));//在Debug模式用Log顯示還要多少秒才啟動通知
                Job constraintReminderJob = dispatcher.newJobBuilder()
                        .setService(WaterReminderFirebaseJobService.class)//從這裡指向 WaterReminderFirebaseJobService.class
                        .setTag(lesson_name)
                        .setLifetime(Lifetime.FOREVER)
                        .setRecurring(false)
                        .setTrigger(Trigger.executionWindow(triggerTime,triggerTime+5))
                        .setReplaceCurrent(true)

                        .build();

                dispatcher.schedule(constraintReminderJob);
                /*************End  schedule這個課程*************/

                //用Intent來回到MainActivity
                Intent goBack_intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(goBack_intent);
            }
        });
        /************** End 「設置時間」的OnClickListener **************/

        //設置「取消」的OnClickListener
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    //用setList在MainActivity的List裡新增課程資訊
    public void setList(String name,String day, String time){
        HashMap<String , String> hashMap = new HashMap<>();
        //把name , day, time存入HashMap之中
        hashMap.put("name" , name);
        hashMap.put("day" , day);
        hashMap.put("time" , time);
        //把HashMap存入list之中
        MainActivity.list.add(hashMap);


    }




}
