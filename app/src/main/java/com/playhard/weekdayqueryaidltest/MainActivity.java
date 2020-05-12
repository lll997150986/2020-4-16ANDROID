package com.playhard.weekdayqueryaidltest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.playhard.studentmgr.activity.service.IQueryWeekday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button ;
    EditText editText ;
    TextView textView ;
    IQueryWeekday queryWeekdayService;
    String content;

    private  ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            queryWeekdayService = IQueryWeekday.Stub.asInterface(iBinder);
            try {
                String result = queryWeekdayService.getWeekday(content);
                if (!TextUtils.isEmpty(result)){
                    textView.setText(result);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.queryBtn);
        textView = findViewById(R.id.result);
        editText = findViewById(R.id.dateInput);
        button.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        content = editText.getText().toString();
        try {
            Date date = sf.parse(content.trim());
        } catch (ParseException e) {
            textView.setText("错误的日期格式，请输入yyyy-MM-dd");
            return;
        }
        Intent intent = new Intent("com.playhard.aidl.action.AIDL_SERVICE");
        Intent explicitFromImplicitIntent = createExplicitFromImplicitIntent(this, intent);
        bindService(explicitFromImplicitIntent,connection, Service.BIND_AUTO_CREATE);
            if (queryWeekdayService!=null){
                try {
                    String result = queryWeekdayService.getWeekday(content);
                    if (!TextUtils.isEmpty(result)){
                        textView.setText(result);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }



//   隐式intent转换显式
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

}
