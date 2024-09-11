package org.example.task4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.task4.task4.LogicHelper.getTurns;

public class task4 {

    public static void main(String[] args) {
        getTurns(args[0]);
    }

    static class LogicHelper {

        static final String numsData = "src/main/java/org/example/task4/data/nums";

        static List<Integer> getElements(String file) {
            List<Integer> elements = new ArrayList<>();
            try (BufferedReader elementsFile = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = elementsFile.readLine()) != null) {
                    elements.add(Integer.parseInt(line));
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return elements;
        }

        static void getTurns(String terminalArgument) {
            List<Integer> elements = getElements(terminalArgument);
            Collections.sort(elements);
            int midIndex = elements.size() / 2;
            int turns = 0;
            for (Integer element : elements) {
                turns += Math.abs(element - elements.get(midIndex));
            }
            System.out.print(turns);
        }
    }
}