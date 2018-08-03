package com.espe.sarcapp.form_curso;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.espe.sarcapp.R;
import com.espe.sarcapp.form_curso.comunication.Prueba;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;


public class CursoFormPart2Fragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    // Widgets XML
    private View view;
    private EditText tinitLunes, tinitMartes, tinitMiercoles, tinitJueves, tinitViernes;
    private EditText tendLunes, tendMartes, tendMiercoles, tendJueves, tendViernes;
    private CheckBox cbxLunes, cbxMartes, cbxMiercoles, cbxJueves, cbxViernes;
    private TextView prueba;
    private EventBus bus = EventBus.getDefault();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_curso_form_part2, container, false);
        // -----------------------------------------------------------------------------------------
        // obtenemos el contexto de los elementos de la vista
        // Hora inicio
        tinitLunes = view.findViewById(R.id.tInit_lunes);
        tinitMartes = view.findViewById(R.id.tInit_martes);
        tinitMiercoles = view.findViewById(R.id.tInit_miercoles);
        tinitJueves = view.findViewById(R.id.tInit_jueves);
        tinitViernes = view.findViewById(R.id.tInit_viernes);
        // Hora salida
        tendLunes = view.findViewById(R.id.tEnd_lunes);
        tendMartes = view.findViewById(R.id.tEnd_martes);
        tendMiercoles = view.findViewById(R.id.tEnd_miercoles);
        tendJueves = view.findViewById(R.id.tEnd_jueves);
        tendViernes = view.findViewById(R.id.tEnd_viernes);
        // checkboxes
        cbxLunes = view.findViewById(R.id.cbxLunes);
        cbxMartes = view.findViewById(R.id.cbxMartes);
        cbxMiercoles = view.findViewById(R.id.cbxMiercoles);
        cbxJueves = view.findViewById(R.id.cbxJueves);
        cbxViernes = view.findViewById(R.id.cbxViernes);
            // añadir checkboxes en arraylist
        ArrayList<CheckBox> arrayCbx = new ArrayList<>();
        arrayCbx.add(cbxLunes);
        arrayCbx.add(cbxMartes);
        arrayCbx.add(cbxMiercoles);
        arrayCbx.add(cbxJueves);
        arrayCbx.add(cbxViernes);
        // ----------------------------------------------------------------------------------------
        // EditTexts events
        tinitLunes.setOnClickListener(new GetTime());
        tinitMartes.setOnClickListener(new GetTime());
        tinitMiercoles.setOnClickListener(new GetTime());
        tinitJueves.setOnClickListener(new GetTime());
        tinitViernes.setOnClickListener(new GetTime());

        tendLunes.setOnClickListener(new GetTime());
        tendMartes.setOnClickListener(new GetTime());
        tendMiercoles.setOnClickListener(new GetTime());
        tendJueves.setOnClickListener(new GetTime());
        tendViernes.setOnClickListener(new GetTime());
        // CheckBox events
        cbxLunes.setOnCheckedChangeListener(new selectDay());
        cbxMartes.setOnCheckedChangeListener(new selectDay());
        cbxMiercoles.setOnCheckedChangeListener(new selectDay());
        cbxJueves.setOnCheckedChangeListener(new selectDay());
        cbxViernes.setOnCheckedChangeListener(new selectDay());
        // -----------------------------------------------------------------------------------------
        prueba = view.findViewById(R.id.diasSemana);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ejecutarLlamada(Prueba b) {
        prueba.setText("Horas: "+b.getHorasSemana());
    }

    public interface OnFragmentInteractionListener { }

    private class GetTime implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String hour;
                    if(selectedMinute == 0 ) { hour = selectedHour + ":00"; }
                    else if(selectedMinute < 10) { hour = selectedHour + ":0" + selectedMinute; }
                    else { hour = selectedHour + ":" + selectedMinute; }
                    switch (v.getId()) {
                        case R.id.tInit_lunes:
                            tinitLunes.setText(hour);
                            break;
                        case R.id.tInit_martes:
                            tinitMartes.setText(hour);
                            break;
                        case R.id.tInit_miercoles:
                            tinitMiercoles.setText(hour);
                            break;
                        case R.id.tInit_jueves:
                            tinitJueves.setText(hour);
                            break;
                        case R.id.tInit_viernes:
                            tinitViernes.setText(hour);
                            break;
                        case R.id.tEnd_lunes:
                            tendLunes.setText(hour);
                            break;
                        case R.id.tEnd_martes:
                            tendMartes.setText(hour);
                            break;
                        case R.id.tEnd_miercoles:
                            tendMiercoles.setText(hour);
                            break;
                        case R.id.tEnd_jueves:
                            tendJueves.setText(hour);
                            break;
                        case R.id.tEnd_viernes:
                            tendViernes.setText(hour);
                            break;
                    }
                }
            }, hour, minute, true);// Yes 24 hour time
            mTimePicker.setTitle("Seleccione la hora");
            mTimePicker.show();
        }
    }

    private class selectDay implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if ( isChecked ) {
                switch (buttonView.getId()) {
                    case R.id.cbxLunes:
                        tinitLunes.setEnabled(true);
                        tendLunes.setEnabled(true);
                        break;
                    case R.id.cbxMartes:
                        tinitMartes.setEnabled(true);
                        tendMartes.setEnabled(true);
                        break;
                    case R.id.cbxMiercoles:
                        tinitMiercoles.setEnabled(true);
                        tendMiercoles.setEnabled(true);
                        break;
                    case R.id.cbxJueves:
                        tinitJueves.setEnabled(true);
                        tendJueves.setEnabled(true);
                        break;
                    case R.id.cbxViernes:
                        tinitViernes.setEnabled(true);
                        tendViernes.setEnabled(true);
                        break;
                }
            } else {
                switch (buttonView.getId()) {
                    case R.id.cbxLunes:
                        tinitLunes.setEnabled(false);
                        tendLunes.setEnabled(false);
                        tinitLunes.setText(null);
                        tendLunes.setText(null);
                        break;
                    case R.id.cbxMartes:
                        tinitMartes.setEnabled(false);
                        tendMartes.setEnabled(false);
                        tinitMartes.setText(null);
                        tendMartes.setText(null);
                        break;
                    case R.id.cbxMiercoles:
                        tinitMiercoles.setEnabled(false);
                        tendMiercoles.setEnabled(false);
                        tinitMiercoles.setText(null);
                        tendMiercoles.setText(null);
                        break;
                    case R.id.cbxJueves:
                        tinitJueves.setEnabled(false);
                        tendJueves.setEnabled(false);
                        tinitJueves.setText(null);
                        tendJueves.setText(null);
                        break;
                    case R.id.cbxViernes:
                        tinitViernes.setEnabled(false);
                        tendViernes.setEnabled(false);
                        tinitViernes.setText(null);
                        tendViernes.setText(null);
                        break;
                }
            }

        }
    }
}
