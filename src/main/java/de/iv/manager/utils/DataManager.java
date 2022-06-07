/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.utils;

import de.iv.manager.core.Main;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {

    public static final File PATH = new File(Main.getInstance().getDataFolder().getPath());
    public static final File SER_PATH = new File(Main.getInstance().getDataFolder() + "/data");


    public DataManager() {
        if(!PATH.exists()) PATH.mkdirs();
        if(!SER_PATH.exists()) SER_PATH.mkdirs();
    }

    /**
     * Serialisiert ein angegebenes Object unter jeweiligem Speichernamen saveName, im angegebenen Ordner mit dem Namen dirName. <br>
     * Wirft bei Input/Output Fehlern eine IOException.
     *
     * @param object - Das zu serialisierende Object
     * @param saveName - Der Speichername der object.ser-Datei
     * @param dirName - Name des Speicherverzeichnisses
     * @throws IOException
     *
     *
     */

    public static void serialize(Object object, String saveName, String dirName) throws IOException {

        FileOutputStream fileOut = new FileOutputStream(PATH.getPath() + "/" + dirName + "/" +  saveName + ".ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        fileOut.close();
        System.out.println("Object info saved.");
    }
    /**
     * Deserialisert ein, in einer <*object*>.ser-Datei "file" serialisiertes Objekt und gibt es zurück.<br>
     * Wirft bei Input/Output Fehlern eine IOException.<br>
     * Wirft bei nicht gefundener Klasse eine ClassNotFoundException.
     *
     * @param file - Die zu deserialisierende Datei
     * @return deserializedObject
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(File file) throws IOException, ClassNotFoundException {
        Object o = null;
        FileInputStream fileIn = new FileInputStream(file.getPath());
        ObjectInputStream in = new ObjectInputStream(fileIn);
        o = in.readObject();

        return o;
    }

    /**
     *
     * @param user - Corresponding user to the respective File
     * @param whatToWrite - Strings what will be written to the File.
     */

    public static void writeToUserData(File user, String... whatToWrite) {
        try {
            FileOutputStream out = new FileOutputStream(user);
            ArrayList<String> strings = new ArrayList<>(Arrays.asList(whatToWrite));
            strings.forEach(s -> {

            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<File> listDirectoryFiles(final File dir) {
        List<File> dirFiles = new ArrayList<>();
        for(File entry : dir.listFiles()) {
            if(entry.isDirectory()) listDirectoryFiles(entry);
            dirFiles.add(entry);
        }
        return dirFiles;
    }

}
