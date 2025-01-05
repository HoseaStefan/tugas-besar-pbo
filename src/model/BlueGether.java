package model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class BlueGether extends Tabungan {
    private Double saldoGether;
    private int jangkaWaktu;
    private double targetSaldo;
    private double tabunganHarian;
    private ArrayList<Nasabah> listNasabah;

    public BlueGether(String tabungan_id, String user_id, String namaTabungan, TabunganType tabunganType,
            double saldoAwal, Timestamp start_date, Double saldoGether, int jangkaWaktu, double targetSaldo,
            double tabunganHarian, ArrayList<Nasabah> listNasabah) {
        super(tabungan_id, user_id, namaTabungan, tabunganType, saldoAwal, start_date);
        this.saldoGether = saldoGether;
        this.jangkaWaktu = jangkaWaktu;
        this.targetSaldo = targetSaldo;
        this.tabunganHarian = tabunganHarian;
        this.listNasabah = listNasabah;
    }

    public Double getSaldoGether() {
        return saldoGether;
    }

    public void setSaldoGether(Double saldoGether) {
        this.saldoGether = saldoGether;
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

    public double getTabunganHarian() {
        return tabunganHarian;
    }

    public void setTabunganHarian(double tabunganHarian) {
        this.tabunganHarian = tabunganHarian;
    }

    public ArrayList<Nasabah> getListNasabah() {
        return listNasabah;
    }

    public void setListNasabah(ArrayList<Nasabah> listNasabah) {
        this.listNasabah = listNasabah;
    }

    public void tambahDana(double jumlahDana) {
        this.saldoGether += jumlahDana;
        System.out.println("Dana berhasil ditambahkan. Saldo terkini: Rp." + this.saldoGether);
    }

    public void tarikDana(double jumlahDana) {
        if (this.saldoGether >= jumlahDana) {
            this.saldoGether -= jumlahDana;
            System.out.println("Dana berhasil ditarik. Saldo terkini: Rp." + this.saldoGether);
        } else {
            System.out.println("Saldo tidak cukup untuk melakukan penarikan.");
        }
    }

    public void showSaldo() {
        System.out.println("Saldo BlueGether: " + saldoGether);
    }

    public void tutupBlueGether() {
        System.out.println("BlueGether ditutup");
    }

    public void gantiNamaBlueGether(String namaBaru) {
        this.namaTabungan = namaBaru;
        System.out.println("Nama Tabungan diubah menjadi: " + namaBaru);
    }

    @Override
    public void createBlueSaving() {
        throw new UnsupportedOperationException("BlueSaving is not supported in BlueSaving class.");
    }

    public void createBlueGether() {
        System.out.println("Creating Blue Gether...");
        System.out.println("Tabungan ID: " + getTabungan_id());
        System.out.println("User ID: " + getuser_id());
        System.out.println("Nama Tabungan: " + getNamaTabungan());
        System.out.println("Saldo Awal: " + getSaldoAwal());
        System.out.println("Jangka Waktu (bulan): " + getJangkaWaktu());
        System.out.println("Target Saldo: " + getTargetSaldo());
    }

    public void createBlueDeposito() {
        throw new UnsupportedOperationException("BlueDeposito is not supported in BlueSaving class.");
    }

    public static double hitungTabunganHarian(double saldo, double targetSaldo, int jangkaWaktuBulan) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(jangkaWaktuBulan);

        long jumlahHari = ChronoUnit.DAYS.between(startDate, endDate);

        double tabunganHarian = (targetSaldo - saldo) / jumlahHari;

        DecimalFormat df = new DecimalFormat("#.##");
        tabunganHarian = Double.parseDouble(df.format(tabunganHarian));

        return tabunganHarian;
    }

}