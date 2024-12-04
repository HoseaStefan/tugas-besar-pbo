package model;

public abstract class Promo {
    private String promo_id;
    private String kode_promo;
    private PromoType promoType;
    private double potonganValue;

    public Promo(){
        
    }

    public Promo(String promo_id, String kode_promo, PromoType promoType, double potonganValue) {
        this.promo_id = promo_id;
        this.kode_promo = kode_promo;
        this.promoType = promoType;
        this.potonganValue = potonganValue;
    }

    public String getPromo_id() {
        return promo_id;
    }

    public void setPromo_id(String promo_id) {
        this.promo_id = promo_id;
    }

    public String getKode_promo() {
        return kode_promo;
    }

    public void setKode_promo(String kode_promo) {
        this.kode_promo = kode_promo;
    }

    public PromoType getPromoType() {
        return promoType;
    }

    public void setPromoType(PromoType promoType) {
        this.promoType = promoType;
    }

    public double getPotonganValue() {
        return potonganValue;
    }

    public void setPotonganValue(double potonganValue) {
        this.potonganValue = potonganValue;
    }
}
