package com.example.serializacion1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    Button le;
    TextView t1;
    private static final int My_PERMISSION_REQUEST_WRITE_EXTERNAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        e1= (EditText) findViewById(R.id.editText);
        e2= (EditText) findViewById(R.id.editText2);
        t1 = (TextView) findViewById(R.id.text);

        le= (Button )findViewById(R.id.leer);

        Button b = (Button)  findViewById(R.id.guardar);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //Intent describe la actividad que se va a iniciar
                Intent i = new Intent(MainActivity.this,Activity2.class);
                String nombre = e1.getText().toString();
                String apellido = e2.getText().toString();

                Estudiante e = new Estudiante(nombre,apellido);
                i.putExtra("object",e);
                startActivity(i);

            }
        });

        le.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile(v);
            }
        });
    }



    public void createFile(View view){
    Intent i = getIntent();
    Bundle b=i.getExtras();
    Estudiante e = (Estudiante) b.getSerializable("object");


    File myFile = new File("/sdcard/datos.txt");
    try {
        myFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(myFile);
        OutputStreamWriter myOutWrite = new OutputStreamWriter(fout);
        myOutWrite.append(e.getNombre());
        myOutWrite.append(e.getApellido());
        myOutWrite.close();
        fout.close();
        Toast.makeText(view.getContext(), "Se guardo",Toast.LENGTH_LONG).show();



    }catch (IOException z){
       Toast.makeText(view.getContext(),z.getMessage(),Toast.LENGTH_LONG).show();

    }

}

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                       My_PERMISSION_REQUEST_WRITE_EXTERNAL
                        );
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
switch (requestCode){
    case My_PERMISSION_REQUEST_WRITE_EXTERNAL:
        if (grantResults.length > 0
            && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            //
        }else {

        } return;
}
    }

    public void readFile(View view){
        File myFile = new File("/sdcard/datos.txt");
        try{
            FileInputStream fileInputStream = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String aData="";
            String aBuffer="";
            while ((aData = myReader.readLine()) !=null){
                aBuffer += aData;
            }
            myReader.close();
            t1.setText(aBuffer + ",");
        }catch (Exception a){
            a.printStackTrace();

        }

    }
}
