package model;

import java.sql.Timestamp;

public abstract class Transaksi {
    private String transaksi_id;
    private String nasabah_id;
    private TransaksiType transaksiType;
    private double biayaAdmin;
    private Timestamp transaksiDate;
    private String kodePromo;
    private double jumlahSaldoTerpotong;
    private StatusType statusType;

    public Transaksi(){
    }

    public Transaksi(String transaksi_id, String nasabah_id, TransaksiType transaksiType, double biayaAdmin,
            Timestamp transaksiDate, String kodePromo, double jumlahSaldoTerpotong, StatusType statusType) {
        this.transaksi_id = transaksi_id;
        this.nasabah_id = nasabah_id;
        this.transaksiType = transaksiType;
        this.biayaAdmin = biayaAdmin;
        this.transaksiDate = transaksiDate;
        this.kodePromo = kodePromo;
        this.jumlahSaldoTerpotong = jumlahSaldoTerpotong;
        this.statusType = statusType;
    }

    public String getTransaksi_id() {
        return transaksi_id;
    }

    public void setTransaksi_id(String transaksi_id) {
        this.transaksi_id = transaksi_id;
    }

    public String getNasabah_id() {
        return nasabah_id;
    }

    public void setNasabah_id(String nasabah_id) {
        this.nasabah_id = nasabah_id;
    }

    public TransaksiType getTransaksiType() {
        return transaksiType;
    }

    public void setTransaksiType(TransaksiType transaksiType) {
        this.transaksiType = transaksiType;
    }

    public double getBiayaAdmin() {
        return biayaAdmin;
    }

    public void setBiayaAdmin(double biayaAdmin) {
        this.biayaAdmin = biayaAdmin;
    }

    public Timestamp getTransaksiDate() {
        return transaksiDate;
    }

    public void setTransaksiDate(Timestamp transaksiDate) {
        this.transaksiDate = transaksiDate;
    }

    public String getKodePromo() {
        return kodePromo;
    }

    public void setKodePromo(String kodePromo) {
        this.kodePromo = kodePromo;
    }

    public double getJumlahSaldoTerpotong() {
        return jumlahSaldoTerpotong;
    }

    public void setJumlahSaldoTerpotong(double jumlahSaldoTerpotong) {
        this.jumlahSaldoTerpotong = jumlahSaldoTerpotong;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
}