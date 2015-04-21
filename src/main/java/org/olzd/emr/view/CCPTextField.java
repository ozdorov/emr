package org.olzd.emr.view;

import org.olzd.emr.UIHelper;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CCPTextField extends JTextField {
    public CCPTextField(int columns) {
        super(columns);

        final JPopupMenu tempMenu = UIHelper.createCCPContextMenu();

        add(tempMenu);
        addMouseListener(new CCPMouseAdapter(this, tempMenu));
    }

    private class CCPMouseAdapter extends MouseAdapter {
        private CCPTextField jTextArea;
        private JPopupMenu jPopupMenu;

        private CCPMouseAdapter(CCPTextField textArea, JPopupMenu popupMenu) {
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

