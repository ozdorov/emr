package org.olzd.emr.view;

import org.olzd.emr.UIHelper;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CCPTextArea extends JTextArea {
    public CCPTextArea(int rows, int columns) {
        super(rows, columns);

        final JPopupMenu tempMenu = UIHelper.createCCPContextMenu();

        add(tempMenu);
        addMouseListener(new CCPMouseAdapter(this, tempMenu));
    }

    private class CCPMouseAdapter extends MouseAdapter {
        private JTextArea jTextArea;
        private JPopupMenu jPopupMenu;

        private CCPMouseAdapter(JTextArea textArea, JPopupMenu popupMenu) {
            jTextArea = textArea;
            jPopupMenu = popupMenu;
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                int x = e.getX();
                int y = e.getY();
                jPopupMenu.show(jTextArea, x, y);
            }
        }
    }
}
