package com.example.pomodoro;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Activitysettings extends AppCompatActivity{

    private static Context context;

    private Toolbar toolbar;
    private Button saveConf;
    private EditText _Productivity, _Short, _long;
    private CheckBox _Som, _Vibrate;
    private SeekBar _Volume;
    private settings getSettings;

    public static Context getContext(){
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        context = getApplicationContext();
        getSettings = new settings(getContext());


        //Inicio toolbar
        toolbar = findViewById(R.id.toolbarSettings);


        TextView title = (TextView)findViewById(R.id.toolbarTitle);
        title.setText(R.string.settings);

        setupToolbar();
        //Fim toolbar

        //Event Buttons

        // Save Activitysettings button
        saveConf = (Button) findViewById(R.id.saveConf);
        saveConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Configurações salvas com sucesso!", Toast.LENGTH_LONG).show();

                getSettings.setProductivityTime(_Productivity.getText().toString());
                getSettings.setShortBreakTime(_Short.getText().toString());
                getSettings.setLongBreakTime(_long.getText().toString());
                getSettings.setVolume(_Volume.getProgress());
                getSettings.setSong(_Som.isChecked());
                getSettings.setVibrate(_Vibrate.isChecked());


            }
        });
        //End Event buttons

        //Tempo de produção
        _Productivity = (EditText) findViewById(R.id.productivityTime);
        _Productivity.setText(getSettings.getProductivityTime());
        //Fim tempo de produção

        //Tempo de parada curta
        _Short = (EditText) findViewById(R.id.shortTime);
        _Short.setText(getSettings.getShortBreakTime());
        //Fim tempo de parada curta

        //Tempo de para longa
        _long = (EditText) findViewById(R.id.longTime);
        _long.setText(getSettings.getLongBreakTime());
        // Fim tempo de parada longa


        //Controle de volume
        _Volume = (SeekBar) findViewById(R.id.songHeight);
        _Volume.setProgress(getSettings.getVolume());
        //Fim do controle de volume


        // Verificação de notificação
        _Som = (CheckBox) findViewById(R.id.unmute);
        _Som.setChecked(getSettings.getSong());

        _Vibrate = (CheckBox) findViewById(R.id.vibrate);
        _Vibrate.setChecked(getSettings.getVibrate());
        // Fim verificação de notificação

    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);;
        ActionBar ab = getSupportActionBar();

        //Desativar titulo do toolbar
        ab.setDisplayShowTitleEnabled(false);
        //Toolbar aceitar constomização
        ab.setDisplayShowCustomEnabled(true);
        //Ativar button return
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        //Base pro código: https://pt.stackoverflow.com/questions/251116/como-centralizar-a-string-do-titulo-da-action-bar-e-como-alterar-o-tamanho-dela
        View viewTitle = getLayoutInflater().inflate(R.layout.toolbartitle, null);

        ActionBar.LayoutParams viewParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        //Operação bitwise para gravity
        viewParams.gravity = viewParams.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;

        ab.setCustomView(viewTitle, viewParams);
        ab.setDisplayOptions(
                ab.getDisplayOptions(),
                ActionBar.DISPLAY_SHOW_CUSTOM
        );
    }

}
