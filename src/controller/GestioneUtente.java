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

public class GestioneUtente {


    private String filePathJson = "src/ConfigUtente/output.json";

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

    public String getPathAvatarIcon(Avatar avatar) {

        String pathRoot = "src/view/res/icons/";
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
     * Uso degli Stream
     * Usato per fare il filtro della lista di utenti con nickname
     *
     * @param nickname
     */
    public Utente getUtente(String nickname) {
        return getUtenti().stream().filter(utente -> utente.Nickname.equals(nickname)).findFirst().get();
    }

//    public static <T> String convertUsingGson(T object) {
//        Gson gson = new Gson();
//        return gson.toJson(object);
//    }
//
//    public static <T> T convertFromJson(String jsonString, Class<T> classType) {
//        Gson gson = new Gson();
//        return gson.fromJson(jsonString, classType);
//    }

}
