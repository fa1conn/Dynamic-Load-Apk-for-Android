package com.dynamic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.nfc.Tag;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.dynamic.IShowToast;
import java.io.File;
import java.io.IOException;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IShowToast lib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showBannerBtn = (Button) findViewById(R.id.button);
        showBannerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                loadDexClass();
            }
        });
    }
    /**
     * 加载dex文件中的class，并调用其中的showToast方法
     */
    private void loadDexClass() {
        String dexPath = Environment.getExternalStorageDirectory().toString() + File.separator + "app-debug.apk";
        Log.i(TAG,dexPath);

        File dexOutputDir = getDir("dex", 0);
        DexClassLoader cl = new DexClassLoader(dexPath,dexOutputDir.getAbsolutePath(),null,getClassLoader());

        try {
            //该name就是internalPath路径下的dex文件里面的ShowToastImpl这个类的包名+类名
            Class<?> clz = cl.loadClass("com.dynamic.ShowToastImpl");
            IShowToast impl= (IShowToast) clz.newInstance();//通过该方法得到IShowToast类
            if (impl!=null)
                impl.showToast(this);//调用打开弹窗
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"加载app-debug.apk有问题");
        }
    }
}
