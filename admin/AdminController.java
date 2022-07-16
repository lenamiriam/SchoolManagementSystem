package admin;

import dbUtil.dbConnection;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dataofbirth;
    @FXML
    private TableView<StudentData> studenttable;
    @FXML
    private TableColumn<StudentData, String> idcolumn;
    @FXML
    private TableColumn<StudentData, String> firstnamecolumn;
    @FXML
    private TableColumn<StudentData, String> lastnamecolumn;
    @FXML
    private TableColumn<StudentData, String> emailcolumn;
    @FXML
    private TableColumn<StudentData, String> dateofbirthcolumn;

    private dbConnection dc;
    private ObservableList<StudentData> data;

    private String sql = "SELECT * FROM students";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dc = new dbConnection();
    }

    @FXML
    private void loadStudentData (ActionEvent event) throws SQLException {
        try {
            Connection connection = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                this.data.add(new StudentData(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
            }
        } catch (SQLException e) {
            System.out.println("Error" + e);
        }

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("id"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.dateofbirthcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("dataOfBirth"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);
    }

    @FXML
    private void addStudent(ActionEvent event) {
        String sqlInsert = "INSERT INTO students(id, firstName, lastName, email, dataOfBirth) VALUES (?,?,?,?,?)";

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1,this.id.getText());
            statement.setString(2,this.firstname.getText());
            statement.setString(3,this.lastname.getText());
            statement.setString(4,this.email.getText());
            statement.setString(5,this.dataofbirth.getEditor().getText());
            statement.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields(ActionEvent event) {
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dataofbirth.setValue(null);
    }
}
