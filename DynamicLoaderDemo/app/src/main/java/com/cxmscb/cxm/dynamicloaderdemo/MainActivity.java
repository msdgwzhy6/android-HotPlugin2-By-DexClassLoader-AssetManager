package com.cxmscb.cxm.dynamicloaderdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void loadRedPaper(View view) {

        dynamicLoader("redpaper");

    }

    private void dynamicLoader(String pluginName) {

        String apkPath = findPlugin(pluginName);
        if(apkPath==null){

            Toast.makeText(this,"请先下载该插件apk",Toast.LENGTH_SHORT).show();

        }else {

            Intent intent = new Intent(this,LoaderActivity.class);
            intent.putExtra("apkPath",apkPath);
            intent.putExtra("class","com.cxmscb.cxm."+pluginName+".DynamicAcitivty");
            startActivity(intent);

        }

    }

    private String findPlugin(String pluginName) {

        String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+pluginName+".apk";



        File apk = new File(apkPath);

        if(apk.exists()){
            return apkPath;
        }

        return null;

    }


}
