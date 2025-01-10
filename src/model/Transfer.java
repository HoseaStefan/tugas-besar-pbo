package model;

import java.sql.Timestamp;

public class Transfer extends Transaksi {
    private double jumlahSaldoTerpotong;
    private int nomorRekeningTujuan;

    public Transfer(String transaksi_id, String nasabah_id, TransaksiType transaksiType, double biayaAdmin,
            Timestamp transaksiDate, String kodePromo, StatusType statusType, double jumlahSaldoTerpotong,
            int nomorRekeningTujuan) {
        super(transaksi_id, nasabah_id, transaksiType, biayaAdmin, transaksiDate, kodePromo, statusType);
        this.jumlahSaldoTerpotong = jumlahSaldoTerpotong;
        this.nomorRekeningTujuan = nomorRekeningTujuan;
    }

    public Transfer(double jumlahSaldoTerpotong, int nomorRekeningTujuan) {
        this.jumlahSaldoTerpotong = jumlahSaldoTerpotong;
        this.nomorRekeningTujuan = nomorRekeningTujuan;
    }

    public double getJumlahSaldoTerpotong() {
        return jumlahSaldoTerpotong;
    }

    public void setJumlahSaldoTerpotong(double jumlahSaldoTerpotong) {
        this.jumlahSaldoTerpotong = jumlahSaldoTerpotong;
    }

    public int getNomorRekeningTujuan() {
        return nomorRekeningTujuan;
    }

    public void setNomorRekeningTujuan(int nomorRekeningTujuan) {
        this.nomorRekeningTujuan = nomorRekeningTujuan;
    }

}