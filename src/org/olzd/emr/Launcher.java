package org.olzd.emr;

import org.olzd.emr.view.MainWindow;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        setWindowsLookAndFeel();
        final MainWindow mainWindow = new MainWindow();
        mainWindow.pack();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainWindow.setVisible(true);
            }
        });

        //think about error handling and error logging
    }

    private static void setWindowsLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
    }
}
