package org.olzd.emr.view.popups;

import org.olzd.emr.UIHelper;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.service.MedicalCardService;
import org.olzd.emr.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PrivateNotesPopup extends JDialog {
    private JTextArea privateNotes = new JTextArea(6, 30);

    public PrivateNotesPopup(final MainWindow owner, String title, boolean modal) {
        super(owner, title, modal);
        final MedicalCard medicalCard = owner.getMedicalCardPanel().getModel().getCard();

        JButton saveNotes = new JButton();
        saveNotes.setAction(new AbstractAction("Сохранить") {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicalCardService cardService = new MedicalCardService();
                cardService.savePrivateNotesForCard(medicalCard.getCardId(), privateNotes.getText());
            }
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollPaneForPrivNotes = new JScrollPane(privateNotes);
        UIHelper.setAutoScrollingForTextArea(privateNotes);

        panel.add(scrollPaneForPrivNotes);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(saveNotes);

        getContentPane().add(panel);

        renderNotes(medicalCard);
    }

    private void renderNotes(MedicalCard medicalCard) {
        privateNotes.setText(medicalCard.getPrivateNotes());
    }
}
