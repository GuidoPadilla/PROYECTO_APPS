package com.example.proyecto.ui.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto.R;
import com.example.proyecto.ui.home.HomeViewModel;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kotlin.jvm.Throws;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private HomeViewModel homeviewmodel;
    private Button btnArchivo;
    private TextView mostrar;
    private Intent Archivo;
    private Button botonparagenerar ;
    InputStream inputStream;
    String[]ids;
    private Uri uris;
    private String escaneado;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        btnArchivo = root.findViewById(R.id.button);
        mostrar = root.findViewById(R.id.textView2);
        botonparagenerar = root.findViewById(R.id.botonparagenerar);
        btnArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Archivo = new Intent(Intent.ACTION_GET_CONTENT);
                Archivo.setType("*/*");
                startActivityForResult(Archivo, 10);
            }
        });

        homeviewmodel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        homeviewmodel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                escaneado = s;
            }
        });
        uris = null;
        botonparagenerar.setOnClickListener (new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     ArrayList<String> contenido = new ArrayList<String>();
                                                     String[]escaneadosplit = escaneado.split("\n");
                                                     /*CSVReader reader = null;
                                                     try {
                                                         File csvfile = new File(Environment.getExternalStorageDirectory() + mostrar.getText().toString());
                                                         reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                                                     } catch (FileNotFoundException e) {
                                                         e.printStackTrace();
                                                     }
                                                     try {
                                                         List myEntries = reader.readAll();
                                                     } catch (IOException e) {
                                                         e.printStackTrace();
                                                     }*/
                                                     InputStream  inputStream = null;
                                                     try {
                                                         inputStream = getContext().getContentResolver().openInputStream(uris);
                                                     } catch (FileNotFoundException e) {
                                                         e.printStackTrace();
                                                     }
                                                     /*inputStream = getResources().openRawResource(R.raw.gabrielculero);*/
                                                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                                                     try {
                                                         String csvLine;
                                                         while ((csvLine = reader.readLine()) != null) {

                                                             ids = csvLine.split(",");
                                                             contenido.add(csvLine);
                                                         }

                                                     } catch (IOException ex) {
                                                         throw new RuntimeException("Error in reading CSV file: " + ex);
                                                     }

                                                     for(int i =0; i<contenido.size(); i++){
                                                         String palabratemporal =  (contenido.get(i).substring(0, contenido.get(i).indexOf(";")));
                                                         if(i<=escaneadosplit.length-1){
                                                             if(escaneadosplit[i].indexOf(contenido.get(i).substring(0, contenido.get(i).indexOf(";"))) > -1){

                                                                 int posicion =escaneadosplit[i].indexOf(contenido.get(i).substring(0, contenido.get(i).indexOf(";")));
                                                                 String b = escaneadosplit[i].substring(palabratemporal.length(), escaneadosplit[i].length()   );
                                                                 int d = contenido.get(i).indexOf(";") +1;
                                                                 String s =contenido.get(i).substring(0,contenido.get(i).indexOf(";") +1) + escaneadosplit[i].substring(palabratemporal.length(), escaneadosplit[i].length()   );
                                                                 contenido.set(i, contenido.get(i).substring(0,contenido.get(i).indexOf(";") +1) + escaneadosplit[i].substring(palabratemporal.length(), escaneadosplit[i].length()   ) );

                                                             }
                                                         }
                                                     }

                                                 }
                                             }
        );

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch(requestCode){
           case 10:
               if(resultCode == RESULT_OK){
                   String path = data.getData().getPath();
                   mostrar.setText(path);
                   uris = data.getData();
               }

               break;
       }
    }




}
