package com.example.barvius.lb4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<File> {
    private int SelectedItem = -1;

    public ListAdapter(Context context, ArrayList<File> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        File f = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }

        TextView text = (TextView) convertView.findViewById(R.id.listview_item_title);
        ImageView img = (ImageView) convertView.findViewById(R.id.listview_item_image);

        text.setText(f.getName());

        if (position == SelectedItem) {
            convertView.setBackgroundResource(R.color.colorActiveIthems);
        } else {
            convertView.setBackgroundResource(R.color.colorInactiveIthems);
        }

        if (f.isDirectory()) {
            img.setImageResource(R.drawable.ic_folder_black_24dp);
        } else {
            img.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        }
        return convertView;
    }

    public void setSelectedItem(int item) {
        SelectedItem = item;
        notifyDataSetChanged();
    }

    public int getSelectedItem() {
        return SelectedItem;
    }
}
