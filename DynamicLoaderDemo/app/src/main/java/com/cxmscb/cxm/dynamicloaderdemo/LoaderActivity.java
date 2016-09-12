package com.cxmscb.cxm.dynamicloaderdemo;

import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class LoaderActivity extends Activity {


    //宿主Activity，专用于加载插件apk的Fragment

    private String apkPath;
    private String className;
    private DexClassLoader dexClassLoader;
    private Resources resources;
    private AssetManager assetManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        apkPath = intent.getStringExtra("apkPath");
        className = intent.getStringExtra("class");

        try {


            dexClassLoader = new DexClassLoader(apkPath,this.getDir("dex",Context.MODE_PRIVATE).getAbsolutePath(),null,super.getClassLoader());


            assetManager = AssetManager.class.newInstance();
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class)
                    .invoke(assetManager, apkPath);

            resources = new Resources(assetManager,this.getResources().getDisplayMetrics(),this.getResources().getConfiguration());



            Class activityClass = dexClassLoader.loadClass(className);
            Constructor localConstructor = activityClass.getConstructor(new Class[]{});
            Object instance = localConstructor.newInstance(new Object[]{});
            Log.d("zz", "instance = " + instance);

            Method localMethodSetActivity = activityClass.getDeclaredMethod(
                    "setActivity", new Class[] { Activity.class });
            localMethodSetActivity.setAccessible(true);
            localMethodSetActivity.invoke(instance, new Object[] { this });

            Method methodOnCreate = activityClass.getDeclaredMethod(
                    "onCreate", new Class[] { Bundle.class });
            methodOnCreate.setAccessible(true);
            Bundle paramBundle = new Bundle();
            paramBundle.putBoolean("KEY_START_FROM_OTHER_ACTIVITY", true);
            methodOnCreate.invoke(instance, new Object[] { paramBundle });


/*

            Fragment fragment = (Fragment) dexClassLoader.loadClass(className).newInstance();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(2,fragment);
            fragmentTransaction.commit();
*/

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/ catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }




    @Override
    public ClassLoader getClassLoader() {
        return dexClassLoader==null?super.getClassLoader():dexClassLoader;
    }

    @Override
    public Resources getResources() {
        return resources==null?super.getResources():resources;
    }


    public AssetManager getAssetManager() {
        return assetManager==null?super.getAssets():assetManager;
    }

}
