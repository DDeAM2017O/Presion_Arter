package com.example.jebus_vladimir.notimportant;

import android.widget.SeekBar;
import android.widget.TextView;


public class MiConjuntoSeekBar {
    public SeekBar seekB;
    private TextView textV;
    public int value, min;
    public MiConjuntoSeekBar(SeekBar.OnSeekBarChangeListener oSBCListner, SeekBar seekB, TextView textV, int value, int min)  {
        this.seekB = seekB;
        this.seekB.setProgress( value - min );
        this.seekB.setOnSeekBarChangeListener( oSBCListner );
        this.textV = textV;
        this.value = value;
        this.min = min;
    }
    public int setProgress(int value, String str)  {
        if( value - this.min < 0 )  {
            this.value = this.min;
        }  else  {
            this.value = value;
        }
        this.seekB.setProgress( this.value - min );
        if( str == null ) {
            this.textV.setText( String.valueOf( this.value ) );
        }  else  {
            this.textV.setText( str );
        }
        return this.value;
    }
}
