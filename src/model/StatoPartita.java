package model;


/**
 * Enumerazione che rappresenta gli stati possibili di una partita.
 * Gli stati includono: Playing (in gioco), Win (vittoria), GameOver (sconfitta),
 * Title (schermata principale), Playing_StageSelect (selezione del livello in gioco).
 */
public enum StatoPartita {
    /**
     * Stato della partita durante il normale svolgimento del gioco.
     */
    Playing,
    /**
     * Stato della partita in caso di vittoria.
     */
    Win,

    /**
     * Stato della partita in caso di sconfitta.
     */
    GameOver,

    /**
     * Stato della partita durante la visualizzazione della schermata principale.
     */
    Title,
    /**
     * Stato della partita durante la selezione del livello in gioco.
     */
    Playing_StageSelect
}
