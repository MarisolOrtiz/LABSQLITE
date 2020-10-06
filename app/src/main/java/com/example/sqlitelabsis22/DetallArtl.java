package com.example.sqlitelabsis22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetallArtl extends AppCompatActivity {
    private TextView codg, descr, ins;
    private TextView codg1,desc1,mas1, fecha1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_artl);
        codg = (TextView) findViewById(R.id.TV_codU1);
        descr = (TextView) findViewById(R.id.tv_descdETT2);
        ins = (TextView) findViewById(R.id.tV_PrcDtt3);
        codg1 = (TextView) findViewById(R.id.TV_codU1);
        desc1 = (TextView) findViewById(R.id.Tv_Descdllt2);
        mas1 = (TextView) findViewById(R.id.Tvprecdt3);
        fecha1 = (TextView) findViewById(R.id.tv_fecha);
        Bundle obj = getIntent().getExtras();
        DB dB = null;
        if (obj != null){
            dB = (DB) obj.getSerializable("articulo");
            codg.setText(""+dB.getCodigo());
            descr.setText(dB.getDescripcion());
            ins.setText(""+dB.getPrecio());

            codg1.setText(""+dB.getCodigo());
            desc1.setText(dB.getDescripcion());
            mas1.setText(String.valueOf(dB.getPrecio()));
            fecha1.setText("Fecha de creacion: "+getDateTime());
        }
    }
    private String getDateTime(){
        SimpleDateFormat dates = new SimpleDateFormat(
                "yyyy-MM-dd-HH:mm:ss a", Locale.getDefault());
        Date day = new Date();
        return dates.format(day);
    }
    public  void  vol (View view){
        onBackPressed();
    }
}