package com.example.sqlitelabsis22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class Listview_Articl extends AppCompatActivity {
    ListView listVpersonas;
    ArrayAdapter adaptadar;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;
    String[] ver = {"Item1","Item2","Item3","Item4","Item5","Item6","Item7","Item8","Item9","Item10","Item11","Item12","Item13","Item14","Item15"};
    CRUDSQLITE conexion = new CRUDSQLITE(this);
    DB datos = new DB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview__articl);
        listVpersonas = (ListView) findViewById(R.id.ListViewPeople);
        searchView = (SearchView) findViewById(R.id.buscador);
        adaptadar = new ArrayAdapter(this, android.R.layout.simple_list_item_1,conexion.consultarListaArticulos1());
        listVpersonas.setAdapter(adaptadar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s;
                adaptadar.getFilter().filter(text);
                return false;
            }
        });
        listVpersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String informacion = "Codigo: "+conexion.consultarListaArticulos().get(i).getDescripcion()+"\n";
                informacion+="Precio: "+conexion.consultarListaArticulos().get(i).getPrecio();

                DB arti = conexion.consultarListaArticulos().get(i);
                Intent intent = new Intent(Listview_Articl.this, DetallArtl.class);
                Bundle bun = new Bundle();
                bun.putSerializable("articulo",arti);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

    }
    public  void  vol (View view){
        onBackPressed();
    }
}