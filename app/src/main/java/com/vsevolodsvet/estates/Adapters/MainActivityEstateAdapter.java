package com.vsevolodsvet.estates.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vsevolodsvet.estates.Objects.Estate;
import com.vsevolodsvet.estates.R;

import java.util.List;

public class MainActivityEstateAdapter extends ArrayAdapter<Estate> {

    public MainActivityEstateAdapter(Context context, List<Estate> dataList) {
        super(context, 0, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Estate data = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.estate_listview_item, parent, false);
        }

        TextView column1 = convertView.findViewById(R.id.col_id);
        TextView column2 = convertView.findViewById(R.id.col_region);
        TextView column3 = convertView.findViewById(R.id.col_adress);
        TextView column4 = convertView.findViewById(R.id.col_level);


        column1.setText(String.valueOf(data.getId()));
        column2.setText(String.valueOf(data.getRegion()));
        column3.setText(String.valueOf(data.getAdress()));
        column4.setText(String.valueOf(data.getLevel()));

        return convertView;
    }
}