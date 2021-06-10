package com.codecool.report.util;

public enum PrintColor {

    TEXT_RED("\u001B[31m"),
    TEXT_GREEN("\u001B[32m"),
    TEXT_RESET("\u001B[0m");

    private String unicode;

    PrintColor(String unicode) { this.unicode = unicode; }

    public String getUnicode() { return unicode; }
}
