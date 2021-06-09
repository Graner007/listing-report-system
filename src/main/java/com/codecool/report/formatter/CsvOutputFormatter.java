package com.codecool.report.formatter;

import com.codecool.report.model.ImportLog;
import lombok.NoArgsConstructor;

import java.io.*;

@NoArgsConstructor
public class CsvOutputFormatter implements OutputFormatter {

    private static final String CSV_FILE_NAME = "importLog.csv";

    @Override
    public void printToFile(Object data) throws IOException {
        ImportLog importLog = (ImportLog) data;

        if (checkFileExists(CSV_FILE_NAME)) {
            FileWriter fileWriter = new FileWriter(CSV_FILE_NAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String row = format(importLog);
            bufferedWriter.write(row + "\n");

            bufferedWriter.close();
        }
        else {
            FileWriter fileWriter = new FileWriter(CSV_FILE_NAME);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            String row= format(importLog);
            printWriter.print(row + "\n");

            printWriter.close();
        }
    }

    private String format(ImportLog data) {
        String result = "";
        result += data.getPlantId() + ";";
        result += data.getMarketplaceName() + ";";
        result += data.getInvalidField() + ";";
        return result;
    }

    private boolean checkFileExists(String fileName) { return new File(fileName).exists(); }
}
