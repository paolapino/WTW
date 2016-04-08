package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import epiphany_soft.wtw.Activities.Series.ActivityConsultarSerie;
import epiphany_soft.wtw.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickConsultar(View v){
        Intent i = new Intent(this, ActivityConsultarPelicula.class);
        startActivity(i);
    }

    public void onClickConsultarSerie(View v){
        Intent i = new Intent(this, ActivityConsultarSerie.class);
        startActivity(i);
    }

    public void onClickAgregarGenero(View v){
        Intent i = new Intent(this, ActivityAgregarGenero.class);
        startActivity(i);
    }

    public void onClickAgregarPrograma(View v){
        Intent i = new Intent(this, ActivityRegistrarPrograma.class);
        startActivity(i);
    }


    public void onClickBtnEliminarGenero(View v){
        Intent i = new Intent(this,ActivityEliminarGenero.class);
        startActivity(i);
    }


    public void onClickAgregarUsuario(View v){
        Intent i = new Intent(this, ActivityAgregarUsuario.class);
        startActivity(i);
    }



}
