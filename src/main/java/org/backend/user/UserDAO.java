package org.backend.user;

import org.backend.common.DAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;



public class UserDAO implements DAO {

    private static final Logger logger = Logger.getLogger(User.class.getName());

    private final DataSource dataSource;

    private User mapper(ResultSet rs) throws SQLException {
        User contact = new User();
        contact.setId(rs.getInt("id"));
        contact.setUsername(rs.getString("username"));
        contact.setEmail(rs.getString("email"));
        contact.setPassword(rs.getString("password"));
        contact.setFirstName(rs.getString("firstname"));
        contact.setLastName(rs.getString("lastname"));
        contact.setPhoneNumber(String.valueOf(rs.getInt("phonenumber")));
        contact.setRol(rs.getString("rol"));
        return contact;
    }

    public UserDAO(DataSource dataProvider) {
        this.dataSource = dataProvider;
    }




    public Optional save(User user) {
        String query = "Insert into users (username, email, password_hash, first_name, last_name, phone_number, role) values (?, ?, ?, ?, ?, ?, ?)";
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
    public Optional save(Object o) {
        return Optional.empty();
    }

    @Override
    public Optional update(Object o) {
        return Optional.empty();
    }

    @Override
    public Optional delete(Object o) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public Optional findById(Integer id) {
        return Optional.empty();
    }
}
