package edu.ciromelody.titleofsong;


import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class MainActivity extends Activity {
    private static final int MY_PERMISSIONS_RECORD_AUDIO =8 ;
    String RADIO_STATION_URL="http://icestreaming.rai.it/3.mp3";
    TextView artista;
    TextView titolo;
    Runnable runnable;
    android.media.MediaPlayer mediaplayer;
    Handler handler;
    Spinner spinner_elenco_radio;
    TextInputEditText indirizzoWebRadio;
    RicevitoreCanzone ricevitoreCanzone;
    BroadcastReceiver mReceiver;
    Button btn_startMusica;
    Button btn_stoptMusica;
    @Override
    protected void onStop() {
        super.onStop();
       /* if (mediaplayer!=null){
         mediaplayer.release();
         mediaplayer = null;
        }
       */

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaplayer!=null){
            mediaplayer.release();
            mediaplayer = null;
        }
        if(mReceiver!=null){
            unregisterReceiver(mReceiver);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
       // ricavaIndirizzoAlCambiamentoSpinner();
        AsyncTask.execute(runnable);
       //registraBroadcastREceiver();
        ricevitoreCanzone=new RicevitoreCanzone(this);
        if(mReceiver!=null){mReceiver=null;}
         mReceiver=ricevitoreCanzone.registraBroadcastREceiver();
    }


    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titolo=findViewById(R.id.id_tx_titolo);
        artista=findViewById(R.id.id_tx_artista);
        indirizzoWebRadio=findViewById(R.id.textInputEdit);
        btn_startMusica=findViewById(R.id.id_btn_start);
        btn_stoptMusica=findViewById(R.id.id_btn_stop);
        btn_stoptMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusica();
            }
        });
        btn_startMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startMusica();
            }
        });
        mediaplayer=new MediaPlayer();
        inizializzalistaRadio();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
        }else {
            spinner_elenco_radio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    RADIO_STATION_URL = parentView.getItemAtPosition(position+1).toString();

                    indirizzoWebRadio.setText(RADIO_STATION_URL);
                    AsyncTask.execute(runnable);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });





             handler = new Handler();

        }


        runnable = new Runnable() {
            @Override
            public void run() {
                //your action
                //LeggiTitoloCanzone.titleOfSong();
                try {
                    sleep(1000);
                    suonaOnline();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        //
        //
        //AsyncTask.execute(runnable);

       // ricavaIndirizzoAlCambiamentoSpinner();

    }

    private MediaPlayer.OnPreparedListener miolistenermusic =new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.getDuration();
            mp.start();
        }
    };
private void suonaOnline(){
   // inizializzalistaRadio();
    stopMusica();
    mediaplayer=new MediaPlayer();
    mediaplayer.setAudioAttributes(
            new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
    );

   mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    try {

         mediaplayer.setDataSource(RADIO_STATION_URL);
        mediaplayer.prepare();
        //mediaplayer.setOnPreparedListener(miolistenermusic);
        mediaplayer.prepareAsync();
    } catch (IllegalArgumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (SecurityException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {


            mp.start();

           // LeggiTitoloCanzone.ricavaTitoloCanzone();
        }


    });
    mediaplayer.start();

}

private void stopMusica(){
    if(mediaplayer!=null){
        if(mediaplayer.isPlaying()){mediaplayer.stop();mediaplayer.release();mediaplayer=null;}
    }else { }
}

    private void startMusica(){
        AsyncTask.execute(runnable);
    }
    private void inizializzalistaRadio()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Dati.vettoreStazioniRadio);

        spinner_elenco_radio=(Spinner)findViewById(R.id.id_spin_titolo);
        spinner_elenco_radio.setAdapter(adapter);
        RADIO_STATION_URL =   spinner_elenco_radio.getSelectedItem().toString();
        indirizzoWebRadio.setText(RADIO_STATION_URL);

    }
    private int trovaposizionespinnerradio(String vocespinner){
        for(int i=0;i<Dati.vettoreStazioniRadio.length;i++){
            Log.d("EDIT","VOCESPINNER:"+vocespinner);
            Log.d("EDIT",  spinner_elenco_radio.getItemAtPosition(i).toString() +":"+i);
            if(   spinner_elenco_radio.getItemAtPosition(i).toString().equals(vocespinner)){return i;}
        }
        return 0;}

private  void eseguiinBackground(){

        handler.post(LeggiTitoloCanzone.runnableName);


    }
}
