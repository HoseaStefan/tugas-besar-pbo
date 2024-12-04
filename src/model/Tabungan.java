package model;

import java.sql.Timestamp;

public abstract class Tabungan {
    private String tabungan_id;
    private String user_id;
    private TabunganType tabunganType;
    private Timestamp start_date;

    public Tabungan(String tabungan_id, String user_id, TabunganType tabunganType, Timestamp start_date) {
        this.tabungan_id = tabungan_id;
        this.user_id = user_id;
        this.tabunganType = tabunganType;
        this.start_date = start_date;
    }

    public String getTabungan_id() {
        return tabungan_id;
    }

    public void setTabungan_id(String tabungan_id) {
        this.tabungan_id = tabungan_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public TabunganType getTabunganType() {
        return tabunganType;
    }

    public void setTabunganType(TabunganType tabunganType) {
        this.tabunganType = tabunganType;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }
}