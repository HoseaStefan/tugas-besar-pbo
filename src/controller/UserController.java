package controller;

import model.UserType;

public class UserController {
    public static UserType verifyUser(String username, String password){
        System.out.println(username);
        System.out.println(password);
        return null;
    }

    public static boolean verifyRegister(String username, String email, String password){
        System.out.println(username);
        System.out.println(email);
        System.out.println(password);
        return true;
    }
}
