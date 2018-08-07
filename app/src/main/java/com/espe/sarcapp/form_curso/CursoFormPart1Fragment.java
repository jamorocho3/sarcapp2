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
import com.espe.sarcapp.models.Curso;

import org.greenrobot.eventbus.EventBus;

public class CursoFormPart1Fragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // Widgets XML
    private EditText periodo, codigo_materia, materia, nrc, campus, docente;
    private Spinner diasSemana;
    private View view = null;
    private EventBus bus = EventBus.getDefault();
    private Curso curso;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (inflater != null) {
            view = inflater.inflate(R.layout.fragment_curso_form_part1, container, false);
        }
        // obtenemos el contexto de los elementos de la vista
        periodo = view.findViewById(R.id.periodo);
        codigo_materia = view.findViewById(R.id.cod_materia);
        materia = view.findViewById(R.id.materia);
        nrc = view.findViewById(R.id.nrc);
        diasSemana = view.findViewById(R.id.dias_por_semana_spinner);
        campus = view.findViewById(R.id.campus);
        docente = view.findViewById(R.id.docente);
        // Funcion para cargar datos en los edittext
        assert getArguments() != null;
        curso = (Curso) getArguments().getSerializable("curso");
        datosCurso(curso, container.getContext());
        // Llenamos el spinner con: 1 2 3 y 4 días
        // Creamos un ArrayAdapter usando un String resource y un spinner por default
        // Especificamos el diseño que usaremos cuando aparezca la lista de opciones
        // Aplicamos el adaptador al spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(), R.array.dias_semana_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diasSemana.setAdapter(adapter);
        // Evento para extraer el día seleccionado
        diasSemana.setOnItemSelectedListener(this);
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

    private void datosCurso(Curso curso, Context context) {
        if(curso != null) {
            periodo.setText(curso.getPeriodo());
            codigo_materia.setText(curso.getCodigo_materia());
            materia.setText(curso.getMateria());
            nrc.setText(curso.getNrc());
            campus.setText(curso.getCampus());
            docente.setText(curso.getDocente());
        } else {
            Toast.makeText(context, "Algo sucedió mientras se leía la información del curso!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            Toast.makeText(getContext(), "Seleccione el número de dias a la semana en los que tiene esta asignatura", Toast.LENGTH_LONG).show();
        }
        //le asignamos el valor a la clase Curso
        curso.setDias_semana(i);
        // Preparamos el dato que será enviado al otro fragment
        bus.post(new DiasSemana(String.valueOf(i)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    interface OnFragmentInteractionListener {
    }
}

