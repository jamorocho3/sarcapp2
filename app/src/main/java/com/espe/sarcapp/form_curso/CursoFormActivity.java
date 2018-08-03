package com.espe.sarcapp.form_curso;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.espe.sarcapp.R;

import java.util.Objects;

public class CursoFormActivity extends AppCompatActivity implements CursoFormPart1Fragment.OnFragmentInteractionListener,
        CursoFormPart3Fragment.OnFragmentInteractionListener, CursoFormPart2Fragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    // Creamos un nuevo Bundle
    private static Bundle datosCurso = new Bundle();
    private static Bundle datosEstudiantes = new Bundle();
    private int positionanterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_form_tabbed);
        // añadimos el boton de regreso en el actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Detectar cambio de pagina
        mViewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageScrollStateChanged(int state) { }
            @Override
            public void onPageSelected(int position) {
                // dependiendo de la posición en el viewpager puedes determinar si cambio.
                if(positionanterior != position) {
                    // Cambio de pagina.
                } else {
                    //No cambio.
                }
                positionanterior = position;
            }
        });
        // recuperar datos de la activity CrearCursoActivity
        // Obtienes el Bundle del Intent
        Bundle bundle = getIntent().getExtras();
        // Colocamos los datos del curso en sus respesctivos lugares
        datosCurso.putString("periodo", Objects.requireNonNull(bundle).getString("periodo"));
        datosCurso.putString("cod_materia", bundle.getString("cod_materia"));
        datosCurso.putString("materia", bundle.getString("materia"));
        datosCurso.putString("nrc", bundle.getString("nrc"));
        datosCurso.putString("campus", bundle.getString("campus"));
        datosCurso.putString("docente", bundle.getString("docente"));
        // Colocamos el array de estudiantes en su respectivo lugar
        datosEstudiantes.putStringArrayList("estudiantes",bundle.getStringArrayList("estudiantes"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form_curso, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            // guardarCurso();
            // TODO: programar la funcion para guardar datos
            Toast.makeText(this, "Esto debe guardar los datos", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(int sectionNumber) {
            Fragment fragment = null;
            switch (sectionNumber) {
                case 1: fragment = new CursoFormPart1Fragment();
                    fragment.setArguments(datosCurso);
                    break;
                case 2: fragment = new CursoFormPart2Fragment();
                    break;
                case 3: fragment = new CursoFormPart3Fragment();
                    fragment.setArguments(datosEstudiantes);
                    break;
            }
            return fragment;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}

