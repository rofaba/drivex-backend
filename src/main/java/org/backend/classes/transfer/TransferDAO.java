package org.backend.classes.transfer;

import org.backend.common.DAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TransferDAO implements DAO<Transfer> {

    private final DataSource dataSource;
    Logger log = Logger.getLogger(TransferDAO.class.getName());
    private Transfer mapper(ResultSet rs) throws SQLException {
        Transfer transfer = new Transfer();
        transfer.setId(rs.getInt("id"));
        transfer.setVehicle_id(rs.getInt("vehicle_id"));
        transfer.setBuyer_id(rs.getInt("buyer_id"));
        transfer.setSeller_id(rs.getInt("seller_id"));
        transfer.setType_transaction(rs.getString("transaction_type"));
        transfer.setAmount(rs.getDouble("amount"));
        transfer.setOrder_status(rs.getString("order_status"));
        return transfer;
    }

    public TransferDAO(DataSource dataProvider) {
        this.dataSource = dataProvider;
    }

    @Override
    public Optional<Transfer> save(Transfer transfer) {
        String query = "Insert into transactions(vehicle_id,seller_id,buyer_id,transaction_type,amount,order_status,transaction_date) values (?,?,?,?,?,?,?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            pst.setInt(1, transfer.getVehicle_id());
            pst.setInt(2, transfer.getSeller_id());
            pst.setInt(3, transfer.getBuyer_id());
            pst.setString(4, transfer.getType_transaction());
            pst.setDouble(5, transfer.getAmount());
            pst.setString(6, transfer.getOrder_status());
            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            log.info("Transaction inserted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Transfer> update(Transfer transfer) {
        return Optional.empty();
    }

    @Override
    public Optional<Transfer> delete(Transfer transfer) {
        return Optional.empty();
    }

    @Override
    public List<Transfer> findAll() {
        return List.of();
    }

    @Override
    public Optional<Transfer> findById(Integer id) {
        return Optional.empty();
    }
}
