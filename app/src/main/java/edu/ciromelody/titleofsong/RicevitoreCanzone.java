package edu.ciromelody.titleofsong;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class RicevitoreCanzone {
    Context mContext;
    public RicevitoreCanzone(BroadcastReceiver mReceiver, Context context) {
        this.mReceiver = mReceiver;
        registraBroadcastREceiver();
        mContext=context;
    }

    private void registraBroadcastREceiver() {
        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");

        mContext.registerReceiver(mReceiver, iF);
    }

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
            //artista.setText(artist);
        }
    };
}
