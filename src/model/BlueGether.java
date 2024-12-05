package model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class BlueGether extends Tabungan {
    private String owner_ID;
    private Double saldoGether;
    private double targetSaldo;
    private ArrayList<Nasabah> listNasabah;

    public BlueGether(String tabungan_id, String user_id, String namaTabungan, TabunganType tabunganType,
            double saldoAwal, Timestamp start_date, String owner_ID, Double saldoGether, double targetSaldo,
            ArrayList<Nasabah> listNasabah) {
        super(tabungan_id, user_id, namaTabungan, tabunganType, saldoAwal, start_date);
        this.owner_ID = owner_ID;
        this.saldoGether = saldoGether;
        this.targetSaldo = targetSaldo;
        this.listNasabah = listNasabah;
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

    public void pindahSaldo() {
        System.out.println("Pindah Saldo BlueGether");
    }

    public void tarikSaldo() {
        System.out.println("Tarik Saldo BlueGether");
    }

    public void showSaldo() {
        System.out.println("Saldo BlueGether: " + saldoGether);
    }

    public void tutupBlueGether() {
        System.out.println("BlueGether ditutup");
    }

    @Override
    public void createBlueSaving() {
    }

    public void createBlueGether() {
        System.out.println("BlueGether created");
    }

    public void createBlueDeposito() {
    }

}