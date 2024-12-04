package model;

import java.sql.Timestamp;


public class Transfer extends Transaksi {
    private String topup_id;
    private int nomorRekeningTujuan;

    public Transfer(String topup_id, int nomorRekeningTujuan) {
        this.topup_id = topup_id;
        this.nomorRekeningTujuan = nomorRekeningTujuan;
    }

    public Transfer(String transaksi_id, String nasabah_id, TransaksiType transaksiType, double biayaAdmin,
            Timestamp transaksiDate, String kodePromo, double jumlahSaldoTerpotong, StatusType statusType,
            String topup_id, int nomorRekeningTujuan) {
        super(transaksi_id, nasabah_id, transaksiType, biayaAdmin, transaksiDate, kodePromo, jumlahSaldoTerpotong,
                statusType);
        this.topup_id = topup_id;
        this.nomorRekeningTujuan = nomorRekeningTujuan;
    }

    public String getTopup_id() {
        return topup_id;
    }

    public void setTopup_id(String topup_id) {
        this.topup_id = topup_id;
    }

    public int getNomorRekeningTujuan() {
        return nomorRekeningTujuan;
    }

    public void setNomorRekeningTujuan(int nomorRekeningTujuan) {
        this.nomorRekeningTujuan = nomorRekeningTujuan;
    }

}