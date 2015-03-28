package org.olzd.emr.reminder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.service.RemindersService;
import org.olzd.emr.view.ReminderPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BirthdayReminderTask implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(BirthdayReminderTask.class.getName());

    @Override
    public void run() {
        LOGGER.info("Birthday Reminder works");
        RemindersService remindersService = new RemindersService();
        List<MedicalCard> birthdayMen = remindersService.findCardsByBirthday(LocalDate.now());

        final JWindow window = new JWindow();
        ReminderPanel reminderPanel = new ReminderPanel(birthdayMen);

        JButton closeButton = new JButton();
        closeButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                window.dispose();
            }
        });
        closeButton.setText("Закрыть");

        Container container = window.getContentPane();
        BoxLayout boxLayout = new BoxLayout(container, BoxLayout.PAGE_AXIS);

        window.getContentPane().setLayout(boxLayout);
        container.add(reminderPanel);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        container.add(closeButton);
        window.setAlwaysOnTop(true);
        window.pack();
        window.setVisible(true);
    }
}
