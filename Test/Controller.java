package Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Controller {

        // All your FXML injections remain the same
        @FXML private AnchorPane anchor;
        @FXML private AnchorPane anchor1;
        @FXML private AnchorPane anchor2;
        @FXML private AnchorPane anchorbuton;
        @FXML private AnchorPane anchorbuton1;
        @FXML private AnchorPane anchorbuton2;
        @FXML private AnchorPane anchorbutonnv;
        @FXML private AnchorPane anchorbutonnv1;
        @FXML private AnchorPane khohang;
        @FXML private Button btn1;
        @FXML private Button btn2;
        @FXML private Button btn3;
        @FXML private Button btn31;
        @FXML private Button btn311;
        @FXML private Button btnChonAnh;
        @FXML private Button btnChonAnhnv;
        @FXML private Button filexml;
        @FXML private Button filexml1;
        @FXML private ImageView imageView;
        @FXML private ImageView imageView1;
        @FXML private ImageView imageViewnv;
        @FXML private Pane pan1;
        @FXML private Pane pan2;
        @FXML private Button sua;
        @FXML private Button suanv;
        @FXML private Button suasp;
        @FXML private TableView<SanPham> table;
        @FXML private TableView<SanPham> tableTP;
        @FXML private TableView<SanPham> tableNU;
        @FXML private TableView<SanPham> tableKhac;
        @FXML private TableView<NhanVien> table1;
        @FXML private TextField tex;
        @FXML private TextField tex1;
        @FXML private TextField text;
        @FXML private TextField text1;
        @FXML private TextField text2;
        @FXML private TextField text3;
        @FXML private TextField text4;
        @FXML private TextField text5;
        @FXML private TextField text6;
        @FXML private TextField text7;
        @FXML private TextField text8;
        @FXML private TextField text9;
        @FXML private Button them;
        @FXML private Button themnv;
        @FXML private Button themsp;
        @FXML private TextField txtHinhAnh;
        @FXML private VBox vbox;
        @FXML private Button xoa;
        @FXML private Button xoanv;
        @FXML private Button xuatfile;
        @FXML private Button xuatfile1;
        // Form Sửa sản phẩm
        @FXML private TextField editMaSP;
        @FXML private TextField editTenSP;
        @FXML private TextField editLoaiSP;
        @FXML private TextField editSoLuong;
        @FXML private TextField editGiaGoc;
        @FXML private TextField editGiaBan;
        @FXML private TextField editNhaCungCap;
        @FXML private TextField editMaVach;
        @FXML private TextField editMoTa;
        @FXML private TextField editHinhAnh;
        @FXML private ImageView editImageView;

        //From Nhân viên
        @FXML
        private TextField addnv;

        @FXML
        private TextField addnv1;

        @FXML
        private TextField addnv2;

        @FXML
        private TextField addnv3;

        @FXML
        private TextField addnv4;

        @FXML
        private TextField addnv5;

        @FXML
        private TextField addnv6;

        @FXML
        private TextField addnv7;

        @FXML
        private TextField addnv8;
        @FXML
        private TextField txtHinhAnhNV;
        @FXML
        private TextField txtHinhAnhNV1;
        //Form sửa Nhân viên

        @FXML
        private TextField editnv;

        @FXML
        private TextField editnv1;

        @FXML
        private TextField editnv3;

        @FXML
        private TextField editnv4;

        @FXML
        private TextField editnv5;

        @FXML
        private TextField editnv6;

        @FXML
        private TextField editnv7;

        @FXML
        private TextField editnv8;
        @FXML
        private DatePicker addNgaySinh;
        @FXML
        private DatePicker editNgaysinh;
        @FXML private ImageView editImageViewnv;

        private ObservableList<SanPham> sanPhamList = FXCollections.observableArrayList();
        private ObservableList<NhanVien> nhanVienList = FXCollections.observableArrayList();
        private ObservableList<DoanhThu> doanhThuList = FXCollections.observableArrayList();
// Biểu đồ

        @FXML
        private BarChart<String, Number> barChart;
        @FXML
        private CategoryAxis xAxis;
        @FXML
        private NumberAxis yAxis;

        @FXML
        private TableView<DoanhThu> revenueTable;

        @FXML
        private TableColumn<DoanhThu, String> ngayColumn;

        @FXML
        private TableColumn<DoanhThu, Number> vonColumn;

        @FXML
        private TableColumn<DoanhThu, Number> doanhThuColumn;

        @FXML
        private TableColumn<DoanhThu, Number> loiNhuanColumn;

        @FXML
        public void initialize() {
                anchor1.setVisible(false);
                anchor2.setVisible(false);
                anchorbuton.setVisible(false);
                anchorbuton1.setVisible(false);
                anchorbuton2.setVisible(false);
                anchorbutonnv.setVisible(false);
                anchorbutonnv1.setVisible(false);
                khohang.setVisible(false);

                // Setup tables
                setupSanPhamTable();
                setupNhanVienTable();
                setupDoanhThuTable();
                khoThucPham();
                khoNuocUong();
                khoSPKhac();
                // Load data
                loadSanPhamData();
                loadNhanVienData();
                loadDoanhThuData();
                // Setup search listeners
                tex.textProperty().addListener((observable, oldValue, newValue) -> {
                        searchSanPham(newValue);
                });
                tex1.textProperty().addListener((observable, oldValue, newValue) -> {
                        searchNhanVien(newValue);
                });

        }

        private void setupSanPhamTable() {
                try {
                        table.getColumns().clear();

                        TableColumn<SanPham, String> maSPCol = new TableColumn<>("Mã SP");
                        maSPCol.setCellValueFactory(new PropertyValueFactory<>("maSP"));

                        TableColumn<SanPham, String> tenSPCol = new TableColumn<>("Tên SP");
                        tenSPCol.setCellValueFactory(new PropertyValueFactory<>("tenSP"));

                        TableColumn<SanPham, Integer> soLuongCol = new TableColumn<>("Số lượng");
                        soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

                        TableColumn<SanPham, String> loaiCol = new TableColumn<>("Loại");
                        loaiCol.setCellValueFactory(new PropertyValueFactory<>("loaiSP"));

                        TableColumn<SanPham, Double> giaGocCol = new TableColumn<>("Giá gốc");
                        giaGocCol.setCellValueFactory(new PropertyValueFactory<>("giaGoc"));

                        TableColumn<SanPham, Double> giaBanCol = new TableColumn<>("Giá bán");
                        giaBanCol.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

                        TableColumn<SanPham, String> nhaCungCapCol = new TableColumn<>("Nhà CC");
                        nhaCungCapCol.setCellValueFactory(new PropertyValueFactory<>("nhaCungCap"));

                        TableColumn<SanPham, String> maVachCol = new TableColumn<>("Mã vạch");
                        maVachCol.setCellValueFactory(new PropertyValueFactory<>("maVach"));

                        TableColumn<SanPham, String> moTaCol = new TableColumn<>("Mô tả");
                        moTaCol.setCellValueFactory(new PropertyValueFactory<>("moTa"));

                        TableColumn<SanPham, String> hinhAnhCol = new TableColumn<>("Hình ảnh");
                        hinhAnhCol.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));

                        table.getColumns().addAll(maSPCol, tenSPCol, soLuongCol, loaiCol,
                                giaGocCol, giaBanCol, nhaCungCapCol,
                                maVachCol, moTaCol, hinhAnhCol);

                } catch (Exception e) {
                        showErrorAlert("Lỗi thiết lập bảng sản phẩm", e.getMessage());
                }
        }

        private void setupNhanVienTable() {
                try {
                        table1.getColumns().clear();

                        TableColumn<NhanVien, String> maNVCol = new TableColumn<>("Mã NV");
                        maNVCol.setCellValueFactory(new PropertyValueFactory<>("MaNV"));

                        TableColumn<NhanVien, String> tenCol = new TableColumn<>("Tên");
                        tenCol.setCellValueFactory(new PropertyValueFactory<>("Ten"));

                        TableColumn<NhanVien, Date> ngaySinhCol = new TableColumn<>("Ngày sinh");
                        ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("Ngaysinh"));
                        ngaySinhCol.setCellFactory(column -> new TableCell<NhanVien, Date>() {
                                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                                @Override
                                protected void updateItem(Date item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty || item == null) {
                                                setText(null);
                                        } else {
                                                setText(format.format(item));
                                        }
                                }
                        });

                        TableColumn<NhanVien, String> gioiTinhCol = new TableColumn<>("Giới tính");
                        gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("Gioitinh"));

                        TableColumn<NhanVien, String> diaChiCol = new TableColumn<>("Địa chỉ");
                        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("Diachi"));

                        TableColumn<NhanVien, String> sdtCol = new TableColumn<>("SĐT");
                        sdtCol.setCellValueFactory(new PropertyValueFactory<>("SĐT"));

                        TableColumn<NhanVien, String> emailCol = new TableColumn<>("Email");
                        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));

                        TableColumn<NhanVien, String> taiKhoanCol = new TableColumn<>("Tài khoản");
                        taiKhoanCol.setCellValueFactory(new PropertyValueFactory<>("Taikhoan"));

                        TableColumn<NhanVien, String> matKhauCol = new TableColumn<>("Mật khẩu");
                        matKhauCol.setCellValueFactory(new PropertyValueFactory<>("Matkhau"));

                        TableColumn<NhanVien, String> hinhAnhCol = new TableColumn<>("Hình ảnh");
                        hinhAnhCol.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));

                        table1.getColumns().addAll(maNVCol, tenCol, ngaySinhCol, gioiTinhCol,
                                diaChiCol, sdtCol, emailCol,
                                taiKhoanCol, matKhauCol, hinhAnhCol);

                } catch (Exception e) {
                        showErrorAlert("Lỗi thiết lập bảng nhân viên", e.getMessage());
                }
        }
        private void setupDoanhThuTable() {
                try {
                        revenueTable.getColumns().clear();

                        // Cột ngày (Date -> String)
                        TableColumn<DoanhThu, String> ngayColumn = new TableColumn<>("Ngày");
                        ngayColumn.setCellValueFactory(cellData -> {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                return new SimpleStringProperty(dateFormat.format(cellData.getValue().getNgayMua()));
                        });

                        TableColumn<DoanhThu, Number> vonColumn = new TableColumn<>("Vốn");
                        vonColumn.setCellValueFactory(new PropertyValueFactory<>("von"));

                        TableColumn<DoanhThu, Number> doanhThuColumn = new TableColumn<>("Doanh Thu");
                        doanhThuColumn.setCellValueFactory(new PropertyValueFactory<>("doanhThu"));

                        TableColumn<DoanhThu, Number> loiNhuanColumn = new TableColumn<>("Lợi Nhuận");
                        loiNhuanColumn.setCellValueFactory(new PropertyValueFactory<>("loiNhuan"));

                        revenueTable.getColumns().addAll(ngayColumn, vonColumn, doanhThuColumn, loiNhuanColumn);

                } catch (Exception e) {
                        showErrorAlert("Lỗi thiết lập bảng doanh thu", e.getMessage());
                }
        }

