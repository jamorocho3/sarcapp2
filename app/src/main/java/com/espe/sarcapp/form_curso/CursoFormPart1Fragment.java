package com.espe.sarcapp.form_curso;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.espe.sarcapp.R;
import com.espe.sarcapp.form_curso.comunication.DiasSemana;

import org.greenrobot.eventbus.EventBus;

public class CursoFormPart1Fragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // Widgets XML
    private EditText periodo, codigo_materia, materia, nrc, campus, docente;
    private Spinner diasSemana;
    private EventBus bus = EventBus.getDefault();

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curso_form_part1, container, false);
        // obtenemos el contexto de los elementos de la vista
        periodo = view.findViewById(R.id.periodo);
        codigo_materia = view.findViewById(R.id.cod_materia);
        materia = view.findViewById(R.id.materia);
        nrc = view.findViewById(R.id.nrc);
        diasSemana = view.findViewById(R.id.dias_por_semana_spinner);
        campus = view.findViewById(R.id.campus);
        docente = view.findViewById(R.id.docente);
        // Funcion para cargar datos en los edittext
        datosCurso(getArguments(), container.getContext());
        // Llenamos el spinner con: 1 2 3 y 4 días
            // Creamos un ArrayAdapter usando un String resource y un spinner por default
            // Especificamos el diseño que usaremos cuando aparezca la lista de opciones
            // Aplicamos el adaptador al spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(), R.array.dias_semana_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diasSemana.setAdapter(adapter);
        // Evento para extraer el día seleccionado
        diasSemana.setOnItemSelectedListener(this);


//        diasSemana.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                // Preparamos el dato que será enviado al otro fragment
//                bus.post(new DiasSemana(diasSemana.getText().toString()));
//            }
//        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void datosCurso(Bundle parametros, Context context) {
        if(parametros != null) {
            // invocar periodo
            String txtPeriodo = parametros.getString("periodo");
            periodo.setText(txtPeriodo);
            // invocar cod_materia
            String txtCodMateria = parametros.getString("cod_materia");
            codigo_materia.setText(txtCodMateria);
            // invocar materia
            String txtMateria = parametros.getString("materia");
            materia.setText(txtMateria);
            // invocar nrc
            String txtNrc = parametros.getString("nrc");
            nrc.setText(txtNrc);
            // invocar campus
            String txtCampus = parametros.getString("campus");
            campus.setText(txtCampus);
            // invocar docente
            String txtDocente = parametros.getString("docente");
            docente.setText(txtDocente);
        } else {
            Toast.makeText(context, "Algo sucedió mientras se leía la información del curso!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0: Toast.makeText(getContext(), "Seleccione el número de dias a la semana en los que tiene esta asignatura", Toast.LENGTH_LONG).show();
                break;
            case 1: Toast.makeText(getContext(), "1Selección: "+l+" y posicion: "+i, Toast.LENGTH_SHORT).show();
                break;
            case 2: Toast.makeText(getContext(), "2Selección: "+l+" y posicion: "+i, Toast.LENGTH_SHORT).show();
                break;
            case 3: Toast.makeText(getContext(), "3Selección: "+l+" y posicion: "+i, Toast.LENGTH_SHORT).show();
                break;
            case 4: Toast.makeText(getContext(), "4Selección: "+l+" y posicion: "+i, Toast.LENGTH_SHORT).show();
                break;
        }
        // Preparamos el dato que será enviado al otro fragment
        bus.post(new DiasSemana(String.valueOf(i)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnFragmentInteractionListener {
    }
}

