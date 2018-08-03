package com.espe.sarcapp.form_curso;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.espe.sarcapp.R;
import com.espe.sarcapp.form_curso.comunication.Prueba;

import org.greenrobot.eventbus.EventBus;

public class CursoFormPart1Fragment extends Fragment {

    // Widgets XML
    private EditText periodo, codigo_materia, materia, nrc, horas, campus, docente;
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
        horas = view.findViewById(R.id.horas);
        campus = view.findViewById(R.id.campus);
        docente = view.findViewById(R.id.docente);
        // Funcion para cargar datos en los edittext
        datosCurso(getArguments(), container.getContext());
        // TextWatcher en el horas
                //bus.post(new Prueba("2"));
        horas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                // Preparamos el dato que será enviado al otro fragment
                bus.post(new Prueba(horas.getText().toString()));
            }
        });
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

    public interface OnFragmentInteractionListener {
    }
}

