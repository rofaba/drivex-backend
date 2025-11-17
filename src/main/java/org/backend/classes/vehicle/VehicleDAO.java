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

    public VehicleDAO(DataSource dataSource) {
        this.dataSource = dataSource;
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
        String sql = """
                INSERT INTO vehicles
                    (reference, brand, model, color, description,
                     price, year, fuel_type, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
                // Si hay PK autogenerada la recuperamos
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        vehicle.setId(keys.getInt(1));
                    }
                }
                log.info("Vehicle inserted successfully");
                return Optional.of(vehicle);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving vehicle", e);
        }
    }

    @Override
    public Optional<Vehicle> update(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            // Sin id no tiene sentido actualizar
            return Optional.empty();
        }

        String sql = """
                UPDATE vehicles
                SET reference = ?,
                    brand = ?,
                    model = ?,
                    color = ?,
                    description = ?,
                    price = ?,
                    year = ?,
                    fuel_type = ?,
                    updated_at = ?
                WHERE id = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

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
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating vehicle", e);
        }
    }

    @Override
    public Optional<Vehicle> delete(Vehicle vehicle) {
        if (vehicle == null || vehicle.getId() == null) {
            return Optional.empty();
        }

        String sql = "DELETE FROM vehicles WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, vehicle.getId());
            int res = pst.executeUpdate();
            if (res > 0) {
                log.info("Vehicle deleted successfully");
                // Devolvemos el vehículo que se ha borrado
                return Optional.of(vehicle);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting vehicle", e);
        }
    }

    @Override
    public List<Vehicle> findAll() {
        String sql = "SELECT id, reference, brand, model, color, description, price, year, fuel_type FROM vehicles";

        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                vehicles.add(mapper(rs));
            }
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all vehicles", e);
        }
    }

    @Override
    public Optional<Vehicle> findById(Integer id) {
        String sql = "SELECT id, reference, brand, model, color, description, price, year, fuel_type FROM vehicles WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapper(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicle by id", e);
        }
    }
}
