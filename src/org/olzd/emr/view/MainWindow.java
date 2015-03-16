package org.olzd.emr.view;

import org.olzd.emr.action.*;
import org.olzd.emr.model.ContextMenuCommand;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;
import org.olzd.emr.view.popups.SearchPopup;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
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
    private JPopupMenu treeContextMenu = new JPopupMenu();

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
        TreeNodeModel nodeModelTop = new TreeNodeModel("Карточка пациента", TreeNodeType.ROOT);
        TreeNodeModel nodeModelAnalysis = new TreeNodeModel("Анализы", TreeNodeType.ANALYSIS_PLACEHOLDER);
        TreeNodeModel nodeModelOperations = new TreeNodeModel("Операции", TreeNodeType.SURGERY_PLACEHOLDER);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(nodeModelTop);
        DefaultMutableTreeNode placeholderForAnalysis = new DefaultMutableTreeNode(nodeModelAnalysis);
        DefaultMutableTreeNode placeholderForOperations = new DefaultMutableTreeNode(nodeModelOperations);
        top.add(placeholderForAnalysis);
        top.add(placeholderForOperations);

        cardStructureTreeModel = new DefaultTreeModel(top);

        cardStructureTree = new JTree(cardStructureTreeModel);
        cardStructureTree.setCellRenderer(new CustomTreeRenderer());
        cardStructureTree.setScrollsOnExpand(true);
        cardStructureTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        cardStructureTree.addMouseListener(new TreeContextMenuTrigger(cardStructureTree, treeContextMenu));
        cardStructureTree.add(constructTreePopupMenu());
    }

    private JPopupMenu constructTreePopupMenu() {
        AddAnalysisGroupAction addAnalysisGroup = new AddAnalysisGroupAction(cardStructureTree, "Добавить группу для результатов");
        addAnalysisGroup.putValue("command", ContextMenuCommand.ADD_ANALYSIS_GROUP);
        AddAnalysisDocAction addAnalysisDoc = new AddAnalysisDocAction(cardStructureTree, "Добавить файл");
        addAnalysisDoc.putValue("command", ContextMenuCommand.ADD_ANALYSIS_DOC);
        JMenuItem removeItem = new JMenuItem("Удалить группу");
        treeContextMenu.add(addAnalysisGroup);
        treeContextMenu.add(addAnalysisDoc);
        treeContextMenu.add(removeItem);

        return treeContextMenu;
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

    private class CustomTreeRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            TreeNodeModel nodeModel = (TreeNodeModel) treeNode.getUserObject();
            TreeNodeType nodeType = nodeModel.getNodeType();
            if (nodeType == TreeNodeType.ANALYSIS_PLACEHOLDER || nodeType == TreeNodeType.ANALYSIS_TYPE
                    || nodeType == TreeNodeType.SURGERY_PLACEHOLDER) {
                setIcon(getDefaultOpenIcon());
            }

            return this;
        }
    }
}
