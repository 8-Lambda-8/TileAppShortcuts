package com.a8lambda8.tileappshortcuts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends WearableActivity {

    private List<ImageButton> buttons;
    private static final int[] BUTTON_IDS = {
            R.id.IB_0,
            R.id.IB_1,
            R.id.IB_2,
            R.id.IB_3,
            R.id.IB_4,
            R.id.IB_5,

    };

    public static final String TAG = "XXX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttons = new ArrayList<>();

        for(int id : BUTTON_IDS){
            ImageButton button = findViewById(id);
            final int finalID = id;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"BTN_" + (finalID-R.id.IB_0) +" was Clicked");

                    Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    List<ResolveInfo> pkgAppsList = MainActivity.this.getPackageManager().queryIntentActivities( mainIntent, 0);

                    //Log.d(TAG,""+pkgAppsList);

                    Intent i = new Intent(MainActivity.this, AppSelectorActivity.class);
                    i.putExtra("AppList",(ArrayList<ResolveInfo>)pkgAppsList);
                    startActivityForResult(i, 1);

                }
            });
            buttons.add(button);
        }

        //mTextView = findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                ResolveInfo result = data.getParcelableExtra("result");

                Log.d(TAG,"back in main: "+result.loadLabel(getPackageManager()));

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

}
