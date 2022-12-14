package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //En el PrimaryStege contiene to_do
    private Stage primaryStage;

    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        //1º Paso: Decalramos al primaryStage(To_do estará dentro de este stage)
        this.primaryStage = primaryStage;

        //Opcional: Cambiamos el título de la app
        this.primaryStage.setTitle("EmpleadoApp - Ejemplo Aplicación JavaFX");

        //2º Paso: Inicializamos el RootLayout
        initRootLayout();

        //3º Paso: Desplegamos la ventana EmployeeOperations
        showEmployeeView();
    }

    //Inicializamos el root layout.
    public void initRootLayout() {
        try {
            //1º Paso: Se carga el root layout del RootLayout.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //2ºPaso: se enseña la escena que contiene el root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            //3º Paso: Enseñamos el primaryStage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Se enseña la ventana de las operaciones del empleado dentro del root layout
    public void showEmployeeView() {
        try {
            //Primero se carga la ventana Empleado desde EmpleadoView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EmpleadoView.fxml"));
            AnchorPane employeeOperationsView = (AnchorPane) loader.load();

            // Establezca la vista Empleado Operations en el centro del diseño raíz.
            rootLayout.setCenter(employeeOperationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
