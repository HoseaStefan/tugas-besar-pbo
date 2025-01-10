package model;

import java.sql.Timestamp;

public class Loyalty {
    private String loyalty_id;
    private String user_id;
    private double hargaLoyalty;
    private int banyakVoucherSetor;
    private int banyakVoucherTransfer;
    private int banyakVoucherTopup;
    private boolean loyaltyActive;
    private Timestamp expiredDate;

    public String getLoyalty_id() {
        return loyalty_id;
    }


    public void setLoyalty_id(String loyalty_id) {
        this.loyalty_id = loyalty_id;
    }


    public String getUser_id() {
        return user_id;
    }


    public void setUser_id(String getuser_id) {
        this.user_id = getuser_id;
    }

    
    public double getHargaLoyalty() {
        return hargaLoyalty;
    }


    public void setHargaLoyalty(double hargaLoyalty) {
        this.hargaLoyalty = hargaLoyalty;
    }


    public int getBanyakVoucherSetor() {
        return banyakVoucherSetor;
    }


    public void setBanyakVoucherSetor(int banyakVoucherSetor) {
        this.banyakVoucherSetor = banyakVoucherSetor;
    }


    public int getBanyakVoucherTransfer() {
        return banyakVoucherTransfer;
    }


    public void setBanyakVoucherTransfer(int banyakVoucherTransfer) {
        this.banyakVoucherTransfer = banyakVoucherTransfer;
    }


    public int getBanyakVoucherTopup() {
        return banyakVoucherTopup;
    }


    public void setBanyakVoucherTopup(int banyakVoucherTopup) {
        this.banyakVoucherTopup = banyakVoucherTopup;
    }


    public boolean isLoyaltyActive() {
        return loyaltyActive;
    }


    public void setLoyaltyActive(boolean loyaltyActive) {
        this.loyaltyActive = loyaltyActive;
    }


    public Timestamp getExpiredDate() {
        return expiredDate;
    }


    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }

    
    public Loyalty(String loyalty_id, String getuser_id, double hargaLoyalty, int banyakVoucherSetor,
            int banyakVoucherTransfer, int banyakVoucherTopup, boolean loyaltyActive, Timestamp expiredDate) {
        this.loyalty_id = loyalty_id;
        this.user_id = getuser_id;
        this.hargaLoyalty = hargaLoyalty;
        this.banyakVoucherSetor = banyakVoucherSetor;
        this.banyakVoucherTransfer = banyakVoucherTransfer;
        this.banyakVoucherTopup = banyakVoucherTopup;
        this.loyaltyActive = loyaltyActive;
        this.expiredDate = expiredDate;
    }

    
}