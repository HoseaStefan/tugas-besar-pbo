package model;

import java.sql.Timestamp;

public class SetorSaldo extends Transaksi{
    private double jumlahSaldoDitambah;

    public SetorSaldo(String transaksi_id, String nasabah_id, TransaksiType transaksiType, double biayaAdmin,
            Timestamp transaksiDate, String kodePromo, double jumlahSaldoTerpotong, StatusType statusType,
            double jumlahSaldoDitambah) {
        super(transaksi_id, nasabah_id, transaksiType, biayaAdmin, transaksiDate, kodePromo, jumlahSaldoTerpotong,
                statusType);
        this.jumlahSaldoDitambah = jumlahSaldoDitambah;
    }

    public SetorSaldo(double jumlahSaldoDitambah) {
        this.jumlahSaldoDitambah = jumlahSaldoDitambah;
    }

    public double getJumlahSaldoDitambah() {
        return jumlahSaldoDitambah;
    }

    public void setJumlahSaldoDitambah(double jumlahSaldoDitambah) {
        this.jumlahSaldoDitambah = jumlahSaldoDitambah;
    }
}
