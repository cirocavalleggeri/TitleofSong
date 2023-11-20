package edu.ciromelody.titleofsong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_RECORD_AUDIO =8 ;
    String RADIO_STATION_URL="http://icestreaming.rai.it/3.mp3";
    TextView artista;
    TextView titolo;
    Runnable runnable;
    android.media.MediaPlayer mediaplayer;
    Handler handler;
    @Override
    protected void onStop() {
        super.onStop();
       /* if (mediaplayer!=null){
         mediaplayer.release();
         mediaplayer = null;
        }
        if(mReceiver!=null){
            unregisterReceiver(mReceiver);
        }*/

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
       suonaOnline();
       registraBroadcastREceiver();

    }

    private void registraBroadcastREceiver() {
        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");

        registerReceiver(mReceiver, iF);
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
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
        }else {

            suonaOnline();
            registraBroadcastREceiver();


            handler = new Handler();
            handler.post(runnableName);

        }


        runnable = new Runnable() {
            @Override
            public void run() {
                //your action
                titleOfSong();
            }
        };
        //
        //
        AsyncTask.execute(runnable);



    }
    private Runnable runnableName= new Runnable() {
        @Override
        public void run() {
            //call function, do something
            Log.d("Handler", "Running Handler");
            ricavaTitoloCanzone();
            handler.postDelayed(runnableName, 2000);//this is the line that makes a runnable repeat itself

        }
    };
    private void titleOfSong( ) {
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();



        try {
            Uri uri = Uri.parse(RADIO_STATION_URL);



              /*  metaRetriever.setDataSource(getApplicationContext(),  uri);
                metaRetriever.setDataSource(RADIO_STATION_URL);
*/
                metaRetriever.setDataSource(RADIO_STATION_URL, new HashMap<String, String>());

        }catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
            System.out.println("il titolo  " + e.getMessage());
            //AsyncTask.execute(runnable);


        }

        String artist =  metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String title = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if(title!=null){
            titolo.setText(title);}
        if(artist!=null){
            artista.setText(artist);}
    }
private void suonaOnline(){
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
        //mediaplayer.prepare();
        mediaplayer.setOnPreparedListener(miolistenermusic);
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

            ricavaTitoloCanzone();
        }


    });
    //mediaplayer.start();
}
    private void ricavaTitoloCanzone() {
        if (mediaplayer.isPlaying()){
        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
        mmr.setDataSource(RADIO_STATION_URL);
        String datoGenerico= mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
        String artist= mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_AUDIO_CODEC);

        //Log.d("Music",mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM));
        Bitmap b = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST); // frame at 2 seconds
        byte [] artwork = mmr.getEmbeddedPicture();
        if(artist!=null){
           artista.setText(artist);
             }
        if(datoGenerico!=null){
            titolo.setText(datoGenerico);
        }
            Log.d("Handler", "Running Mediaplayer");
        mmr.release();
        }
    }
    private MediaPlayer.OnPreparedListener miolistenermusic =new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.getDuration();
            mp.start();
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            Log.d("mIntentReceiver.onReceive ", action + " / " + cmd);
            String artist = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            String track = intent.getStringExtra("track");
            Log.d("Music",artist+":"+album+":"+track);
            artista.setText(artist);
        }
    };
}
