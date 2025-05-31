package Test;

import java.util.Date;

public class SanPham {
    private String maSP;
    private String tenSP;
    private String loaiSP;
    private int soLuong;
    private double giaGoc;
    private double giaBan;
    private String nhaCungCap;
    private String maVach;
    private String moTa;
    private String hinhAnh;
    private Date ngaythem;
    // Constructor
    public SanPham(String maSP, String tenSP, String loaiSP, int soLuong, double giaGoc,
                   double giaBan, String nhaCungCap, String maVach, String moTa, String hinhAnh, Date ngaythem) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.soLuong = soLuong;
        this.giaGoc = giaGoc;
        this.giaBan = giaBan;
        this.nhaCungCap = nhaCungCap;
        this.maVach = maVach;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.ngaythem=ngaythem;
    }

    // Getter và Setter methods
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(double giaGoc) {
        this.giaGoc = giaGoc;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getMaVach() {
        return maVach;
    }

    public void setMaVach(String maVach) {
        this.maVach = maVach;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    public Date getNgayThem() {
        return ngaythem;
    }

    public void setNgayThem(Date ngaythem) {
        this.ngaythem = ngaythem;
    }
}