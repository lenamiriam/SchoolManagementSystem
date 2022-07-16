package login;

import admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import students.StudentsController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label labelDBStatus;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<OptionStudentAdmin> comboBox;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.loginModel.checkIfDatabaseConnected()) {
            this.labelDBStatus.setText("Connected");
        } else {
            this.labelDBStatus.setText("Not connected");
        }

        this.comboBox.setItems(FXCollections.observableArrayList(OptionStudentAdmin.values()));
    }

    @FXML
    public void Login(ActionEvent e) {
        try {
            if (this.loginModel.isLogin(this.username.getText(), this.password.getText(), ((OptionStudentAdmin)this.comboBox.getValue()).toString())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                switch (((OptionStudentAdmin)this.comboBox.getValue()).toString()) {
                    case "Student":
                        studentLogin();
                        break;
                    case "Admin":
                        adminLogin();
                        break;
                }
            }
            else {

            }
        } catch (Exception exception) {

        }
    }

    public void studentLogin() {
        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load((getClass().getResource("/students/student.fxml").openStream()));

            StudentsController studentsController = (StudentsController) loader.getController();

            Scene scene = new Scene(root);

            userStage.setScene(scene);
            userStage.setTitle("Student dashboard");
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adminLogin() {
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminRoot = (Pane) adminLoader.load((getClass().getResource("/admin/admin.fxml").openStream()));
            AdminController adminController = (AdminController) adminLoader.getController();

            Scene scene = new Scene(adminRoot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin dashboard");
            adminStage.setResizable(false);
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}