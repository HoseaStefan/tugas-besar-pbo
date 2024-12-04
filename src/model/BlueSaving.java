package model;

import java.sql.Timestamp;

public class BlueSaving extends Tabungan {
    private String tabungan_ID;
    private Double saldoSaving;
    private int jangkaWaktu;
    private double targetSaldo;

    public BlueSaving(String tabungan_id, String user_id, TabunganType tabunganType, Timestamp start_date,
            String tabunganID, Double saldoSaving, int jangkaWaktu, double targetSaldo) {
        super(tabungan_id, user_id, tabunganType, start_date);
        this.tabunganID = tabunganID;
        this.saldoSaving = saldoSaving;
        this.jangkaWaktu = jangkaWaktu;
        this.targetSaldo = targetSaldo;
    }

    public String getTabunganID() {
        return tabunganID;
    }

    public void setTabunganID(String tabunganID) {
        this.tabunganID = tabunganID;
    }

    public Double getSaldoSaving() {
        return saldoSaving;
    }

    public void setSaldoSaving(Double saldoSaving) {
        this.saldoSaving = saldoSaving;
    }

    public int getJangkaWaktu() {
        return jangkaWaktu;
    }

    public void setJangkaWaktu(int jangkaWaktu) {
        this.jangkaWaktu = jangkaWaktu;
    }

    public double getTargetSaldo() {
        return targetSaldo;
    }

    public void setTargetSaldo(double targetSaldo) {
        this.targetSaldo = targetSaldo;
    }
}