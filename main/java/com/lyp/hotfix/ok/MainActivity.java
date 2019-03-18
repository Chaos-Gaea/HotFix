package com.lyp.hotfix.ok;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lyp.hotfix.R;
import com.lyp.hotfix.Replace;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private ListView mListview;
    private ArrayList<String> mCitys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListview = findViewById(R.id.listview);
    }
    @Replace(clasz = "com.lyp.hotfix.MainActivity",method = "requestData")

    public void requestData(String s) {

        if (s.contains("sf")){
            mCitys = new ArrayList<>();
            mCitys.add("顺丰接口关闭 十分抱歉 ");

            Log.e("000000000", "requestData: "+"我是修复好的方法");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mCitys);
        mListview.setAdapter(adapter);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
}
