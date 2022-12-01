package ch.makery.address;

import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public MainApp() {
        // Añadir algunos datos de muestra
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        this.primaryStage.getIcons().add(new Image("file: resources//images/86957_address_book_icon.png"));

        initRootLayout();
        showPersonOverview();
    }

    private void showPersonOverview() {

    }


    public void initRootLayout() {
        try {
            //Carga el diseño raíz desde el archivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //Mostrar la escena que contiene el rootlayout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            //Acceso del controlador al mainApp
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Abrir el ultimo file person
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }

        public void showPersonOverview() {
            try {
                // Vista general de PersonOverview
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
                AnchorPane personOverview = (AnchorPane) loader.load();

                // Establecer PersonOverview en el centro de RootLayout.
                rootLayout.setCenter(personOverview);

                // Dar acceso al controller a la MainApp.
                PersonOverviewController controller = loader.getController();
                controller.setMainApp(this);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean showPersonEditDialog(Person person){
            try {
                //Cargar el archivo fxml y crear una nueva clase para el diálogo emergente (PersonEditDialog).
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                //Crear el Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Person");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);

                //Crear Scene
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // setPerson en el controller.
                PersonEditDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setPerson(person);

                //Mostrar la ventana/el diálogo y esperar hasta que el usuario lo cierre
                dialogStage.showAndWait();

                return controller.isOkClicked();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        //Metodo que ibtiebe la info del path del fichero de la clase preferences
        public File getPersonFilePath() {
            Preferences prefs = Preferences.userNodeForPackage(MainApp.class);

            //Creación de una variable de tipo caracter
            String filePath = prefs.get("filePath", null);

            //Devuelve la ruta si hay fichero
            if (filePath != null) {
                return new File(filePath);
            } else {
                return null;
            }
        }

        //Establecer el path que he obtenido en el método info
        public void setPersonFilePath (File file){
            //Creacion del objeto prefs. para la obtención de los nodos de javaFx
            Preferences pref = Preferences.userNodeForPackage(MainApp.class);
            //Condicional para comprobar si hay fichero para obtener la ruta y titularlo
            //Sino hay fichero descarto la ruta que ha pasado el preferences
            if (file != null) {
                //Obtiene la ruta del fichero path y lo introduce en filepath
                pref.put("filePath", file.getPath());
                //Actualización
                primaryStage.setTitle("AdressApp" + file.getName());
            } else {
                //Se quita la ruta
                pref.remove("filePath");
                primaryStage.setTitle("AdressApp");
            }

        }

        //Metodo para cargar datos desde el fichero especificado con el wrapper, fichero.xml
        public void loadDataFromFile(File file) {
            try {
                JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);

                //Utilizo el contexto para crear un Unmarshaller
                Unmarshaller um = context.createUnmarshaller();

                //Creo un objeto para leer xml del fichero xml: sacar datos del xml con unmarshaller
                PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

                //Ya extraidos los datos xml, se añaden los datos al objeto
                personData.clear();
                personData.addAll(wrapper.getPersons());
                personData.clear();

                //Guardar en el path
                setPersonFilePath(file);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not load data");
                alert.setContentText("Could not load data from file:\n" + file.getPath());

                alert.showAndWait();
            }
        }

        //Metodo para guardar la persona seleccionada en el fichero xml
        public void savePersonDataToFile (File file){
            try {
                //Contexto de JAXBContext
                JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);

                //Declaracion de un marshaller
                Marshaller m = context.createMarshaller();
                //Marshaller m configurado
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                //Donde se van a fijar los datos
                PersonListWrapper wrapper = new PersonListWrapper();
                wrapper.setPersons(personData);

                m.marshal(wrapper, file);

                setPersonFilePath(file);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not save data");
                alert.setContentText("Could not save data to file:\n" + file.getPath());

                alert.showAndWait();
            }
        }

        private File getPersonFilePath () {
            Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
            String filePath = prefs.get("filePath", null);
            if (filePath != null) {
                return new File(filePath);
            } else {
                return null;
            }
        }

        public void setPersonFilePath (File file){
            Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
            if (file != null) {
                prefs.put("filePath", file.getPath());

                // Update the stage title.
                primaryStage.setTitle("AddressApp - " + file.getName());
            } else {
                prefs.remove("filePath");

                // Update the stage title.
                primaryStage.setTitle("AddressApp");
            }
        }
        public void loadPersonDataFromFile (File file){
            try {
                JAXBContext context = JAXBContext
                        .newInstance(PersonListWrapper.class);
                Unmarshaller um = context.createUnmarshaller();

                // Reading XML from the file and unmarshalling.
                PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

                personData.clear();
                personData.addAll(wrapper.getPersons());

                // Save the file path to the registry.
                setPersonFilePath(file);

            } catch (Exception e) { // catches ANY exception
                Dialogs.create()
                        .title("Error")
                        .masthead("Could not load data from file:\n" + file.getPath())
                        .showException(e);
            }
        }

        public void savePersonDataToFile (File file){
            try {
                JAXBContext context = JAXBContext
                        .newInstance(PersonListWrapper.class);
                Marshaller m = context.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                // Wrapping our person data.
                PersonListWrapper wrapper = new PersonListWrapper();
                wrapper.setPersons(personData);

                // Marshalling and saving XML to the file.
                m.marshal(wrapper, file);

                // Save the file path to the registry.
                setPersonFilePath(file);
            } catch (Exception e) { // catches ANY exception
                Dialogs.create().title("Error")
                        .masthead("Could not save data to file:\n" + file.getPath())
                        .showException(e);
            }
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}