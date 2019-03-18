package com.lyp.hotfix;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    // Used to load the 'native-lib' library on application startup.

    private ListView mListview;
    private Button mLoad;
    private Button mFix;
    private ArrayList<String> mCitys;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mListview = findViewById(R.id.listview);
        mLoad = findViewById(R.id.btn1);

        mFix = findViewById(R.id.btn2);

        mLoad.setOnClickListener(this);
        mFix.setOnClickListener(this);

    }

    @Replace(clasz = "com.lyp.hotfix.MainActivity",method = "requestData")

    public void requestData(String s) {

        if (!s.equals("sf")){
            mCitys = new ArrayList<>();
            mCitys.add("顺丰接口 不处理");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  mListview.setVisibility(View.GONE);
                }
            },4000);
        }

        mCitys = new ArrayList<>();
        mCitys.add("包裹正在派送,请稍后 ");
        mCitys.add("包裹已经抵大北京");
        mCitys.add("快递已经由天津中转站发往北京");
        mCitys.add("青花堂已经发货");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mCitys);
        mListview.setAdapter(adapter);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    File sdDir = null;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                DexManager dexManager = new DexManager(this);
                dexManager.loadDex(getSDPath());
                Log.e(TAG, "===================="+getSDPath().toString() );
                Toast.makeText(this, "加载Dex", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                requestData("sf");
                Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onClick: "+"===========" );
                break;
                default:
                    break;

        }


    }
    public File getSDPath(){

        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);  //判断sd卡是否存在
        if  (sdCardExist)
        {
            sdDir = new File(Environment.getDataDirectory() ,"out.dex");//获取跟目录
        }

            return sdDir;
    }
}
