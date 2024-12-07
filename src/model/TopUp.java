package model;

import java.sql.Timestamp;

public class TopUp extends Transaksi {
    private double jumlahSaldoTerpotong;
    private TopUpType topUpType;
    private int nomorTujuan;

    public TopUp(String transaksi_id, String nasabah_id, TransaksiType transaksiType, double biayaAdmin,
            Timestamp transaksiDate, String kodePromo, StatusType statusType, double jumlahSaldoTerpotong,
            TopUpType topUpType, int nomorTujuan) {
        super(transaksi_id, nasabah_id, transaksiType, biayaAdmin, transaksiDate, kodePromo, statusType);
        this.jumlahSaldoTerpotong = jumlahSaldoTerpotong;
        this.topUpType = topUpType;
        this.nomorTujuan = nomorTujuan;
    }

    public TopUp(double jumlahSaldoTerpotong, TopUpType topUpType, int nomorTujuan) {
        this.jumlahSaldoTerpotong = jumlahSaldoTerpotong;
        this.topUpType = topUpType;
        this.nomorTujuan = nomorTujuan;
    }

    public double getJumlahSaldoTerpotong() {
        return jumlahSaldoTerpotong;
    }

    public void setJumlahSaldoTerpotong(double jumlahSaldoTerpotong) {
        this.jumlahSaldoTerpotong = jumlahSaldoTerpotong;
    }

    public TopUpType getTopUpType() {
        return topUpType;
    }

    public void setTopUpType(TopUpType topUpType) {
        this.topUpType = topUpType;
    }

    public int getNomorTujuan() {
        return nomorTujuan;
    }

    public void setNomorTujuan(int nomorTujuan) {
        this.nomorTujuan = nomorTujuan;
    }

}