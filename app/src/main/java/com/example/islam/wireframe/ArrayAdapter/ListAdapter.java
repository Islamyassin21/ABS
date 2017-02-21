package com.example.islam.wireframe.ArrayAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.Shapping;
import com.example.islam.wireframe.R;
import com.example.islam.wireframe.WorkDayActivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by islam on 24/12/2016.
 */

public class ListAdapter extends ArrayAdapter<Shapping> {

    Activity activity;
    int layoutresource;
    List<Shapping> mData = Collections.emptyList();
    boolean[] mCheckstates;

    public ListAdapter(Activity act, int resource, List<Shapping> data) {
        super(act, resource, data);

        activity = act;
        layoutresource = resource;
        mData = data;
        notifyDataSetChanged();
        mCheckstates = new boolean[data.size()];
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Shapping getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getPosition(Shapping item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null || (row.getTag()) == null) {

            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();

            holder.costumerName = (TextView) row.findViewById(R.id.listName);
            holder.costumerId = (TextView) row.findViewById(R.id.listCustomerId);
            holder.shappingCode = (TextView) row.findViewById(R.id.listShippedId);
            holder.costumerAddress = (TextView) row.findViewById(R.id.listAddress);
            holder.checkBox = (CheckBox) row.findViewById(R.id.listSelect);
            holder.checkBox.setChecked(false);

            row.setTag(holder);

        } else {

            holder = (ViewHolder) row.getTag();
        }

        holder.myShapping = getItem(position);
        holder.costumerName.setText(holder.myShapping.getUsername());
        holder.costumerId.setText(holder.myShapping.getCostumerphone() + "");
        holder.shappingCode.setText(holder.myShapping.getShappingCode());
        holder.costumerAddress.setText(holder.myShapping.getCostumeraddress());
        //  holder.checkBox.setChecked(getItem(position).getSe);

        holder.checkBox.setChecked(mCheckstates[position]);

        final ViewHolder finalHolder = holder;
        holder.checkBox.setOnClickListener(new View.OnClickListener() {

            DatabaseHelper db = new DatabaseHelper(activity);

            @Override
            public void onClick(View v) {
                if (finalHolder.checkBox.isChecked()) {
                    Shapping shop = mData.get(position);
                    db.AddChoose(shop);

                } else {
                    Shapping shop = mData.get(position);
                    db.deleteCostumChoose(shop);
                }
            }
        });
//        holder.checkBox.setTag(position);
//        holder.checkBox.setChecked(mCheckstates.get(position, false));
//        holder.checkBox.setOnCheckedChangeListener(this);

        return row;
    }

    public void selectedAll() {


        DatabaseHelper db = new DatabaseHelper(activity);
        ProgressDialog dialog;
        dialog = new ProgressDialog(activity);
        dialog.setMessage("برجاء الانتظار ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        for (int i = 0; i < mCheckstates.length; i++) {
            mCheckstates[i] = true;
            Shapping shop = mData.get(i);
            notifyDataSetChanged();
            db.AddChoose(shop);
            dialog.cancel();
        }

    }

    public void deSelectedAll() {

        DatabaseHelper db = new DatabaseHelper(activity);
        for (int i = 0; i < mCheckstates.length; i++) {
            mCheckstates[i] = false;
            db.deleteTableChoose();
        }
        notifyDataSetChanged();
    }


    class ViewHolder {

        Shapping myShapping;
        TextView costumerName;
        TextView costumerId;
        TextView shappingCode;
        TextView costumerAddress;
        CheckBox checkBox;

    }

}
