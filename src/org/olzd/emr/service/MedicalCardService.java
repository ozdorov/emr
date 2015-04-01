package org.olzd.emr.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.olzd.emr.entity.AttachedFileWrapper;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.entity.ParentsInfo;
import org.olzd.emr.model.SearchByNameModel;
import org.olzd.emr.model.SearchResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalCardService {
    private static final Logger LOGGER = LogManager.getLogger(MedicalCardService.class.getName());

    public MedicalCard loadMedicalCard(SearchResult searchByName) {
        MedicalCard card = new MedicalCard();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String findQuery = "select card.card_id, name, middle_name, surname, birthday, contact_phone, contact_phone2, "
                    + " email, address, diagnosis, related_diagnosis, next_exam_date, mother_name, mother_phone, father_name, father_phone "
                    + "from medical_card card left join parents_info par on (card.card_id = par.card_id) where card.card_id = ?";
            try (PreparedStatement st = conn.prepareStatement(findQuery)) {
                st.setInt(1, searchByName.getCardId());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    card.setCardId(rs.getInt(1));
                    card.setName(rs.getString(2));
                    card.setMiddleName(rs.getString(3));
                    card.setSurname(rs.getString(4));
                    card.setDateOfBirth(rs.getDate(5));
                    card.setContactPhone1(rs.getString(6));
                    card.setContactPhone2(rs.getString(7));
                    card.setEmail(rs.getString(8));
                    card.setAddress(rs.getString(9));
                    card.setMainDiagnosis(rs.getString(10));
                    card.setRelatedDiagnosis(rs.getString(11));
                    card.setDateOfNextExamination(rs.getDate(12));

                    card.setAnalysisAttachedFiles(findAllAttachedAnalysisFiles(card));
                    card.setTechExaminationAttachedFiles(findAllAttachedTechExamFiles(card));

                    ParentsInfo parentsInfo = new ParentsInfo();
                    parentsInfo.setMotherName(rs.getString(12));
                    parentsInfo.setMotherPhone(rs.getString(13));
                    parentsInfo.setFatherName(rs.getString(14));
                    parentsInfo.setFatherPhone(rs.getString(15));
                    card.setParentsInfo(parentsInfo);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while trying to load medical card", e);
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
            LOGGER.error("Error while trying to find medical card by surname + = " + searchByName.getSurname(), e);
        }

        return res;
    }

    public void updateMedicalCard(MedicalCard card) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String updateSql = new StringBuilder("update medical_card "
                    + "set name = ?, middle_name = ?, surname = ?, birthday = ?, contact_phone = ?, contact_phone2 = ?, "
                    + "email = ?, address = ?, diagnosis = ?, related_diagnosis = ?, next_exam_date = ?"
                    + " where card_id = ?").toString();

            try (PreparedStatement statement = conn.prepareStatement(updateSql)) {
                statement.setString(1, card.getName());
                statement.setString(2, card.getMiddleName());
                statement.setString(3, card.getSurname());
                statement.setDate(4, card.getDateOfBirth() != null ? new Date(card.getDateOfBirth().getTime()) : null);
                statement.setString(5, card.getContactPhone1());
                statement.setString(6, card.getContactPhone2());
                statement.setString(7, card.getEmail());
                statement.setString(8, card.getAddress());
                statement.setString(9, card.getMainDiagnosis());
                statement.setString(10, card.getRelatedDiagnosis());
                statement.setDate(11, card.getDateOfNextExamination() != null ? new Date(card.getDateOfNextExamination().getTime()) : null);
                statement.setInt(12, card.getCardId());
                statement.execute();
            } catch (SQLException e) {
                LOGGER.error("Error", e);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while trying to update medical card with Id = " + card.getCardId(), e);
        }

        saveParentsInfo(card);
    }

    public void createMedicalCard(MedicalCard card) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("insert into medical_card"
                    + "(name, middle_name, surname, birthday, contact_phone, contact_phone2,"
                    + "email, address, diagnosis, related_diagnosis, next_exam_date)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").toString();

            try (PreparedStatement statement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, card.getName());
                statement.setString(2, card.getMiddleName());
                statement.setString(3, card.getSurname());
                statement.setDate(4, card.getDateOfBirth() != null ? new Date(card.getDateOfBirth().getTime()) : null);
                statement.setString(5, card.getContactPhone1());
                statement.setString(6, card.getContactPhone2());
                statement.setString(7, card.getEmail());
                statement.setString(8, card.getAddress());
                statement.setString(9, card.getMainDiagnosis());
                statement.setString(10, card.getRelatedDiagnosis());
                statement.setDate(11, card.getDateOfNextExamination() != null ? new Date(card.getDateOfNextExamination().getTime()) : null);
                statement.execute();
                int generatedId;
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    card.setCardId(generatedId);
                }
                saveParentsInfo(card);

            } catch (SQLException e) {
                LOGGER.error("Error", e);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while creating card with surname = " + card.getSurname(), e);
        }
    }

    public void saveAnalysisFileRecord(MedicalCard card, AttachedFileWrapper analysisFile) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("insert into analysis_docs" +
                    "(card_id, analysis_group, file_location) values (?, ?, ?)").toString();
            try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
                statement.setInt(1, card.getCardId());
                statement.setString(2, analysisFile.getGroupName());
                statement.setString(3, analysisFile.getPathToFile());
                statement.execute();
            }
        } catch (SQLException e) {
            LOGGER.error("Error", e);
        }
    }

    public void saveTechExaminationFileRecord(MedicalCard card, AttachedFileWrapper file) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("insert into tech_examination_docs" +
                    "(card_id, examination_group, file_location) values (?, ?, ?)").toString();
            try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
                statement.setInt(1, card.getCardId());
                statement.setString(2, file.getGroupName());
                statement.setString(3, file.getPathToFile());
                statement.execute();
            }
        } catch (SQLException e) {
            LOGGER.error("Error", e);
        }
    }

    public List<AttachedFileWrapper> findAllAttachedAnalysisFiles(MedicalCard card) {
        List<AttachedFileWrapper> result = new ArrayList<>(5);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("select analysis_group, file_location from analysis_docs" +
                    " where card_id = ?").toString();
            try (PreparedStatement st = conn.prepareStatement(insertSQL)) {
                st.setInt(1, card.getCardId());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    result.add(new AttachedFileWrapper(rs.getString(2), rs.getString(1)));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return result;
    }

    public List<AttachedFileWrapper> findAllAttachedTechExamFiles(MedicalCard card) {
        List<AttachedFileWrapper> result = new ArrayList<>(5);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/emr_schema?user=emr&password=emr_")) {
            String insertSQL = new StringBuilder("select examination_group, file_location from tech_examination_docs" +
                    " where card_id = ?").toString();
            try (PreparedStatement st = conn.prepareStatement(insertSQL)) {
                st.setInt(1, card.getCardId());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    result.add(new AttachedFileWrapper(rs.getString(2), rs.getString(1)));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error", e);
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
            LOGGER.error("Error", ex);
        }
    }
}