//Bảng Thực phẩm
        @FXML
        private void khoThucPham(){
                try {
                        tableTP.getColumns().clear();
                        TableColumn<SanPham, String> maSPCol = new TableColumn<>("Mã SP");
                        maSPCol.setCellValueFactory(new PropertyValueFactory<>("maSP"));

                        TableColumn<SanPham, String> tenSPCol = new TableColumn<>("Tên SP");
                        tenSPCol.setCellValueFactory(new PropertyValueFactory<>("tenSP"));

                        TableColumn<SanPham, Integer> soLuongCol = new TableColumn<>("Số lượng");
                        soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

                        TableColumn<SanPham, String> loaiCol = new TableColumn<>("Loại");
                        loaiCol.setCellValueFactory(new PropertyValueFactory<>("loaiSP"));

                        TableColumn<SanPham, Double> giaGocCol = new TableColumn<>("Giá gốc");
                        giaGocCol.setCellValueFactory(new PropertyValueFactory<>("giaGoc"));

                        TableColumn<SanPham, Double> giaBanCol = new TableColumn<>("Giá bán");
                        giaBanCol.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

                        TableColumn<SanPham, String> nhaCungCapCol = new TableColumn<>("Nhà CC");
                        nhaCungCapCol.setCellValueFactory(new PropertyValueFactory<>("nhaCungCap"));

                        TableColumn<SanPham, String> maVachCol = new TableColumn<>("Mã vạch");
                        maVachCol.setCellValueFactory(new PropertyValueFactory<>("maVach"));

                        TableColumn<SanPham, String> moTaCol = new TableColumn<>("Mô tả");
                        moTaCol.setCellValueFactory(new PropertyValueFactory<>("moTa"));

                        TableColumn<SanPham, String> hinhAnhCol = new TableColumn<>("Hình ảnh");
                        hinhAnhCol.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));

                        tableTP.getColumns().addAll(maSPCol, tenSPCol, soLuongCol, loaiCol,
                                giaGocCol, giaBanCol, nhaCungCapCol,
                                maVachCol, moTaCol, hinhAnhCol);
                        Connection connection = DatabaseConnection.getConnection();
                        String query = "SELECT * FROM qlysanpham WHERE loaiSP = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, "Thực phẩm"); // Chỉ lấy sản phẩm loại "Thực phẩm"

                        ResultSet resultSet = preparedStatement.executeQuery();

                        // Xóa dữ liệu cũ trong TableView
                        tableTP.getItems().clear();

                        // Thêm dữ liệu mới vào TableView
                        while (resultSet.next()) {
                                SanPham sp = new SanPham(
                                        resultSet.getString("maSP"),
                                        resultSet.getString("tenSP"),
                                        resultSet.getString("loaiSP"),
                                        resultSet.getInt("soLuong"),
                                        resultSet.getDouble("giaGoc"),
                                        resultSet.getDouble("giaBan"),
                                        resultSet.getString("nhaCungCap"),
                                        resultSet.getString("maVach"),
                                        resultSet.getString("moTa"),
                                        resultSet.getString("hinhAnh"),
                                        resultSet.getDate("ngaythem")
                                );
                                tableTP.getItems().add(sp);
                        }

                        // Đóng các tài nguyên (tuỳ theo cách bạn quản lý connection)
                        resultSet.close();
                        preparedStatement.close();
                } catch (Exception e) {
                        showErrorAlert("Lỗi thiết lập bảng sản phẩm", e.getMessage());
                }

        }
