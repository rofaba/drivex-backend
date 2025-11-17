package org.backend.classes.vehicle;

import org.backend.common.DAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class VehicleDAO implements DAO<Vehicle> {
    Logger log = Logger.getLogger(VehicleDAO.class.getName());
    private final DataSource dataSource;

    private Vehicle mapper(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(rs.getInt("id"));
        vehicle.setReference(rs.getString("reference"));
        vehicle.setBrand(rs.getString("brand"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setDescription(rs.getString("description"));
        vehicle.setPrice(rs.getDouble("price"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setFuel_type(rs.getString("role"));
        return vehicle;
    }

    public VehicleDAO(DataSource dataProvider) {
        this.dataSource = dataProvider;
    }



    @Override
    public Optional<Vehicle> save(Vehicle vehicle) {
        String query = "Insert into vehicles(reference, brand, model, description, price, year, fuel_type, created_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            pst.setString(1, vehicle.getReference());
            pst.setString(2, vehicle.getBrand());
            pst.setString(3, vehicle.getModel());
            pst.setString(4, vehicle.getDescription());
            pst.setDouble(5, vehicle.getPrice());
            pst.setInt(6, vehicle.getYear());
            pst.setString(7, vehicle.getFuel_type());
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pst.execute();
            log.info("Vehicle inserted successfully");
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public Optional<Vehicle> update(Vehicle vehicle) {
        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> delete(Vehicle vehicle) {
        return Optional.empty();
    }

    @Override
    public List<Vehicle> findAll() {
        return List.of();
    }

    @Override
    public Optional<Vehicle> findById(Integer id) {
        return Optional.empty();
    }
}
