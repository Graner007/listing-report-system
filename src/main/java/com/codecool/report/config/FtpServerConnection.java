package com.codecool.report.config;

import com.codecool.report.util.PrintColor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@NoArgsConstructor
public class FtpServerConnection {

    private final PropertyReader propertyReader = new PropertyReader();
    private static final String DIRECTORY_PATH = "./result";

    public void uploadJsonFile() {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;

        try {
            client.connect(propertyReader.getFtpServerHost());
            client.login(propertyReader.getFtpServerUsername(), propertyReader.getFtpServerPassword());
            client.enterLocalPassiveMode();

            File file = getLatestFileFromDir(DIRECTORY_PATH);
            String filename = file.getName();
            fis = new FileInputStream(file.getAbsolutePath());

            client.storeFile(filename, fis);
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                System.out.println(PrintColor.TEXT_GREEN + "Json file uploaded." + PrintColor.TEXT_RESET);
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getLatestFileFromDir(String dirPath){
        File dir = new File(dirPath);
        File[] files = dir.listFiles();

        if (files == null || files.length == 0)
            return null;

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++)
            if (lastModifiedFile.lastModified() < files[i].lastModified())
                lastModifiedFile = files[i];

        return lastModifiedFile;
    }
}
