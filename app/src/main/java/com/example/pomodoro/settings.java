package com.example.pomodoro;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class settings {
    private final String PREFERENCE = "Settings";

    private String _PRODUTIVY = "30:00", _SHORT = "05:00", _LONG = "10:00";
    private int _VOLUME = 100;

    private SharedPreferences.Editor send;
    private SharedPreferences get;

    public settings(Context context){
        try{
            send = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit();
            get = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        }catch (Exception e){
            Toast.makeText(context, "Erro ao obter cache!", Toast.LENGTH_LONG).show();
            Log.e("Erro ao obter cache: ", e.toString());
        }
    }

    // Get
    public String getProductivityTime(){
        return get.getString("ProductivityTime", _PRODUTIVY);
    }
    public String getShortBreakTime(){
        return get.getString("ShortBreakTime", _SHORT);
    }
    public String getLongBreakTime(){
        return get.getString("LongBreakTime", _LONG);
    }
    public int getVolume(){
        return get.getInt("Volume", _VOLUME);
    }
    public boolean getSong(){ return get.getBoolean("Song", true); }
    public boolean getVibrate(){
        return get.getBoolean("Vibrate", true);
    }

    // Set
    public void setProductivityTime(String time){
        send.putString("ProductivityTime", time);
        send.commit();
    }
    public void setShortBreakTime(String time){
        send.putString("ShortBreakTime", time);
        send.commit();
    }
    public void setLongBreakTime(String time){
        send.putString("LongBreakTime", time);
        send.commit();
    }

    public void setVolume(int porcentagem){
        send.putInt("Volume", porcentagem);
        send.commit();
    }
    public void setSong(boolean status){
        send.putBoolean("Song", status);
    }
    public void setVibrate(boolean status){
        send.putBoolean("Vibrate", status);
    }

}
