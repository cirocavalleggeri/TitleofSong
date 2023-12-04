package edu.ciromelody.titleofsong;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;



public class TraduciInTesto {
    Context context;

    public TraduciInTesto(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String traduciFileAudio(String percorsofile) throws Exception {
       String testoCanzone=" ";
        /* // Instantiates a client
        SpeechClient speech = null;
        try {
            speech = SpeechClient.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // The path to the audio file to transcribe
        String fileName = "./resources/audio.raw";

        // Reads the audio file into memory
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteString audioBytes = ByteString.copyFrom(data);

        // Builds the sync recognize request
        RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setSampleRateHertz(16000)
                .setLanguageCode("en-US")
                .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder()
                .setContent(audioBytes)
                .build();

        // Performs speech recognition on the audio file
        RecognizeResponse response = speech.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();

        for (SpeechRecognitionResult result: results) {
            // There can be several alternative transcripts for a given chunk of speech. Just use the
            // first (most likely) one here.
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            System.out.printf("Transcription: %s%n", alternative.getTranscript());

        }
        speech.close();*/

        return testoCanzone;
    }
}
