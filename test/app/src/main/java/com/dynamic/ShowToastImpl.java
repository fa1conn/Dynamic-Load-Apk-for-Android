package com.dynamic;
import android.content.Context;
import android.widget.Toast;

import com.dynamic.IShowToast;
public class ShowToastImpl implements IShowToast{

    @Override
    public int showToast(Context context) {
        Toast.makeText(context, "我来自另一个dex文件", Toast.LENGTH_LONG).show();
        return 0;
    }
}
