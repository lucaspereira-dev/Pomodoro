package com.example.pomodoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class Pomodoro extends AppCompatActivity {

    private TabHost tabs;
    private Toolbar toolbar;
    private settings getSettings;
    private EventChronometer cronometro;
    private TextView status_message, title;
    private Button play_button, stop_button;
    private boolean Running = false, RunningImg = true;
    private boolean tabProductivity, tabShort, tabLong;

    private static Context context;
    private static int count = 0, total_shortPause = 4;
    private static String PrincipalMensagem = null, SubMensagem = null;
    private static boolean runProductivity = true, runShort = false, runLong = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pomodoro);

        //get context
        context = getBaseContext();

        //Carregando configurações de tempo do pomodoro
        getSettings = new settings(Pomodoro.this);


        //Instanciando configurações para toolbar
        toolbar = findViewById(R.id.toolbarPomodoro);

        //Nomeando titulo de acordo com a activity
        title = (TextView)findViewById(R.id.toolbarTitle);
        title.setText(R.string.app_name);

        setupToolbar();

        //Fim toolbar


        //Obtendo tabs
        tabs = (TabHost) findViewById(R.id.tabHost);

        // Obtendo eventos para botões que foi criada em PomodoroEventButtons

        //Botão Play
        play_button = (Button) findViewById(R.id.play_button);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(runProductivity == tabProductivity && runShort == tabShort && runLong == tabLong){
                    status_message.setText("");

                    if (!Running) {
                        play_button.setBackground(ContextCompat.getDrawable(Pomodoro.this, R.drawable.pausebutton));
                        cronometro.start();
                        Running = true;

                    } else {
                        if (RunningImg) {
                            play_button.setBackground(ContextCompat.getDrawable(Pomodoro.this, R.drawable.playbutton));
                            RunningImg = false;
                        } else {
                            play_button.setBackground(ContextCompat.getDrawable(Pomodoro.this, R.drawable.pausebutton));
                            RunningImg = true;
                        }
                        cronometro.PauseResume();
                    }
                }else{
                    status_message.setText(SubMensagem);
                }
            }
        });

        // Stop button
        stop_button = (Button) findViewById(R.id.stop_button);
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                play_button.setBackground(ContextCompat.getDrawable(Pomodoro.this, R.drawable.playbutton));
                cronometro.stop();
                Running = false;
                RunningImg = true;
            }
        });

        //Fim buttons

        status_message = (TextView) findViewById(R.id.status_message);

        tabs.setup();
        //Configuração Aba 1 Pomodoro
        TabHost.TabSpec productivity = tabs.newTabSpec("aba1").setContent(R.id.stopwatch).setIndicator("Productivity");

        //Configuração Aba 2 Short break
        TabHost.TabSpec short_break = tabs.newTabSpec("aba2").setContent(R.id.stopwatch).setIndicator("Short Break");

        //Configuração Aba 3 Long break
        TabHost.TabSpec long_break = tabs.newTabSpec("aba3").setContent(R.id.stopwatch).setIndicator("Long Break");

        //Adicionando abas
        tabs.addTab(productivity);
        tabs.addTab(short_break);
        tabs.addTab(long_break);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabName){
                switch(tabName){
                    case "aba1":
                        Productivity();
                        break;
                    case "aba2":
                        ShortBreak();
                        break;
                    case "aba3":
                        LongBreak();
                        break;
                    default:
                        //Erro
                        Toast.makeText(Pomodoro.this, "Error: Valor de tab desconhecido!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        //Fim tabs

        //Chronometer
        cronometro = new EventChronometer((Chronometer)findViewById(R.id.time));
        Productivity();
        //Fim chronometer

    }

    // Definindo o tempo que ira percorrer
    private void Productivity(){
        String[] values = getSettings.getProductivityTime().split(":");
        cronometro.setEndCount(Long.parseLong(values[0]), Long.parseLong(values[1]));

        tabProductivity = true;

        tabShort = false;
        tabLong = false;
    }
    private void ShortBreak(){
        String[] values = getSettings.getShortBreakTime().split(":");
        cronometro.setEndCount(Long.parseLong(values[0]), Long.parseLong(values[1]));

        tabShort = true;

        tabProductivity = false;
        tabLong = false;
    }
    private void LongBreak(){
        String[] values = getSettings.getLongBreakTime().split(":");
        cronometro.setEndCount(Long.parseLong(values[0]), Long.parseLong(values[1]));

        tabLong = true;

        tabProductivity = false;
        tabShort = false;
    }
    //End

    private void setupToolbar(){
        //Base pro código: https://pt.stackoverflow.com/questions/251116/como-centralizar-a-string-do-titulo-da-action-bar-e-como-alterar-o-tamanho-dela

        setSupportActionBar(toolbar);;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        View viewTitle = getLayoutInflater().inflate(R.layout.toolbartitle, null);

        ActionBar.LayoutParams viewParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        //Operação bitwise para gravity
        viewParams.gravity = viewParams.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;

        getSupportActionBar().setCustomView(viewTitle, viewParams);
        getSupportActionBar().setDisplayOptions(
                getSupportActionBar().getDisplayOptions(),
                ActionBar.DISPLAY_SHOW_CUSTOM
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:{
                Intent intent = new Intent(Pomodoro.this, Activitysettings.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static void ViewAlert(){
        if(runProductivity){
            //Desativo o tempo de produção
            runProductivity = false;
            // Insiro a mensagem de finalizado
            PrincipalMensagem = "Finalized Productivity!";

            //Verifica se a contagem de pausas curtas continuam sendo menor do que o limite
            if(count < total_shortPause-1){
                //Caso ainda menor recomendo a pausa curta
                SubMensagem = "Take a short break";

                //Ativo modo de pausa curta
                runShort = true;
            }else{
                //Caso seja maior recomendo a pausa longa
                SubMensagem = "Take a long break";

                //Ativo modo de pausa longa
                runLong = true;
            }
        }else if(runShort){
            //Desativo a pausa curta
            runShort = false;
            //Faço a contagem
            count++;
            //Insiro a mensagem de finalizado
            PrincipalMensagem = "Finalized Short Break!";
            //Insiro a quantidade de pausas curtas
            SubMensagem = "Count short break: " + String.valueOf(count);

            //Ativo modo de produtividade
            runProductivity = true;

        }else if(runLong){
            //Desativo a pausa longa
            runLong = false;
            //Insiro a mensagem de finalizado
            PrincipalMensagem = "Finalized Long Break!";
            //Insiro a mensagem para voltar a produtividade
            SubMensagem = "Get back to productivity";
            //Zero a contagem da pausas curtas
            count = 0;
            //Ativo o modo produtivo
            runProductivity = true;
        }

        Alert.setPrincipalText(PrincipalMensagem);
        Alert.setSubText(SubMensagem);

        Intent intent = new Intent(context, Alert.class);
        context.startActivity(intent);
    }

}