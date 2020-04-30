package com.playhard.studentmgr.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.playhard.studentmgr.R;
import com.playhard.studentmgr.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityPhonePlace extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ActivityPhonePlace";
    private EditText et_num;
    private Button bt_num;
    private TextView tv_num_orin;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_place);
        contentInit();
        textView = findViewById(R.id.title_text);
        textView.setText("号码归属地查询");
        bt_num.setOnClickListener(this);

    }








    private void  contentInit(){
        et_num = findViewById(R.id.et_num);
        bt_num = findViewById(R.id.bt_num);
        tv_num_orin = findViewById(R.id.tv_num_orin);
    }


//    自己实现回调机制(回调接口)
    @Override
    public void onClick(View view) {
        String num = et_num.getText().toString().trim();
        final String queryUrl = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_name=guishudi&query="+num;
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tv_num_orin.setText(msg.getData().getString("queryResult"));
                tv_num_orin.setVisibility(View.VISIBLE);
//                剪切板
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText("result", msg.getData().getString("queryResult"));
                clipboardManager.setPrimaryClip(cd);
                ToastUtil.putMessageCenter(ActivityPhonePlace.this,"已剪切");
            }
        };
        parseJSONWithJSONObject(queryUrl,handler);



    }
    private void parseJSONWithJSONObject(final String queryUrl, final Handler handler)  {
       new Thread( new Runnable() {
           @Override
           public void run() {
               try {
                   String queryResult="";
                   String city="";
                   String prov="";
                   String type = "";
                   OkHttpClient client = new OkHttpClient();
                   Request request = new Request.Builder()
                           .url(queryUrl)
                           .build();
                   Response response = client.newCall(request).execute();
                   String responseData = response.body().string();
                   JSONObject jsonObject = new JSONObject(responseData);
                   JSONArray jsonArray = null;
                   jsonArray = jsonObject.getJSONArray("data");
                   for (int i = 0; i < jsonArray.length(); i++) {


                        city = jsonArray.getJSONObject(i).getString("city");//市
                        prov = jsonArray.getJSONObject(i).getString("prov");//省份
                        type = jsonArray.getJSONObject(i).getString("type");//厂商

                   }
                   queryResult ="省份：" + prov + ",城市:" + city + ",服务商:" + type;
                   Log.d(TAG, "run: "+queryResult);
                   Message message = new Message();
                   Bundle data = new Bundle();
                   data.putString("queryResult",queryResult);
                   message.setData(data);
                   handler.sendMessage(message);
               } catch (JSONException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }
       }).start();


    }
}
