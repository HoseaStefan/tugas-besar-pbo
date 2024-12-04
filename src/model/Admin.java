package model;

import java.util.ArrayList;

public class Admin extends User {
    private String admin_id;
    private ArrayList<Promo> listPromo;
    public String getAdmin_id() {
        return admin_id;
    }
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }
    public ArrayList<Promo> getListPromo() {
        return listPromo;
    }
    public void addListPromo(Promo promo) {
        this.listPromo.add(promo);
    }
    public Admin(String admin_id) {
        this.admin_id = admin_id;
    }
    public Admin(String user_id, String name, String username, String email, UserType userType, String admin_id) {
        super(user_id, name, username, email, userType);
        this.admin_id = admin_id;
    }
}
