package trangchu;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Dangnhap extends JFrame implements ActionListener {
    JButton dangnhap;
    JButton dangki;
    JTextField login;
    JPasswordField pass;
    JLabel tk;
    JLabel mk;
   

    public Dangnhap() {
        super("Đăng nhập");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("—Pngtree—avatar icon profile icon member_5247852.png");
        Image ima = icon.getImage().getScaledInstance(100, 100, 4);
        this.setIconImage(ima);
        this.taikhoan();
        this.setVisible(true);
    }

    public void taikhoan() {
        JPanel pan = new JPanel();
        pan.setLayout((LayoutManager)null);
        ImageIcon icon = new ImageIcon("—Pngtree—avatar icon profile icon member_5247852.png");
        Image image = icon.getImage().getScaledInstance(100, 100, 4);
        JLabel ICON = new JLabel(new ImageIcon(image));
        ICON.setBounds(150, -100, 400, 300);
        pan.add(ICON);
        ImageIcon icon1 = new ImageIcon("image-removebg-preview (1).png");
        Image image1 = icon1.getImage().getScaledInstance(300, 250, 4);
        JLabel ICON1 = new JLabel(new ImageIcon(image1));
        ICON1.setBounds(-50, 10, 400, 300);
        pan.add(ICON1);
        JLabel nhap = new JLabel("Đăng nhập");
        nhap.setFont(new Font("Serif Bold", 1, 23));
        nhap.setBounds(400, 20, 200, 50);
        pan.add(nhap);
        tk = new JLabel("Tài khoản");
        tk.setBounds(300, 100, 70, 30);
        login = new JTextField();
        login.setBounds(400, 100, 150, 30);
        pan.add(this.login);
        pan.add(this.tk);
        mk = new JLabel("Mật khẩu");
        mk.setBounds(300, 150, 70, 30);
        pass = new JPasswordField();
        pass.setBounds(400, 150, 150, 30);
        pan.add(this.pass);
        pan.add(this.mk);
        dangnhap = new JButton("Đăng nhập");
        dangnhap.setBounds(340, 200, 100, 30);
        dangnhap.addActionListener(this);
        pan.add(this.dangnhap);
        dangki = new JButton("Đăng kí");
        dangki.setBounds(450, 200, 100, 30);
        dangki.addActionListener(this);
        pan.add(this.dangki);
        this.add(pan);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dangnhap) { 
            String taikhoan = login.getText().trim();
            String matkhau = (new String(pass.getPassword())).trim();
          if(taikhoan.isEmpty()||matkhau.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ");
          return ;
          }
        	String nguoidung=Kiemtra(taikhoan,matkhau);  
        	if (nguoidung != null) {
                if ("Nhân viên quản lý kho".equals(nguoidung)) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công: Quản lý kho hàng");
                  new Admin();
                } else if ("Nhân viên bán hàng".equals(nguoidung)) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công: Trang chủ bán hàng");
                 new NhanVien();
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại");
            }
        } else if (e.getSource() == dangki) {
            new Dangki();
            this.dispose();
        }
        
    }
public String Kiemtra(String taikhoan,String matkhau) {
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet res=null;
	try {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlycuahang", "root", "2904");
		String query="SELECT*FROM dangki WHERE taikhoan =? AND matkhau =? ";
		stm =con.prepareStatement(query);
		stm.setString(1,taikhoan);
		stm.setString(2,matkhau);
		res= stm.executeQuery();
		if (res.next()) {
            return res.getString("nguoidung");
        }
	}catch (SQLException ex) {
		ex.printStackTrace();
		JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage());
		
	}finally {
        try {
            if (res != null) res.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	 return null;
}
    public static void main(String[] args) {
        new Dangnhap();
    }
}
