package org.olzd.emr;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultEditorKit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UIHelper {
    public static GroupLayout.SequentialGroup createFixedRowFromParams(final GroupLayout groupLayout, JComponent compA, JComponent compB) {
        GroupLayout.SequentialGroup group = groupLayout.createSequentialGroup()
                .addComponent(compA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(compB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);

        return group;
    }

    public static GroupLayout.ParallelGroup createFixedColumnFromParams(final GroupLayout groupLayout, JComponent compA, JComponent compB) {
        GroupLayout.ParallelGroup group = groupLayout.createParallelGroup()
                .addComponent(compA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(compB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);

        return group;
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static Date parseDate(String stringDate) {
        if (stringDate != null && !stringDate.isEmpty()) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public static void setAutoScrollingForTextArea(JTextArea textArea) {
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    public static JPopupMenu createCCPContextMenu() {
        JPopupMenu ccpMenu = new JPopupMenu();
        JMenuItem menuItem = null;

        menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
        menuItem.setText("Вырезать");
        //menuItem.setMnemonic(KeyEvent.VK_T);
        ccpMenu.add(menuItem);

        menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        menuItem.setText("Копировать");
        //menuItem.setMnemonic(KeyEvent.VK_C);
        ccpMenu.add(menuItem);

        menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        menuItem.setText("Вставить");
        //menuItem.setMnemonic(KeyEvent.VK_P);
        ccpMenu.add(menuItem);
        return ccpMenu;
    }
}
