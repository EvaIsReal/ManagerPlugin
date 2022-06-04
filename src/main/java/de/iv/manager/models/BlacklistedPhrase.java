package de.iv.manager.models;

import java.util.UUID;

public class BlacklistedPhrase {

    private String name, content;
    private UUID phraseID;


    public BlacklistedPhrase(String name, String content) {
        this.name = name;
        this.content = content;
        this.phraseID = UUID.randomUUID();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getPhraseID() {
        return phraseID;
    }
}
