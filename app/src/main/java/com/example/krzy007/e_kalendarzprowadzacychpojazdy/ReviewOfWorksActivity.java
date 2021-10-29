package com.example.krzy007.e_kalendarzprowadzacychpojazdy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class ReviewOfWorksActivity extends Activity {
    public static final String EXTRA_DATE = "extraDate";
    Cursor cursor;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przeglad_sluzb);

        ListView workList = findViewById(R.id.lista_prac);
        String extraDATE = (String)getIntent().getExtras().get(EXTRA_DATE);
        try{
            SQLiteOpenHelper dataBase = new NewDataBaseMPK(this);
            db = dataBase.getReadableDatabase();
            cursor = db.query("ECALENDAR", new String[]{"_id","DATA","LINIA","POCZATEK","KONIEC","NRPOJAZDU","GODZINY","MINUTY","NOTATKA"},
                    "DATA LIKE '%"+extraDATE+"'",null,null,null,"_id DESC");

            MyAdapter adapter = new MyAdapter(ReviewOfWorksActivity.this,cursor);
            workList.setAdapter(adapter);
            workList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ReviewOfWorksActivity.this,EditionWorkActivity.class);
                    intent.putExtra(EditionWorkActivity.EXTRA_NO,(int)id);
                    startActivity(intent);
                }
            });

        }catch(SQLException e){
            Toast toast = Toast.makeText(this,"Brak dostepu do bazy danych",Toast.LENGTH_LONG);
            toast.show();
        }

    }
    public void onClickBackToMenu(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }


}
