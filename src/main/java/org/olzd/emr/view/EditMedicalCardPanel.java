package org.olzd.emr.view;

import org.olzd.emr.UIHelper;
import org.olzd.emr.action.CardTreeRefresher;
import org.olzd.emr.action.SaveMedicalCardAction;
import org.olzd.emr.model.MedicalCardModel;
import org.olzd.emr.view.popups.PrivateNotesPopup;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;

public class EditMedicalCardPanel extends JPanel {

    private JTextField name = new JTextField(16);
    private JTextField surname = new JTextField(16);
    private JTextField middleName = new JTextField(16);
    private JFormattedTextField birthday = new JFormattedTextField();
    private JFormattedTextField dateOfNextExamination = new JFormattedTextField();
    private JTextField phoneNumber = new JTextField(16);
    private JTextField phoneNumber2 = new JTextField(16);
    private JTextField email = new JTextField(20);
    private JTextArea address = new JTextArea(3, 30);
    private JTextArea mainDiagnosis = new JTextArea(3, 30);
    private JTextArea relatedDiagnosis = new JTextArea(3, 30);
    private JTextField motherName = new JTextField(16);
    private JTextField motherPhone = new JTextField(16);
    private JTextField fatherName = new JTextField(16);
    private JTextField fatherPhone = new JTextField(16);
    private JButton saveButton = new JButton();
    private JPasswordField privateNotesPass = new JPasswordField(16);
    private JButton showPasswordButton = new JButton();
    private JButton checkPasswordButton = new JButton();
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

