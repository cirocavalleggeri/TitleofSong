package edu.ciromelody.titleofsong;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class RicevitoreCanzone {
    Context mContext;

    public RicevitoreCanzone( Context context) {


        mContext=context;
    }

   public BroadcastReceiver registraBroadcastREceiver() {
        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");
        iF.addAction(Intent.ACTION_SCREEN_ON);
        iF.addAction(Intent.ACTION_SCREEN_OFF);
       iF.addAction(Intent.ACTION_TIME_TICK);

        mContext.registerReceiver(mReceiver, iF);
        return  mReceiver;
    }

    public  BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
          // String  cmd1 = intent.getExtras().toString();
            Log.d("mIntentReceiver.onReceive ", action + " / " + cmd+ " ");
            String artist = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            String track = intent.getStringExtra("track");
            //  Log.d("Music",artist+":"+album+":"+track);
            //artista.setText(artist);
        }
    };
}
