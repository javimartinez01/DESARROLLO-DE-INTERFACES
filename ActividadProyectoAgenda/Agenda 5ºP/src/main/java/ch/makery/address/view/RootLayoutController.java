package ch.makery.address.view;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

import java.io.File;

public class RootLayoutController {

    //Referencia a la clase Main
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    //Metodo para seleccionar el fichero que cargar
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Establecer filtro de extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Mostrar diálogo de archivo abierto
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        //If para comprobar si es valido o no
        if (file != null) {
            mainApp.loadDataFromFile(file);
        }
    }


    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    //Metodo parar guaradar los datos de la persona seleccionada o actual en el fichero xml
    private void handleSaveAs() {
       //Declaramos el objeto personFile que posee el path donde se guardan los ficheros del metodo
        FileChooser fileChooser = new FileChooser();

        //Establecer filtro de extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Mostrar diálogo de archivo abierto
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Marco Jakob - Aut.Español: Javier Martínez");

        alert.showAndWait();
    }

    private void handleExit() {
        System.exit(0);
    }
}