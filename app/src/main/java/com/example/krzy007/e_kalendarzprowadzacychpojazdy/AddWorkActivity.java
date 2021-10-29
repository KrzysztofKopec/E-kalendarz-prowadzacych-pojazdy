package com.example.krzy007.e_kalendarzprowadzacychpojazdy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWorkActivity extends Activity {

        NewDataBaseMPK dataWorkDay;
        EditText date, line_brigade, startHour, endHour, hours, minutes, vehicleNo, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        dataWorkDay = new NewDataBaseMPK(this);

        date = findViewById(R.id.data);
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormat.format(currentDate);
        date.setText(dateString);

        line_brigade = findViewById(R.id.linia_brygada);
        startHour = findViewById(R.id.godzPoczatkowa);
        endHour = findViewById(R.id.godzKonca);
        hours = findViewById(R.id.godziny);
        minutes = findViewById(R.id.minuty);
        vehicleNo = findViewById(R.id.nrPojazdu);
        note = findViewById(R.id.notatka);
        note.setText("BEZ UWAG");

    }

    public void onClickSave(View view) {
        int fieldHour = 0;
        int fieldMinutes = 0;
        String poleData = date.getText().toString();
        if(!poleData.substring(2,3).equals("-") || !poleData.substring(5,6).equals("-")){
            Toast.makeText(this,"Niepoprawny format daty", Toast.LENGTH_LONG).show();
            return;
        }
        String line = line_brigade.getText().toString();
        String start = startHour.getText().toString();
        String end = endHour.getText().toString();
        String vehicleNo = this.vehicleNo.getText().toString();
        try {
           fieldHour = Integer.parseInt(hours.getText().toString());
            fieldMinutes = Integer.parseInt(minutes.getText().toString());
        }catch (NumberFormatException nfe){
            Toast.makeText(this,"Niepoprawny format godzin lub minut", Toast.LENGTH_LONG).show();
            return;
        }
        if(fieldMinutes > 59){
            Toast.makeText(this,"Za dużo minut", Toast.LENGTH_LONG).show();
            return;
        }
        String note = this.note.getText().toString();
        dataWorkDay.enteringWorkingDay(""+poleData,""+line,
                ""+start,""+end,""+vehicleNo,fieldHour,fieldMinutes,""+note);
        Toast toast = Toast.makeText(this,"Dane zostały zapisane", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    protected void onDestroy() {
        super.onDestroy();
        dataWorkDay.close();
    }
}
