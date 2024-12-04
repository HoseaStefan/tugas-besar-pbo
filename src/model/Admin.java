package model;

import java.util.ArrayList;

public class Admin extends User {
    private ArrayList<Promo> listPromo;
    
    public Admin() {
        
    }
    public Admin(String user_id, String name, String username, String email, UserType userType) {
        super(user_id, name, username, email, userType);
        
    }

    public ArrayList<Promo> getListPromo() {
        return listPromo;
    }
    public void addListPromo(Promo promo) {
        this.listPromo.add(promo);
    }
}
