package controller;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.*;

/**
 * Classe Singleton che gestisce l'audio dell'applicazione.
 * Permette di riprodurre suoni in modo asincrono tramite un indice.
 *
 * @author Gabriel Guerra
 */

public class AudioManager {
    private static AudioManager instance;

    /**
     * Enabler della musica
     */
    public boolean enable = true;

    Clip clip;

    private ArrayList<String> pathSounds= new ArrayList<>();


    /**
     * Restituisce l'istanza unica di AudioManager.
     *
     * @return L'istanza di AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    private AudioManager() {
        pathSounds.add("src/view/res/sound/BackgroundMap1.wav"); //0
        pathSounds.add("src/view/res/sound/BombExplodes.wav");   //1
        pathSounds.add("src/view/res/sound/Item Get.wav");   //2
    }

    /**
     * Imposta il Clip per il suono con l'indice specificato.
     *
     * @param i L'indice del suono.
     */
    public void setClip(int i) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(pathSounds.get(i)).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audio);

        } catch (Exception e) {
        }
    }

    /**
     * Riproduce del effeto di suono indicato
     * dal parametro
     * @param index L'indice del suono.
     */
    public void playSE(int index){

        setClip(index);
        play();
    }

    /**
     * Riproduce una musica di sottofondo con
     * l'indice specificato.
     *
     * @param index L'indice della musica da riprodurre.
     */
    public void playMusic(int index){
        setClip(index);
        play();
        loop();
    }

    /**
     * Interrompe la riproduzione della musica corrente.
     */
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