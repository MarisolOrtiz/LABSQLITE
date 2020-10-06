package com.example.sqlitelabsis22;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MODELO {
    Dialog dial;
    AlertDialog.Builder diag;
    boolean validarInput = false;
    String ModlCdg, ModlDcp, ModlPrc;
    SQLiteDatabase db = null;
    DB datos = new DB();
    public void  Search(final Context context){
        dial = new Dialog(context);
        dial.setContentView(R.layout.activity_v_n_t);
        dial.setCancelable(false);
        final CRUDSQLITE conexion = new CRUDSQLITE(context);
        final EditText codfinal = (EditText)dial.findViewById(R.id.ETCDVnt1);
        Button buscarr = (Button)dial.findViewById(R.id.BTN_BCR2);
        ImageButton cancel = (ImageButton)dial.findViewById(R.id.IconCerra3);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial.dismiss();
            }
        });
        buscarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (codfinal.getText().toString().length()== 0){
                    validarInput = false;
                    codfinal.setError("rellene el campo");
                }else {
                    validarInput = true;
                }
                if (validarInput){
                    String cod = codfinal.getText().toString();
                    datos.setCodigo(Integer.parseInt(cod));
                    if (conexion.cosultCod(datos) == true){
                        ModlCdg = String.valueOf(datos.getCodigo());
                        ModlDcp = datos.getDescripcion();
                        ModlPrc = String.valueOf(datos.getPrecio());
                        Intent ee = new Intent(context,Principal.class);
                        ee.putExtra("senale","1");
                        ee.putExtra("codigo",ModlCdg);
                        ee.putExtra("descripci","descripci");
                        ee.putExtra("per",ModlPrc);
                        Toast.makeText(context,"Enviado",Toast.LENGTH_SHORT).show();
                        dial.dismiss();
                    }else {
                        Toast.makeText(context,"Los resultados no existen",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context,"No has ingresado nada",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dial.show();
    }
}
