package org.olzd.emr.view;

import org.olzd.emr.UIHelper;
import org.olzd.emr.action.CardTreeRefresher;
import org.olzd.emr.action.SaveMedicalCardAction;
import org.olzd.emr.model.MedicalCardModel;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;

public class EditMedicalCardPanel extends JPanel {

    private JTextField name = new JTextField(16);
    private JTextField surname = new JTextField(16);
    private JTextField middleName = new JTextField(16);
    private JFormattedTextField birthday = new JFormattedTextField();
    private JTextField phoneNumber = new JTextField(16);
    private JTextField email = new JTextField(20);
    private JTextArea address = new JTextArea(3, 20);
    private JButton saveButton = new JButton();
    private final MainWindow parentFrame;
    private MedicalCardModel model;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public EditMedicalCardPanel(MainWindow window) {
        GroupLayout layoutManager = new GroupLayout(this);
        setLayout(layoutManager);
        parentFrame = window;

        constructPanelView(layoutManager);
        constructPanelLogic();
    }

    private void constructPanelLogic() {
        SaveMedicalCardAction saveCardAction = new SaveMedicalCardAction(this);
        saveCardAction.putValue(Action.NAME, "Сохранить");
        saveButton.setAction(saveCardAction);

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##/##/####");
        } catch (ParseException e) {
            System.out.println(e);
        }
        birthday.setFormatterFactory(new DefaultFormatterFactory(maskFormatter));
        addModelPropertyChangeSupport(new CardTreeRefresher(getParentFrame().getCardStructureTree()));
    }

    private void constructPanelView(GroupLayout layout) {
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel nameLabel = new JLabel("Имя");
        JLabel surnameLabel = new JLabel("Фамилия");
        JLabel middleNameLabel = new JLabel("Отчество");
        JLabel birthdayLabel = new JLabel("Год рождения");
        JLabel phoneNumberLabel = new JLabel("Контактный телефон");
        JLabel emailLabel = new JLabel("Адрес электронной почты");
        JLabel addressLabel = new JLabel("Адрес");
        address.setLineWrap(true);

        birthday.setColumns(10);

        GroupLayout.SequentialGroup nameRow = UIHelper.createFixedRowFromParams(layout, nameLabel, name);
        GroupLayout.ParallelGroup nameColumn = UIHelper.createFixedColumnFromParams(layout, nameLabel, name);

        GroupLayout.SequentialGroup surnameRow = UIHelper.createFixedRowFromParams(layout, surnameLabel, surname);
        GroupLayout.ParallelGroup surnameColumn = UIHelper.createFixedColumnFromParams(layout, surnameLabel, surname);

        GroupLayout.SequentialGroup midNameRow = UIHelper.createFixedRowFromParams(layout, middleNameLabel, middleName);
        GroupLayout.ParallelGroup midNameColumn = UIHelper.createFixedColumnFromParams(layout, middleNameLabel, middleName);

        GroupLayout.SequentialGroup birthdayRow = UIHelper.createFixedRowFromParams(layout, birthdayLabel, birthday);
        GroupLayout.ParallelGroup birthColumn = UIHelper.createFixedColumnFromParams(layout, birthdayLabel, birthday);

        GroupLayout.SequentialGroup emailRow = UIHelper.createFixedRowFromParams(layout, emailLabel, email);
        GroupLayout.ParallelGroup emailColumn = UIHelper.createFixedColumnFromParams(layout, emailLabel, email);

        GroupLayout.SequentialGroup phoneNumRow = UIHelper.createFixedRowFromParams(layout, phoneNumberLabel, phoneNumber);
        GroupLayout.ParallelGroup phoneNumColumn = UIHelper.createFixedColumnFromParams(layout, phoneNumberLabel, phoneNumber);

        GroupLayout.SequentialGroup addressRow = UIHelper.createFixedRowFromParams(layout, addressLabel, address);
        GroupLayout.ParallelGroup addressColumn = UIHelper.createFixedColumnFromParams(layout, addressLabel, address);

        GroupLayout.SequentialGroup saveButtonRow = layout.createSequentialGroup().addComponent(saveButton);
        GroupLayout.ParallelGroup saveButtonColumn = layout.createParallelGroup().addComponent(saveButton);

        //todo add gap between text fields and [Save] button
        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(nameRow).addGroup(surnameRow)
                .addGroup(midNameRow).addGroup(birthdayRow).addGroup(emailRow).addGroup(phoneNumRow)
                .addGroup(addressRow).addGroup(saveButtonRow));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(nameColumn).addGroup(surnameColumn)
                .addGroup(midNameColumn).addGroup(birthColumn).addGroup(emailColumn).addGroup(phoneNumColumn)
                .addGroup(addressColumn).addGroup(saveButtonColumn));

        layout.linkSize(SwingConstants.HORIZONTAL, nameLabel, surnameLabel, middleNameLabel,
                birthdayLabel, phoneNumberLabel, emailLabel, addressLabel);
    }

    public void injectMedicalCardModel(MedicalCardModel cardModel) {
        setModel(cardModel);
        name.setDocument(cardModel.getNameDoc());
        surname.setDocument(cardModel.getSurnameDoc());
        birthday.setDocument(cardModel.getBirthdayDoc());
        phoneNumber.setDocument(cardModel.getContactPhoneDoc());
        email.setDocument(cardModel.getEmailDoc());
        address.setDocument(cardModel.getAddressDoc());
        middleName.setDocument(cardModel.getMiddleNameDoc());
    }

    public MedicalCardModel getModel() {
        return model;
    }

    public void setModel(MedicalCardModel model) {
        pcs.firePropertyChange("model", this.model, model);
        this.model = model;
    }

    public void addModelPropertyChangeSupport(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener("model", listener);
    }

    public void refreshCardStructureTree() {
        //todo remove this hack and refresh tree when saving new Card
        pcs.firePropertyChange("model", null, this.model);
    }

    public MainWindow getParentFrame() {
        return parentFrame;
    }

}
