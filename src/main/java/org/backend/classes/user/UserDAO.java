package org.backend.classes.user;

import org.backend.common.DAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;



public class UserDAO implements DAO<User> {
    private List<User> users = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(User.class.getName());

    private final DataSource dataSource;

    private User mapper(ResultSet rs) throws SQLException {
        User contact = new User();
        contact.setId(rs.getInt("id"));
        contact.setUsername(rs.getString("username"));
        contact.setEmail(rs.getString("email"));
        contact.setPassword(rs.getString("password_hash"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setPhoneNumber(String.valueOf(rs.getInt("phone_number")));
        contact.setRol(rs.getString("role"));
        return contact;
    }

    public UserDAO(DataSource dataProvider) {
        this.dataSource = dataProvider;
    }



    @Override
    public Optional save(User user) {

        String query = "Insert into users (username, email, password_hash, first_name, last_name, phone_number, role, created_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            pst.setString(3, hashedPassword);
            pst.setString(4, user.getFirstName());
            pst.setString(5, user.getLastName());
            pst.setString(6, user.getPhoneNumber());
            pst.setString(7, user.getRol());
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            Integer res = pst.executeUpdate();
            if(res>0){
                logger.info("User added successfully");
                ResultSet keys = pst.getGeneratedKeys();
                keys.next();
                user.setId(keys.getInt(1));
                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();

    }

    @Override
    public Optional<User> update(User user) {
        String query = "Update users set username = ?, email = ?, first_name = ?, last_name = ?, phone_number = ?, password_hash = ?, updated_at = ? where id = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getFirstName());
            pst.setString(4, user.getLastName());
            pst.setString(5, user.getPhoneNumber());
            pst.setString(6, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pst.setInt(8, user.getId());
            Integer res = pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> delete(User user) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        String query = "Select * from users";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement pst = conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            ResultSet res = pst.executeQuery();
            while (res.next()){
                users.add(mapper(res));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional findById(Integer id) {
        String query = "Insert into users (id) values (?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            pst.setString(1, String.valueOf(id));
            ResultSet res = pst.executeQuery();
            while (res.next()){
                logger.info("User found with id " + id);
                users.add(mapper(res));
           }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional deleteById(Integer id) {
        String query = "Delete from users where id = ?";
        try (Connection conn = dataSource.getConnection();
        PreparedStatement pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            pst.setInt(1, id);
            Integer res = pst.executeUpdate();
            if (res>0){
                logger.info("User deleted successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
