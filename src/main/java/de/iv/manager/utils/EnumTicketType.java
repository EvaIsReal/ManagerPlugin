package de.iv.manager.utils;

public enum EnumTicketType {

    SUPPORT("ticket.sup"),
    ADMIN("ticket.admin"),
    QUESTION("ticket.quest"),
    BUGREPORT("ticket.bug")
    ;

    String perm;
    EnumTicketType(String perm) {
        this.perm = perm;
    }

    public String getPermission() {
        return perm;
    }
}
