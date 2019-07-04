package com.team_ten.wavemusic.objects;

import android.media.AudioManager;
import android.provider.MediaStore;

public class AppSettings {
    private static AudioManager audioManager;

    private static int maxVolume;

    public static int getMaxVolume() {
        return maxVolume;
    }

    /**
        * Return the app's current volume.
        *
        * @return the volume as an integer from 0 to maxVolume
        */
    public static int getVolume() {
        if (audioManager == null)
            return 0;

        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
        * Set the current application volume.
        *
        */
    public static void setVolume(int newVolume) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
    }

    /**
        * Initialize the Settings class by injecting any necessary dependencies.
        *
        */
    public static void init(AudioManager newManager) {
        if (newManager == null)
            throw (new NullPointerException());

        audioManager = newManager;
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

}
