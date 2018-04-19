package com.kodilla.sudoku;


import javax.swing.*;
import java.io.File;

public class ChooseFile {
    private JFrame frame;

    public ChooseFile() {
        frame = new JFrame();

        frame.setVisible(true);
        BringToFront();
    }

    public File getFile() {
        JFileChooser fc = new JFileChooser();
        ClassLoader classLoader = getClass().getClassLoader();

        //fc.setCurrentDirectory(getExecutionPath()); //alternative method
        fc.setCurrentDirectory(new File(classLoader.getResource("files").getPath()));

        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
            frame.setVisible(false);
            return fc.getSelectedFile();
        } else {
            System.out.println("Next time select a file.");
            System.exit(1);
        }
        return null;
    }

    private void BringToFront() {
        frame.setExtendedState(JFrame.ICONIFIED);
        frame.setExtendedState(JFrame.NORMAL);

    }

    //https://stackoverflow.com/a/12715491
    public File getExecutionPath() {
        String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
        absolutePath = absolutePath.replaceAll("%20", " "); // Surely need to do this here
        absolutePath = absolutePath.replaceAll("production/classes", "production/resources/files");
        return new File(absolutePath);
    }
}