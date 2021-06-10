package com.codecool.report.formatter;

import com.codecool.report.model.Report;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

public class JsonOutPutFormatter implements OutputFormatter {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String JSON_FILE_PATH = "./result/";
    private static final String JSON_FILE_NAME = "report-";
    private static final String EXTENSION = ".json";

    @Override
    public void printToFile(Object data) throws IOException {
        Report report = (Report) data;
        FileWriter fileWriter = new FileWriter(JSON_FILE_PATH + JSON_FILE_NAME + String.valueOf(UUID.randomUUID()) + EXTENSION);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.print(gson.toJson(List.of(report)) + "\n");

        printWriter.close();
    }
}
