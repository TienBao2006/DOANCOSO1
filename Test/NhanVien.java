package Test;

import java.sql.Date;

public class NhanVien {
    private String MaNV;
    private String Ten;
    private Date Ngaysinh;
    private String Gioitinh;
    private String Diachi;
    private String SDT;
    private String Email;
    private String Taikhoan;
    private String Matkhau;
    private String hinhAnh;



    public NhanVien(String maNV, String ten, Date ngaysinh, String gioitinh,
                    String diachi, String sdt, String email, String taikhoan,
                    String matkhau, String hinhAnh) {
        this.MaNV = maNV;
        this.Ten = ten;
        this.Ngaysinh = ngaysinh;
        this.Gioitinh = gioitinh;
        this.Diachi = diachi;
        this.SDT = sdt;
        this.Email = email;
        this.Taikhoan = taikhoan;
        this.Matkhau = matkhau;
        this.hinhAnh = hinhAnh;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        this.MaNV = maNV;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        this.Ten = ten;
    }

    public Date getNgaySinh() {
        return Ngaysinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.Ngaysinh = Ngaysinh;
    }



    public String getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.Gioitinh = gioitinh;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        this.Diachi = diachi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sdt) {
        this.SDT = sdt;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getTaikhoan() {
        return Taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.Taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.Matkhau = matkhau;
    }

    public String gethinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}