package com.good.gd.example.skeleton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by developer01 on 28/06/2017.
 */
public class ColleagueListAdapter extends BaseAdapter {

    private static ArrayList<Colleague> colleagueArrayList;
    private LayoutInflater mInflater;
    private Context ctx;

    public ColleagueListAdapter(Context context, ArrayList<Colleague> colleagues) {
        colleagueArrayList = colleagues;
        mInflater = LayoutInflater.from(context);
        this.ctx = context;
    }

    @Override
    public int getCount() {
        return colleagueArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return colleagueArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtDepartment = (TextView) convertView.findViewById(R.id.department);
            viewHolder.txtLocation = (TextView) convertView.findViewById(R.id.location);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(colleagueArrayList.get(position).getName());
        viewHolder.txtDepartment.setText(colleagueArrayList.get(position).getDepartment());
        viewHolder.txtLocation.setText(colleagueArrayList.get(position).getLocation());

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtDepartment;
        TextView txtLocation;
        ImageView colleagueImage;
    }
}
