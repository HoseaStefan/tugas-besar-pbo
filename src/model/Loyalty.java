package model;

public class Loyalty {
    private String loyalty_id;
    private String nasabah_id;
    private LoyaltyType loyaltyType;
    private double hargaLoyalty;
    private int banyakKodePromo;

    public Loyalty(String loyalty_id, String nasabah_id, LoyaltyType loyaltyType, double hargaLoyalty,
            int banyakKodePromo) {
        this.loyalty_id = loyalty_id;
        this.nasabah_id = nasabah_id;
        this.loyaltyType = loyaltyType;
        this.hargaLoyalty = hargaLoyalty;
        this.banyakKodePromo = banyakKodePromo;
    }

    public String getLoyalty_id() {
        return loyalty_id;
    }

    public void setLoyalty_id(String loyalty_id) {
        this.loyalty_id = loyalty_id;
    }

    public String getNasabah_id() {
        return nasabah_id;
    }

    public void setNasabah_id(String nasabah_id) {
        this.nasabah_id = nasabah_id;
    }

    public LoyaltyType getLoyaltyType() {
        return loyaltyType;
    }

    public void setLoyaltyType(LoyaltyType loyaltyType) {
        this.loyaltyType = loyaltyType;
    }

    public double getHargaLoyalty() {
        return hargaLoyalty;
    }

    public void setHargaLoyalty(double hargaLoyalty) {
        this.hargaLoyalty = hargaLoyalty;
    }

    public int getBanyakKodePromo() {
        return banyakKodePromo;
    }

    public void setBanyakKodePromo(int banyakKodePromo) {
        this.banyakKodePromo = banyakKodePromo;
    }

}