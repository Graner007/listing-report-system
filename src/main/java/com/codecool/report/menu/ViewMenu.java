package com.codecool.report.menu;

public class ViewMenu {

    private static final String[] menuOptions = {"Update database", "Create report", "Create report and Upload to FTP server"};

    public static void displayMenu() {
        System.out.println("Hi! Welcome to listing report system!\n" +
                "Here you can create a report about plants in json a file extension and upload it to a FTP server.");
        System.out.println("Choose an option below: (type number)");

        int menuOptionCounter = 1;
        for (String menuOption : menuOptions) {
            System.out.println(menuOptionCounter + ". " +menuOption);
            menuOptionCounter++;
        }
    }

    public static int getMenuOptionsSize() { return menuOptions.length; }
}
