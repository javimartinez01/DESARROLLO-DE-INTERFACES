package sample.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class RootControlador {
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    //BOTON AYUDA EN EL MENU
    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Información del programa");
        alert.setHeaderText("Es un Proyecto basado en la SWTestAcademy."+ "\n" + "Práctica realizada por Javier Martínez y Pablo Triguero.");
        alert.setContentText("En este programa, puedes buscar, eliminar, actualizar y añadir un nuevo empleado.");
        alert.show();
    }
}
