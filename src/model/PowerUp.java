package model;


/**
 * Enumerazione che rappresenta i diversi tipi di power-up disponibili nel gioco.
 * Ogni elemento dell'enum corrisponde a un power-up specifico che può essere
 * utilizzato dai personaggi durante la partita.
 * Gli elementi dell'enum sono SpeedUp (aumento della velocità),
 * ExtraBomb (ottenimento di una bomba aggiuntiva) e IncreaseExplosion
 * (aumento dell'esplosione delle bombe).
 *
 * @author Gabriel Guerra
 */
public enum PowerUp {

    /**
     * Aumento della velocità del personaggio.
     */
    SpeedUp,

    /**
     * Ottenimento di una bomba aggiuntiva.
     */
    ExtraBomb,

    /**
     * Aumento dell'esplosione delle bombe.
     */
    IncreaseExplosion
}
