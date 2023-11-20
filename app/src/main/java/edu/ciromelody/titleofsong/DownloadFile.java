package edu.ciromelody.titleofsong;


import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;

import java.net.URL;

public class DownloadFile extends AsyncTask<URL, Integer, String> {
    String titolo;
        protected String doInBackground(URL... urls) {
            int count = urls.length;
           titolo="valore di ritorno";

            return titolo;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            //showDialog("Downloaded " + result + " bytes");
        }


    }


