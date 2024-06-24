package org.project.savingsystem;

import lombok.Getter;
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
    @Getter
    private String path;
    private StringBuilder text;

    @Getter
    private final BucketManager bucketManager;

    /**
     * Creates a new SavingIO object with the given path.
     * remember to call flush() before the program ends to save the changes.
     *
     * @param path the path to the save file.
     */
    public SavingIO(String path) {
        this.bucketManager = new BucketManager(
                "exam-slayer",
                "exam-slayer",
                "resources/bucket/bucket_key.json"
        );

        if(path != null)
            this.text = new StringBuilder(bucketManager.getFileContent(path));
        else
            this.text = new StringBuilder();
        this.path = path;
    }
    public SavingIO(){
        this(null);
    }

    public void setPath(String path){
        this.path = path;
        this.text = new StringBuilder(bucketManager.getFileContent(path));
    }

    public List<String> allFiles(){
        return bucketManager.getAllFileNames();
    }
    public void deleteFile(String fileName){
        bucketManager.deleteFile(fileName);

        new File(fileName).delete();
    }


    /**
     * Flushes all the changes applied to the file.
     * if not called before the program ends, the changes will be lost.
     */
    public void flush() {
        if(path == null)
            return;

        try {
            PrintWriter writer = new PrintWriter(path);
            String textString = text.toString();

            writer.println(textString);
            writer.close();
            bucketManager.uploadFile(path, textString);

            log.info("File saved : {}", path);
        } catch (IOException e) {
            log.warn("File not found : {}", path);
            log.error(e);
        }
    }

    /* ----------------- PRIMITIVE SETTERS ------------------- */

    public void setInt(String name, int value) {
        set(name, String.valueOf(value));
    }

    public void setFloat(String name, float value) {
        set(name, String.valueOf(value));
    }

    public void setLong(String name, long value) {
        set(name, String.valueOf(value));
    }

    public void setString(String name, String value) {
        set(name, value);
    }

    /* ------------------------- SAVABLE SETTERS ------------------------- */

    public void setVec2Int(String name, Vec2Int value) {
        set(name, value.toSaveString());
    }

    /* --------------------------- LIST SETTERS -------------------------*/

    public void setVec2IntList(String name, List<Vec2Int> list) {
        set(name, listToSaveString(list));
    }

    public void setStringList(String name, List<String> list) {
        set(name, listToString(list));
    }

    public void setIntList(String name, List<Integer> list) {
        set(name, listToString(list));
    }


    /* -------------------------- PRIMITIVE GETTERS -------------------------*/

    public Integer getInt(String name) {
        String value = get(name);
        return value != null ? Integer.parseInt(value) : null;
    }

    public Float getFloat(String name) {
        String value = get(name);
        return value != null ? Float.parseFloat(value) : null;
    }

    public Long getLong(String name) {
        String value = get(name);
        return value != null ? Long.parseLong(value) : null;
    }

    public String getString(String name) {
        return get(name);
    }

    /* ----------------------------- SAVABLE GETTERS ----------------------------*/

    public Vec2Int getVec2Int(String name) {
        String value = get(name);
        return value != null ? new Vec2Int().fromSaveStringToObject(value) : null;
    }

    /* --------------------------------- LIST GETTERS ---------------------------- -*/

    public List<Vec2Int> getVec2IntList(String name) {
        String value = get(name);
        if (value != null) {
            int elements = elementsCount(value);
            ArrayList<Vec2Int> list = new ArrayList<>(elements);
            for (int i = 0; i < elements; i++) {
                list.add(new Vec2Int());
            }

            return new ListReader<Vec2Int>(value).readList(list);
        }
        return null;
    }

    public List<String> getStringList(String name) {
        String value = get(name);
        if (value != null) {
            List<String> list = new ArrayList<>();
            Scanner scanner = new Scanner(value);

            while (scanner.hasNext())
                list.add(scanner.next());
            return list;
        }
        return null;
    }

    public List<Integer> getIntList(String name) {
        String value = get(name);
        if (value != null) {
            List<Integer> list = new ArrayList<>();
            Scanner scanner = new Scanner(value);

            while (scanner.hasNextInt())
                list.add(scanner.nextInt());
            return list;
        }
        return null;
    }


    /* ----------------------------- PRIVATE METHODS -------------------------*/


    private void set(String name, String savingString) {
        int startIndex = startIndex(name);
        int endIndex = endIndex(startIndex);

        if (startIndex != -1)
            text.replace(startIndex, endIndex, savingString);
        else
            text.append(name).append(" ").append(savingString).append("\n");
    }

    private String get(String name) {
        int startIndex = startIndex(name);
        int endIndex = endIndex(startIndex);

        if (startIndex == -1)
            return null;
        else
            return text.substring(startIndex, endIndex);
    }

    private String listToSaveString(List<? extends Savable<?>> list) {
        StringBuilder builder = new StringBuilder();

        for (Savable<?> obj : list) {
            builder.append(obj.toSaveString()).append(" ");
        }

        return builder.toString();
    }

    private String listToString(List<?> list) {
        StringBuilder builder = new StringBuilder();

        for (Object obj : list) {
            builder.append(obj.toString()).append(" ");
        }

        return builder.toString();
    }

    private int elementsCount(String text) {
        String[] tokens = text.split(" ");
        return tokens[0].isEmpty() ? 0 : tokens.length;
    }

    private int startIndex(String name) {
        int indexOfName = text.indexOf(name);
        if (indexOfName == -1)
            return -1;
        else
            return indexOfName + name.length() + 1;
    }

    private int endIndex(int startIndex) {
        return text.indexOf("\n", startIndex);
    }
}
