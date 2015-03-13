package org.olzd.emr;

import javax.swing.*;
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
        return new SimpleDateFormat("MM/dd/YYYY").format(date);
    }

    public static Date parseDate(String stringDate) throws ParseException {
        if (stringDate != null && !stringDate.isEmpty()) {
            return new SimpleDateFormat("MM/dd/YYYY").parse(stringDate);
        }
        return null;
    }
}
