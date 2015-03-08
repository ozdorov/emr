package org.olzd.emr.view;

import org.olzd.emr.action.ExitFromApplicationAction;
import org.olzd.emr.action.OpenSearchPopupAction;
import org.olzd.emr.action.PrepareEditMedicalCardAction;
import org.olzd.emr.view.popups.SearchPopup;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private SearchPopup searchPopup;
    private JSplitPane splitPane = new JSplitPane();
    private JTree cardStructureTree = new JTree();
    private JPanel emptyPanel = new JPanel();
    private EditMedicalCardPanel medicalCardPanel;

    public MainWindow() {
        constructView();
        setJMenuBar(createMenuBar());

        setExtendedState(MAXIMIZED_BOTH);
        //TODO temp, until system tray is implemented
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //File menu
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem createCardItem = new JMenuItem();
        PrepareEditMedicalCardAction editCardAction = new PrepareEditMedicalCardAction(this);
        createCardItem.setAction(editCardAction);
        createCardItem.setText("Создать карточку");

        OpenSearchPopupAction popupAction = new OpenSearchPopupAction(searchPopup);
        popupAction.putValue(Action.NAME, "Найти карточку");

        ExitFromApplicationAction exitAction = new ExitFromApplicationAction();
        exitAction.putValue(Action.NAME, "Выход");
        fileMenu.add(createCardItem);
        fileMenu.add(popupAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);

        menuBar.add(fileMenu);
        return menuBar;
    }

    private void constructView() {
        searchPopup = new SearchPopup(this, "Найти карточку", true);
        searchPopup.setLocationRelativeTo(this);

        cardStructureTree.setMinimumSize(new Dimension(200, 0));

        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(cardStructureTree);
        splitPane.setRightComponent(emptyPanel);

        getContentPane().add(splitPane);
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public void showMedicalCardPanel(boolean show) {
        if (show) {
            splitPane.setRightComponent(getMedicalCardPanel());
        } else {
            splitPane.setRightComponent(emptyPanel);
        }

        splitPane.repaint();
    }

    public EditMedicalCardPanel getMedicalCardPanel() {
        if (medicalCardPanel == null) {
            medicalCardPanel = new EditMedicalCardPanel();
        }
        return medicalCardPanel;
    }
}
