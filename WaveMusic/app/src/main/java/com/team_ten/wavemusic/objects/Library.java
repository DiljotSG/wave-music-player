package com.team_ten.wavemusic.objects;

import java.util.ArrayList;

public class Library {
    private static ArrayList<Song> fullLibrary = new ArrayList<>();
    private static ArrayList<Song> curLibrary = new ArrayList<>();

    public static void setFullLibrary(ArrayList<Song> newLibrary) {
        if (newLibrary == null)
            System.out.println("[!] Tried setting fulllibrary to null; ignoring request");
        else
           fullLibrary = newLibrary;
    }

    public static void setCurLibrary(ArrayList<Song> newLibrary) {
        if (newLibrary == null)
            System.out.println("[!] Tried setting current library to null; ignoring request");
        else
           curLibrary = newLibrary;
    }

    public static ArrayList<Song> getFullLibrary() {
        return fullLibrary;
    }

    public static ArrayList<Song> getCurLibrary() {
        return curLibrary;
    }
}
