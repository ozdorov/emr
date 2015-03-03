package org.olzd.emr.view;

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
        OpenSearchPopupAction popupAction = new OpenSearchPopupAction(searchPopup);
        popupAction.putValue(Action.NAME, "Найти карточку");
        JMenuItem searchCardItem = new JMenuItem(popupAction);

        JMenuItem exitItem = new JMenuItem("Выход");
        fileMenu.add(searchCardItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        return menuBar;
    }

    protected void createLayout() {
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(groupLayout);
    }
}
