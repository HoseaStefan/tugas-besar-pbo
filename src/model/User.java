package model;

public abstract class User {
    private String user_id;
    private String name;
    private String username;
    private String email;
    private UserType userType;

    public User(){
    }

    public User(String user_id, String name, String username, String email, UserType userType) {
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.userType = userType;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}