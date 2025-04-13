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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Dangki extends JFrame implements ActionListener {
    JTextField nameField;
    JPasswordField xacnhanJPasswordField;
    JPasswordField passwordField;
    JButton dangki;
    JComboBox<String> ngdungBox;
    public Dangki() {
        this.setSize(600, 400);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        this.trang();
        this.setVisible(true);
    }

    public void trang() {
        JPanel panel = new JPanel();
        panel.setLayout((LayoutManager)null);
        ImageIcon icon = new ImageIcon("image-removebg-preview.png");
        JLabel xacnhan = new JLabel("Xác nhận:");
        xacnhan.setBounds(320, 200, 80, 30);
        panel.add(xacnhan);
        this.xacnhanJPasswordField = new JPasswordField();
        this.xacnhanJPasswordField.setBounds(400, 200, 160, 30);
        panel.add(this.xacnhanJPasswordField);
        Image ima = icon.getImage().getScaledInstance(250, 200, 4);
        JLabel ICON = new JLabel(new ImageIcon(ima));
        ICON.setBounds(1, 70, 300, 200);
        panel.add(ICON);
        JLabel label = new JLabel("Đăng ký");
        label.setFont(new Font("A", 1, 23));
        label.setBounds(440, 30, 150, 30);
        panel.add(label);
        ImageIcon ico = new ImageIcon("—Pngtree—avatar icon profile icon member_5247852.png");
        Image image = ico.getImage().getScaledInstance(100, 100, 4);
        JLabel ICON1 = new JLabel(new ImageIcon(image));
        ICON1.setBounds(200, -100, 400, 300);
        panel.add(ICON1);
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setBounds(320, 150, 80, 30);
        panel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(400, 150, 160, 30);
        panel.add(this.passwordField);
        JLabel nameLabel = new JLabel("Tài khoản:");
        nameLabel.setBounds(320, 100, 80, 30);
        panel.add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(400, 100, 160, 30);
        panel.add(this.nameField);
        
        JLabel nguoidung = new JLabel("Người dùng:");
        nguoidung.setBounds(320, 250, 80, 30);
        panel.add(nguoidung);
        ngdungBox = new JComboBox<String>(new String[] {"Quản lý","Nhân viên"});
        ngdungBox.setBounds(400, 250, 160, 30);
        panel.add(ngdungBox);
        
	       dangki = new JButton("Đăng ký");
	       dangki.setBounds(370, 300, 150, 30);
	       dangki.addActionListener(this);
        panel.add(this.dangki);
        this.add(panel);
    }
  
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dangki) {
            String xacnhan = (new String(xacnhanJPasswordField.getPassword())).trim();
            String taikhoan = this.nameField.getText().trim();
            String matkhau = (new String(this.passwordField.getPassword())).trim();
            String nguoidung=(String) this.ngdungBox.getSelectedItem();
            if (!xacnhan.isEmpty() && !taikhoan.isEmpty() && !matkhau.isEmpty()) {
                if (!matkhau.equals(xacnhan)) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu không khớp");
                } else if (Kiemtra(taikhoan)) {
                    JOptionPane.showMessageDialog(this, "Tài khoản tồn tại");
                } else {
                	if(Themtaikhoan(taikhoan, matkhau,nguoidung)) {
               
                    JOptionPane.showMessageDialog(this, "Thành công ");
                    new Dangnhap();
                    this.dispose();
                }else {
                	JOptionPane.showMessageDialog(this, "Lỗi khi thêm tài khoản !");
                }
              }
            } else {
                JOptionPane.showConfirmDialog(this, "Vui lòng nhập đủ !");
            }
        }
    }
    public boolean Kiemtra(String name ) {
    	Connection con=null;
    	PreparedStatement stm=null;
    	ResultSet res=null;
    	try {
    		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlycuahang", "root", "2904");
    		String query="SELECT*FROM dangki WHERE taikhoan= ?";
    		stm =con.prepareStatement(query);
    		stm.setString(1,name);
    		res = stm.executeQuery();
    		return res.next();
    	
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    		return false;
    	} finally {
    		try  {
    			if (res != null) res.close();
                if (stm != null) stm.close();
                if (con != null) con.close();
    		}catch(SQLException ex) {
    			ex.printStackTrace();
    		}
    	}
    }
    public boolean Themtaikhoan(String taikhoan,String matkhau,String nguoidung) {
    	Connection con=null;
    	PreparedStatement stm =null;
    	try {
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlycuahang", "root", "2904");
    		String query = "INSERT INTO dangki (taikhoan,matkhau,nguoidung) VALUES(?,?,?)";
    stm=con.prepareStatement(query);
    stm.setString(1,taikhoan);
    stm.setString(2,matkhau);
    stm.setString(3,nguoidung);
    int kiemtra =stm.executeUpdate();
    return kiemtra > 0;
    
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    		return false;
    	}finally {
    			try {
    				if(stm !=null) stm.close();
    				if(con != null) con.close();    			
    		}catch(SQLException ex) {
    			ex.printStackTrace();
    		}
    	}
    }
 
}

