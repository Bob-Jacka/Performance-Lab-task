package org.example.task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.example.task2.task2.DataHelper.*;

public class task2 {

    public static void main(String[] args) {
        Map<String, Double> circle = getCircle(args[0]);
        List<List<Double>> positions = getPoints(args[1]);
        System.out.println(circle);
        System.out.println(positions);
        getDecisionAbout(circle, positions);
    }

    static class DataHelper {

        public static final String file1 = "src/main/java/org/example/task2/data/testData";
        public static final String file2 = "src/main/java/org/example/task2/data/testData2";

        static Map<String, Double> getCircle(String file1) {
            Map<String, Double> circle = new LinkedHashMap<>();
            try (BufferedReader circleFile = new BufferedReader(new FileReader(file1))) {
                String[] line = circleFile.readLine().split(" ");
                circle.put("x", Double.parseDouble(line[0]));
                circle.put("y", Double.parseDouble(line[1]));
                circle.put("radius", Math.pow(Double.parseDouble(circleFile.readLine()), 2));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return circle;
        }

        static List<List<Double>> getPoints(String file2) {
            List<List<Double>> points = new LinkedList<>();
            try (BufferedReader pointsFile = new BufferedReader(new FileReader(file2))) {
                String line;
                List<Double> point;
                while ((line = pointsFile.readLine()) != null) {
                    point = new ArrayList<>();
                    point.add(Double.parseDouble(line.split(" ")[0]));
                    point.add(Double.parseDouble(line.split(" ")[1]));
                    points.add(point);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return points;
        }

        static void getDecisionAbout(Map<String, Double> circle, List<List<Double>> points) {
            List<Integer> positionList = new LinkedList<>();
            for (List<Double> point : points) {
                double diff = Math.pow(point.get(0) - circle.get("x"), 2) +
                        Math.pow(point.get(1) - circle.get("y"), 2) -
                        circle.get("radius");
                if (diff > 0) {
                    positionList.add(2);
                } else if (diff == 0) {
                    positionList.add(0);
                } else {
                    positionList.add(1);
                }
            }
            for (Integer integer : positionList) {
                String message = switch (integer) {
                    case 0 -> " - точка лежит на окружности";
                    case 1 -> " - точка внутри";
                    case 2 -> " - точка снаружи";
                    default -> null;
                };
                System.out.println(integer + message);
            }
        }
    }
}