        showPasswordButton.setAction(new AbstractAction("Заметки") {
            @Override
            public void actionPerformed(ActionEvent e) {
                privateNotesPass.setVisible(!privateNotesPass.isVisible());
                checkPasswordButton.setVisible(!checkPasswordButton.isVisible());
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        checkPasswordButton.setAction(new AbstractAction("Отобразить") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = String.valueOf(privateNotesPass.getPassword());
                if (pass.equals("карандаш")) {
                    PrivateNotesPopup privateNotesPopup = new PrivateNotesPopup(parentFrame, "Заметки", true);
                    privateNotesPopup.setLocationRelativeTo(parentFrame);
                    privateNotesPopup.pack();
                    privateNotesPopup.setVisible(true);

                    //hide password field and show button after successful password
                    hideNotesBlock();
                }
                privateNotesPass.setText("");
            }
        });

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##/##/####");
        } catch (ParseException e) {
            System.out.println(e);
        }
        DefaultFormatterFactory dateFormatterFactory = new DefaultFormatterFactory(maskFormatter);
        birthday.setFormatterFactory(dateFormatterFactory);
        dateOfNextExamination.setFormatterFactory(dateFormatterFactory);
        addModelPropertyChangeSupport(new CardTreeRefresher(getParentFrame().getCardStructureTree()));
    }

    private void constructPanelView(GroupLayout layout) {
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        hideNotesBlock();

        JLabel nameLabel = new JLabel("Имя");
        JLabel surnameLabel = new JLabel("Фамилия");
        JLabel middleNameLabel = new JLabel("Отчество");
        JLabel birthdayLabel = new JLabel("Год рождения");
        JLabel phoneNumberLabel = new JLabel("Контактный телефон #1");
        JLabel phoneNumberLabel2 = new JLabel("Контактный телефон #2");
        JLabel emailLabel = new JLabel("Адрес электронной почты");
        JLabel addressLabel = new JLabel("Адрес");
        JLabel nextExaminationLabel = new JLabel("Дата контрольного осмотра");
        JLabel mainDiagnosisLabel = new JLabel("Основной диагноз");
        JLabel relatedDiagnosisLabel = new JLabel("Сопутствующий диагноз");
        JLabel motherNameLabel = new JLabel("ФИО мамы");
        JLabel fatherNameLabel = new JLabel("ФИО папы");
        JLabel motherPhoneLabel = new JLabel("Телефон мамы");
        JLabel fatherPhoneLabel = new JLabel("Телефон папы");

        address.setLineWrap(true);

        birthday.setColumns(10);
        dateOfNextExamination.setColumns(10);
        JScrollPane scrollPaneForAddress = new JScrollPane(address);
        JScrollPane scrollPaneForDiagnosis = new JScrollPane(mainDiagnosis);
        JScrollPane scrollPaneForRelatedDiagnosis = new JScrollPane(relatedDiagnosis);
        UIHelper.setAutoScrollingForTextArea(address);
        UIHelper.setAutoScrollingForTextArea(mainDiagnosis);
        UIHelper.setAutoScrollingForTextArea(relatedDiagnosis);

        GroupLayout.SequentialGroup nameRow = UIHelper.createFixedRowFromParams(layout, nameLabel, name).addGap(200)
                .addComponent(nextExaminationLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(dateOfNextExamination, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        GroupLayout.ParallelGroup nameColumn = UIHelper.createFixedColumnFromParams(layout, nameLabel, name)
                .addComponent(nextExaminationLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(dateOfNextExamination, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);

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

        GroupLayout.SequentialGroup phoneNum2Row = UIHelper.createFixedRowFromParams(layout, phoneNumberLabel2, phoneNumber2);
        GroupLayout.ParallelGroup phoneNum2Column = UIHelper.createFixedColumnFromParams(layout, phoneNumberLabel2, phoneNumber2);

        GroupLayout.SequentialGroup addressRow = UIHelper.createFixedRowFromParams(layout, addressLabel, scrollPaneForAddress);
        GroupLayout.ParallelGroup addressColumn = UIHelper.createFixedColumnFromParams(layout, addressLabel, scrollPaneForAddress);

        GroupLayout.SequentialGroup diagnosisRow = UIHelper.createFixedRowFromParams(layout, mainDiagnosisLabel, scrollPaneForDiagnosis);
        GroupLayout.ParallelGroup diagnosisColumn = UIHelper.createFixedColumnFromParams(layout, mainDiagnosisLabel, scrollPaneForDiagnosis);

        GroupLayout.SequentialGroup relatedDiagnosisRow = UIHelper.createFixedRowFromParams(layout, relatedDiagnosisLabel, scrollPaneForRelatedDiagnosis);
        GroupLayout.ParallelGroup relatedDiagnosisColumn = UIHelper.createFixedColumnFromParams(layout, relatedDiagnosisLabel, scrollPaneForRelatedDiagnosis);

        GroupLayout.SequentialGroup motherNameRow = UIHelper.createFixedRowFromParams(layout, motherNameLabel, motherName);
        GroupLayout.ParallelGroup motherNameColumn = UIHelper.createFixedColumnFromParams(layout, motherNameLabel, motherName);

        GroupLayout.SequentialGroup motherPhoneRow = UIHelper.createFixedRowFromParams(layout, motherPhoneLabel, motherPhone);
        GroupLayout.ParallelGroup motherPhoneColumn = UIHelper.createFixedColumnFromParams(layout, motherPhoneLabel, motherPhone);

        GroupLayout.SequentialGroup fatherNameRow = UIHelper.createFixedRowFromParams(layout, fatherNameLabel, fatherName);
        GroupLayout.ParallelGroup fatherNameColumn = UIHelper.createFixedColumnFromParams(layout, fatherNameLabel, fatherName);

        GroupLayout.SequentialGroup fatherPhoneRow = UIHelper.createFixedRowFromParams(layout, fatherPhoneLabel, fatherPhone);
        GroupLayout.ParallelGroup fatherPhoneColumn = UIHelper.createFixedColumnFromParams(layout, fatherPhoneLabel, fatherPhone);

        GroupLayout.SequentialGroup saveButtonRow = layout.createSequentialGroup().addComponent(saveButton)
                .addComponent(showPasswordButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(privateNotesPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(checkPasswordButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        GroupLayout.ParallelGroup saveButtonColumn = layout.createParallelGroup()
                .addComponent(saveButton)
                .addComponent(showPasswordButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(privateNotesPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(checkPasswordButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);

        //todo add gap between text fields and [Save] button
        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(nameRow).addGroup(surnameRow)
                .addGroup(midNameRow).addGroup(birthdayRow).addGroup(emailRow).addGroup(phoneNumRow).addGroup(phoneNum2Row)
                .addGroup(addressRow).addGroup(diagnosisRow).addGroup(relatedDiagnosisRow)
                .addGroup(motherNameRow).addGroup(motherPhoneRow).addGroup(fatherNameRow).addGroup(fatherPhoneRow)
                .addGroup(saveButtonRow));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(nameColumn).addGroup(surnameColumn)
                .addGroup(midNameColumn).addGroup(birthColumn).addGroup(emailColumn).addGroup(phoneNumColumn).addGroup(phoneNum2Column)
                .addGroup(addressColumn).addGroup(diagnosisColumn).addGroup(relatedDiagnosisColumn)
                .addGroup(motherNameColumn).addGroup(motherPhoneColumn).addGroup(fatherNameColumn).addGroup(fatherPhoneColumn)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(saveButtonColumn));

        layout.linkSize(SwingConstants.HORIZONTAL, nameLabel, surnameLabel, middleNameLabel,
                birthdayLabel, phoneNumberLabel, phoneNumberLabel2, emailLabel, addressLabel,
                mainDiagnosisLabel, relatedDiagnosisLabel,
                motherNameLabel, fatherNameLabel, motherPhoneLabel, fatherPhoneLabel);
    }

    public void injectMedicalCardModel(MedicalCardModel cardModel) {
        setModel(cardModel);
        name.setDocument(cardModel.getNameDoc());
        surname.setDocument(cardModel.getSurnameDoc());
        birthday.setDocument(cardModel.getBirthdayDoc());
        phoneNumber.setDocument(cardModel.getContactPhoneDoc());
        phoneNumber2.setDocument(cardModel.getContactPhone2Doc());
        email.setDocument(cardModel.getEmailDoc());
        address.setDocument(cardModel.getAddressDoc());
        middleName.setDocument(cardModel.getMiddleNameDoc());
        mainDiagnosis.setDocument(cardModel.getDiagnosisDoc());
        relatedDiagnosis.setDocument(cardModel.getRelatedDiagnosisDoc());
        dateOfNextExamination.setDocument(cardModel.getNextExamDate());

        motherName.setDocument(cardModel.getMotherNameDoc());
        motherPhone.setDocument(cardModel.getMotherPhoneDoc());
        fatherName.setDocument(cardModel.getFatherNameDoc());
        fatherPhone.setDocument(cardModel.getFatherPhoneDoc());

        //enable notes button is card is already saved.
        showPasswordButton.setEnabled(cardModel.getCard().isExisting());
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

    public MainWindow getParentFrame() {
        return parentFrame;
    }

    public void setShowPasswordButtonVisible() {
        showPasswordButton.setEnabled(true);
    }

    protected void hideNotesBlock() {
        // showPasswordButton.setEnabled(false);
        privateNotesPass.setVisible(false);
        checkPasswordButton.setVisible(false);
    }

}
