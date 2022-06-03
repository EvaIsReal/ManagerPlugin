package de.iv.manager.menus.cc;

import com.google.gson.Gson;

import java.util.ArrayList;

public class BlackListManager {

    //Handles I/O for blacklisted words
    private static ArrayList<String> blacklistedWords = new ArrayList<>();

    public static void addNewPhrase(String phrase) {
        Gson gson = new Gson();
        blacklistedWords.add(phrase);

    }

}
