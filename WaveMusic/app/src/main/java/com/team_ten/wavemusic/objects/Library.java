package com.team_ten.wavemusic.objects;

import java.util.ArrayList;

public class Library
{
    private static ArrayList<Song> fullSongLibrary = new ArrayList<>();
    private static ArrayList<Song> curSongLibrary = new ArrayList<>();
    private static ArrayList<String> curStringLibrary = new ArrayList<>();

    public static void setFullSongLibrary(ArrayList<Song> newLibrary)
    {
        if (newLibrary == null)
        {
            System.out.println("[!] Tried setting fulllibrary to null; ignoring request");
        }
        else
        {
            fullSongLibrary = newLibrary;
        }
    }

    public static void setCurSongLibrary(ArrayList<Song> newLibrary)
    {
        if (newLibrary == null)
        {
            System.out.println("[!] Tried setting current library to null; ignoring request");
        }
        else
        {
            curSongLibrary = newLibrary;
        }
    }

    public static void setCurStringLibrary(ArrayList<String> newLibrary)
    {
        if (newLibrary == null)
        {
            System.out.println("[!] Tried setting current library to null; ignoring request");
        }
        else
        {
            curStringLibrary = newLibrary;
        }
    }

    public static ArrayList<Song> getFullSongLibrary()
    {
        return fullSongLibrary;
    }

    public static ArrayList<String> getCurStringLibrary()
    {
        return curStringLibrary;
    }

    public static ArrayList<Song> getCurSongLibrary()
    {
        return curSongLibrary;
    }
}

