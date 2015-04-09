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
        RemindersService remindersService = new RemindersService();
        LocalDate currentDate = LocalDate.now();
        LOGGER.info("is launched for " + currentDate);

        List<MedicalCard> birthdayMen = remindersService.findCardsByBirthday(currentDate);
        List<MedicalCard> closeExamDateMen = remindersService.findCardsByExamDate(currentDate);
        for (MedicalCard card : birthdayMen) {
            LOGGER.debug("Selected card with birthday : " + card);
        }
        for (MedicalCard card : closeExamDateMen) {
            LOGGER.debug("Selected card with close exam date : " + card);
        }

        if (birthdayMen.isEmpty() && closeExamDateMen.isEmpty()) {
            return;
        }

        final JWindow window = new NotificationPopup();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        final Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
                window.getGraphicsConfiguration());
        final int taskBarSize = scnMax.bottom;

        ReminderPanel birthdayPanel = new ReminderPanel("Именинники", birthdayMen);
        ReminderPanel examinationPanel = new ReminderPanel("Осмотры", closeExamDateMen);

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
        container.add(birthdayPanel);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        container.add(examinationPanel);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        container.add(closeButton);

        window.setAlwaysOnTop(true);
        window.pack();
        window.setLocation(screenSize.width - window.getWidth(), screenSize.height - taskBarSize// *3
                - window.getHeight());

        window.setVisible(true);
    }

    private class NotificationPopup extends JWindow {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            final Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}
