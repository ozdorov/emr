package org.olzd.emr.view;

import org.olzd.emr.entity.MedicalCard;

import javax.swing.*;
import java.util.List;

public class ReminderPanel extends JPanel {
    private JLabel text = new JLabel();

    public ReminderPanel(List<MedicalCard> cardsToRender) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        "Именинники"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));


        StringBuilder textToRender = new StringBuilder();
        textToRender.append("<html>");
        for (MedicalCard card : cardsToRender) {
            textToRender.append(card.getSurname()).append(", ").append(card.getName()).append("<br>");
        }

        //textToRender.append("<html>hello world<br>second row</html>");
        textToRender.append("</html>");
        text.setText(textToRender.toString());
        add(text);
    }
}
