package com.example.serializacion1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Activity2  extends AppCompatActivity {

    TextView t1,t2;
    Button w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_2);

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        w = (Button)findViewById(R.id.guardar1);

        Intent i = getIntent();
        Bundle b=i.getExtras();
        Estudiante e = (Estudiante) b.getSerializable("object");

        t1.setText(e.getNombre()) ;
        t2.setText(e.getApellido());

        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFile(v);
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

}