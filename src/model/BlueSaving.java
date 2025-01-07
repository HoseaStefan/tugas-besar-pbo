package model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BlueSaving extends Tabungan {
    private Double saldoSaving;
    private int jangkaWaktu;
    private double targetSaldo;
    private double tabunganHarian;

    public BlueSaving(String tabungan_id, String user_id, String namaTabungan, TabunganType tabunganType,
            double saldoAwal, Timestamp start_date, Double saldoSaving, int jangkaWaktu, double targetSaldo,
            double tabunganHarian) {
        super(tabungan_id, user_id, namaTabungan, tabunganType, saldoAwal, start_date);
        this.saldoSaving = saldoSaving;
        this.jangkaWaktu = jangkaWaktu;
        this.targetSaldo = targetSaldo;
        this.tabunganHarian = tabunganHarian;
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

    public double getTabunganHarian() {
        return tabunganHarian;
    }

    public void setTabunganHarian(double tabunganHarian) {
        this.tabunganHarian = tabunganHarian;
    }

    // Method untuk menambah dana
    public void tambahDana(double jumlahDana) {
        this.saldoSaving += jumlahDana;
        System.out.println("Dana berhasil ditambahkan. Saldo terkini: Rp." + this.saldoSaving);
    }

    // Method untuk menarik dana
    public void tarikDana(double jumlahDana) {
        if (this.saldoSaving >= jumlahDana) {
            this.saldoSaving -= jumlahDana;
            System.out.println("Dana berhasil ditarik. Saldo terkini: Rp." + this.saldoSaving);
        } else {
            System.out.println("Saldo tidak cukup untuk melakukan penarikan.");
        }
    }

    public void showSaldo() {
        System.out.println("Saldo BlueSaving: Rp." + saldoSaving);
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
        System.out.println("Creating Blue Saving...");
        System.out.println("Tabungan ID: " + getTabungan_id());
        System.out.println("User ID: " + getuser_id());
        System.out.println("Nama Tabungan: " + getNamaTabungan());
        System.out.println("Saldo Awal: " + getSaldoAwal());
        System.out.println("Jangka Waktu (bulan): " + getJangkaWaktu());
        System.out.println("Target Saldo: " + getTargetSaldo());
    }

    @Override
    public void createBlueGether() {
        throw new UnsupportedOperationException("BlueGether is not supported in BlueSaving class.");
    }

    @Override
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
