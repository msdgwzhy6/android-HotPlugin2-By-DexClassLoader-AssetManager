package com.cxmscb.cxm.redpaper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DynamicAcitivty extends Activity {

    private Activity otherActivity;
    private View v;
    private Button button;
    private RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        boolean b = false;
        if (savedInstanceState != null) {
            b = savedInstanceState.getBoolean("KEY_START_FROM_OTHER_ACTIVITY", false);
            if (b) {
                v = LayoutInflater.from(this.otherActivity).inflate(R.layout.fragment_dynamic,null, false);
                this.otherActivity.setContentView(v);
                button  = (Button) v.findViewById(R.id.start);
                relativeLayout = (RelativeLayout) v.findViewById(R.id.rl);
                button.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(otherActivity,"开始抢红包",Toast.LENGTH_SHORT).show();
                        Log.i("zz","zz");
                       // v.setBackgroundDrawable(otherActivity.getResources().getDrawable(R.drawable.zz));
                        relativeLayout.setBackground(otherActivity.getResources().getDrawable(R.drawable.zz));
                    }
                });
            }
        }
        if (!b) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_dynamic);
        }
    }

    public void setActivity(Activity paramActivity) {
        this.otherActivity = paramActivity;
    }
}
