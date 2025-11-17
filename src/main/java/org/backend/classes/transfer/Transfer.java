package org.backend.classes.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    private Integer id;
    private int vehicle_id;
    private int buyer_id;
    private int seller_id;
    private String type_transaction;
    private double amount;
    private String order_status;
}
