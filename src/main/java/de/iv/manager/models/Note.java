/*This class from the Project Manager was created by thoiv at 10.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Note {

    private String playerName;
    private String message;
    private String noteID;
    private String dateCreated;

    public Note(String playerName, String message) {
        this.playerName = playerName;
        this.message = message;
        this.noteID = "NOTE_" + UUID.randomUUID().toString();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", new Locale("de", "DE"));
        this.dateCreated = format.format(new Date());

    }


    public String getNoteID() {
        return noteID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy ss:mm:HH", new Locale("de", "DE"));
        this.dateCreated = format.format(new Date());
    }
}
