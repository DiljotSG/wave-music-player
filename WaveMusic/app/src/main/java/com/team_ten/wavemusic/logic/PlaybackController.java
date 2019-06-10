package com.team_ten.wavemusic.logic;

import android.media.MediaPlayer;
import android.media.AudioManager;

import com.team_ten.wavemusic.objects.PlaybackQueue;
import com.team_ten.wavemusic.objects.Song;

import java.io.IOException;
import java.util.ArrayList;


// Enumeration for the current audio playback state
enum PlaybackState {
    PAUSED,
    PLAYING
}

// Enumeration for the current method of repeating songs
enum PlaybackMode {
    PLAY_ALL,
    LOOP_ALL,
    LOOP_ONE;

    public PlaybackMode nextMode() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}

// A controller which allows other classes to control audio playback.
public class PlaybackController {
    private static PlaybackQueue playbackQueue;
    private static PlaybackState state;
    private static PlaybackMode playbackMode;
    private static MediaPlayer mediaPlayer;
    private static boolean initialized;

    /**
     * Purpose: initializes the media player of the playback controller
     */

    public static void init() {
        if (!initialized) {
            initialized = true;
            playbackQueue = new PlaybackQueue();
            state = PlaybackState.PAUSED;
            playbackMode = PlaybackMode.PLAY_ALL;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }

    /**
     * Purpose: initializes the media player of the playback controller for test methods (no library calls)
     */

    public static void init_for_testing() {
        if (!initialized) {
            initialized = true;
            playbackQueue = new PlaybackQueue();
            state = PlaybackState.PAUSED;
            playbackMode = PlaybackMode.PLAY_ALL;
        }
    }

    /**
     * Purpose: returns the playback state as an integer; intended mainly for unit testing
     *
     * @returns: an integer representation of the current playback mode
     */

    public static int get_playback_state_num() {
        return playbackMode.ordinal();
    }

    /**
     * Purpose: returns the number of playback modes as an integer, generally for unit testing
     *
     * @returns: an integer representation of the number of available playback modes
     */

    public static int get_num_playback_states() {
        return PlaybackMode.values().length;
    }

    /**
     * Purpose: stop audio from playing
     */
    public static void pause() {
        mediaPlayer.pause();
        state = PlaybackState.PAUSED;
    }

    /**
     * Purpose: start playing audio
     */
    public static Song startSong() {
        if (playbackQueue.getCur() != null)
            mediaPlayer.start();
        else
            PlaybackController.startSong(playbackQueue.jumpFirst());

        state = PlaybackState.PLAYING;
        return getCur();
    }

    /**
     * Purpose: Move to next song repeat mode
     */
    public static void toggleLoopMode() {
        if (playbackMode != null)
            playbackMode = playbackMode.nextMode();
        // todo: exceptions etc. for null mode
    }

    /**
     * Purpose: start playing a specific song
     */
    public static Song startSong(Song song) {
        if (mediaPlayer == null) {
            // raise exception
        } else if (song == null) {
            state = PlaybackState.PAUSED;
        } else {
            // todo: When playing a new song, we should call playbackQueue.setQueue() on the current filtered library
            if (playbackQueue.getCur() == null || !(playbackQueue.getCur().equals(song)))
                playbackQueue.jumpSong(song);

            // todo: increment play count of curSong
            state = PlaybackState.PLAYING;
            mediaPlayer.reset();

            try {
                mediaPlayer.setDataSource(song.getURI());
            } catch (IllegalArgumentException e) {
                // todo: handle exception
                System.out.printf("Illegal argument playing <%s>.", song.getURI());
                System.out.println(e);
            } catch (IOException e) {
                // todo: handle exception
                System.out.printf("IO Exception playing <%s>.", song.getURI());
                System.out.println(e);
            }

            try {
                mediaPlayer.prepare();
            } catch (Exception e) {
                // todo: properly handle exception
                System.out.println("Exception while preparing audio.");
                System.out.println(e);
            }

            try {
                mediaPlayer.start();
            } catch (Exception e) {
                // todo: properly handle exception
                System.out.println("Exception while starting audio.");
                System.out.println(e);
            }
        }

        return getCur();
    }


    /**
     * Purpose: start playing the next song in the playback queue
     */
    public static Song playNext() {
        boolean was_paused = state == PlaybackState.PAUSED;

        startSong(playbackQueue.jumpNext());

        if (was_paused)
            pause();

        return getCur();
    }

    /**
     * Purpose: start playing the previous song in the playback queue
     */
    public static Song playPrev() {
        boolean was_paused = state == PlaybackState.PAUSED;

        startSong(playbackQueue.jumpPrev());

        if (was_paused)
            pause();

        return getCur();
    }

    /**
     * Purpose: start current song from beginning
     */
    public static Song restart() {
        boolean was_paused = state == PlaybackState.PAUSED;

        startSong(playbackQueue.getCur());

        if (was_paused)
            pause();

        return getCur();
    }

    /**
     * Purpose: set the current queue of songs to play
     */
    public static void setPlaybackQueue(ArrayList<Song> newQueue) {
        playbackQueue.setQueue(newQueue);
    }

    /**
     * Purpose: return the currently playing song
     */
    public static Song getCur() {
        return playbackQueue.getCur();
    }
}
