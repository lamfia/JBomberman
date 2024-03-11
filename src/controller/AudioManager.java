package controller;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.*;

/**
 * Classe SingleTon
 * Questa classe serve per riprodurre asincronamente i diversi
 * suoni tramite un indice
 * che li identifica
 * @author Gabriel Guerra
 */
public class AudioManager {
    private static AudioManager instance;

    public boolean enable = true;

    private ArrayList<String> pathSounds= new ArrayList<>();

    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    Clip clip;

    private AudioManager() {
        pathSounds.add("src/view/res/sound/BackgroundMap1.wav"); //0
        pathSounds.add("src/view/res/sound/BombExplodes.wav");   //1
        pathSounds.add("src/view/res/sound/Item Get.wav");   //2
    }

    public void setClip(int i) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(pathSounds.get(i)).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audio);

        } catch (Exception e) {
        }
    }

    public void playSE(int index){

        setClip(index);
        play();
    }

    public void playMusic(int index){
        setClip(index);
        play();
        loop();
    }

    public void stopMusic() {
        clip.stop();
    }

    private void play() {

        if (enable == true) {
            clip.start();
        }
    }

    private void loop() {
        if (enable == true) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

}