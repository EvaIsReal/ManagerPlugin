/*This class from the Project Manager was created by thoiv at 10.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.utils;

import com.google.gson.Gson;
import de.iv.manager.core.Main;
import de.iv.manager.models.Note;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteStorageUtil {

    //CRUD:
    private static ArrayList<Note> notes = new ArrayList<>();

    public static Note createNote(Player p, String message) {
        Note note = new Note(p.getName(), message);
        notes.add(note);

        return note;

    }

    public static Note findNote(String noteID) {
        for(Note note : notes) {
            if(note.getNoteID().equals(noteID)) {
                return note;
            }
        }
        return null;
    }

    public static void deleteNote(String noteID) {
        for(Note note : notes) {
            if(note.getNoteID().equals(noteID)) {
                notes.remove(note);
                break;
            }
        }
    }

    public static Note updateNote(String noteID, Note newNote) {
        for(Note note : notes) {
            if(note.getNoteID().equalsIgnoreCase(noteID)) {
                note.setPlayerName(newNote.getPlayerName());
                note.setMessage(newNote.getMessage());
                return newNote;
            }
        }
        return null;
    }

    public static void saveNotesPersistently() throws IOException {
        Gson gson = new Gson();
        File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/persistentData/", "notes.json");
        if(!file.exists()) file.createNewFile();
        Writer writer = new FileWriter(file, false);

        //Convert notes ArrayList into JSON-File:
        gson.toJson(notes, writer);
        writer.flush();
        writer.close();
        Main.getInstance().getLogger().info("NOTES SAVED!");
    }

    public static void loadNotes() throws IOException {
        Gson gson = new Gson();
        File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/persistentData/", "notes.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            Note[] n = gson.fromJson(reader, Note[].class);
            notes = new ArrayList<>(Arrays.asList(n));
            Main.getInstance().getLogger().info("NOTES LOADED!");
        }
    }

    public static List<Note> getNotes() {
        return notes;
    }

}
