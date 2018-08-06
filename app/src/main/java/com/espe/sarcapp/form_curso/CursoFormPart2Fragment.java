package com.espe.sarcapp.form_curso;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.espe.sarcapp.form_curso.comunication.DiasSemana;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CursoFormPart2Fragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Widgets XML
    private View view = null;
    private EditText tinitLunes, tinitMartes, tinitMiercoles, tinitJueves, tinitViernes;
    private EditText tendLunes, tendMartes, tendMiercoles, tendJueves, tendViernes;
    private CheckBox cbxLunes, cbxMartes, cbxMiercoles, cbxJueves, cbxViernes;
    private TextView tvDias;
    private EventBus bus = EventBus.getDefault();
    private String diasSemana;
    private int CONT_CBX_ENABLED = 0; // contador de checkboxes activos
    private ArrayList<CheckBox> arrayCbx = new ArrayList<>();

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (inflater != null) {
            view = inflater.inflate(R.layout.fragment_curso_form_part2, container, false);
        }
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
        tvDias = view.findViewById(R.id.diasSemana);
        // añadir checkboxes en arraylist
        arrayCbx.add(cbxLunes);
        arrayCbx.add(cbxMartes);
        arrayCbx.add(cbxMiercoles);
        arrayCbx.add(cbxJueves);
        arrayCbx.add(cbxViernes);
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
    public void ejecutarLlamada(DiasSemana b) {
        diasSemana = b.getDiasSemana().isEmpty() ? "0" : b.getDiasSemana();
        // Colocamos los dias por semana en el textView
        tvDias.setVisibility(View.VISIBLE);
        tvDias.setText(String.format("%s día/s", String.valueOf(diasSemana)));
        // Si los días son diferentes de "0" los checkboxes se activan
        for (int i = 0; i < arrayCbx.size() ; i++) {
            if (diasSemana.equals("0")) {
                arrayCbx.get(i).setEnabled(false);
                arrayCbx.get(i).setChecked(false);
            } else {
                arrayCbx.get(i).setEnabled(true);
            }
        }
    }

    interface OnFragmentInteractionListener { }

    private class GetTime implements View.OnClickListener {
        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("HH:mm");
        String horaFin, horaInicio;

        @Override
        public void onClick(final View v) {
            final Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            final TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Date horaObtenida;
                    try {
                        horaObtenida = df.parse(selectedHour+":"+selectedMinute);
                        mcurrentTime.setTime(horaObtenida);
                        mcurrentTime.add(Calendar.HOUR, 2);
                        horaInicio = df.format(horaObtenida.getTime());
                        horaFin = df.format(mcurrentTime.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    switch (v.getId()) {
                        case R.id.tInit_lunes: tinitLunes.setText(horaInicio);
                            tendLunes.setText(horaFin);
                            break;
                        case R.id.tInit_martes: tinitMartes.setText(horaInicio);
                            tendMartes.setText(horaFin);
                            break;
                        case R.id.tInit_miercoles: tinitMiercoles.setText(horaInicio);
                            tendMiercoles.setText(horaFin);
                            break;
                        case R.id.tInit_jueves: tinitJueves.setText(horaInicio);
                            tendJueves.setText(horaFin);
                            break;
                        case R.id.tInit_viernes: tinitViernes.setText(horaInicio);
                            tendViernes.setText(horaFin);
                            break;
                        case R.id.tEnd_lunes: tendLunes.setText(horaInicio);
                            break;
                        case R.id.tEnd_martes: tendMartes.setText(horaInicio);
                            break;
                        case R.id.tEnd_miercoles: tendMiercoles.setText(horaInicio);
                            break;
                        case R.id.tEnd_jueves: tendJueves.setText(horaInicio);
                            break;
                        case R.id.tEnd_viernes: tendViernes.setText(horaInicio);
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
            int cbxEnabled = 0;
            // -------------------------------------------------------------------------------------
            /*
             * for que va de cero al tamaño del array
             * tanto si se cumple o no la condicioón le pasamos el contador a la variable horasSemana
             * pero tiene más prioridad resultado que arroja si la condición es verdadera
             * al final es necesario resetear el contador
             */
            for (int i = 0; i < arrayCbx.size() ; i++) {
                if (arrayCbx.get(i).isChecked()) {
                    CONT_CBX_ENABLED++;
                    cbxEnabled = CONT_CBX_ENABLED;
                } else {
                    cbxEnabled = CONT_CBX_ENABLED;
                }
            }
            CONT_CBX_ENABLED = 0;
            // -------------------------------------------------------------------------------------
            /*
             * Si los diasSemana coinciden con el # de checkbox activos
             *  for que recorra toda el array y bloquee los checks restantes
             * Sino
             */
            for (int i = 0; i < arrayCbx.size() ; i++) {
                if (diasSemana.equals(String.valueOf(cbxEnabled))) {
                    if (!arrayCbx.get(i).isChecked()) {
                        arrayCbx.get(i).setEnabled(false);
                    }
                } else {
                    arrayCbx.get(i).setEnabled(true);
                }
            }
            // -------------------------------------------------------------------------------------
            if ( isChecked ) {
                switch (buttonView.getId()) {
                    case R.id.cbxLunes: activarEditTexts(tinitLunes, tendLunes, true);
                        break;
                    case R.id.cbxMartes: activarEditTexts(tinitMartes, tendMartes, true);
                        break;
                    case R.id.cbxMiercoles: activarEditTexts(tinitMiercoles, tendMiercoles, true);
                        break;
                    case R.id.cbxJueves: activarEditTexts(tinitJueves, tendJueves, true);
                        break;
                    case R.id.cbxViernes: activarEditTexts(tinitViernes, tendViernes, true);
                        break;
                }
            } else {
                switch (buttonView.getId()) {
                    case R.id.cbxLunes: activarEditTexts(tinitLunes, tendLunes, false);
                        break;
                    case R.id.cbxMartes: activarEditTexts(tinitMartes, tendMartes, false);
                        break;
                    case R.id.cbxMiercoles: activarEditTexts(tinitMiercoles, tendMiercoles, false);
                        break;
                    case R.id.cbxJueves: activarEditTexts(tinitJueves, tendJueves, false);
                        break;
                    case R.id.cbxViernes: activarEditTexts(tinitViernes, tendViernes, false);
                        break;
                }
            }
        }
    }

    private void activarEditTexts(EditText init, EditText end, boolean isChecked) {
        init.setEnabled(isChecked);
        end.setEnabled(isChecked);
        if (!isChecked) {
            init.setText(null);
            end.setText(null);
        }
    }
}
