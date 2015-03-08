package org.olzd.emr.view;

import org.olzd.emr.UIHelper;
import org.olzd.emr.action.SaveMedicalCardAction;
import org.olzd.emr.entity.MedicalCard;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditMedicalCardPanel extends JPanel {

    private JTextField name = new JTextField(15);
    private JTextField surname = new JTextField(15);
    private JFormattedTextField birthday = new JFormattedTextField();
    private JButton saveButton = new JButton();

    public EditMedicalCardPanel() {
        GroupLayout layoutManager = new GroupLayout(this);
        setLayout(layoutManager);

        constructPanelView(layoutManager);
        constructPanelLogic();
    }

    private void constructPanelLogic() {
        SaveMedicalCardAction saveCardAction = new SaveMedicalCardAction(this);
        saveCardAction.putValue(Action.NAME, "Сохранить");
        saveButton.setAction(saveCardAction);

        DateFormatter df = new DateFormatter(new SimpleDateFormat("MM/dd/YYYY"));
        birthday.setFormatterFactory(new DefaultFormatterFactory(df));
    }

    private void constructPanelView(GroupLayout layout) {
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel nameLabel = new JLabel("Имя");
        JLabel surnameLabel = new JLabel("Фамилия");
        JLabel birthdayLabel = new JLabel("Год рождения");

        birthday.setColumns(10);

        GroupLayout.SequentialGroup nameRow = UIHelper.createFixedRowFromParams(layout, nameLabel, name);
        GroupLayout.ParallelGroup nameColumn = UIHelper.createFixedColumnFromParams(layout, nameLabel, name);

        GroupLayout.SequentialGroup surnameRow = UIHelper.createFixedRowFromParams(layout, surnameLabel, surname);
        GroupLayout.ParallelGroup surnameColumn = UIHelper.createFixedColumnFromParams(layout, surnameLabel, surname);

        GroupLayout.SequentialGroup birthdayRow = UIHelper.createFixedRowFromParams(layout, birthdayLabel, birthday);
        GroupLayout.ParallelGroup birthColumn = UIHelper.createFixedColumnFromParams(layout, birthdayLabel, birthday);

        GroupLayout.SequentialGroup saveButtonRow = layout.createSequentialGroup().addComponent(saveButton);
        GroupLayout.ParallelGroup saveButtonColumn = layout.createParallelGroup().addComponent(saveButton);

        //todo add gap between text fields and [Save] button
        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(nameRow).addGroup(surnameRow)
                .addGroup(birthdayRow).addGroup(saveButtonRow));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(nameColumn).addGroup(surnameColumn)
                .addGroup(birthColumn).addGroup(saveButtonColumn));

        layout.linkSize(SwingConstants.HORIZONTAL, nameLabel, surnameLabel, birthdayLabel);
    }

    public void injectMedicalCardModel(MedicalCard card) {
        name.setText(card.getName());
        surname.setText(card.getSurname());
        if (card.getDateOfBirth() != null) {
            birthday.setText(new SimpleDateFormat("MM/dd/YYYY").format(card.getDateOfBirth()));
        }
    }

    public String getNameValue() {
        return name.getText();
    }

    public String getSurnameValue() {
        return surname.getText();
    }

    public Date getDateValue() {
        return (Date) birthday.getValue();
    }

}
