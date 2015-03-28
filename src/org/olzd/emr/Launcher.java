package org.olzd.emr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.olzd.emr.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher {
    private static final Logger LOGGER = LogManager.getLogger(Launcher.class.getName());

    public static void main(String[] args) {
        setWindowsLookAndFeel();
        final MainWindow mainWindow = new MainWindow();
        mainWindow.pack();
        addTrayIcon(mainWindow);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainWindow.setVisible(true);
            }
        });
        LOGGER.info("Application has been started");

//        BirthdayReminderTask task = new BirthdayReminderTask();
//        task.run();

//        Scheduler scheduler = new Scheduler();
//        BirthdayReminderTask birthTask = new BirthdayReminderTask();
//        scheduler.schedule("* * * * *", birthTask);
//        //9 30 * * * [every day at 9 30]
//        scheduler.start();
        //think about error handling and error logging
    }

    private static void setWindowsLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
    }

    private static void addTrayIcon(final JFrame mainWindow) {
        PopupMenu popupMenu = new PopupMenu();
        MenuItem exitItem = new MenuItem("Выход");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        MenuItem showWindowItem = new MenuItem("Показать");
        showWindowItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.setVisible(true);
            }
        });
        popupMenu.add(showWindowItem);
        popupMenu.add(exitItem);

        SystemTray tray = SystemTray.getSystemTray();
        ImageIcon icon = new ImageIcon("src/bulb.gif");
        TrayIcon trayIcon = new TrayIcon(icon.getImage());
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println(e);
        }

        trayIcon.setPopupMenu(popupMenu);
    }
}
