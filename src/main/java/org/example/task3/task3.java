package org.example.task3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.task3.task3.UnderhoodLogic.*;

class task3 {

    public static void main(String[] args) {
        getValues(args[0]);
        getTests(args[1]);
        writeReportTo(args[2]);
    }

    public static class Test {

        private int id;
        private String title;
        private String value;
        private List<Test> values;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<Test> getValues() {
            return values;
        }

        public void setValues(List<Test> values) {
            this.values = values;
        }

        @Override
        public String toString() {
            return "id: " + id + ", title: " + title + ", value: " + value + ", values: " + values;
        }
    }

    public static class Values {

        private int id;
        private String value;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "id: " + id + ", value: " + value;
        }


    }

    static class UnderhoodLogic {

        static final String valuesFileStr = "src/main/java/org/example/task3/data/values.json";
        static final String testsFileStr = "src/main/java/org/example/task3/data/tests.json";
        private static final String tests = "tests";
        private static final String values = "values";
        private static List<Values> valuesList;
        private static List<Test> testsList;

        static void getValues(String valuesFile) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonRoot;
            try {
                jsonRoot = mapper.readTree(new File(valuesFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            valuesList = new ArrayList<>();
            for (JsonNode valueNode : jsonRoot.path(values)) {
                Values value = new Values();
                value.setId(valueNode.path("id").intValue());
                value.setValue(valueNode.path("value").textValue());
                valuesList.add(value);
            }
        }

        static void getTests(String file) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonRoot;
            try {
                jsonRoot = mapper.readTree(new File(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            testsList = new ArrayList<>();
            for (JsonNode testNode : jsonRoot.path(tests)) {
                testsList.add(parseNode(testNode));
            }
        }

        private static Test parseNode(JsonNode testNode) {
            Test test = new Test();
            test.setId(testNode.path("id").intValue());
            test.setTitle(testNode.path("title").textValue());
            test.setValue(testNode.path("value").textValue());
            if (testNode.has(values)) {
                List<Test> curTest = new ArrayList<>();
                for (JsonNode node : testNode.path(values)) {
                    curTest.add(parseNode(node));
                }
                test.setValues(curTest);
            }
            return test;
        }

        static void setValues(Test test) {
            for (Values values : valuesList) {
                if (test.getId() == values.getId()) {
                    test.setValue(values.getValue());
                    valuesList.remove(values);
                    break;
                }
            }
            if (test.getValues() != null) {
                for (Test nestedTest : test.getValues()) {
                    setValues(nestedTest);
                }
            }
        }

        static void writeReportTo(String file) {
            for (Test curTest : UnderhoodLogic.testsList) {
                setValues(curTest);
            }
            Map<String, Object> map = new HashMap<>();
            map.put(tests, testsList);
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .writerWithDefaultPrettyPrinter()
                        .writeValue(new File(file), map);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}