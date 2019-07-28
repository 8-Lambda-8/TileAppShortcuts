package com.a8lambda8.tileappshortcuts;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.wear.widget.WearableRecyclerView;

import java.util.ArrayList;

/**
 * Created by jwasl on 28.07.2019.
 */
public class ListAdapter_AppSelector extends WearableRecyclerView.Adapter<ListAdapter_AppSelector.MyViewHolder> {

    private ArrayList<ResolveInfo> mDataset;
    private PackageManager pm;

    private View.OnClickListener onItemClickListener;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class MyViewHolder extends WearableRecyclerView.ViewHolder {
    // each data item is just a string in this case
    public ImageView IV_appList;
    public TextView TV_appList;
    public MyViewHolder(View v) {
        super(v);
        IV_appList = v.findViewById(R.id.iv_applist);
        TV_appList = v.findViewById(R.id.tv_applist);

        v.setTag(this);
        //v.setOnClickListener(onItemClickListener);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter_AppSelector(ArrayList<ResolveInfo> myDataset, PackageManager pm) {
        mDataset = myDataset;
        this.pm = pm;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter_AppSelector.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listadapterlayout_appselector, parent, false);


        return new ListAdapter_AppSelector.MyViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.TV_appList.setText(mDataset.get(position).loadLabel(pm));
        holder.IV_appList.setImageDrawable(mDataset.get(position).loadIcon(pm));

        //holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(onItemClickListener);

    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
