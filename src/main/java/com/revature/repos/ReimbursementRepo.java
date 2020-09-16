package com.revature.repos;

import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.util.ConnectionFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ReimbursementRepo {

    private String baseQuery = "SELECT * FROM project1.ers_reimbursements er " +
                               "Join project1.ers_reimbursement_types rt " +
                               "ON er.reimb_type_id = rt.reimb_type_id " +
                               "JOIN project1.ers_reimbursement_statuses rs " +
                               "ON er.reimb_status_id = rs.reimb_status_id ";


    public Reimbursement getReimbursementById(int id){
        Optional<Reimbursement> reimbursement = Optional.empty();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "Where reimb_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);

            ResultSet rs = pstmt.executeQuery();

            reimbursement = mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!reimbursement.isPresent()){
            throw new ResourceNotFoundException("Reimbursement is not found with id: " + id);
        }
        return reimbursement.get();
    }

    public Set<Reimbursement> getPendingReimbursementById(int userId) {
        Set<Reimbursement> reimbursement = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE author_id = ? AND er.reimb_status_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, 3);
            // 3 is pending

            ResultSet rs = pstmt.executeQuery();

            reimbursement = mapResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reimbursement;
    }


    public Set<Reimbursement> getResolvedReimbursementById(int userId) {
        Set<Reimbursement> reimbursement = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE author_id = ? AND er.reimb_status_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, 1);
            // 1 is approved

            ResultSet rs1 = pstmt.executeQuery();

            sql = baseQuery + "WHERE author_id = ? AND er.reimb_status_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, 2);
            // 2 is denied

            ResultSet rs2 = pstmt.executeQuery();

            reimbursement = mapResultSet(rs1);
            reimbursement.addAll(mapResultSet(rs2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reimbursement;
    }

    public Set<Reimbursement> getAllPendingReimbursements() {
        Set<Reimbursement> reimbursement = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE er.reimb_status_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 3);
            // 3 is pending

            ResultSet rs = pstmt.executeQuery();

            reimbursement = mapResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reimbursement;
    }

    public Set<Reimbursement> getAllResolvedReimbursements() {
        Set<Reimbursement> reimbursement = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE er.reimb_status_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            // 1 is approved

            ResultSet rs1 = pstmt.executeQuery();

            sql = baseQuery + "WHERE er.reimb_status_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 2);
            // 2 is denied

            ResultSet rs2 = pstmt.executeQuery();

            reimbursement = mapResultSet(rs1);
            reimbursement.addAll(mapResultSet(rs2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reimbursement;
    }

    public void updateReimbursement(Reimbursement reimbursement) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            String sql = "UPDATE project1.ers_reimbursements " +
                         "SET amount = ?, submitted = ?, resolved = ?, description = ?, receipt = ?, author_id = ?, resolver_id = ?, reimb_status_id = ?, reimb_type_id = ? " +
                         "Where reimb_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"reimb_id"});
            pstmt.setDouble(1, reimbursement.getAmount());
            pstmt.setTimestamp(2, reimbursement.getSubmitted());
            pstmt.setTimestamp(3, reimbursement.getResolved());
            pstmt.setString(4, reimbursement.getDescription());
            pstmt.setString(5, reimbursement.getDescription());
            pstmt.setInt(6, reimbursement.getAuthorId());
            pstmt.setInt(7, reimbursement.getResolverId());
            pstmt.setInt(8, reimbursement.getReimbursementStatus().ordinal());
            pstmt.setInt(9, reimbursement.getReimbursementType().ordinal());
            pstmt.setInt(10, reimbursement.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNew(Reimbursement reimbursement) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT into project1.ers_reimbursements (amount, submitted, description, receipt, author_id, reimb_status_id, reimb_type_id) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"reimb_id"});
            pstmt.setDouble(1, reimbursement.getAmount());
            pstmt.setTimestamp(2, reimbursement.getSubmitted());
            pstmt.setString(3, reimbursement.getDescription());
            pstmt.setString(4, reimbursement.getReceipt());
            pstmt.setInt(  5, reimbursement.getAuthorId());
            pstmt.setInt( 6, reimbursement.getReimbursementStatus().ordinal());
            pstmt.setInt(7, reimbursement.getReimbursementType().ordinal());

            pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Set<Reimbursement> mapResultSet(ResultSet rs) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();

        while (rs.next()) {
            Reimbursement temp = new Reimbursement();
            temp.setId(rs.getInt("reimb_id"));
            temp.setAmount(rs.getDouble("amount"));
            temp.setSubmitted(rs.getTimestamp("submitted"));
            temp.setResolved(rs.getTimestamp("resolved"));
            temp.setDescription(rs.getString("description"));
            temp.setReceipt(rs.getString("receipt"));
            temp.setAuthorId(rs.getInt("author_id"));
            temp.setResolverId(rs.getInt("resolver_id"));
            temp.setReimbursementStatus(ReimbursementStatus.getByName(rs.getString("reimb_status")));
            temp.setReimbursementType(ReimbursementType.getByName(rs.getString("reimb_type")));
            reimbursements.add(temp);
        }

        return reimbursements;
    }
}
