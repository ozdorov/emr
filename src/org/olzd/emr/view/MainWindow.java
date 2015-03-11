package org.olzd.emr.view;

import org.olzd.emr.action.EditMedicalCardActionBase;
import org.olzd.emr.action.ExitFromApplicationAction;
import org.olzd.emr.action.OpenSearchPopupAction;
import org.olzd.emr.view.popups.SearchPopup;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class MainWindow extends JFrame {
    private SearchPopup searchPopup;
    private JSplitPane splitPane = new JSplitPane();
    private JTree cardStructureTree;
    private DefaultTreeModel cardStructureTreeModel;
    private JPanel emptyPanel = new JPanel();
    private EditMedicalCardPanel medicalCardPanel;

    public MainWindow() {
        constructLogic();
        constructView();
        setJMenuBar(createMenuBar());

        setExtendedState(MAXIMIZED_BOTH);
        //TODO temp, until system tray is implemented
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void constructLogic() {
        constructTree();
    }

    private void constructTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Карточка пациента");
        DefaultMutableTreeNode placeholderForAnalysis = new DefaultMutableTreeNode("Анализы");
        DefaultMutableTreeNode placeholderForOperations = new DefaultMutableTreeNode("Операции");
        top.add(placeholderForAnalysis);
        top.add(placeholderForOperations);

        cardStructureTreeModel = new DefaultTreeModel(top);

        cardStructureTree = new JTree(cardStructureTreeModel);
        cardStructureTree.setScrollsOnExpand(true);
        cardStructureTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //File menu
        JMenu fileMenu = new JMenu("Файл");
        JMenu operationsMenu = new JMenu("Дополнительно");
        JMenuItem createCardItem = new JMenuItem();
        EditMedicalCardActionBase editCardAction = new EditMedicalCardActionBase(this);
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
        menuBar.add(operationsMenu);
        return menuBar;
    }

    private void constructView() {
        searchPopup = new SearchPopup(this, "Найти карточку", true);
        searchPopup.setLocationRelativeTo(this);

        cardStructureTree.setPreferredSize(new Dimension(250, 0));
        JScrollPane scrollPaneForTree = new JScrollPane(cardStructureTree);

        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(scrollPaneForTree);
        showMedicalCardPanel(false);

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
            medicalCardPanel = new EditMedicalCardPanel(this);
        }
        return medicalCardPanel;
    }

    public JTree getCardStructureTree() {
        return cardStructureTree;
    }
}
