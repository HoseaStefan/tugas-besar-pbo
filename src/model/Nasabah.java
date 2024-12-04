package model;

import java.util.ArrayList;

public class Nasabah extends User {
    private String nasabah_id;
    private String fullName;
    private int pin;
    private int nomorRekening;
    private double saldo;
    private Loyalty loyalty;
    private ArrayList<Tabungan> listTabungan;
    private ArrayList<PromoUsed> listVoucher;
    private ArrayList<Transaksi> listTransaksi;

    public Nasabah(String nasabah_id, String fullName, int pin, int nomorRekening, double saldo, Loyalty loyalty) {
        this.nasabah_id = nasabah_id;
        this.fullName = fullName;
        this.pin = pin;
        this.nomorRekening = nomorRekening;
        this.saldo = saldo;
        this.loyalty = loyalty;
    }

    public Nasabah(String user_id, String name, String username, String email, UserType userType, String nasabah_id,
            String fullName, int pin, int nomorRekening, double saldo, Loyalty loyalty) {
        super(user_id, name, username, email, userType);
        this.nasabah_id = nasabah_id;
        this.fullName = fullName;
        this.pin = pin;
        this.nomorRekening = nomorRekening;
        this.saldo = saldo;
        this.loyalty = loyalty;
    }

    public String getNasabah_id() {
        return nasabah_id;
    }

    public void setNasabah_id(String nasabah_id) {
        this.nasabah_id = nasabah_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getNomorRekening() {
        return nomorRekening;
    }

    public void setNomorRekening(int nomorRekening) {
        this.nomorRekening = nomorRekening;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Loyalty getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Loyalty loyalty) {
        this.loyalty = loyalty;
    }

    public ArrayList<Tabungan> getListTabungan() {
        return listTabungan;
    }

    public void setListTabungan(ArrayList<Tabungan> listTabungan) {
        this.listTabungan = listTabungan;
    }

    public ArrayList<Transaksi> getListTransaksi() {
        return listTransaksi;
    }

    public void setListTransaksi(ArrayList<Transaksi> listTransaksi) {
        this.listTransaksi = listTransaksi;
    }

}