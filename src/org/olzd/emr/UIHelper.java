package org.olzd.emr;

import javax.swing.*;

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
}
