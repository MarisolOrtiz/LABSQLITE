package com.example.sqlitelabsis22;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends AppCompatActivity {
    private EditText et_codigo1,et_descripcion2,et_precio3;
    private Button BTNguardar1,BTN_Consult1,BTN_Consult2,btn_actualizar,BTN_Eliminar;
    private TextView result;
    boolean inputET = false;
    boolean inputED = false;
    boolean input1 = false;
    int resultInsert = 0;
    //private FABToolbarLayout morph;
    MODELO ventanas = new MODELO();
    CRUDSQLITE conexion = new CRUDSQLITE(this);
    DB dato = new DB();
    AlertDialog.Builder adios;

    public boolean onKeyDown(int KeyCode, KeyEvent event){
        if (KeyCode == KeyEvent.KEYCODE_BACK){
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.alert)
                    .setTitle("Advertencia!")
                    .setMessage("¿Desea salir?")
                    .setNegativeButton(android.R.string.cancel,null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                        }
                    })
                    .show();
            return true;
        }
        return  super.onKeyDown(KeyCode,event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Inicio del menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.regresar));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("Bertha Marisol Ortiz Ramos");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("CRUD SQLite");
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comfirmacion();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanas.Search(Principal.this);
            }
        });
        et_codigo1 = (EditText) findViewById(R.id.et_codigo1);
        et_descripcion2 = (EditText) findViewById(R.id.et_descripcion2);
        et_precio3 = (EditText) findViewById(R.id.et_precio3);
        BTN_Consult1 = (Button) findViewById(R.id.BTN_Consult1);
        BTN_Consult2 = (Button) findViewById(R.id.BTN_Consult2);
        BTNguardar1 = (Button) findViewById(R.id.BTNguardar1);
        btn_actualizar = (Button) findViewById(R.id.BTN_ACtualizar);
        BTN_Eliminar = (Button) findViewById(R.id.BTN_Eliminar);
        String sign = "";
        String codigo = "";
        String desc = "";
        String precio = "";
        try {
            Bundle bun = getIntent().getExtras();
            if (bun != null){
                String a = (String) bun.get("codigo");
                String b = (String) bun.get("descr");
                String c = (String) bun.get("codigo");
                sign = bun.getString("pe");
                desc = bun.getString("de");
                precio = bun.getString("pe");
                et_codigo1.setText(a);
                et_descripcion2.setText(b);
                et_precio3.setText(c);
            }
        }catch (Exception o){
        }
        /*metodo de la toolbarr
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        View one, two, three, four;

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);

        fab1.setOnClickListener((View.OnClickListener) this);
        one.setOnClickListener((View.OnClickListener) this);
        two.setOnClickListener((View.OnClickListener) this);
        three.setOnClickListener((View.OnClickListener) this);
        four.setOnClickListener((View.OnClickListener) this);
        menu toolbar*/

    }

    private void comfirmacion(){
        String msm = "¿Desea salir?";
        adios = new AlertDialog.Builder(Principal.this);
        adios.setIcon(R.drawable.alert);
        adios.setTitle("Advertencia");
        adios.setMessage(msm);
        adios.setCancelable(false);
        adios.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Principal.this.finish();
            }
        });
        adios.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        adios.show();
    }
    //fin del menu
    public void limpiardat(){
        et_codigo1.setText(null);
        et_descripcion2.setText(null);
        et_precio3.setText(null);
    }
    public void limpiardat2(View view){
        et_codigo1.setText(null);
        et_descripcion2.setText(null);
        et_precio3.setText(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_listaArt){
            Intent spinnerAct = new Intent(Principal.this,Consult.class);
            startActivity(spinnerAct);
            return true;
        }else if (id == R.id.action_listaArt1){
            Intent listVAct = new Intent(Principal.this,Listview_Articl.class);
            startActivity(listVAct);
            return  true;
        }else if (id == R.id.menu_5){
            Intent listViewActivity = new Intent(Principal.this,activity_lista_articulos_recyclerview.class);
            startActivity(listViewActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    public void guardar (View view){
        if (et_codigo1.getText().toString().length()== 0){
            et_codigo1.setError("rellene el campo");
            inputET = false;
        }else {
            inputET = true;
        }
        if (et_descripcion2.getText().toString().length()==0){
            et_descripcion2.setError("rellene el campo");
            inputED = false;
        }else {
            inputED = true;
        }
        if (et_precio3.getText().toString().length()== 0){
            et_precio3.setError("rellene el campo");
            input1 = false;
        }else{
            input1 = true;
        }
        if (inputET && inputED && input1){
            try {
                dato.setCodigo(Integer.parseInt(et_codigo1.getText().toString()));
                dato.setDescripcion(et_descripcion2.getText().toString());
                dato.setPrecio(Double.parseDouble(et_precio3.getText().toString()));
                if (conexion.InsertTradicional(dato)){
                    Toast.makeText(this,"Guardo Existoso",Toast.LENGTH_SHORT).show();
                    limpiardat();
                }else {
                    Toast.makeText(this,"Estos datos ya esta"+et_codigo1.getText().toString(),Toast.LENGTH_LONG).show();
                }
            }catch (Exception o){
                Toast.makeText(this,"Error!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public  void mensaje(String msm){
        Toast.makeText(this," "+msm,Toast.LENGTH_SHORT).show();
    }
    public  void consultcod (View view){
        if (et_codigo1.getText().toString().length()== 0){
            et_codigo1.setError("rellene el campo");
            inputET = false;
        }else {
            inputET = true;
        }
        if (inputET){
            String codigo = et_codigo1.getText().toString();
            dato.setCodigo(Integer.parseInt(codigo));
            if (conexion.consultArt(dato)){
                et_descripcion2.setText(dato.getDescripcion());
                et_precio3.setText(""+dato.getPrecio());
            }else{
                Toast.makeText(this,"El producto no existe",Toast.LENGTH_SHORT).show();
                limpiardat();
            }
        }else{
            Toast.makeText(this,"Ingrese el producto",Toast.LENGTH_SHORT).show();
        }
    }
    public  void consuldesc (View view){
        if (et_descripcion2.getText().toString().length()== 0){
            et_descripcion2.setError("rellene el campo");
            inputED = false;
        }else {
            inputED = true;
        }
        if (inputED){
            String desc = et_descripcion2.getText().toString();
            dato.setDescripcion(desc);
            if (conexion.cosultDesc(dato)){
                et_codigo1.setText(""+dato.getCodigo());
                et_descripcion2.setText(dato.getDescripcion());
                et_precio3.setText(""+dato.getPrecio());
            }else {
                Toast.makeText(this,"El producto no existe",Toast.LENGTH_SHORT).show();
                limpiardat();
            }
        }else {
            Toast.makeText(this,"ingrese la descriccion de su producto",Toast.LENGTH_SHORT).show();
        }
    }
    public  void bajacod(View view){
        if (et_codigo1.getText().toString().length()== 0){
            et_codigo1.setError("rellene el campo");
            inputET = false;
        }else {
            inputET = true;
        }
        if (inputET){
            String codj = et_codigo1.getText().toString();
            dato.setCodigo(Integer.parseInt(codj));
            if (conexion.delCod(Principal.this,dato)){
                limpiardat();
            }else {
                Toast.makeText(this,"Ingrese el articulo ",Toast.LENGTH_SHORT).show();
                limpiardat();
            }
        }
    }
    public void modi (View view){
        if (et_codigo1.getText().toString().length()== 0){
            et_codigo1.setError("rellene el campo");
            inputET = false;
        }else {
            inputET = true;
        }
        if (inputET){
            String cod = et_codigo1.getText().toString();
            String desc = et_descripcion2.getText().toString();
            double precio = Double.parseDouble(et_precio3.getText().toString());
            dato.setCodigo(Integer.parseInt(cod));
            dato.setDescripcion(desc);
            dato.setPrecio(precio);
            if (conexion.mod(dato)){
                Toast.makeText(this,"exitosamente Editado",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"No existe",Toast.LENGTH_SHORT).show();
            }
        }
    }

}