package com.example.krzy007.e_kalendarzprowadzacychpojazdy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
        SharedPreferences utils;
        NewDataBaseMPK base;
        TextView numberHour, mscYear;
        Cursor cursor;
        int hour, minutes, result,yearInt,mscInt;
        String dateString;
        Boolean infoText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utils = getSharedPreferences("boolean", Activity.MODE_PRIVATE);
        loadBoolean();
        if(infoText == false){
            createDialog();
            saveBoolean();
        }

        base = new NewDataBaseMPK(this);
        mscYear = findViewById(R.id.miesiacRok);
        numberHour = findViewById(R.id.viewIloscGodzin);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        dateString = dateFormat.format(currentDate);
        yearInt = Integer.parseInt(dateString.substring(3));
        mscInt = Integer.parseInt(dateString.substring(0,2));
        hoursWorked(dateString);
    }

    public void hoursWorked(String msc){
        mscYear.setText("W miesiącu "+ msc +" przepracowałeś: ");
        cursor = base.getHoursMinutes();
        while (cursor.moveToNext()){
            if(cursor.getString(0).substring(3).equals(msc)) {
                hour += Integer.parseInt(cursor.getString(1).toString());
                minutes += Integer.parseInt(cursor.getString(2).toString());
            }
        }
        result = hour *60 + minutes;
        hour = result /60;
        minutes = result % 60;
        if(minutes < 10){
            numberHour.setText(""+ hour +":0"+ minutes);
        }else {
            numberHour.setText(""+ hour +":"+ minutes);
        }
    }


    public void onClickAddWork(View view) {
        Intent intent = new Intent(this,AddWorkActivity.class);
        startActivity(intent);
    }

    public void onClickSearchWork(View view) {
        Intent intent = new Intent(this, ReviewOfWorksActivity.class);
        intent.putExtra(ReviewOfWorksActivity.EXTRA_DATE, dateString);
        startActivity(intent);
    }

    public void onClickPrev(View view) {
        mscInt--;
        if(mscInt < 1){
            yearInt--;
            mscInt = 12;
        }
        String mc = Integer.toString(mscInt);
        String year = Integer.toString(yearInt);
        hour =0;
        minutes =0;
        if(mscInt > 9){
            hoursWorked(mc + "-" + year);
            dateString = mc + "-" + year;
        }
        else {
            hoursWorked("0"+mc + "-" + year);
            dateString = "0"+mc + "-" + year;
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        base.close();
        cursor.close();;
    }

    public void onClickNext(View view) {
        mscInt++;
        if(mscInt > 12){
            yearInt++;
            mscInt = 1;
        }
        String mc = Integer.toString(mscInt);
        String year = Integer.toString(yearInt);
        dateString = mc;
        hour =0;
        minutes =0;
        if(mscInt > 9){
            hoursWorked(mc + "-" + year);
            dateString = mc + "-" + year;
        }
        else {
            hoursWorked("0"+mc + "-" + year);
            dateString = "0"+mc + "-" + year;
        }
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
           if (doubleBackToExitPressedOnce) {
                    finish();
            }else{

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Naciśnij jeszcze raz aby wyjść", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }
    public void onClickDelete(View view) {
       createDialog();
    }
    public void createDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_info);
        dialog.setTitle("Informacja");

        Button exit = dialog.findViewById(R.id.button3);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void loadBoolean(){
        Boolean loadFromPref = utils.getBoolean("boolean",false);
        infoText = loadFromPref;
    }
    private void saveBoolean(){
        SharedPreferences.Editor editor = utils.edit();
        editor.putBoolean("boolean", true);
        editor.commit();
    }
}
