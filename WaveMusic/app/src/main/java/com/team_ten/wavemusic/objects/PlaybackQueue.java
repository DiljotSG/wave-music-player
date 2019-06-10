package com.team_ten.wavemusic.objects;

import java.util.ArrayList;

public class PlaybackQueue {
    private ArrayList<Song> playbackQueue;
    private Song curSong;
    private int position;

    public PlaybackQueue() {
        playbackQueue = new ArrayList<>();
        position = 0;
        curSong = null;
    }

    public void setQueue(ArrayList<Song> songList) {
        playbackQueue = songList;
    }

    public Song getCur() {
        return curSong;
    }

    public Song jumpFirst() {
        return jumpIndex(0);
    }

    public Song jumpNext() {
        curSong = null;

        if (playbackQueue.size() == 0)
            throw new ArrayIndexOutOfBoundsException(); // todo: throw EmptyQueueException?
        else {
            position++;
            if (position >= playbackQueue.size())
                position = 0;
            curSong = playbackQueue.get(position);
        }

        return curSong;
    }

    public Song jumpPrev() {
        curSong = null;

        if (playbackQueue.size() == 0)
            throw new ArrayIndexOutOfBoundsException(); // todo: throw EmptyQueueException?
        else {
            position--;
            if (position < 0)
                position = playbackQueue.size() - 1;
            curSong = playbackQueue.get(position);
        }

        return curSong;
    }

    public Song jumpSong(Song song) {
        curSong = null;

        for (position = 0; position < playbackQueue.size(); position++) {
            if (playbackQueue.get(position).equals(song)) {
                curSong = song;
                break;
            }
        }

        return curSong;
    }

    public Song jumpIndex(int index) {
        curSong = null;

        if (index < 0 || index >= playbackQueue.size()) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            position = index;
            curSong = playbackQueue.get(position);
        }

        return curSong;
    }
}
