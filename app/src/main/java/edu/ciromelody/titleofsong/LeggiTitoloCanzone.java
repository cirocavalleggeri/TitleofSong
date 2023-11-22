package edu.ciromelody.titleofsong;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.logging.Handler;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class LeggiTitoloCanzone {



   public static void titleOfSong(String  RADIO_STATION_URL, TextView titolo,TextView artista) {
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
    public static void  ricavaTitoloCanzone(MediaPlayer mediaplayer,String RADIO_STATION_URL,TextView titolo,TextView artista) {
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

public static Runnable runnableName= new Runnable() {
        @Override
        public void run() {
            //call function, do something
            Log.d("Handler", "Running Handler");
           // LeggiTitoloCanzone.ricavaTitoloCanzone();
            //handler.postDelayed(runnableName, 2000);//this is the line that makes a runnable repeat itself

        }
    };
/*    private void ricavaIndirizzoAlCambiamentoSpinner(){
        spinner_elenco_radio.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        RADIO_STATION_URL = parent.getItemAtPosition(pos).toString();

                        indirizzoWebRadio.setText(RADIO_STATION_URL);
                        // System.out.println(item.toString());     //prints the text in spinner item.
                        suonaOnline();

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }*/

}
