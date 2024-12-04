package model;

public class PromoUsed extends Promo {
    private int jumlahKuota;

    public PromoUsed(int jumlahKuota) {
        this.jumlahKuota = jumlahKuota;
    }

    public PromoUsed(String promo_id, String kode_promo, PromoType promoType, double potonganValue, int jumlahKuota) {
        super(promo_id, kode_promo, promoType, potonganValue);
        this.jumlahKuota = jumlahKuota;
    }

    public int getJumlahKuota() {
        return jumlahKuota;
    }

    public void setJumlahKuota(int jumlahKuota) {
        this.jumlahKuota = jumlahKuota;
    }
}
