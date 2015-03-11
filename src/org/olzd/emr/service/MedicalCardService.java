package org.olzd.emr.service;

import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.SearchByNameModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalCardService {

    public List<MedicalCard> findMedicalCardByName(SearchByNameModel searchByName) {
        List<MedicalCard> res = new ArrayList<>(3);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String findQuery = "select card_id, name, surname, birthday from medical_card where surname like ?";
            try (PreparedStatement st = conn.prepareStatement(findQuery)) {
                st.setString(1, searchByName.getSurname());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    MedicalCard card = new MedicalCard();
                    card.setCardId(rs.getInt(1));
                    card.setName(rs.getString(2));
                    card.setSurname(rs.getString(3));
                    card.setDateOfBirth(rs.getDate(4));

                    res.add(card);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return res;
    }

    public void saveMedicalCard(MedicalCard card) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("insert into medical_card(name, surname, birthday) values (?, ?, ?)").toString();

            try (PreparedStatement statement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, card.getName());
                statement.setString(2, card.getSurname());
                statement.setDate(3, new Date(card.getDateOfBirth().getTime()));
                statement.execute();
                int generatedId = -1;
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    card.setCardId(generatedId);
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


    }

}
