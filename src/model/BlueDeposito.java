package model;

import controller.BlueDepositoController;
import controller.DatabaseHandler;
import java.sql.Timestamp;
import java.util.Calendar;

public class BlueDeposito extends Tabungan {
    private DepositoType depositoType;
    private Double saldoAkhir;
    private Timestamp endDate;
    private Boolean complete;


    public BlueDeposito(String tabungan_id, String user_id, String namaTabungan, TabunganType tabunganType,
            double saldoAwal, Timestamp start_date, DepositoType depositoType, double  saldoAkhir, Timestamp endDate,
            Boolean complete) {
        super(tabungan_id, user_id, namaTabungan, tabunganType, saldoAwal, start_date);
        this.depositoType = depositoType;
        this.saldoAkhir = saldoAkhir;
        this.endDate = endDate;
        this.complete = complete;
    }

    


    public BlueDeposito(String tabungan_id, String user_id, String namaTabungan, TabunganType tabunganType,
            double saldoAwal, Timestamp start_date) {
        super(tabungan_id, user_id, namaTabungan, tabunganType, saldoAwal, start_date);
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
        throw new UnsupportedOperationException("BlueSaving is not supported in BlueDeposito class.");
    }
    
    public void createBlueGether() {
        throw new UnsupportedOperationException("BlueGether is not supported in BlueDeposito class.");
    }

    public void createBlueDeposito() {
        System.out.println("BlueGether created");
    }

    public double hitungSaldoAkhir(double saldo_awal , DepositoType depoType, String userId, String tabunganId){
        DatabaseHandler conn = new DatabaseHandler();
        conn.connect();
        BlueDepositoController controller = new BlueDepositoController();

        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (depoType.equals(depoType.TIGABULAN)) {
            Timestamp end_date = calculateEndDate(now, 3);
            if (now.equals(end_date)||now.after(end_date)) {
                saldoAkhir = saldo_awal + saldo_awal*0.03;
                controller.updateCompleteStatus(userId, tabunganId);
            } else {
                return saldoAkhir = saldo_awal;
            }
            
        } else if (depoType.equals(depoType.ENAMBULAN)) {
            Timestamp end_date = calculateEndDate(now, 6);
            if (now.equals(end_date)||now.after(end_date)) {
                saldoAkhir = saldo_awal + saldo_awal*0.06;
                controller.updateCompleteStatus(userId, tabunganId);
            } else {
                return saldoAkhir = saldo_awal;
            }
            
        } else if (depoType.equals(depoType.SETAHUN)) {
            Timestamp end_date = calculateEndDate(now, 12);
            if (now.equals(end_date)||now.after(end_date)) {
                saldoAkhir = saldo_awal + saldo_awal*0.1;
                controller.updateCompleteStatus(userId, tabunganId);
            } else {
                return saldoAkhir = saldo_awal;
            }

        } else {
            return saldoAkhir = saldo_awal;
        }

        return saldoAkhir;
    }


    public Timestamp calculateEndDate(Timestamp startDate, int depoUpdate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startDate.getTime());
        calendar.add(Calendar.MONTH, depoUpdate);
        return new Timestamp(calendar.getTimeInMillis());
    }

}