/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    /*
            注意：這是第1個用到的class，接下來用到AddLesson.class
       */


    //建立ListView顯示課程，list儲存課程資訊
    ListView listView;
    public static List<HashMap<String , String>> list = new ArrayList<>();//宣告static才能在其他class裡改動



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("進來了","第1個class：MainActivity");//設置Log，方便在debug模式顯示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //指向ListView
        listView = (ListView) findViewById(R.id.listView);

        //自訂ListAdapter指向task_layout
        ListAdapter listAdapter = new SimpleAdapter(
                this,
                list,
                R.layout.task_layout,
                new String[]{"name","day","time"} ,
                new int[]{R.id.lesson_name ,R.id.lesson_day,R.id.lesson_time});
        // 5個參數 : context , List , layout , 三個HashMap的key, 三個task_layout的欄位（對應前面的Key）


        listView.setAdapter(listAdapter);

        //設定FloatingButton指向AddLesson
        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTaskIntent = new Intent(MainActivity.this, AddLesson.class);
                startActivity(addTaskIntent);//從這裡指向AddLesson.class
            }
        });

    }


}

