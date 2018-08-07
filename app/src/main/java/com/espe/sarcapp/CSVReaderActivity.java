package com.espe.sarcapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.espe.sarcapp.form_curso.CursoFormActivity;
import com.espe.sarcapp.models.Curso;
import com.espe.sarcapp.models.Estudiante;
import com.espe.sarcapp.models.ListaEstudiantes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class CSVReaderActivity extends AppCompatActivity {

    private TextView ruta;
    private Button SubirArchivo;
    private int VALOR_RETORNO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv_reader);
        // agregamos el return en la ActionBar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        //elementos del contexto
        SubirArchivo = findViewById(R.id.btnSubirCSV);
        ruta = findViewById(R.id.txtRuta);
        //eventos de botones

        SubirArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarCSV();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sincronizar_curso, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sincronizar) {
            procesarCSV();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // activar el boton sincronizar
        String route = ruta.getText().toString();
        if (route.contains("/storage/")) {
            if (route.contains(".csv")) {
                menu.findItem(R.id.action_sincronizar).setEnabled(true);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }
    // ---------------------------------------Leer datos csv----------------------------------------
    private void procesarCSV() throws RuntimeException {
        // leer el archivo csv
        String uri_csv = ruta.getText().toString();
        String charSplit = ",";
        String[] datosEstudiantes;
        BufferedReader br = null;
        ArrayList<String> resultList = new ArrayList();
        ListaEstudiantes formatListEstudiantes = new ListaEstudiantes();
        try {
            String csvLine;
            Bundle bundle = new Bundle();
            FileInputStream fis = new FileInputStream(uri_csv);
            br = new BufferedReader(new InputStreamReader(fis));
            // añadir en un arraylist todas las lineas del archivo
            while ((csvLine = br.readLine()) != null) {
                resultList.add(csvLine);
            }
            // en un array nuevo colocamos los datos de los estudiantes
            for(int i = 13; i<resultList.size(); i++) {
                if (resultList.get(i).equals(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,")) {
                    break;
                }
                datosEstudiantes = resultList.get(i).split(charSplit);
                formatListEstudiantes.add(new Estudiante(datosEstudiantes[2],datosEstudiantes[3],datosEstudiantes[4]));
            }
            // Redirigir al formulario de creacion de curso
            Intent CursoFormView = new Intent(CSVReaderActivity.this, CursoFormActivity.class);
            // Obtener info del curso
            String[] periodo = resultList.get(1).split(charSplit);
            String[] cod_materia = resultList.get(4).split(charSplit);
            String[] materia = resultList.get(4).split(charSplit);
            String[] nrc = resultList.get(5).split(charSplit);
            String[] campus = resultList.get(5).split(charSplit);
            String[] docente = resultList.get(6).split("\"");
            Curso curso = new Curso(periodo[1].substring(9), cod_materia[1], nrc[1],
                0, materia[7], campus[7], docente[1]);
            bundle.putSerializable("curso", curso);
            bundle.putParcelable("estudiantes", formatListEstudiantes);
            CursoFormView.putExtras(bundle);
            startActivity(CursoFormView);

        } catch (Exception e) {
            Toast.makeText(this, "Archivo no cumple el formato de asistencia", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(br).close();
            }
            catch (Exception e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }

    }

    // ------------------------------Abrir csv desde External Storage-------------------------------
    private void seleccionarCSV() {
        //abrir el explorador de archivos
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
        intent.setDataAndType(uri, "text/csv");
        startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
            Toast.makeText(this, "No se ha seleccionado ningún archivo .csv", Toast.LENGTH_SHORT).show();
        }
        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO)) {
            //Procesar el resultado
            Uri uri = data.getData(); //obtener el uri content
            // mostrar la ruta del archivo
            ruta.setText(Objects.requireNonNull(uri).getPath());
            // invocamos al metodo onPrepareOptionsMenu
            invalidateOptionsMenu();
        }
    }
}
