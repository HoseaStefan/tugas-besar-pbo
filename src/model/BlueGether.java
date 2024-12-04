package model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class BlueGether extends Tabungan {
    private String tabungan_ID;
    private String owner_ID;
    private Double saldoGether;
    private double targetSaldo;
    private ArrayList<Nasabah> listNasabah;

    public BlueGether(String tabungan_id, String user_id, TabunganType tabunganType, Timestamp start_date,
            String tabungan_ID2, String owner_ID, Double saldoGether, double targetSaldo,
            ArrayList<Nasabah> listNasabah) {
        super(tabungan_id, user_id, tabunganType, start_date);
        tabungan_ID = tabungan_ID2;
        this.owner_ID = owner_ID;
        this.saldoGether = saldoGether;
        this.targetSaldo = targetSaldo;
        this.listNasabah = listNasabah;
    }

    public String getTabungan_ID() {
        return tabungan_ID;
    }

    public void setTabungan_ID(String tabungan_ID) {
        this.tabungan_ID = tabungan_ID;
    }

    public String getOwner_ID() {
        return owner_ID;
    }

    public void setOwner_ID(String owner_ID) {
        this.owner_ID = owner_ID;
    }

    public Double getSaldoGether() {
        return saldoGether;
    }

    public void setSaldoGether(Double saldoGether) {
        this.saldoGether = saldoGether;
    }

    public double getTargetSaldo() {
        return targetSaldo;
    }

    public void setTargetSaldo(double targetSaldo) {
        this.targetSaldo = targetSaldo;
    }

    public ArrayList<Nasabah> getListNasabah() {
        return listNasabah;
    }

    public void setListNasabah(ArrayList<Nasabah> listNasabah) {
        this.listNasabah = listNasabah;
    }

}