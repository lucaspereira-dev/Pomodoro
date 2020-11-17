package com.example.pomodoro;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

public class EventChronometer{
    private Chronometer _chronometerView;
    private long endCount, Count = 0;
    private boolean _isRegressive = true, pauseOff = false;

    public EventChronometer(Chronometer chronometerView){
        this._chronometerView = chronometerView;
        init();
        this._chronometerView.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (!pauseOff)
                    Count = (SystemClock.elapsedRealtime() - _chronometerView.getBase()) + (_isRegressive ? endCount : 0);
                if(_isRegressive){
                    if(Count >= endCount){
                        _chronometerView.stop();
                        Pomodoro.ViewAlert();
                    }
                }else{
                    if((SystemClock.elapsedRealtime() - _chronometerView.getBase()) >= endCount){
                        _chronometerView.stop();
                        Pomodoro.ViewAlert();
                    }
                }
                Log.i("Log time: ", "Count time: "+ String.valueOf(Count) + "| End time: " + String.valueOf(endCount));
            }
        });
    }

    public void init(){
        if(this._isRegressive){
            this._chronometerView.setCountDown(this._isRegressive);
            this._chronometerView.setBase(SystemClock.elapsedRealtime() + this.endCount);
        }else{
            this._chronometerView.setCountDown(this._isRegressive);
            this._chronometerView.setBase(SystemClock.elapsedRealtime());
        }
    }

    public void setEndCount(Long min, Long seg){
        this.endCount = (min * 60000) + (seg * 1000);
        init();
    }
    public void setIsRegressive(boolean value){
        this._isRegressive = value;
    }

    public void start() {
        init();
        this._chronometerView.start();
    }

    public void PauseResume(){
        if(this.pauseOff){
            if(this._isRegressive){
                this._chronometerView.setBase(SystemClock.elapsedRealtime() + (this.endCount - this.Count));
            }else{
                this._chronometerView.setBase(SystemClock.elapsedRealtime() - (this.Count));
            }
            this._chronometerView.start();
           this. pauseOff = false;
        }else{
           this._chronometerView.stop();
           this.pauseOff = true;
        }
    }

    public void stop() {
        this.Count = 0;
       this. _chronometerView.stop();
        init();
    }

}
