package ch.makery.address.view;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    //Referencia la MainApp
    private MainApp mainApp;

    public PersonOverviewController() {
    }

    @FXML
    private void initialize() {
        //Inicializar la tabla person con las dos columnas.
        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());

        //Detalles claros de person.
        showPersonDetails(null);

        //Escuchar los cambios de selección y mostrar los detalles de la persona cuando se cambia
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Añadir datos de lista observables a la tabla
        personTable.setItems(mainApp.getPersonData());
    }

    private void showPersonDetails(Person person) {
        if (person != null) { // Llene las etiquetas con información del objeto persona.
            firstNameLabel.setText(Person.getFirstName());
            lastNameLabel.setText(Person.getLastName());
            streetLabel.setText(Person.getStreet());
            postalCodeLabel.setText(Integer.toString(Person.getPostalCode()));
            cityLabel.setText(Person.getCity());
            birthdayLabel.setText(DateUtil.format(Person.getBirthday()));

        } else { //Persona es nula = eliminar el texto
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        }
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            //Nada selecionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nada seleccionado - No Selection");
            alert.setHeaderText("Ninguna persona seleccionada - No Person Selected");
            alert.setContentText("Por favor, selecciona a una persona de la tabla - Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nada seleccionado.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nada seleccionado - No Selection");
            alert.setHeaderText("Ninguna persona seleccionada - No Person Selected");
            alert.setContentText("Por favor, selecciona a una persona de la tabla - Please select a person in the table.");
        }

    }




}
