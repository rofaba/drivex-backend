package org.backend.classes.vehicle;

import org.backend.common.DAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class VehicleDAO implements DAO<Vehicle> {

    private static final Logger log = Logger.getLogger(VehicleDAO.class.getName());
    private final DataSource dataSource;

    public VehicleDAO(DataSource dataProvider) {
        this.dataSource = dataProvider;
    }

    // Mapea una fila del ResultSet a un Vehicle
    private Vehicle mapper(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(rs.getInt("id"));
        vehicle.setReference(rs.getString("reference"));
        vehicle.setBrand(rs.getString("brand"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setColor(rs.getString("color"));
        vehicle.setDescription(rs.getString("description"));
        vehicle.setPrice(rs.getDouble("price"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setFuel_type(rs.getString("fuel_type"));
        return vehicle;
    }

    @Override
    public Optional<Vehicle> save(Vehicle vehicle) {
        String query = "INSERT INTO vehicles(" +
                "reference, brand, model, color, description, price, year, fuel_type, created_at" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, vehicle.getReference());
            pst.setString(2, vehicle.getBrand());
            pst.setString(3, vehicle.getModel());
            pst.setString(4, vehicle.getColor());
            pst.setString(5, vehicle.getDescription());
            pst.setDouble(6, vehicle.getPrice());
            pst.setInt(7, vehicle.getYear());
            pst.setString(8, vehicle.getFuel_type());
            pst.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

            int res = pst.executeUpdate();
            if (res > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        vehicle.setId(keys.getInt(1));
                    }
                }
                log.info("Vehicle inserted successfully");
                return Optional.of(vehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> update(Vehicle vehicle) {
        String query = "UPDATE vehicles SET " +
                "reference = ?, brand = ?, model = ?, color = ?, description = ?, " +
                "price = ?, year = ?, fuel_type = ?, updated_at = ? " +
                "WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, vehicle.getReference());
            pst.setString(2, vehicle.getBrand());
            pst.setString(3, vehicle.getModel());
            pst.setString(4, vehicle.getColor());
            pst.setString(5, vehicle.getDescription());
            pst.setDouble(6, vehicle.getPrice());
            pst.setInt(7, vehicle.getYear());
            pst.setString(8, vehicle.getFuel_type());
            pst.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            pst.setInt(10, vehicle.getId());

            int res = pst.executeUpdate();
            if (res > 0) {
                log.info("Vehicle updated successfully");
                return Optional.of(vehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> delete(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            return Optional.empty();
        }

        String query = "DELETE FROM vehicles WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, vehicle.getId());
            int res = pst.executeUpdate();
            if (res > 0) {
                log.info("Vehicle deleted successfully");
                return Optional.of(vehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                vehicles.add(mapper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public Optional<Vehicle> findById(Integer id) {
        String query = "SELECT * FROM vehicles WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapper(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public Optional<Vehicle> deleteById(Integer id) {
        String query = "DELETE FROM vehicles WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            int res = pst.executeUpdate();
            if (res > 0) {
                log.info("Vehicle deleted successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
