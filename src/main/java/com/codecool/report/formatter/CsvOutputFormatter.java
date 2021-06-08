package com.codecool.report.formatter;

import lombok.NoArgsConstructor;

import java.io.*;

@NoArgsConstructor
public class CsvOutputFormatter implements OutputFormatter {

    private static final String CSV_FILE_NAME = "importLog.csv";

    @Override
    public void printToFile(String[] data) throws IOException {
        if (checkFileExists(CSV_FILE_NAME)) {
            FileWriter fileWriter = new FileWriter(CSV_FILE_NAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String row = format(data);
            bufferedWriter.write(row);
            bufferedWriter.newLine();

            bufferedWriter.close();
        }
        else {
            FileWriter fileWriter = new FileWriter(CSV_FILE_NAME);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            String row = format(data);
            printWriter.print(row + "\n");

            printWriter.close();
        }
    }

    private String format(String[] data) {
        String result = "";
        for (String d : data) {
            result += d + ";";
        }
        return result;
    }

    private boolean checkFileExists(String fileName) { return new File(fileName).exists(); }
}
