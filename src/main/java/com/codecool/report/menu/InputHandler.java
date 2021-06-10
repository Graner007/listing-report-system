package com.codecool.report.menu;

import com.codecool.report.util.PrintColor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput() {
        while (true) {
            try {
                int num = scanner.nextInt();
                int menuOptionNumber = ViewMenu.getMenuOptionsSize();
                if (num <= menuOptionNumber && num > 0)
                    return num;
                System.out.println(PrintColor.TEXT_RED.getUnicode() + "Input number must be range of 1 and " + menuOptionNumber + "!" + PrintColor.TEXT_RESET.getUnicode());
                continue;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println(PrintColor.TEXT_RED.getUnicode() + "Input must be a number!" + PrintColor.TEXT_RESET.getUnicode());
                continue;
            }
        }
    }
}
