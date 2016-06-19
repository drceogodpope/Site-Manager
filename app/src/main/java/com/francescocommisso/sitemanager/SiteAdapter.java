package com.francescocommisso.sitemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

class SiteAdapter extends ArrayAdapter<Site> {

    DBHandler dbh;
    private int layout;

    public SiteAdapter(Context context, ArrayList<Site> sites) {
        super(context, R.layout.array_row, sites);
        this.layout = R.layout.array_row;
        dbh = DBHandler.getInstance(context);
    }

    private class ViewHolder{
        TextView nameView;
        View row;

        public ViewHolder(View view){
            nameView = (TextView) view.findViewById(R.id.site_name);
            row = view.findViewById(R.id.row);
        }
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        View row = convertView;

        if (row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layout, parent, false);
            holder = new ViewHolder(row);
            holder.nameView.setText(dbh.getSites().get(position).getName());
            row.setTag(holder);
            final View test = row;
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            holder.nameView.setText(getItem(position).getName());

        }
        return row;

    }
}
