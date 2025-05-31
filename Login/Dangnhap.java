package Login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dangnhap implements Initializable {

    @FXML
    private Label statusLabel;
    @FXML
    private AnchorPane PaneDangNhap;
    @FXML
    private AnchorPane panlogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField textField;
    @FXML
    private TextField username;

    @FXML
    private Button eyeButton;

    private boolean isVisible = false;
    private DatabaseConnection databaseConnection;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseConnection = new DatabaseConnection();
        textField.textProperty().bindBidirectional(password.textProperty());
        eyeButton.setOnMouseClicked(this::handleEyeButtonClick);

        if (statusLabel == null) {
            System.err.println("Warning: statusLabel is not properly initialized in FXML");
        }
    }

    private void handleEyeButtonClick(MouseEvent event) {
        isVisible = !isVisible;
        textField.setVisible(isVisible);
        textField.setManaged(isVisible);
        password.setVisible(!isVisible);
        password.setManaged(!isVisible);
        eyeButton.setText(isVisible ? "🙈" : "👁");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void Logintk() {
        String user = username.getText().trim();
        String passwd = password.getText().trim();

        System.out.println("Đang thử đăng nhập với: " + username + "/" + passwd);

        if (user.isEmpty() || passwd.isEmpty()) {
            setStatusMessage("Vui lòng nhập đầy đủ thông tin!", "red");
            return;
        }

        // Kiểm tra kết nối database trước
        try (Connection testConn = databaseConnection.getConnection()) {
            System.out.println("Kết nối database OK");
        } catch (SQLException e) {
            setStatusMessage("Lỗi kết nối database", "red");
            e.printStackTrace();
            return;
        }

        if (checkLogin(user, passwd)) {
            setStatusMessage("Đăng nhập thành công!", "green");
            openMainApplication();
            PaneDangNhap.setVisible(false);
            panlogin.setVisible(false);
        } else {
            setStatusMessage("Sai tài khoản hoặc mật khẩu!", "red");
        }
        try {
            Connection testConn = databaseConnection.getConnection();
            System.out.println("Database connection successful!");
            testConn.close();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            setStatusMessage("Lỗi kết nối database", "red");
            return;
        }
    }

    private void setStatusMessage(String message, String color) {
        if (statusLabel != null) {
            statusLabel.setText(message);
            statusLabel.setStyle("-fx-text-fill: " + color + ";");
        } else {
            System.out.println("Status: " + message);
        }
    }

    private boolean checkLogin(String user, String passwd) {
        String sql = "SELECT * FROM qlynhanvien WHERE Taikhoan = ? AND Matkhau = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user);
            stmt.setString(2, passwd);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful for: " + user);
                return true;
            } else {
                System.out.println("Login failed - Invalid credentials");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            setStatusMessage("Lỗi kết nối database", "red");
            return false;
        }
    }

    private void openMainApplication() {
        try {
            new Test.App().start(new Stage());
        } catch (Exception e) {
            setStatusMessage("Lỗi khi mở ứng dụng chính!", "red");
            e.printStackTrace();
        }
    }


}