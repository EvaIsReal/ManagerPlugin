package de.iv.manager.menus.cc;

import de.iv.manager.exceptions.ObjectNotContainedException;
import de.iv.manager.utils.DataManager;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.lang.reflect.Array;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class BlackListManager {

    private static ArrayList<String> blacklistedPhrases = new ArrayList<>();


    public static ArrayList<String> loadPhrases() {
        try {
            blacklistedPhrases = (ArrayList<String>) DataManager.deserialize(new File(DataManager.PATH + "/persistentData/blacklistedPhrases.ser"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return blacklistedPhrases;
    }

    public static boolean addToList(String toAdd) {
        if(blacklistedPhrases.contains(toAdd)) {
            return false;
        }
        blacklistedPhrases.add(toAdd);
        return true;
    }

    public static void updatePhrase(String phrase, String newPhrase) {
        for(String s : blacklistedPhrases) {
            if(s.equalsIgnoreCase(phrase)) {
                int index = blacklistedPhrases.indexOf(s);
                blacklistedPhrases.remove(phrase);
                blacklistedPhrases.add(index, newPhrase);
            }
        }
    }

    public static boolean isBlacklisted(String s) {
        return blacklistedPhrases.contains(s);
    }

    public boolean removeFromList(String toRemove) throws ObjectNotContainedException {
        if(!blacklistedPhrases.contains(toRemove)) {
            throw new ObjectNotContainedException();
        }
        blacklistedPhrases.remove(toRemove);
        return true;
    }

    public static void storeBlacklist() {
        try {
            DataManager.serialize(blacklistedPhrases, "blacklistedPhrases", "persistentData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<String> getBlacklistedPhrases() {
        return blacklistedPhrases;
    }
}
