package model;

import java.sql.Timestamp;

public abstract class Tabungan {
    private String tabungan_id;
    private String user_id;
    protected String namaTabungan;
    private TabunganType tabunganType;
    private double saldoAwal;
    private Timestamp start_date;

    public Tabungan(String tabungan_id, String user_id, String namaTabungan, TabunganType tabunganType,
            double saldoAwal, Timestamp start_date) {
        this.tabungan_id = tabungan_id;
        this.user_id = user_id;
        this.namaTabungan = namaTabungan;
        this.tabunganType = tabunganType;
        this.saldoAwal = saldoAwal;
        this.start_date = start_date;
    }

    public String getTabungan_id() {
        return tabungan_id;
    }

    public void setTabungan_id(String tabungan_id) {
        this.tabungan_id = tabungan_id;
    }

    public String getuser_id() {
        return user_id;
    }

    public void setuser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNamaTabungan() {
        return namaTabungan;
    }

    public void setNamaTabungan(String namaTabungan) {
        this.namaTabungan = namaTabungan;
    }

    public TabunganType getTabunganType() {
        return tabunganType;
    }

    public void setTabunganType(TabunganType tabunganType) {
        this.tabunganType = tabunganType;
    }

    public double getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(double saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public abstract void createBlueSaving();

    public abstract void createBlueGether();

    public abstract void createBlueDeposito();
}