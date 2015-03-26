package org.olzd.emr.service;

import org.olzd.emr.entity.AttachedFileWrapper;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.entity.ParentsInfo;
import org.olzd.emr.model.SearchByNameModel;
import org.olzd.emr.model.SearchResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalCardService {

    public MedicalCard loadMedicalCard(SearchResult searchByName) {
        MedicalCard card = new MedicalCard();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String findQuery = "select card.card_id, name, middle_name, surname, birthday, contact_phone, email, address, "
                    + "diagnosis, related_diagnosis, next_exam_date, mother_name, mother_phone, father_name, father_phone "
                    + "from medical_card card, parents_info par where card.card_id = par.card_id and card.card_id = ?";
            try (PreparedStatement st = conn.prepareStatement(findQuery)) {
                st.setInt(1, searchByName.getCardId());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    card.setCardId(rs.getInt(1));
                    card.setName(rs.getString(2));
                    card.setMiddleName(rs.getString(3));
                    card.setSurname(rs.getString(4));
                    card.setDateOfBirth(rs.getDate(5));
                    card.setContactPhone(rs.getString(6));
                    card.setEmail(rs.getString(7));
                    card.setAddress(rs.getString(8));
                    card.setMainDiagnosis(rs.getString(9));
                    card.setRelatedDiagnosis(rs.getString(10));
                    card.setDateOfNextExamination(rs.getDate(11));

                    card.setAnalysisAttachedFiles(findAllAttachedAnalysisFiles(card));

                    ParentsInfo parentsInfo = new ParentsInfo();
                    parentsInfo.setMotherName(rs.getString(12));
                    parentsInfo.setMotherPhone(rs.getString(13));
                    parentsInfo.setFatherName(rs.getString(14));
                    parentsInfo.setFatherPhone(rs.getString(15));
                    card.setParentsInfo(parentsInfo);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return card;
    }

    public List<SearchResult> findMedicalCardByName(SearchByNameModel searchByName) {
        List<SearchResult> res = new ArrayList<>(3);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String findQuery = "select card_id, name, surname"
                    + " from medical_card where surname like concat(?, '%')";
            try (PreparedStatement st = conn.prepareStatement(findQuery)) {
                st.setString(1, searchByName.getSurname());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    res.add(new SearchResult(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
                    + "email = ?, address = ?, diagnosis = ?, related_diagnosis = ?, next_exam_date = ?"
                    + " where card_id = ?").toString();

            try (PreparedStatement statement = conn.prepareStatement(updateSql)) {
                statement.setString(1, card.getName());
                statement.setString(2, card.getMiddleName());
                statement.setString(3, card.getSurname());
                statement.setDate(4, card.getDateOfBirth() != null ? new Date(card.getDateOfBirth().getTime()) : null);
                statement.setString(5, card.getContactPhone());
                statement.setString(6, card.getEmail());
                statement.setString(7, card.getAddress());
                statement.setString(8, card.getMainDiagnosis());
                statement.setString(9, card.getRelatedDiagnosis());
                statement.setDate(10, card.getDateOfNextExamination() != null ? new Date(card.getDateOfNextExamination().getTime()) : null);
                statement.setInt(11, card.getCardId());
                statement.execute();
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        saveParentsInfo(card);
    }

    public void createMedicalCard(MedicalCard card) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("insert into medical_card" +
                    "(name, middle_name, surname, birthday, contact_phone, email, address, diagnosis, related_diagnosis, next_exam_date)" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").toString();

            try (PreparedStatement statement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, card.getName());
                statement.setString(2, card.getMiddleName());
                statement.setString(3, card.getSurname());
                statement.setDate(4, card.getDateOfBirth() != null ? new Date(card.getDateOfBirth().getTime()) : null);
                statement.setString(5, card.getContactPhone());
                statement.setString(6, card.getEmail());
                statement.setString(7, card.getAddress());
                statement.setString(8, card.getMainDiagnosis());
                statement.setString(9, card.getRelatedDiagnosis());
                statement.setDate(10, card.getDateOfNextExamination() != null ? new Date(card.getDateOfNextExamination().getTime()) : null);
                statement.execute();
                int generatedId;
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    card.setCardId(generatedId);
                }
                saveParentsInfo(card);

            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void saveAnalysisFile(MedicalCard card, AttachedFileWrapper analysisFile) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("insert into analysis_docs" +
                    "(card_id, analysis_group, analysis_doc_location) values (?, ?, ?)").toString();
            try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
                statement.setInt(1, card.getCardId());
                statement.setString(2, analysisFile.getGroupName());
                statement.setString(3, analysisFile.getPathToFile());
                statement.execute();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<AttachedFileWrapper> findAllAttachedAnalysisFiles(MedicalCard card) {
        List<AttachedFileWrapper> result = new ArrayList<>(5);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("select analysis_group, analysis_doc_location from analysis_docs" +
                    " where card_id = ?").toString();
            try (PreparedStatement st = conn.prepareStatement(insertSQL)) {
                st.setInt(1, card.getCardId());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    result.add(new AttachedFileWrapper(rs.getString(2), rs.getString(1)));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public void saveParentsInfo(MedicalCard card) {
        ParentsInfo parentsInfo = card.getParentsInfo();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String query = "insert into parents_info (card_id, mother_name, mother_phone, father_name, father_phone) " +
                    "values (?, ?, ?, ?, ?) " +
                    "on duplicate key update mother_name = ?, mother_phone = ?, father_name = ?, father_phone = ?";
            try (PreparedStatement st = conn.prepareStatement(query)) {
                st.setInt(1, card.getCardId());
                st.setString(2, parentsInfo.getMotherName());
                st.setString(3, parentsInfo.getMotherPhone());
                st.setString(4, parentsInfo.getFatherName());
                st.setString(5, parentsInfo.getFatherPhone());
                st.setString(6, parentsInfo.getMotherName());
                st.setString(7, parentsInfo.getMotherPhone());
                st.setString(8, parentsInfo.getFatherName());
                st.setString(9, parentsInfo.getFatherPhone());
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
