package com.codecool.report.formatter;

import java.io.IOException;

public interface OutputFormatter<T> {

    void printToFile(String[] data) throws IOException;
}
