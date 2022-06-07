package de.iv.manager.exceptions;

import java.util.Collection;

public class ObjectNotContainedException extends Exception{


    @Override
    public String getMessage() {
        return "The specified Object is not contained in the specified collection.";
    }
}
