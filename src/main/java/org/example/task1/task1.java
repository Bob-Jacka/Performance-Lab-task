package org.example.task1;

import java.util.Arrays;

import static org.example.task1.task1.MainLogic.mainLogic;

public class task1 {

    public static void main(String[] args) {
        switch (args.length) {
            case 0:
                System.out.println("Введите параметры");
            case 1:
                System.out.println("Введите ещё один параметр");
            case 2:
                mainLogic(args[0], args[1]);
        }
    }

    static class MainLogic {

        static final String testN1 = "4";
        static final String testN2 = "5";
        static final String testM1 = "3";
        static final String testM2 = "4";

        static void mainLogic(String arrayLength, String lengthInterval) {
            int n = Integer.parseInt(arrayLength);
            int m = Integer.parseInt(lengthInterval);
            int[] arr = new int[n];
            Arrays.setAll(arr, i -> ++i);
            int current = 0;
            System.out.print("Полученный путь: ");
            do {
                System.out.print(arr[current]);
                current = (current + m - 1) % n;
            } while (current != 0);
        }
    }
}