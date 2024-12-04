package model;

import java.sql.Timestamp;

public class BlueSaving extends Tabungan {
    private String tabungan_ID;
    private Double saldoSaving;
    private int jangkaWaktu;
    private double targetSaldo;

    public BlueSaving(String tabungan_id, String user_id, String namaTabungan, TabunganType tabunganType,
            double saldoAwal, Timestamp start_date, String tabungan_ID2, Double saldoSaving, int jangkaWaktu,
            double targetSaldo) {
        super(tabungan_id, user_id, namaTabungan, tabunganType, saldoAwal, start_date);
        tabungan_ID = tabungan_ID2;
        this.saldoSaving = saldoSaving;
        this.jangkaWaktu = jangkaWaktu;
        this.targetSaldo = targetSaldo;
    }

    public String getTabungan_ID() {
        return tabungan_ID;
    }

    public void setTabungan_ID(String tabungan_ID) {
        this.tabungan_ID = tabungan_ID;
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

    public void pindahSaldo() {
        System.out.println("Pindah Saldo BlueSaving");
    }

    public void tarikSaldo() {
        System.out.println("Tarik Saldo BlueSaving");
    }

    public void showSaldo() {
        System.out.println("Saldo BlueSaving: " + saldoSaving);
    }

    public void tutupBlueSaving() {
        System.out.println("BlueSaving ditutup");
    }

    public void gantiNamaBlueSaving(String namaBaru) {
        this.namaTabungan = namaBaru;
        System.out.println("Nama Tabungan diubah menjadi: " + namaBaru);
    }

    @Override
    public void createBlueSaving() {
        System.out.println("BlueSaving created");
    }

    public void createBlueGether() {
    }

    public void createBlueDeposito() {
    }
}