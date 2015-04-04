package org.olzd.emr;

import it.sauronsoftware.cron4j.Scheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalTime;
import org.olzd.emr.reminder.BirthdayReminderTask;
import org.olzd.emr.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Launcher {
    private static final Logger LOGGER = LogManager.getLogger(Launcher.class.getName());
    private static final LocalTime CHECK_TIME = new LocalTime(9, 30);

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

        Scheduler scheduler = new Scheduler();
        BirthdayReminderTask birthTask = new BirthdayReminderTask();
        StringBuilder patternString = new StringBuilder();
        patternString.append(CHECK_TIME.getMinuteOfHour()).append(" ").append(CHECK_TIME.getHourOfDay()).append(" * * *");
        scheduler.schedule(patternString.toString(), birthTask);
        //30 9 * * * [every day at 9 30]
        scheduler.start();
        //if time of launching had been passed, force check
        if (LocalTime.now().isAfter(CHECK_TIME)) {
            LOGGER.info("Force check after application has been launched");
            birthTask.run();
        }
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
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        MenuItem showWindowItem = new MenuItem("Show window");
        showWindowItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.setVisible(true);
            }
        });
        popupMenu.add(showWindowItem);
        popupMenu.add(exitItem);

        SystemTray tray = SystemTray.getSystemTray();
        URL bufferedImage = ClassLoader.getSystemResource("hospital16.png");
        ImageIcon icon = new ImageIcon(bufferedImage);

        TrayIcon trayIcon = new TrayIcon(icon.getImage());
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println(e);
        }

        trayIcon.setPopupMenu(popupMenu);
    }
}
