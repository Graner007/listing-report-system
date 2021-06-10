package com.codecool.report.menu;

public class ViewMenu {

    private static final String[] menuOptions = {"Fill up database", "Update database", "Remove database and Fill up database", "Create report", "Create report and Upload to FTP server", "Exit program"};

    public static void welcomeMessage() {
        System.out.println("Hi! Welcome to listing report system!\n" +
                "Here you can create a report about plants in a Json file and upload it to a FTP server.");
        System.out.println("Choose an option below: (type number)");
    }

    public static void displayMenu() {
        int menuOptionCounter = 1;
        for (String menuOption : menuOptions) {
            System.out.println(menuOptionCounter + ". " +menuOption);
            menuOptionCounter++;
        }
    }

    public static int getMenuOptionsSize() { return menuOptions.length; }
}
