package model;


/**
 * Classe che gestisce il profilo utente, includendo il nickname, l'avatar,
 * il numero di partite giocate, vinte e perse, il livello raggiunto e i punti ottenuti.
 *
 * @author Gabriel Guerra
 */
public class Utente {

    /**
     * Nickname associato all'utente.
     */
    public String Nickname;

    /**
     * Avatar associato all'utente.
     */
    public Avatar avatar;

    /**
     * Numero totale di partite giocate dall'utente.
     */
    public int partiteGiocate;

    /**
     * Numero totale di partite vinte dall'utente.
     */
    public int partiteVinte;

    /**
     * Numero totale di partite perse dall'utente.
     */
    public int partitePerse;

    /**
     * Livello raggiunto dall'utente.
     */
    public int lastLevelArrived;

    /**
     * Punti ottenuti dall'utente.
     */
    public int puntiOttenuti;

}
