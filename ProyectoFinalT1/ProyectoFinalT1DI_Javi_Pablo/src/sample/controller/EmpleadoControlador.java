package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.Empleado;
import sample.model.EmpleadoDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EmpleadoControlador {

    @FXML
    private TextField empIdText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField newEmailText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField emailText;
    @FXML
    private TableView employeeTable;
    @FXML
    private TableColumn<Empleado, Integer>  empIdColumn;
    @FXML
    private TableColumn<Empleado, String>  empNameColumn;
    @FXML
    private TableColumn<Empleado, String> empLastNameColumn;
    @FXML
    private TableColumn<Empleado, String> empEmailColumn;
    @FXML
    private TableColumn<Empleado, String> empPhoneNumberColumn;
    @FXML
    private TableColumn<Empleado, Date> empHireDateColumn;

    //Para subprocesamiento multiple
    private Executor exec;

    //Iniciar la clase controlador
    @FXML
    private void initialize () {
        //Creamos un ejecutor para el uso del setDaemon
        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread (runnable);
            t.setDaemon(true);
            return t;
        });

        empIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        empNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        empEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        empPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        empHireDateColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());
    }


    //Método para buscar un empleado
    @FXML
    private void searchEmployee (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Se recoge la información
            Empleado emp = EmpleadoDAO.searchEmployee(empIdText.getText());
            //Se puebla para mostrar la información del empleado escogido
            populateAndShowEmployee(emp);
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("¡ERROR! No se ha podido obtener la información del empleado de la base de datos .\n" + e);
            throw e;
        }
    }

    //Método para buscar todos los empleados
    @FXML
    private void searchEmployees(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Empleado> empData = EmpleadoDAO.searchEmployees();
            populateEmployees(empData);
        } catch (SQLException e){
            System.out.println("¡ERROR! No se ha podido obtener la información de los empleados de la base de datos .\n" + e);
            throw e;
        }
    }


    //Método para poblar empleado
    @FXML
    private void populateEmployee (Empleado emp) throws ClassNotFoundException {
        //Se declara el ObservableList
        ObservableList<Empleado> empData = FXCollections.observableArrayList();
        //Se añade el empleado al ObservableList
        empData.add(emp);
        employeeTable.setItems(empData);
    }

    @FXML
    private void populateAndShowEmployee(Empleado emp) throws ClassNotFoundException {
        if (emp != null) {
            populateEmployee(emp);
            setEmpInfoToTextArea(emp);
        } else {
            resultArea.setText("!ERROR¡ Este empleado no existe\n");
        }
    }

    @FXML
    private void setEmpInfoToTextArea ( Empleado emp) {
        resultArea.setText("Nombre: " + emp.getFirstName() + "\n" +
                "Apellido: " + emp.getLastName());
    }

    @FXML
    private void populateEmployees (ObservableList<Empleado> empData) throws ClassNotFoundException {
        employeeTable.setItems(empData);
    }

    //Método para actualizar el correo electronico (e-mail) del empleado
    @FXML
    private void updateEmployeeEmail (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmpleadoDAO.updateEmpEmail(empIdText.getText(),newEmailText.getText());
            resultArea.setText("Correo electronico actualizado para: " + empIdText.getText() + "\n");
        } catch (SQLException e) {
            resultArea.setText("!ERROR¡ en la actualización del correo electronico: " + e);
        }
    }

    //Método para añadir un nuevo empleado en la DB
    @FXML
    private void insertEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmpleadoDAO.insertEmp(nameText.getText(),surnameText.getText(),emailText.getText());
            resultArea.setText("Empleado añadido correctamente! \n");
        } catch (SQLException e) {
            resultArea.setText("!ERROR¡ al añadir un empleado" + e);
            throw e;
        }
    }

    //Método para eliminar un empleado (utilizando su ID) de la DB
    @FXML
    private void deleteEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmpleadoDAO.deleteEmpWithId(empIdText.getText());
            resultArea.setText("Se ha eliminado correctamente al empleado id: " + empIdText.getText() + "\n");
        } catch (SQLException e) {
            resultArea.setText("!ERROR¡ al eliminar un empleado" + e);
            throw e;
        }
    }
}
