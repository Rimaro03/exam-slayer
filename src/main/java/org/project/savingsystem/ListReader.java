package org.project.savingsystem;

import java.util.List;
import java.util.Scanner;

/**
 * Class to read a list of Savable objects from a string
 * @param <T> the type of the Savable object
 */
public class ListReader<T extends Savable<T>> {
    private final String value;
    ListReader(String value){
        this.value = value;
    }

    /**
     * Updates the list elements with the values read from the string,
     * if the string has more values than the list, the extra values are ignored.
     * @param list the list to read
     * @return the list with the values read
     */
    public List<T> readList(List<T> list) {
        Scanner scanner = new Scanner(value);

        int i = 0;
        while(scanner.hasNext() && i < list.size()){
            String next = scanner.next();
            list.get(i).fromSaveStringToObject(next);

            i++;
        }

        scanner.close();
        return list;
    }
}
