package model;

import controller.Direzione;

public  class EnemicoAttacante extends Enemico {


    /**
     * la sua creazione è uguale a quello normale, aggiungere solo il metodo per attacco
     * , cambia solo gli sprites e hitbox,
     * TODO aggiungere metodo atacco enemico,
     */


    public EnemicoAttacante(int posX, int posY, int Salute, int velocita, int width, int height, Direzione direzioneIniziale) {
        super(posX, posY, Salute, velocita, width, height,direzioneIniziale);
    }



}
