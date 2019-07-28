package com.a8lambda8.tileappshortcuts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import java.util.ArrayList;

import static com.a8lambda8.tileappshortcuts.MainActivity.TAG;


public class AppSelectorActivity extends WearableActivity {

    private WearableRecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_selector);

        final ArrayList<ResolveInfo> pkgAppsList = getIntent().getParcelableArrayListExtra("AppList");

        recyclerView = findViewById(R.id.rec_view);

        recyclerView.setEdgeItemsCenteringEnabled(true);


        CustomScrollingLayoutCallback customScrollingLayoutCallback =
                new CustomScrollingLayoutCallback();
        recyclerView.setLayoutManager(
                new WearableLinearLayoutManager(this, customScrollingLayoutCallback));


        final PackageManager pm =  getApplication().getPackageManager();


        for(ResolveInfo ri : pkgAppsList){
            Log.d(TAG,""+ri.loadLabel(pm));
        }

        // specify an adapter (see also next example)
        mAdapter = new ListAdapter_AppSelector(pkgAppsList,pm);

        ((ListAdapter_AppSelector) mAdapter).setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WearableRecyclerView.ViewHolder viewHolder = (WearableRecyclerView.ViewHolder) v.getTag();
                int id = viewHolder.getAdapterPosition();

                Log.d(TAG,"clicked: "+id+" "+pkgAppsList.get(id).loadLabel(pm));

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",pkgAppsList.get(id));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });//

        recyclerView.setAdapter(mAdapter);

    }

    public class CustomScrollingLayoutCallback extends WearableLinearLayoutManager.LayoutCallback {
        /** How much should we scale the icon at most. */
        private static final float MAX_ICON_PROGRESS = 0.65f;

        private float progressToCenter;

        @Override
        public void onLayoutFinished(View child, RecyclerView parent) {

            // Figure out % progress from top to bottom
            float centerOffset = ((float) child.getHeight() / 2.0f) / (float) parent.getHeight();
            float yRelativeToCenterOffset = (child.getY() / parent.getHeight()) + centerOffset;

            // Normalize for center
            progressToCenter = Math.abs(0.5f - yRelativeToCenterOffset);
            // Adjust to the maximum scale
            progressToCenter = Math.min(progressToCenter, MAX_ICON_PROGRESS);

            child.setScaleX(1 - progressToCenter);
            child.setScaleY(1 - progressToCenter);
        }
    }
}
