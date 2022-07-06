package de.iv.manager.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class AdminSettings {
    private static HashMap<Player, ArrayList<String>> chatHistory = new HashMap<>();

    public static void handleHistory(AsyncPlayerChatEvent e) {
        for (Player a : Bukkit.getOnlinePlayers()) {
            if(!chatHistory.containsKey(a)) {
                ArrayList<String> aHistory = new ArrayList<>();
                chatHistory.put(a, aHistory);
            } else {
                chatHistory.get(a).add(e.getMessage());
            }
        }
    }

    public static ArrayList<String> getChatHistory(Player p) {
        return chatHistory.get(p);
    }

    public static ArrayList<String> getChatHistory(Player p, int maxLines) {
        ArrayList<String> out = new ArrayList<>();
        if(maxLines <= chatHistory.get(p).size()) {
            for(int i = 0; i < maxLines; i++) {
                out.add(chatHistory.get(p).get(i));
            }
            return out;
        }
        throw new ArrayIndexOutOfBoundsException();
    }


}
