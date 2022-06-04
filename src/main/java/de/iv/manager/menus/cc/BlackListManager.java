package de.iv.manager.menus.cc;

import com.google.gson.Gson;
import de.iv.manager.core.Main;
import de.iv.manager.models.BlacklistedPhrase;
import de.iv.manager.models.Note;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class BlackListManager {

    //Handles I/O for blacklisted words
    private static ArrayList<BlacklistedPhrase> blacklistedWords = new ArrayList<>();

    public static BlacklistedPhrase addNewPhrase(String name, String phrase) {
        BlacklistedPhrase blacklistedPhrase = new BlacklistedPhrase(name, phrase);
        blacklistedWords.add(blacklistedPhrase);
        return blacklistedPhrase;
    }

    public static void loadPhrases() throws IOException {
        Gson gson = new Gson();
        File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/persistentData/", "blacklistedWords.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            BlacklistedPhrase[] n = gson.fromJson(reader, BlacklistedPhrase[].class);
            blacklistedWords = new ArrayList<>(Arrays.asList(n));
            Main.getInstance().getLogger().info("PHRASES LOADED!");
        }
    }

    public static BlacklistedPhrase findNote(UUID uuid) {
        for(BlacklistedPhrase phrase : blacklistedWords) {
            if(phrase.getPhraseID().equals(uuid)) {
                return phrase;
            }
        }
        return null;
    }

    public static void deleteNote(String noteID) {
        blacklistedWords.forEach(phrase -> blacklistedWords.remove(findNote(phrase.getPhraseID())));
    }


    public static BlacklistedPhrase updatePhrases(UUID uuid, BlacklistedPhrase newPhrase) {
        for(BlacklistedPhrase phrase : blacklistedWords) {
            if(phrase.getPhraseID().equals(uuid)) {
                phrase.setName(newPhrase.getName());
                phrase.setContent(newPhrase.getContent());
                return newPhrase;
            }
        }
        return null;
    }

    public static void storePhrasesPersistently() throws IOException {
        Gson gson = new Gson();
        File file = new File(Main.getInstance().getDataFolder() + "/persistentData/", "blacklistedPhrases.json");
        if(!file.exists()) file.createNewFile();
        FileWriter writer = new FileWriter(file, false);

        gson.toJson(blacklistedWords, writer);
        writer.flush();
        writer.close();
        Main.logInfo("PHRASES SAVED");
    }

    public static ArrayList<BlacklistedPhrase> getBlacklistedWords() {
        return blacklistedWords;
    }
}
