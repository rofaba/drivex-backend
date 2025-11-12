package org.backend;

import org.backend.common.DataProvider;
import org.backend.user.User;
import org.backend.user.UserDAO;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido a DriveX");
        UserDAO userDAO = new UserDAO(DataProvider.getDataSource());
        userDAO.save(new User(1, "Rodrigo gay", "ignaciocastillon2003@gmail.com", "1234", "Ignacio", "Castillón", "634824724", "Admin"));
    }
}