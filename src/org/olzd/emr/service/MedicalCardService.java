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
            String findQuery = "select card_id, name, middle_name, surname, birthday, contact_phone, email, address"
                    + " from medical_card where surname like concat(?, '%')";
            try (PreparedStatement st = conn.prepareStatement(findQuery)) {
                st.setString(1, searchByName.getSurname());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    MedicalCard card = new MedicalCard();
                    card.setCardId(rs.getInt(1));
                    card.setName(rs.getString(2));
                    card.setMiddleName(rs.getString(3));
                    card.setSurname(rs.getString(4));
                    card.setDateOfBirth(rs.getDate(5));
                    card.setContactPhone(rs.getString(6));
                    card.setEmail(rs.getString(7));
                    card.setAddress(rs.getString(8));

                    res.add(card);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return res;
    }

    public void updateMedicalCard(MedicalCard card) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String updateSql = new StringBuilder("update medical_card "
                    + "set name = ?, middle_name = ?, surname = ?, birthday = ?, contact_phone = ?, "
                    + "email = ?, address = ?"
                    + " where card_id = ?").toString();

            try (PreparedStatement statement = conn.prepareStatement(updateSql)) {
                statement.setString(1, card.getName());
                statement.setString(2, card.getMiddleName());
                statement.setString(3, card.getSurname());
                statement.setDate(4, card.getDateOfBirth() != null ? new Date(card.getDateOfBirth().getTime()) : null);
                statement.setString(5, card.getContactPhone());
                statement.setString(6, card.getEmail());
                statement.setString(7, card.getAddress());
                statement.setInt(8, card.getCardId());
                statement.execute();
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void createMedicalCard(MedicalCard card) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("insert into medical_card" +
                    "(name, middle_name, surname, birthday, contact_phone, email, address) values (?, ?, ?, ?, ?, ?, ?)").toString();

            try (PreparedStatement statement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, card.getName());
                statement.setString(2, card.getMiddleName());
                statement.setString(3, card.getSurname());
                statement.setDate(4, card.getDateOfBirth() != null ? new Date(card.getDateOfBirth().getTime()) : null);
                statement.setString(5, card.getContactPhone());
                statement.setString(6, card.getEmail());
                statement.setString(7, card.getAddress());
                statement.execute();
                int generatedId;
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

    public void saveAnalysisDocLocation() {

    }
}
