package com.codecool.report.formatter;

import java.io.IOException;

public interface OutputFormatter {

    void printToFile(Object data) throws IOException;
}
