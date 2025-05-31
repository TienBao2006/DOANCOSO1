package Test;

import java.sql.Date;

public class DoanhThu {
    private Date ngayMua;
    private double von;
    private double doanhThu;
    private double loiNhuan;

    public DoanhThu(Date ngayMua, double von, double doanhThu, double loiNhuan) {
        this.ngayMua = ngayMua;
        this.von = von;
        this.doanhThu = doanhThu;
        this.loiNhuan = loiNhuan;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public double getVon() {
        return von;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public double getLoiNhuan() {
        return loiNhuan;
    }
}