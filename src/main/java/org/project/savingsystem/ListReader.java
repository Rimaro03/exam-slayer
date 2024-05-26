package org.project.savingsystem;

import java.util.List;
import java.util.Scanner;

public class ListReader<T extends Savable> {
    private final String value;
    ListReader(String value){
        this.value = value;
    }

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
