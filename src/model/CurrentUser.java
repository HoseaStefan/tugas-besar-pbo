package model;

public class CurrentUser extends User {
    private static CurrentUser instance;

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            synchronized (CurrentUser.class) { 
                if (instance == null) {
                    instance = new CurrentUser();
                }
            }
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }
}
