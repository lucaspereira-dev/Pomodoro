package com.example.pomodoro;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Alert extends AppCompatActivity {
    private static String principal = "Mensagem não Definida!", subText = "Mensagem não definida!";
    private MediaPlayer player;
    private settings conf;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);

        //Obtendo configuração
        conf = new settings(Alert.this);

        //Exibindo mensagem
        TextView ViewPrincipal = (TextView)findViewById(R.id.textAlert);
        ViewPrincipal.setText(principal);
        TextView ViewSubText = (TextView)findViewById(R.id.subtextAlert);
        ViewSubText.setText(subText);

        // Vibrar celular
        if(conf.getVibrate()){
            vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(5000);
        }

        //Configurando musica
        if(conf.getSong()){

            PlayDefault();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    player.stop();
                }
            }, 20000);
        }

        //Evento button
        Button ok = (Button)findViewById(R.id.confirmAlert);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conf.getSong()){
                    player.stop();
                }
                if(conf.getVibrate()){
                    vibrator.cancel();
                }

                Intent intent = new Intent(Alert.this, Pomodoro.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void PlayDefault(){
        player = MediaPlayer.create(Alert.this, R.raw.som);
        player.setVolume((float)conf.getVolume(), (float)conf.getVolume());

        player.start();
    }
    public static void setPrincipalText(@NonNull String mensagem){
        principal = mensagem;
    }
    public static void setSubText(@NonNull String mensagem){
        subText = mensagem;
    }
}