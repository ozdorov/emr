package org.olzd.emr.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitFromApplicationAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
