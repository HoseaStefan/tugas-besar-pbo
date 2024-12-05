package model;

import java.sql.Timestamp;

public class BlueDeposito extends Tabungan {
    private DepositoType depositoType;
    private Double saldoAkhir;
    private Timestamp endDate;
    private Boolean complete;

    public BlueDeposito(String tabungan_id, String user_id, String namaTabungan, TabunganType tabunganType,
            double saldoAwal, Timestamp start_date, DepositoType depositoType, Double saldoAkhir, Timestamp endDate,
            Boolean complete) {
        super(tabungan_id, user_id, namaTabungan, tabunganType, saldoAwal, start_date);
        this.depositoType = depositoType;
        this.saldoAkhir = saldoAkhir;
        this.endDate = endDate;
        this.complete = complete;
    }

    public DepositoType getDepositoType() {
        return depositoType;
    }

    public void setDepositoType(DepositoType depositoType) {
        this.depositoType = depositoType;
    }

    public Double getSaldoAkhir() {
        return saldoAkhir;
    }

    public void setSaldoAkhir(Double saldoAkhir) {
        this.saldoAkhir = saldoAkhir;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public void tarikSaldo() {
        System.out.println("Tarik Saldo BlueDeposito");
    }

    public void showSaldo() {
        System.out.println("Saldo BlueDeposito: " + saldoAkhir);
    }

    @Override
    public void createBlueSaving() {
    }

    public void createBlueGether() {
    }

    public void createBlueDeposito() {
        System.out.println("BlueGether created");
    }

}