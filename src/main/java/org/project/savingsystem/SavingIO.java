package org.project.savingsystem;

import lombok.extern.log4j.Log4j2;
import org.project.utils.Vec2Int;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to save and load values from a file
 * Supports int, float, Vec2Int and List<Vec2Int> save and load.
 * Every value is saved with a name, to get and set the value you need to use the same name.
 */
@Log4j2
public class SavingIO {
    private final String path;
    private final StringBuilder text;

    /**
     * Creates a new SavingIO object with the given path.
     * remember to call flush() before the program ends to save the changes.
     * @param path
     */
    public SavingIO(String path){
        this.path = path;
        this.text = getText();;
    }
    /**
     * Flushes all the changes applied to the file.
     * if not called before the program ends, the changes will be lost.
     */
    public void flush(){
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.print(text.toString());
            writer.close();
        } catch (IOException e) {
            log.error("File not found : {}", path);
        }
    }

    /* ----------------- PRIMITIVE SETTERS ------------------- */

    public void setInt(String name, int value){
        set(name, String.valueOf(value));
    }
    public void setFloat(String name, float value){
        set(name, String.valueOf(value));
    }
    public void setLong(String name, long value){
        set(name, String.valueOf(value));
    }

    public void setString(String name, String value){
        set(name, value);
    }

    /* ------------------------- SAVABLE SETTERS ------------------------- */

    public void setVec2Int(String name, Vec2Int value){
        set(name, value.toSaveString());
    }

    /* --------------------------- LIST SETTERS -------------------------*/

    public void setVec2IntList(String name, List<Vec2Int> list){
        set(name, listToSaveString(list));
    }


    /* -------------------------- PRIMITIVE GETTERS -------------------------*/

    public Integer getInt(String name){
        String value = get(name);
        return value != null ? Integer.parseInt(value) : null;
    }
    public Float getFloat(String name){
        String value = get(name);
        return value != null ? Float.parseFloat(value) : null;
    }
    public Long getLong(String name){
        String value = get(name);
        return value != null ? Long.parseLong(value) : null;
    }

    /* ----------------------------- SAVABLE GETTERS ----------------------------*/

    public Vec2Int getVec2Int(String name){
        String value = get(name);
        return value != null ? new Vec2Int().fromSaveStringToObject(value) : null;
    }

    /* --------------------------------- LIST GETTERS ---------------------------- -*/

    public List<Vec2Int> getVec2IntList(String name){
        String value = get(name);
        if(value != null) {
            int elements = elementsCount(value);
            ArrayList<Vec2Int> list = new ArrayList<>(elements);
            for (int i = 0; i < elements; i++) { list.add(new Vec2Int()); }

            return new ListReader<Vec2Int>(value).readList(list);
        }
        return null;
    }


    /* ----------------------------- PRIVATE METHODS -------------------------*/


    private void set(String name, String savingString){
        int startIndex = startIndex(name);
        int endIndex = endIndex(startIndex);

        if(startIndex != -1)
            text.replace(startIndex, endIndex, savingString);
        else
            text.append(name).append(" ").append(savingString).append("\n");
    }
    private String get(String name){
        int startIndex = startIndex(name);
        int endIndex = endIndex(startIndex);

        if (startIndex == -1)
            return null;
        else
            return text.substring(startIndex, endIndex);
    }

    private String listToSaveString(List<? extends Savable<?>> list){
        StringBuilder builder = new StringBuilder();

        for(Savable<?> obj : list) {
            builder.append(obj.toSaveString()).append(" ");
        }

        return builder.toString();
    }
    private int elementsCount(String text){
        return text.split(" ").length;
    }

    private StringBuilder getText() {
        StringBuilder text = new StringBuilder();
        try(FileReader file = new FileReader(path); Scanner scanner = new Scanner(file)){
            while(scanner.hasNextLine())
                text.append(scanner.nextLine()).append("\n");
        } catch (IOException e) {
            log.warn("File not found : {}", path);
        }

        return text;
    }
    private int startIndex(String name){
        int indexOfName = text.indexOf(name);
        if(indexOfName == -1)
            return -1;
        else
            return indexOfName + name.length() + 1;
    }
    private int endIndex(int startIndex){
        return text.indexOf("\n", startIndex);
    }
}
