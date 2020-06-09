package com.chess.gui;

import javax.sound.sampled.*;
import java.io.*;

public class MusicPlayer {

    public static void playMusic(String path) {
        InputStream music;
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
