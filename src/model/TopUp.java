package model;

import java.sql.Timestamp;

public class TopUp extends Transaksi {
    private String topup_id;
    private TopUpType topUpType;
    private int nomorTujuan;

    public TopUp(String transaksi_id, String nasabah_id, TransaksiType transaksiType, double biayaAdmin,
            Timestamp transaksiDate, String kodePromo, double jumlahSaldoTerpotong, StatusType statusType,
            String topup_id, TopUpType topUpType, int nomorTujuan) {
        super(transaksi_id, nasabah_id, transaksiType, biayaAdmin, transaksiDate, kodePromo, jumlahSaldoTerpotong,
                statusType);
        this.topup_id = topup_id;
        this.topUpType = topUpType;
        this.nomorTujuan = nomorTujuan;
    }

    public TopUp(String topup_id, TopUpType topUpType, int nomorTujuan) {
        this.topup_id = topup_id;
        this.topUpType = topUpType;
        this.nomorTujuan = nomorTujuan;
    }

    public String getTopup_id() {
        return topup_id;
    }

    public void setTopup_id(String topup_id) {
        this.topup_id = topup_id;
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