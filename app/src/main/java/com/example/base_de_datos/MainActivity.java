package com.example.base_de_datos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo      = (EditText)findViewById(R.id.txt_codigo);
        et_descripcion = (EditText)findViewById(R.id.txt_descripcion);
        et_precio      = (EditText)findViewById(R.id.txt_precio);
    }

    // Metodo para dar de data a los productos
    public void registrar (View view) {
        adminSQLiteOpenHelper admin = new adminSQLiteOpenHelper(this, "administraciones", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registros = new ContentValues();
            registros.put("codigo", codigo);
            registros.put("descripcion", descripcion);
            registros.put("precio", precio);

            baseDatos.insert("articulos", null, registros);

            baseDatos.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Debes de llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    //Metodo para buscar un articulo o producto
    public void buscar (View view) {
        adminSQLiteOpenHelper admin = new adminSQLiteOpenHelper(this, "administraciones", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = baseDeDatos.rawQuery
                    ("select descripcion, precio from articulos where codigo =" + codigo, null);

            if (fila.moveToFirst()) {
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                baseDeDatos.close();

            } else {
                Toast.makeText(this, "El archivo no existe", Toast.LENGTH_SHORT).show();
                baseDeDatos.close();
            }

        } else {
            Toast.makeText(this, "Debes introducir el campo codigo para buscar el articulo", Toast.LENGTH_SHORT).show();
        }

    }
    //Metodo para eliminar algun archivo
    public void eliminar (View view) {
        adminSQLiteOpenHelper admin = new adminSQLiteOpenHelper(this, "administraciones", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()) {

            int cantidades = baseDeDatos.delete("articulos", "codigo=" + codigo, null);
            baseDeDatos.close();

            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            if(cantidades == 1) {
                Toast.makeText(this, "El archivo se a eliminado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El archivo no existe", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Debes de introducir el codigo de un articulo", Toast.LENGTH_SHORT).show();
        }
    }
    //Metodo para modoficar el codigo
    public void modificar (View view) {
        adminSQLiteOpenHelper admin = new adminSQLiteOpenHelper(this, "administraciones", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
             ContentValues registro = new ContentValues();
             registro.put("codigo", codigo);
             registro.put("descripcion", descripcion);
             registro.put("precio", precio);

             int cantidades = baseDeDatos.update("articulos", registro, "codigo=" + codigo, null);
             baseDeDatos.close();

             if (cantidades == 1) {
                 Toast.makeText(this, "Se a modificado correctamente", Toast.LENGTH_SHORT).show();
             } else {
                 Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
             }

        } else {
            Toast.makeText(this, "Debes de llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}