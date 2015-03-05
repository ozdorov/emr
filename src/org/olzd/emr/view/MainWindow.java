package org.olzd.emr.view;

import org.olzd.emr.action.ExitFromApplicationAction;
import org.olzd.emr.action.OpenSearchPopupAction;
import org.olzd.emr.view.popups.SearchPopup;

import javax.swing.*;

public class MainWindow extends JFrame{
    private SearchPopup searchPopup;

    public MainWindow() {
        searchPopup = new SearchPopup(this, "Найти карточку", true);
        searchPopup.setLocationRelativeTo(this);

        setJMenuBar(createMenuBar());
        createLayout();

        setExtendedState(MAXIMIZED_BOTH);
        //TODO temp, until system tray is implemented
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //File menu
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem createCardItem = new JMenuItem("Создать карточку");

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

    protected void createLayout() {
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(groupLayout);
    }
}
