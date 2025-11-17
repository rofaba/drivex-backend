package org.backend;

import org.backend.common.DataProvider;
import org.backend.classes.transfer.Transfer;
import org.backend.classes.transfer.TransferDAO;
import org.backend.classes.user.UserDAO;
import org.backend.classes.vehicle.VehicleDAO;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido a DriveX");
        UserDAO userDAO = new UserDAO(DataProvider.getDataSource());
        VehicleDAO vehicleDAO = new VehicleDAO(DataProvider.getDataSource());
        TransferDAO transferDAO = new TransferDAO(DataProvider.getDataSource());
        transferDAO.save(new Transfer(0, 1, 5, 1, "sell", 12500.00, "delivered"));
        //vehicleDAO.save(new Vehicle(1, "asfas","Audi","Q5","Blanco", "Suv familiar", 11500.0, 2012, "Gas"));
        //userDAO.deleteById(4);
        //userDAO.save(new User(2, "Rodrigo Faure", "rodrigofaure@gmail.com", "1234", "Rodrigo", "Faure", "629519746", "Admin"));
        //userDAO.update(new User(2, "Rodrigo Faure", "rodrigofaure@gmail.com", "1234", "Rodrigo", "Faure", "629519746", "Admin"));
    }
}