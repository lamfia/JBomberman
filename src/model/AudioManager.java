package model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * Classe che gestisce la riproduzione audio nel gioco.
 * Utilizza il patter Singleton
 * @author Gabriel Guerra
 */
public class AudioManager {
    private static AudioManager instance;

    /**
     * Ottiene un'istanza singola della classe AudioManager utilizzando il pattern Singleton.
     *
     * @return Un'istanza di AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }
    private AudioManager() {
    }

    /**
     * Riproduce un file audio specificato dal percorso del file.
     *
     * @param filename Il percorso del file audio da riprodurre.
     */
    public void play(String filename) {
        try {
            InputStream in = new FileInputStream(filename);
            //AudioStream sound = new AudioStream(in);
            //AudioPlayer.player.start(sound);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
