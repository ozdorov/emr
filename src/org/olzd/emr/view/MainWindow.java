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
    private JTree cardStructureTree = new JTree();
    private JPanel emptyPanel = new JPanel();
    private EditMedicalCardPanel medicalCardPanel = new EditMedicalCardPanel(this);
    private ExaminationPanel examinationPanel = new ExaminationPanel(this);
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
        DefaultTreeModel tempModel = new DefaultTreeModel(null);
        cardStructureTree.setModel(tempModel);
        cardStructureTree.setCellRenderer(new CustomTreeRenderer());
        cardStructureTree.setScrollsOnExpand(true);
        cardStructureTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        cardStructureTree.addMouseListener(new TreeContextMenuTrigger(cardStructureTree, treeContextMenu));
        cardStructureTree.add(constructTreePopupMenu());
    }

    private JPopupMenu constructTreePopupMenu() {
        AddAnalysisGroupAction addAnalysisGroup = new AddAnalysisGroupAction(cardStructureTree, "Добавить группу для результатов");
        addAnalysisGroup.putValue("command", ContextMenuCommand.ADD_ATTACHMENT_GROUP);
        AddAnalysisDocAction addAnalysisDoc = new AddAnalysisDocAction(cardStructureTree, "Добавить файл");
        addAnalysisDoc.putValue("command", ContextMenuCommand.ADD_ATTACHMENT_FILE);
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

        CloseCardAction closeCardAction = new CloseCardAction(this);
        closeCardAction.putValue(Action.NAME, "Закрыть карточку");

        fileMenu.add(createCardItem);
        fileMenu.add(popupAction);
        fileMenu.add(closeCardAction);
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
            if (!(treeNode.getUserObject() instanceof TreeNodeModel)) {
                return this;
            }
            TreeNodeModel nodeModel = (TreeNodeModel) treeNode.getUserObject();
            TreeNodeType nodeType = nodeModel.getNodeType();
            if (nodeType == TreeNodeType.ANALYSIS_PLACEHOLDER || nodeType == TreeNodeType.ANALYSIS_TYPE
                    || nodeType == TreeNodeType.SURGERY_PLACEHOLDER || nodeType == TreeNodeType.EXAMINATION_PLACEHOLDER
                    || nodeType == TreeNodeType.TECH_EXAMINATION_PLACEHOLDER || nodeType == TreeNodeType.TECH_EXAMINATION_TYPE) {
                setIcon(getDefaultOpenIcon());
            }

            return this;
        }
    }
}
