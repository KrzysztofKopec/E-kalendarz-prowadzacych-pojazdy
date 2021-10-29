package com.example.krzy007.e_kalendarzprowadzacychpojazdy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditionWorkActivity extends Activity {
    public static final String EXTRA_NO = "extraNo";
    Cursor cursor;
    NewDataBaseMPK eBase;
    EditText eDate, eLine_Brigade, eStartHour, eEndHour, eHour , eMinutes, eVehicleNo, eNote;
    int eId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_work);

        eBase = new NewDataBaseMPK(this);
        int extraNO = (Integer)getIntent().getExtras().get(EXTRA_NO);
        eId = extraNO;
        eDate = findViewById(R.id.eData);
        eLine_Brigade = findViewById(R.id.eLinia_brygada);
        eStartHour = findViewById(R.id.eGodzPoczatkowa);
        eEndHour = findViewById(R.id.eGodzKonca);
        eHour = findViewById(R.id.eGodziny);
        eMinutes = findViewById(R.id.eMinuty);
        eVehicleNo = findViewById(R.id.eNrPojazdu);
        eNote = findViewById(R.id.eNotatka);
        cursor = eBase.editWork(eId);
        if(cursor.moveToFirst()) {
            eDate.setText(cursor.getString(0));
            eLine_Brigade.setText(cursor.getString(1));
            eStartHour.setText(cursor.getString(2));
            eEndHour.setText(cursor.getString(3));
            eHour.setText(cursor.getString(5));
            eMinutes.setText(cursor.getString(6));
            eVehicleNo.setText(cursor.getString(4));
            eNote.setText(cursor.getString(7));
        }
    }
    public void onClickEditionSave(View view) {
        int fieldHour = 0;
        int fieldMinute = 0;
        String fieldDate = eDate.getText().toString();
        if(!fieldDate.substring(2,3).equals("-") || !fieldDate.substring(5,6).equals("-")){
            Toast.makeText(this,"Niepoprawny format daty", Toast.LENGTH_LONG).show();
            return;
        }
        String line = eLine_Brigade.getText().toString();
        String start = eStartHour.getText().toString();
        String end = eEndHour.getText().toString();
        String vehicleNo = eVehicleNo.getText().toString();
        try {
            fieldHour = Integer.parseInt(eHour.getText().toString());
            fieldMinute = Integer.parseInt(eMinutes.getText().toString());
        }catch (NumberFormatException nfe){
            Toast.makeText(this,"Niepoprawny format godzin lub minut", Toast.LENGTH_LONG).show();
            return;
        }
        if(fieldMinute > 59){
            Toast.makeText(this,"Za dużo minut", Toast.LENGTH_LONG).show();
            return;
        }
        String note = eNote.getText().toString();
        eBase.editWorkingDay(eId,""+fieldDate,""+line,
                ""+start,""+end,""+vehicleNo,fieldHour,fieldMinute,""+note);
        Toast toast = Toast.makeText(this,"Dane zostały zmienione", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    protected void onDestroy() {
        super.onDestroy();
        eBase.close();
        cursor.close();
    }
}
