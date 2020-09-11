package com.revature.repos;

import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.util.ConnectionFactory;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class UserRepo {

    private String baseQuery = "SELECT * FROM project1.ers_users eu " +
                               "Join project1.ers_user_roles ur " +
                               "ON eu.user_role_id = ur.role_id ";

    public UserRepo() {super();}

    public Optional<User> findUserByCredentials(String username, String password){
        Optional<User> _user = Optional.empty();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE username = ? AND user_password = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException se){
            se.printStackTrace();
        }
        return _user;
    }

    public Optional<User> findUserByUsername(String username){
        Optional<User> _user = Optional.empty();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE username = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException se){
            se.printStackTrace();
        }
        return _user;
    }

    public Optional<User> findUserByEmail(String email){
        Optional<User> _user = Optional.empty();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE email = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException se){
            se.printStackTrace();
        }
        return _user;
    }

    public Optional<User> findUserById(int id){
            Optional<User> _user = Optional.empty();
            try (Connection conn = ConnectionFactory.getInstance().getConnection()){

                String sql = baseQuery + "WHERE ers_user_id = ?";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);

                ResultSet rs = pstmt.executeQuery();

                _user = mapResultSet(rs).stream().findFirst();

            } catch (SQLException se){
                se.printStackTrace();
            }
            return _user;
        }

    public Set<User> getAllUsers(){
        Set<User> users = new HashSet<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery;

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            users = mapResultSet(rs);

        } catch (SQLException se){
            se.printStackTrace();
        }
        return  users;
    }

    private Set<User> mapResultSet(ResultSet rs) throws SQLException {
        Set<User> users = new HashSet<>();

        while (rs.next()) {
            User temp = new User();
            temp.setId(rs.getInt("ers_user_id"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("user_password"));
            temp.setFirstName(rs.getString("first_name"));
            temp.setLastName(rs.getString("last_name"));
            temp.setEmail(rs.getString("email"));
            temp.setUserRole(UserRole.getByName(rs.getString("role_name")));
            users.add(temp);
        }

        return users;
    }

    public void save(User newUser) throws IOException {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT into project1.ers_users (username, user_password, first_name, last_name, email, user_role_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"ers_user_id"});
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getFirstName());
            pstmt.setString(4, newUser.getLastName());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setInt(6, newUser.getUserRole().ordinal());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0){

                ResultSet rs = pstmt.getGeneratedKeys();

                rs.next();
                newUser.setId(rs.getInt(1));
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    //deletes user from db
    // TODO ADD CASCADING DELETE IN DB
    public void delete(Optional<User> user) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "DELETE FROM project1.ers_users WHERE ers_user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,user.get().getId());

            pstmt.executeUpdate();


        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    public void updateUser(User updatedUser) throws IOException {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "UPDATE project1.ers_users " +
                         "SET username = ?, user_password = ?, first_name = ?, last_name = ?, email = ?, user_role_id = ? " +
                         "WHERE ers_user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"ers_user_id"});
            pstmt.setString(1, updatedUser.getUsername());
            pstmt.setString(2, updatedUser.getPassword());
            pstmt.setString(3, updatedUser.getFirstName());
            pstmt.setString(4, updatedUser.getLastName());
            pstmt.setString(5, updatedUser.getEmail());
            pstmt.setInt(6, updatedUser.getUserRole().ordinal());
            pstmt.setInt(7, updatedUser.getId());

            pstmt.executeUpdate();

        } catch (SQLException se){
            se.printStackTrace();
        }
    }
}
