package com.francescocommisso.sitemanager;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class LotGridAdapter extends BaseAdapter{

    ArrayList<Lot> list;
    Context context;

    public LotGridAdapter(ArrayList<Lot> lots, Context context){
        list = lots;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }


    class Holder {
        private TextView lotID;
        public Holder(View view){
            lotID = (TextView) view.findViewById(R.id.lot_number_textview);
        }
        public void setID(int i){
            lotID.setText(Integer.toString(i));
        }
    }

// sd
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View cell = convertView;
        Holder holder = null;

        if(cell == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cell = inflater.inflate(R.layout.single_lot, parent, false);
            holder = new Holder(cell);
            cell.setTag(holder);
        }
        else {
            holder = (Holder) cell.getTag();
        }

        holder.setID(position+1);


        switch (list.get(position).getStatus()) {
            case Lot.INCOMPLETE: cell.findViewById(R.id.lot_cell).setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                break;
            case Lot.COMPLETE: cell.findViewById(R.id.lot_cell).setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
                break;
            default: cell.findViewById(R.id.lot_cell).setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));

        }
        return cell;
    }
}
