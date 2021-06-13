package com.codecool.report.menu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    @Test
    void setStringForGetIntInput() {
        String data = "word";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Scanner scanner = new Scanner(System.in);

        assertEquals("Input must be a number!", InputHandler.getIntInput());
        System.setIn(stdin);

    }

}