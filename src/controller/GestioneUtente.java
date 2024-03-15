package controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Avatar;
import model.Utente;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Classe che gestisce le operazioni
 * sugli utenti, come il salvataggio,
 * il caricamento e la modifica.
 *
 * @author Gabriel Guerra
 */
public class GestioneUtente {


    private String filePathJson = "src/ConfigUtente/output.json";


    /**
     * Salva la lista degli utenti in formato JSON su un file.
     *
     * @param utenti La lista degli utenti da salvare
     */
    public void salvaUtenti(ArrayList<Utente> utenti) {

        // Creare un oggetto Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convertire l'oggetto in formato JSON
        String jsonString = gson.toJson(utenti);

        // Scrivere il JSON su un file
        try (FileWriter fileWriter = new FileWriter(filePathJson)) {

            fileWriter.write(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Salva le modifiche apportate a un utente specifico.
     *
     * @param utente_input L'utente con le modifiche da salvare
     */
    public void salvaModificheUtente(Utente utente_input) {

        var listaUtenti = getUtenti();
        var utenteTrovato = listaUtenti.stream().filter(utente -> utente.Nickname.equals(utente_input.Nickname)).findFirst().get();

        int IndexSpecifico = listaUtenti.indexOf(utenteTrovato);

        //Salvo utente in input
        listaUtenti.set(IndexSpecifico, utente_input);

        salvaUtenti(listaUtenti);


    }

    /**
     * Carica la lista degli utenti dal file JSON.
     *
     * @return La lista degli utenti
     */
    public ArrayList<Utente> getUtenti() {

        // Leggere il file JSON
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathJson))) {
            // Leggere il contenuto del file in una stringa
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            Gson gson = new Gson();

            // Creare un TypeToken per rappresentare il tipo di lista di Persona
            Type personaListType = new TypeToken<List<Utente>>() {
            }.getType();

            // Deserializzare la stringa JSON in una lista di oggetti Persona
            ArrayList<Utente> listaUtenti = gson.fromJson(jsonString.toString(), personaListType);

            return listaUtenti;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Restituisce il percorso dell'icona associata all'avatar specificato.
     *
     * @param avatar L'avatar di cui si vuole ottenere il percorso dell'icona
     * @return Il percorso dell'icona dell'avatar
     */
    public String getPathAvatarIcon(Avatar avatar) {

        String pathRoot = "src/view/res/icons/";
        if (avatar == null) {
            return pathRoot + "Bomberman.png";
        }

        switch (avatar) {
            case Bomberman -> {
                return pathRoot + "Bomberman.png";
            }
            case BombermanTheKid -> {
                return pathRoot + "Billy the kid Bomberman.png";
            }
            case PrettyBomberman -> {
                return pathRoot + "Pretty Bomberman.png";
            }
            case PunkBomberman -> {
                return pathRoot + "PunkBomberman.png";
            }

        }

        return null;
    }


    /**
     * Ottiene un utente dalla lista degli utenti in base al suo nickname.
     * USO DEGLI STREAM
     *
     * @param nickname Il nickname dell'utente da cercare
     * @return L'utente corrispondente al nickname specificato
     */
    public Utente getUtente(String nickname) {
        return getUtenti().stream().filter(utente -> utente.Nickname.equals(nickname)).findFirst().get();
    }


}
