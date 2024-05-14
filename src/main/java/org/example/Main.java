package org.example;

import org.example.service.UserService;
import org.example.service.UserServiceJDBCImpl;

public class Main {
    public static void main(String[] args) {
        scrypt(new UserServiceJDBCImpl());
    }

    private static void scrypt(UserService userService) {
        userService.createUserTable();

        userService.saveUser("name1", "lastName1", (byte) 14);
        userService.saveUser("name2", "lastName2", (byte) 34);
        userService.saveUser("name3", "lastName3", (byte) 18);
        userService.saveUser("name4", "lastName4", (byte) 23);

        System.out.println();
        userService.getAllUsers().forEach(System.out::println);

        userService.removeUserById(2);
        System.out.println();
        userService.getAllUsers().forEach(System.out::println);

        userService.clearUserTable();
        System.out.println();
        userService.getAllUsers().forEach(System.out::println);

        userService.dropUserTable();
    }
}