//      Kho hàng Nước uống
         @FXML
        private void khoNuocUong(){
                try {
                        tableNU.getColumns().clear();
                        TableColumn<SanPham, String> maSPCol = new TableColumn<>("Mã SP");
                        maSPCol.setCellValueFactory(new PropertyValueFactory<>("maSP"));

                        TableColumn<SanPham, String> tenSPCol = new TableColumn<>("Tên SP");
                        tenSPCol.setCellValueFactory(new PropertyValueFactory<>("tenSP"));

                        TableColumn<SanPham, Integer> soLuongCol = new TableColumn<>("Số lượng");
                        soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

                        TableColumn<SanPham, String> loaiCol = new TableColumn<>("Loại");
                        loaiCol.setCellValueFactory(new PropertyValueFactory<>("loaiSP"));

                        TableColumn<SanPham, Double> giaGocCol = new TableColumn<>("Giá gốc");
                        giaGocCol.setCellValueFactory(new PropertyValueFactory<>("giaGoc"));

                        TableColumn<SanPham, Double> giaBanCol = new TableColumn<>("Giá bán");
                        giaBanCol.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

                        TableColumn<SanPham, String> nhaCungCapCol = new TableColumn<>("Nhà CC");
                        nhaCungCapCol.setCellValueFactory(new PropertyValueFactory<>("nhaCungCap"));

                        TableColumn<SanPham, String> maVachCol = new TableColumn<>("Mã vạch");
                        maVachCol.setCellValueFactory(new PropertyValueFactory<>("maVach"));

                        TableColumn<SanPham, String> moTaCol = new TableColumn<>("Mô tả");
                        moTaCol.setCellValueFactory(new PropertyValueFactory<>("moTa"));

                        TableColumn<SanPham, String> hinhAnhCol = new TableColumn<>("Hình ảnh");
                        hinhAnhCol.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));

                        tableNU.getColumns().addAll(maSPCol, tenSPCol, soLuongCol, loaiCol,
                                giaGocCol, giaBanCol, nhaCungCapCol,
                                maVachCol, moTaCol, hinhAnhCol);
                        Connection connection = DatabaseConnection.getConnection();
                        String query = "SELECT * FROM qlysanpham WHERE loaiSP = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, "Nước Uống"); // Chỉ lấy sản phẩm loại "Thực phẩm"

                        ResultSet resultSet = preparedStatement.executeQuery();

                        // Xóa dữ liệu cũ trong TableView
                        tableNU.getItems().clear();

                        // Thêm dữ liệu mới vào TableView
                        while (resultSet.next()) {
                                SanPham sp = new SanPham(
                                        resultSet.getString("maSP"),
                                        resultSet.getString("tenSP"),
                                        resultSet.getString("loaiSP"),
                                        resultSet.getInt("soLuong"),
                                        resultSet.getDouble("giaGoc"),
                                        resultSet.getDouble("giaBan"),
                                        resultSet.getString("nhaCungCap"),
                                        resultSet.getString("maVach"),
                                        resultSet.getString("moTa"),
                                        resultSet.getString("hinhAnh"),
                                        resultSet.getDate("ngaythem")
                                );
                                tableTP.getItems().add(sp);
                        }

                        // Đóng các tài nguyên (tuỳ theo cách bạn quản lý connection)
                        resultSet.close();
                        preparedStatement.close();
                } catch (Exception e) {
                        showErrorAlert("Lỗi thiết lập bảng sản phẩm", e.getMessage());
                }

        }
