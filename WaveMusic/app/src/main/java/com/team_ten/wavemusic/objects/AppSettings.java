package com.team_ten.wavemusic.objects;

import android.media.AudioManager;
import android.provider.MediaStore;

public class AppSettings {
    private static AudioManager audioManager;

    private static int maxVolume;

    public static int getMaxVolume() {
        return maxVolume;
    }

    public static int getVolume() {
        if (audioManager == null)
            return 0;

        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public static void setVolume(int newVolume) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
    }

    public static void init(AudioManager newManager) {
        if (newManager == null)
            throw (new NullPointerException());

        audioManager = newManager;
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

}
