package model;

public class Report {
    private String report_id;
    private String nasabah_id;
    private String emailVerifikasi;
    private String deskripsi;
    private StatusReport statusReport;

    public Report(String report_id, String nasabah_id, String emailVerifikasi, String deskripsi,
            StatusReport statusReport) {
        this.report_id = report_id;
        this.nasabah_id = nasabah_id;
        this.emailVerifikasi = emailVerifikasi;
        this.deskripsi = deskripsi;
        this.statusReport = statusReport;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getNasabah_id() {
        return nasabah_id;
    }

    public void setNasabah_id(String nasabah_id) {
        this.nasabah_id = nasabah_id;
    }

    public String getEmailVerifikasi() {
        return emailVerifikasi;
    }

    public void setEmailVerifikasi(String emailVerifikasi) {
        this.emailVerifikasi = emailVerifikasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public StatusReport getStatusReport() {
        return statusReport;
    }

    public void setStatusReport(StatusReport statusReport) {
        this.statusReport = statusReport;
    }

    public static boolean updateStatusReport(Report report, String selectedStatus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStatusReport'");
    }
}