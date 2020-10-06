package com.example.sqlitelabsis22;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CRUDSQLITE extends SQLiteOpenHelper {
    boolean statusdel = true;
    ArrayList<String> listaArt;
    ArrayList<DB> articulolista;
    boolean estados = true;
    public CRUDSQLITE(Context context) {
        super(context, "carlos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table articulos(codigo integer not null primary key," +
                "descripcion text, precio real)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists articulos");
        onCreate(db);
    }
    public SQLiteDatabase bd() {
        SQLiteDatabase bd = this.getWritableDatabase();
        return bd;
    }
    public boolean InsertTradicional(DB datos){
        boolean status = true;
        int result;
        try {
            int codi = datos.getCodigo();
            String descr = datos.getDescripcion();
            double precioo = datos.getPrecio();
            Cursor fila = bd().rawQuery("select codigo from articulos where codigo= '"+codi+"'",null);
            if (fila.moveToFirst()==true){
                status = false;
            }else {
                String SQL = "INSERT INTO articulos \n "+"(codigo,descripcion,precio)\n"+"VALUES\n"+"('"+String.valueOf(codi)+"','"+descr+"','"+String.valueOf(precioo)+"');";
                bd().execSQL(SQL);
                bd().close();
                status = true;
            }
        }catch (Exception esta){
            status = false;
            Log.e("no existe vinculo",esta.toString());
        }
        return  status;
    }
    public  boolean insertDatos(DB datos){
        boolean meda_error = true;
        int resultado;
        ContentValues regis = new ContentValues();
        try {
            regis.put("codigo",datos.codigo);
            regis.put("descripcion",datos.descripcion);
            regis.put("precio",datos.precio);
            Cursor fila = bd().rawQuery("select codigo from articulos where  codigo='"+datos.getCodigo()+"'",null );
            if (fila.moveToFirst()== true){
                meda_error = false;
            }else {
                resultado = (int)bd().insert("articulo",null,regis);
                if (resultado > 0) meda_error = true;
                else meda_error = false;
            }
        }catch (Exception ex){
            meda_error = false;
            Log.e("Datos erroneos",ex.toString());
        }
        return  meda_error;
    }
    public  boolean  InsertReg(DB datos){
        boolean estado = true ;
        int resultado;
        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();
            Calendar calen = Calendar.getInstance();
            SimpleDateFormat yacasi = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String fechaone = yacasi.format(calen.getTime());
            Cursor fila = bd().rawQuery("select codigo from articulos where  codigo='"+datos.getCodigo()+"'",null );

            if (fila.moveToFirst()== true){
                estado = false;
            }else {
                String SQLbien = "INSERT INTO articulos \n"+"(codigo,descripcion,precio)\n"+"VALUES \n"+"(?,?,?);";
                bd().execSQL(SQLbien, new String[]{String.valueOf(codigo),descripcion,String.valueOf(precio)});
                estado = true;
            }
        }catch (Exception e){
            estado = false;
            Log.e("datos insertados error",e.toString());
        }
        return estado;
    }

    public boolean consultArt(DB dat){
        boolean estado = true;
        int results;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String[]    parametros = {String.valueOf(dat.getCodigo())};
            String[]    campos = {"codigo","descripcion","precio"};
            Cursor fila = db.query("articulos",campos,"codigo=?",parametros,null,null,null);
            if (fila.moveToFirst()){
                dat.setCodigo(Integer.parseInt(fila.getString(0)));
                dat.setDescripcion(fila.getString(1));
                dat.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            }else {
                estado = false;
            }
            fila.close();
            db.close();
        }catch (Exception e){
            estado = false;
            Log.e("error de producto",e.toString());
        }
        return estado;
    }
    public boolean cosultCod(DB dat){
        boolean estado = true;
        int results;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int cod = dat.getCodigo();
            Cursor fila = db.rawQuery("select codigo, descripcion, precio from articulos where codigo="+cod,null);
            if (fila.moveToFirst()){
                dat.setCodigo(Integer.parseInt(fila.getString(0)));
                dat.setDescripcion(fila.getString(1));
                dat.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            }else {
                estado = false;
            }
            db.close();
        }catch (Exception e){
            estado = false;
            Log.e("error",e.toString());
        }
        return estado;
    }
    public  boolean cosultDesc(DB datos){
        boolean estatus = true;
        int resulto;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String descripc = datos.getDescripcion();
            Cursor fila = db.rawQuery("select * from articulos where descripcion='"+descripc+"'",null);
            if (fila.moveToFirst()){
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                estatus = true;
            }else {
                estatus = false;
            }
            db.close();
        }catch (Exception e){
            estatus = false;
            Log.e("descripcion erronea",e.toString());
        }
        return estatus;
    }
    public  boolean delCod(final Context context, final DB datos){
        estados = true;
        try {
            int cod = datos.getCodigo();
            Cursor fila = bd().rawQuery("select * from articulos where codigo="+cod,null);
            if (fila.moveToFirst()){
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                AlertDialog.Builder build = new AlertDialog.Builder(context);
                build.setIcon(R.drawable.cancel);
                build.setTitle("error");
                build.setMessage("¿quiere eliminar este dato?\nCódigo:"+ datos.getCodigo()+"\nDescripcion: "+datos.getDescripcion());
                build.setCancelable(false);
                build.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int codigo = datos.getCodigo();
                        int cant = bd().delete("articulos","codigo="+codigo,null);
                        if (cant > 0 ){
                            estados = true;
                            Toast.makeText(context,"Registro eliminado ",Toast.LENGTH_SHORT).show();
                        }else {
                            estados = false;
                        }
                        bd().close();
                    }
                });
                build.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = build.create();
                dialog.show();
            }
        }catch (Exception thiss){
            estados = false;
            Log.e("Error",thiss.toString());
        }
        return estados;
    }
    public boolean mod(DB datos){
        boolean estado = true;
        int result;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int code = datos.getCodigo();
            String desc = datos.getDescripcion();
            double precio = datos.getPrecio();
            ContentValues reg = new ContentValues();
            reg.put("codigo",code);
            reg.put("descripcion",desc);
            reg.put("precio",precio);
            int cont = (int) db.update("articulos",reg,"codigo="+code,null);
            db.close();
            if (cont>0) estado = true;
            else estado = false;
        }catch (Exception e){
            Log.e("error en modificar",e.toString());
        }
        return  estado;
    }
    public ArrayList<DB> consultarListaArticulos() {
        boolean estado = false;
        SQLiteDatabase db = this.getReadableDatabase();
        DB articulos = null;
        articulolista = new ArrayList<DB>();
        try {
            Cursor fila = db.rawQuery("select * from articulos", null);
            while (fila.moveToNext()) {
                articulos = new DB();
                articulos.setCodigo(fila.getInt(0));
                articulos.setDescripcion(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));
                articulolista.add(articulos);
                Log.i("codigo", String.valueOf(articulos.getCodigo()));
                Log.i("codigo", articulos.getDescripcion().toString());
                Log.i("precio", String.valueOf(articulos.getPrecio()));
            }
            obtenerListaArticulos();
        } catch (Exception e) {

        }
        return articulolista;
    }

    public ArrayList<String> consultarListaArticulos1() {
        boolean estado = false;
        SQLiteDatabase db = this.getReadableDatabase();
        DB articulos = null;
        articulolista = new ArrayList<DB>();
        try {
            Cursor fila = db.rawQuery("select * from articulos", null);
            while (fila.moveToNext()) {
                articulos = new DB();
                articulos.setCodigo(fila.getInt(0));
                articulos.setDescripcion(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));
                articulolista.add(articulos);
            }
            listaArt = new ArrayList<String>();
            for (int i = 0;i < articulolista.size() ; i++){
                listaArt.add(articulolista.get(i).getCodigo()+" >> "+articulolista.get(i).getDescripcion());
            }
        } catch (Exception e) {
        }
        return listaArt;
    }
    public ArrayList<String> obtenerListaArticulos () {
        listaArt = new ArrayList<String>();
        listaArt.add("Seleccione");
        for (int i = 0; i < articulolista.size(); i++) {
            listaArt.add(articulolista.get(i).getCodigo() + "~" + articulolista.get(i).getDescripcion());
        }
        return listaArt;

    }
    public List<DB> mostrarArticulos(){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM articulos order by codigo desc", null);
        List<DB> articulos = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                articulos.add(new DB(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));
            }while (cursor.moveToNext());
        }
        return articulos;
    }
}
