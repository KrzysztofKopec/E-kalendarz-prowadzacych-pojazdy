package com.example.krzy007.e_kalendarzprowadzacychpojazdy;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Krzyś007 on 2018-02-25.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private Cursor cursor;

    public MyAdapter(Context context,Cursor cursor){
        this.context = context;
        this.cursor = cursor;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.customrow,parent,false);
            viewHolder.date = convertView.findViewById(R.id.textData);
            viewHolder.line = convertView.findViewById(R.id.textLinia);
            viewHolder.start = convertView.findViewById(R.id.textPoczatek);
            viewHolder.end = convertView.findViewById(R.id.textKoniec);
            viewHolder.numberHour = convertView.findViewById(R.id.textGodzinyMinuty);
            viewHolder.vehicleNo = convertView.findViewById(R.id.textNrPojazdu);
            viewHolder.note = convertView.findViewById(R.id.textNotatka);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        cursor.moveToPosition(position);
            String a = "";
            viewHolder.date.setText("Dzień: "+cursor.getString(1));
            viewHolder.line.setText("Linia: "+cursor.getString(2));
            viewHolder.start.setText("Początek: "+cursor.getString(3));
            viewHolder.end.setText("Koniec: "+cursor.getString(4));
            if(cursor.getString(7).length() == 1){
                a = "0"+ cursor.getString(7);
            }else{
                a = cursor.getString(7);
            }
            viewHolder.numberHour.setText("Godziny: "+cursor.getString(6)+":"+ a);
            viewHolder.vehicleNo.setText("NR Pojazdu: "+cursor.getString(5));
            viewHolder.note.setText(cursor.getString(8));
        return convertView;
    }
    class ViewHolder{
        TextView date;
        TextView line;
        TextView start;
        TextView end;
        TextView numberHour;
        TextView vehicleNo;
        TextView note;
    }
}
