package org.olzd.emr.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.olzd.emr.entity.MedicalCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RemindersService {
    private static final Logger LOGGER = LogManager.getLogger(RemindersService.class.getName());

    public List<MedicalCard> findCardsByExamDate(LocalDate date) {
        List<MedicalCard> result = new ArrayList<>(3);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String query = "select name, surname, contact_phone from medical_card "
                    + "where datediff(next_exam_date, ?) <= 5";
            PreparedStatement stat = conn.prepareStatement(query);
            stat.setDate(1, new Date(date.toDate().getTime()));

            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                MedicalCard card = new MedicalCard();
                card.setName(rs.getString(1));
                card.setSurname(rs.getString(2));
                card.setContactPhone1(rs.getString(3));
                result.add(card);
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }

        return result;
    }

    public List<MedicalCard> findCardsByBirthday(LocalDate date) {
        List<MedicalCard> result = new ArrayList<>(3);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String query = "select name, surname, contact_phone from medical_card "
                    + "where dayofmonth(birthday) = ? and month(birthday) = ?";
            PreparedStatement stat = conn.prepareStatement(query);
            stat.setInt(1, date.getDayOfMonth());
            stat.setInt(2, date.getMonthOfYear());

            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                MedicalCard card = new MedicalCard();
                card.setName(rs.getString(1));
                card.setSurname(rs.getString(2));
                card.setContactPhone1(rs.getString(3));
                result.add(card);
            }
        } catch (SQLException e) {
            LOGGER.error("Error", e);
        }

        return result;
    }
}