// Kho sảng phẩm khác
 @FXML
        private void khoSPKhac(){
                try {
                        tableTP.getColumns().clear();
                        TableColumn<SanPham, String> maSPCol = new TableColumn<>("Mã SP");
                        maSPCol.setCellValueFactory(new PropertyValueFactory<>("maSP"));

                        TableColumn<SanPham, String> tenSPCol = new TableColumn<>("Tên SP");
                        tenSPCol.setCellValueFactory(new PropertyValueFactory<>("tenSP"));

                        TableColumn<SanPham, Integer> soLuongCol = new TableColumn<>("Số lượng");
                        soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

                        TableColumn<SanPham, String> loaiCol = new TableColumn<>("Loại");
                        loaiCol.setCellValueFactory(new PropertyValueFactory<>("loaiSP"));

                        TableColumn<SanPham, Double> giaGocCol = new TableColumn<>("Giá gốc");
                        giaGocCol.setCellValueFactory(new PropertyValueFactory<>("giaGoc"));

                        TableColumn<SanPham, Double> giaBanCol = new TableColumn<>("Giá bán");
                        giaBanCol.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

                        TableColumn<SanPham, String> nhaCungCapCol = new TableColumn<>("Nhà CC");
                        nhaCungCapCol.setCellValueFactory(new PropertyValueFactory<>("nhaCungCap"));

                        TableColumn<SanPham, String> maVachCol = new TableColumn<>("Mã vạch");
                        maVachCol.setCellValueFactory(new PropertyValueFactory<>("maVach"));

                        TableColumn<SanPham, String> moTaCol = new TableColumn<>("Mô tả");
                        moTaCol.setCellValueFactory(new PropertyValueFactory<>("moTa"));

                        TableColumn<SanPham, String> hinhAnhCol = new TableColumn<>("Hình ảnh");
                        hinhAnhCol.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));

                        tableTP.getColumns().addAll(maSPCol, tenSPCol, soLuongCol, loaiCol,
                                giaGocCol, giaBanCol, nhaCungCapCol,
                                maVachCol, moTaCol, hinhAnhCol);
                        Connection connection = DatabaseConnection.getConnection();
                        String query = "SELECT * FROM qlysanpham WHERE loaiSP = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, "Khác"); // Chỉ lấy sản phẩm loại "Thực phẩm"

                        ResultSet resultSet = preparedStatement.executeQuery();

                        // Xóa dữ liệu cũ trong TableView
                        tableTP.getItems().clear();

                        // Thêm dữ liệu mới vào TableView
                        while (resultSet.next()) {
                                SanPham sp = new SanPham(
                                        resultSet.getString("maSP"),
                                        resultSet.getString("tenSP"),
                                        resultSet.getString("loaiSP"),
                                        resultSet.getInt("soLuong"),
                                        resultSet.getDouble("giaGoc"),
                                        resultSet.getDouble("giaBan"),
                                        resultSet.getString("nhaCungCap"),
                                        resultSet.getString("maVach"),
                                        resultSet.getString("moTa"),
                                        resultSet.getString("hinhAnh"),
                                        resultSet.getDate("ngaythem")
                                );
                                tableTP.getItems().add(sp);
                        }

                        // Đóng các tài nguyên (tuỳ theo cách bạn quản lý connection)
                        resultSet.close();
                        preparedStatement.close();
                } catch (Exception e) {
                        showErrorAlert("Lỗi thiết lập bảng sản phẩm", e.getMessage());
                }

        }








        @FXML
        private void chonAnh() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Chọn ảnh sản phẩm");
                // Chỉ cho chọn file ảnh phổ biến
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
                );

                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                        try {
                                // Hiển thị ảnh lên ImageView
                                Image image = new Image(file.toURI().toString());
                                imageView.setImage(image);
                                txtHinhAnh.setText(file.getAbsolutePath());


                        } catch (Exception e) {
                                showErrorAlert("Lỗi", "Không thể mở ảnh!");
                        }
                }
        }


        @FXML
        private void themSanPham() {
                // Validate input
                if (!validateSanPhamInput()) return;

                try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "INSERT INTO qlysanpham (maSP, tenSP, loaiSP, soLuong, giaGoc, giaBan, nhaCungCap, maVach, moTa, hinhanh, ngaythem) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setString(1, text.getText().trim());
                        stmt.setString(2, text1.getText().trim());
                        stmt.setString(3, text2.getText().trim());
                        stmt.setInt(4, Integer.parseInt(text3.getText().trim()));
                        stmt.setDouble(5, Double.parseDouble(text4.getText().trim()));
                        stmt.setDouble(6, Double.parseDouble(text5.getText().trim()));
                        stmt.setString(7, text6.getText().trim());
                        stmt.setString(8, text7.getText().trim());
                        stmt.setString(9, text8.getText().trim());
                        stmt.setString(10, txtHinhAnh.getText().trim());

                        stmt.setDate(11, java.sql.Date.valueOf(LocalDate.now()));

                        if (stmt.executeUpdate() > 0) {
                                showSuccessAlert("Thêm sản phẩm thành công!");
                                clearSanPhamFields();
                                loadSanPhamData();
                                anchorbuton.setVisible(false);
                        }
                } catch (SQLException e) {
                        showErrorAlert("Lỗi thêm sản phẩm", e.getMessage());
                }
        }

        private boolean validateSanPhamInput() {
                if (text.getText().trim().isEmpty() || text1.getText().trim().isEmpty() ||
                        text4.getText().trim().isEmpty() || text3.getText().trim().isEmpty() ||
                        text6.getText().trim().isEmpty()) {
                        showErrorAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
                        return false;
                }

                try {
                        int soLuong = Integer.parseInt(text3.getText().trim());
                        double giaGoc = Double.parseDouble(text4.getText().trim());
                        double giaBan = Double.parseDouble(text5.getText().trim());

                        if (giaBan <= giaGoc) {
                                showErrorAlert("Lỗi", "Giá bán phải lớn hơn giá gốc!");
                                return false;
                        }
                } catch (NumberFormatException e) {
                        showErrorAlert("Lỗi", "Số lượng và giá tiền phải là số hợp lệ!");
                        return false;
                }
                return true;
        }

        private void clearSanPhamFields() {
                text.clear();
                text1.clear();
                text2.clear();
                text3.clear();
                text4.clear();
                text5.clear();
                text6.clear();
                text7.clear();
                text8.clear();
                txtHinhAnh.clear();
                imageView.setImage(null);
        }




        // Nút sửa sản phẩm
        @FXML
        private void suaSanPham() {
                SanPham selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                        showWarningAlert("Vui lòng chọn sản phẩm cần sửa");
                        return;
                }

                // Validate dữ liệu
                if (editTenSP.getText().trim().isEmpty() ||
                        editSoLuong.getText().trim().isEmpty() ||
                        editGiaGoc.getText().trim().isEmpty() ||
                        editGiaBan.getText().trim().isEmpty()) {
                        showErrorAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin bắt buộc!");
                        return;
                }

                try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "UPDATE qlysanpham SET tenSP=?, loaiSP=?, soLuong=?, giaGoc=?, giaBan=?, "
                                + "nhaCungCap=?, maVach=?, moTa=?, hinhanh=? WHERE maSP=?";

                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setString(1, editTenSP.getText().trim());
                        stmt.setString(2, editLoaiSP.getText().trim());
                        stmt.setInt(3, Integer.parseInt(editSoLuong.getText().trim()));
                        stmt.setDouble(4, Double.parseDouble(editGiaGoc.getText().trim()));
                        stmt.setDouble(5, Double.parseDouble(editGiaBan.getText().trim()));
                        stmt.setString(6, editNhaCungCap.getText().trim());
                        stmt.setString(7, editMaVach.getText().trim());
                        stmt.setString(8, editMoTa.getText().trim());
                        stmt.setString(9, editHinhAnh.getText().trim());
                        stmt.setString(10, selected.getMaSP());

                        if (stmt.executeUpdate() > 0) {
                                showSuccessAlert("Sửa sản phẩm thành công!");
                                loadSanPhamData();
                                anchorbuton1.setVisible(false);
                        }
                } catch (SQLException | NumberFormatException e) {
                        showErrorAlert("Lỗi sửa sản phẩm", e.getMessage());
                }
        }
        @FXML
        private void showEditForm() {
                SanPham selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                        showWarningAlert("Vui lòng chọn sản phẩm cần sửa");
                        return;
                }

                // Điền dữ liệu vào các trường form sửa
                editMaSP.setText(selected.getMaSP());
                editTenSP.setText(selected.getTenSP());
                editLoaiSP.setText(selected.getLoaiSP());
                editSoLuong.setText(String.valueOf(selected.getSoLuong()));
                editGiaGoc.setText(String.valueOf(selected.getGiaGoc()));
                editGiaBan.setText(String.valueOf(selected.getGiaBan()));
                editNhaCungCap.setText(selected.getNhaCungCap());
                editMaVach.setText(selected.getMaVach());
                editMoTa.setText(selected.getMoTa());
                editHinhAnh.setText(selected.getHinhAnh());

                // Hiển thị hình ảnh nếu có
                if (selected.getHinhAnh() != null && !selected.getHinhAnh().isEmpty()) {
                        try {
                                Image image = new Image(new File(selected.getHinhAnh()).toURI().toString());
                                editImageView.setImage(image);
                        } catch (Exception e) {
                                System.out.println("Không thể tải hình ảnh: " + e.getMessage());
                                editImageView.setImage(null);
                        }
                } else {
                        editImageViewnv.setImage(null);
                }
        }

        @FXML
        private void chonAnhnv() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Chọn ảnh");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.jpeg")
                );
                File selectedFile = fileChooser.showOpenDialog(new Stage());
                if (selectedFile != null) {
                        String imagePath = selectedFile.getAbsolutePath();
                        txtHinhAnhNV.setText(imagePath);
                        txtHinhAnhNV1.setText(imagePath);
                        Image image = new Image(selectedFile.toURI().toString());
                        imageViewnv.setImage(image);
                        editImageViewnv.setImage(image);
                }
        }
        // Hàm chọn ảnh cho form sửa
        @FXML
        private void chonAnhSua() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Chọn ảnh sản phẩm");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.jpeg")
                );

                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                        editHinhAnh.setText(selectedFile.getAbsolutePath());

                        try {
                                Image image = new Image(selectedFile.toURI().toString());
                                editImageView.setImage(image);
                        } catch (Exception e) {
                                System.out.println("Không thể tải hình ảnh: " + e.getMessage());
                        }
                }
        }


        @FXML
        private void loadSanPhamData() {
                sanPhamList.clear();
                try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "SELECT * FROM qlysanpham";
                        ResultSet resultSet = connection.prepareStatement(query).executeQuery();

                        while (resultSet.next()) {
                                sanPhamList.add(new SanPham(
                                        resultSet.getString("maSP"),
                                        resultSet.getString("tenSP"),
                                        resultSet.getString("loaiSP"),
                                        resultSet.getInt("soLuong"),
                                        resultSet.getDouble("giaGoc"),
                                        resultSet.getDouble("giaBan"),
                                        resultSet.getString("nhaCungCap"),
                                        resultSet.getString("maVach"),
                                        resultSet.getString("moTa"),
                                        resultSet.getString("hinhanh"),
                                        resultSet.getDate("ngaythem")
                                ));
                        }
                        table.setItems(sanPhamList);
                } catch (SQLException e) {
                        showErrorAlert("Lỗi tải dữ liệu sản phẩm", e.getMessage());
                }
        }

        @FXML
        private void loadNhanVienData() {
                nhanVienList.clear();
                try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "SELECT * FROM qlynhanvien";
                        ResultSet resultSet = connection.prepareStatement(query).executeQuery();

                        while (resultSet.next()) {
                                String hinhAnhPath = resultSet.getString("hinhAnh");
                                Image image = null;

                                if (hinhAnhPath != null && !hinhAnhPath.isEmpty()) {
                                        try {

                                                File file = new File(hinhAnhPath);
                                                if (file.exists()) {
                                                        image = new Image(file.toURI().toString());
                                                } else {
                                                        System.out.println("File không tồn tại: " + hinhAnhPath);
                                                }
                                        } catch (Exception e) {
                                                System.out.println("Lỗi khi tải hình ảnh: " + e.getMessage());
                                        }
                                }

                                nhanVienList.add(new NhanVien(
                                        resultSet.getString("MaNV"),
                                        resultSet.getString("Ten"),
                                        resultSet.getDate("Ngaysinh"),
                                        resultSet.getString("Gioitinh"),
                                        resultSet.getString("Diachi"),
                                        resultSet.getString("SDT"),
                                        resultSet.getString("Email"),
                                        resultSet.getString("Taikhoan"),
                                        resultSet.getString("Matkhau"),
                                        hinhAnhPath
                                ));
                        }
                        table1.setItems(nhanVienList);
                } catch (SQLException e) {
                        showErrorAlert("Lỗi tải dữ liệu nhân viên", e.getMessage());
                }
        }

        @FXML
        private void xoaSanPham(ActionEvent event) {
                SanPham selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                        showWarningAlert("Vui lòng chọn sản phẩm cần xóa");
                        return;
                }

                try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "DELETE FROM qlysanpham WHERE maSP = ?";
                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setString(1, selected.getMaSP());

                        if (stmt.executeUpdate() > 0) {
                                showSuccessAlert("Xóa sản phẩm thành công");
                                loadSanPhamData();
                        }
                } catch (SQLException e) {
                        showErrorAlert("Lỗi xóa sản phẩm", e.getMessage());
                }
        }

        @FXML
        private void xoaNhanVien(ActionEvent event) {
                NhanVien selected = table1.getSelectionModel().getSelectedItem();
                if (selected == null) {
                        showWarningAlert("Vui lòng chọn nhân viên cần xóa");
                        return;
                }

                try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "DELETE FROM qlynhanvien WHERE MaNV = ?";
                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setString(1, selected.getMaNV());

                        if (stmt.executeUpdate() > 0) {
                                showSuccessAlert("Xóa nhân viên thành công");
                                loadNhanVienData();
                        }
                } catch (SQLException e) {
                        showErrorAlert("Lỗi xóa nhân viên", e.getMessage());
                }
        }
        private void searchSanPham(String keyword) {
                if (keyword == null || keyword.isEmpty()) {
                        table.setItems(sanPhamList);
                        return;
                }

                ObservableList<SanPham> filteredList = FXCollections.observableArrayList();
                for (SanPham sp : sanPhamList) {
                        if (sp.getTenSP().toLowerCase().contains(keyword.toLowerCase()) ||
                                sp.getMaSP().toLowerCase().contains(keyword.toLowerCase())) {
                                filteredList.add(sp);
                        }
                }
                table.setItems(filteredList);
        }

        //Nhan Viên
        @FXML
        private void themNhanVien() {
                if (!validateNhanVienInput()) return;

                try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "INSERT INTO qlynhanvien (MaNV, Ten, Ngaysinh, Gioitinh, Diachi, SDT, Email, Taikhoan, Matkhau, hinhAnh) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement stmt = connection.prepareStatement(query);

                        stmt.setString(1, addnv.getText().trim());
                        stmt.setString(2, addnv1.getText().trim());

                        if (addNgaySinh.getValue() != null) {
                                if (addNgaySinh.getValue() != null) {
                                        stmt.setDate(3, java.sql.Date.valueOf(addNgaySinh.getValue()));
                                } else {
                                        stmt.setNull(3, Types.DATE);
                                }
                        } else {
                                stmt.setNull(3, Types.DATE);
                        }

                        stmt.setString(4, addnv3.getText().trim());
                        stmt.setString(5, addnv4.getText().trim());

                        int sdt;
                        try {
                                sdt = Integer.parseInt(addnv5.getText().trim());
                                if (sdt <= 0) {
                                        showErrorAlert("Lỗi", "Số điện thoại phải lớn hơn 0");
                                        return;
                                }
                        } catch (NumberFormatException e) {
                                showErrorAlert("Lỗi", "Số điện thoại không hợp lệ");
                                return;
                        }

                        stmt.setInt(6, sdt);
                        stmt.setString(7, addnv6.getText().trim());
                        stmt.setString(8, addnv7.getText().trim());
                        stmt.setString(9, addnv8.getText().trim());
                        stmt.setString(10, txtHinhAnhNV.getText().trim());

                        if (stmt.executeUpdate() > 0) {
                                showSuccessAlert("Thêm nhân viên thành công!");
                                clearNhanVienFields();
                                loadNhanVienData();
                                anchorbutonnv.setVisible(false);
                        }

                } catch (SQLException e) {
                        showErrorAlert("Lỗi thêm nhân viên", e.getMessage());
                }
        }

        private boolean validateNhanVienInput() {
                if (addnv.getText().trim().isEmpty() || addnv1.getText().trim().isEmpty() ||
                        addnv3.getText().trim().isEmpty() || addnv4.getText().trim().isEmpty() ||
                        addnv5.getText().trim().isEmpty()) {
                        showErrorAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
                        return false;
                }

                if (addnv5.getText().trim().length() < 10) {
                        showErrorAlert("Lỗi", "Số điện thoại phải có ít nhất 10 ký tự");
                        return false;
                }
                return true;
        }

        @FXML
        private void suaNhanVien() {
                NhanVien selected = table1.getSelectionModel().getSelectedItem();
                if (selected == null) {
                        showWarningAlert("Vui lòng chọn nhân viên cần sửa");
                        return;
                }

                if (editnv.getText().trim().isEmpty() ||
                        editnv1.getText().trim().isEmpty() ||
                        editnv4.getText().trim().isEmpty() ||
                        editnv5.getText().trim().isEmpty()) {
                        showErrorAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin bắt buộc!");
                        return;
                }

                try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "UPDATE qlynhanvien SET Ten=?, Ngaysinh=?, Gioitinh=?, Diachi=?, SDT=?, Email=?, Taikhoan=?, Matkhau=?, hinhAnh=? WHERE MaNV=?";
                        PreparedStatement stmt = connection.prepareStatement(query);

                        stmt.setString(1, editnv1.getText().trim());

                        if (editNgaysinh.getValue() != null) {
                                stmt.setDate(2, java.sql.Date.valueOf(editNgaysinh.getValue()));
                        } else {
                                stmt.setNull(2, Types.DATE);
                        }

                        stmt.setString(3, editnv3.getText().trim());
                        stmt.setString(4, editnv4.getText().trim());

                        int sdt;
                        try {
                                sdt = Integer.parseInt(editnv5.getText().trim());
                                if (sdt <= 0) {
                                        showErrorAlert("Lỗi", "Số điện thoại phải lớn hơn 0");
                                        return;
                                }
                        } catch (NumberFormatException e) {
                                showErrorAlert("Lỗi", "Số điện thoại không hợp lệ");
                                return;
                        }

                        stmt.setInt(5, sdt);
                        stmt.setString(6, editnv6.getText().trim());
                        stmt.setString(7, editnv7.getText().trim());
                        stmt.setString(8, editnv8.getText().trim());
                        stmt.setString(9, txtHinhAnhNV1.getText().trim());
                        stmt.setString(10, selected.getMaNV());

                        if (stmt.executeUpdate() > 0) {
                                showSuccessAlert("Sửa nhân viên thành công!");
                                loadNhanVienData();
                                anchorbutonnv1.setVisible(false);
                        }
                } catch (SQLException | NumberFormatException e) {
                        showErrorAlert("Lỗi sửa nhân viên", e.getMessage());
                }
        }

        @FXML
        private void showEditFormnv() {
                NhanVien selected = table1.getSelectionModel().getSelectedItem();
                if (selected == null) {
                        showWarningAlert("Vui lòng chọn nhân viên cần sửa");
                        return;
                }

                // Điền dữ liệu vào form sửa
                editnv.setText(selected.getMaNV());
                editnv1.setText(selected.getTen());

                // Định dạng ngày sinh
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (selected.getNgaySinh() != null) {
                        editNgaysinh.setValue(selected.getNgaySinh().toLocalDate());
                } else {
                        editNgaysinh.setValue(null);
                }
                editnv3.setText(selected.getGioitinh());
                editnv4.setText(selected.getDiachi());
                editnv5.setText(selected.getSDT());
                editnv6.setText(selected.getEmail());
                editnv7.setText(selected.getTaikhoan());
                editnv8.setText(selected.getMatkhau());
                txtHinhAnhNV1.setText(selected.gethinhAnh());
                if (selected.gethinhAnh() != null && !selected.gethinhAnh().isEmpty()) {
                        try {
                                Image image = new Image(new File(selected.gethinhAnh()).toURI().toString());
                                editImageViewnv.setImage(image);
                        } catch (Exception e) {
                                System.out.println("Không thể tải hình ảnh: " + e.getMessage());
                                editImageViewnv.setImage(null);
                        }
                } else {
                        editImageView.setImage(null);
                }

        }


        private void loadDoanhThuData() {
                doanhThuList.clear();
                String query = "SELECT * FROM thongke";

                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement stmt = connection.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                Date ngayMua = rs.getDate("ngay");
                                double von = rs.getDouble("von");
                                double doanhThu = rs.getDouble("doanhThu");
                                double loiNhuan = rs.getDouble("loiNhuan");

                                doanhThuList.add(new DoanhThu((java.sql.Date) ngayMua, von, doanhThu, loiNhuan));
                        }

                        revenueTable.setItems(doanhThuList);
                        initChart(); // Gọi khởi tạo biểu đồ sau khi có dữ liệu

                } catch (SQLException e) {
                        showErrorAlert("Lỗi tải dữ liệu doanh thu", e.getMessage());
                }
        }

        // Hàm chọn ảnh cho form sửa

        @FXML
        private void chonAnhSuanv() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Chọn ảnh nhân viên");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.jpeg")
                );
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                        String imagePath = selectedFile.getAbsolutePath();
                        txtHinhAnhNV1.setText(imagePath);
                        try {
                                Image image = new Image(selectedFile.toURI().toString());
                                editImageViewnv.setImage(image);
                        } catch (Exception e) {
                                System.out.println("Không thể tải hình ảnh: " + e.getMessage());
                                editImageViewnv.setImage(null);
                        }
                }
        }
        private void initChart() {
                // Xóa dữ liệu cũ
                barChart.getData().clear();

                // Tạo các series dữ liệu
                XYChart.Series<String, Number> seriesDoanhThu = new XYChart.Series<>();
                seriesDoanhThu.setName("Doanh thu");

                XYChart.Series<String, Number> seriesLoiNhuan = new XYChart.Series<>();
                seriesLoiNhuan.setName("Lợi nhuận");

                // Thêm dữ liệu từ doanhThuList vào biểu đồ
                for (DoanhThu data : doanhThuList) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
                        String ngay = dateFormat.format(data.getNgayMua());

                        seriesDoanhThu.getData().add(new XYChart.Data<>(ngay, data.getDoanhThu()));
                        seriesLoiNhuan.getData().add(new XYChart.Data<>(ngay, data.getLoiNhuan()));
                }

                // Thêm dữ liệu vào biểu đồ
                barChart.getData().addAll(seriesDoanhThu, seriesLoiNhuan);

                // Tùy chỉnh giao diện biểu đồ
                barChart.setLegendVisible(true);
                barChart.setAnimated(false); // Tắt hiệu ứng động để load nhanh hơn

                // Đặt tiêu đề cho các trục
                xAxis.setLabel("Ngày");
                yAxis.setLabel("Số tiền");
        }

        private void clearNhanVienFields() {
                addnv.clear();
                addnv1.clear();
                addNgaySinh.setValue(null);
                addnv3.clear();
                addnv4.clear();
                addnv5.clear();
                addnv6.clear();
                addnv7.clear();
                addnv8.clear();
                txtHinhAnhNV.clear();
                imageViewnv.setImage(null);
        }


        private void searchNhanVien(String keyword) {
                if (keyword == null || keyword.isEmpty()) {
                        table1.setItems(nhanVienList);
                        return;
                }

                ObservableList<NhanVien> filteredList = FXCollections.observableArrayList();
                for (NhanVien nv : nhanVienList) {
                        if (nv.getTen().toLowerCase().contains(keyword.toLowerCase()) ||
                                nv.getMaNV().toLowerCase().contains(keyword.toLowerCase())) {
                                filteredList.add(nv);
                        }
                }
                table1.setItems(filteredList);
        }


        // Helper methods for alerts
        private void showErrorAlert(String header, String content) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(header);
                alert.setContentText(content);
                alert.showAndWait();
        }

        private void showSuccessAlert(String message) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }

        private void showWarningAlert(String message) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }
                @FXML
                void switchToAnchor1(ActionEvent event) {
                        anchor1.setVisible(true);
                        anchor2.setVisible(false);
                        anchorbuton.setVisible(false);
                        anchorbuton1.setVisible(false);
                        anchorbuton2.setVisible(false);
                        anchorbutonnv.setVisible(false);
                        anchorbutonnv1.setVisible(false);
                        khohang.setVisible(false);
                        loadDoanhThuData();

                }

                @FXML
                void switchToAnchor2(ActionEvent event) {
                        anchor1.setVisible(false);
                        anchor2.setVisible(true);
                        anchorbuton.setVisible(false);
                        anchorbuton1.setVisible(false);
                        anchorbuton2.setVisible(false);
                        anchorbutonnv.setVisible(false);
                        anchorbutonnv1.setVisible(false);
                        khohang.setVisible(false);
                }

                @FXML
                void switchToAnchor_button(ActionEvent event) {
                        anchor1.setVisible(false);
                        anchor2.setVisible(true);
                        anchorbuton.setVisible(true);
                        anchorbuton1.setVisible(false);
                        anchorbuton2.setVisible(false);
                        anchorbutonnv.setVisible(false);
                        anchorbutonnv1.setVisible(false);
                        khohang.setVisible(false);
                }

                @FXML
                void switchToAnchor_button1(ActionEvent event) {
                        showEditForm();
                        anchor1.setVisible(false);
                        anchor2.setVisible(true);
                        anchorbuton.setVisible(false);
                        anchorbuton1.setVisible(true);
                        anchorbuton2.setVisible(false);
                        anchorbutonnv.setVisible(false);
                        anchorbutonnv1.setVisible(false);
                        khohang.setVisible(false);

                }

                @FXML
                void switchToAnchor_button2(ActionEvent event) {

                        anchor1.setVisible(false);
                        anchor2.setVisible(false);
                        anchorbuton.setVisible(false);
                        anchorbuton1.setVisible(false);
                        anchorbuton2.setVisible(true);
                        anchorbutonnv.setVisible(false);
                        anchorbutonnv1.setVisible(false);
                        khohang.setVisible(false);
                }

                @FXML
                void switchToAnchor_buttonnv(ActionEvent event) {
                        anchor1.setVisible(false);
                        anchor2.setVisible(false);
                        anchorbuton.setVisible(false);
                        anchorbuton1.setVisible(false);
                        anchorbuton2.setVisible(true);
                        anchorbutonnv.setVisible(true);
                        anchorbutonnv1.setVisible(false);
                        khohang.setVisible(false);
                }

                @FXML
                void switchToAnchor_buttonnv1(ActionEvent event) {
                        showEditFormnv();
                        anchor1.setVisible(false);
                        anchor2.setVisible(false);
                        anchorbuton.setVisible(false);
                        anchorbuton1.setVisible(false);
                        anchorbuton2.setVisible(true);
                        anchorbutonnv.setVisible(false);
                        anchorbutonnv1.setVisible(true);
                        khohang.setVisible(false);
                }
                @FXML
                void switchToAnchorKhohang(ActionEvent event) {
                        showEditFormnv();
                        anchor1.setVisible(false);
                        anchor2.setVisible(false);
                        anchorbuton.setVisible(false);
                        anchorbuton1.setVisible(false);
                        anchorbuton2.setVisible(false);
                        anchorbutonnv.setVisible(false);
                        anchorbutonnv1.setVisible(true);
                        khohang.setVisible(true);
                }
        }