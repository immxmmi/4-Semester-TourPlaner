package at.technikum.tourplanner;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.view.controller.ShowTourController;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneControllerImpl implements SceneController {
    static int width = Integer.parseInt(ConfigurationManager.getConfigPropertyValue("stage_width"));
    static int height = Integer.parseInt(ConfigurationManager.getConfigPropertyValue("stage_height"));
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Erstellt für die einzelnen Pages eine Stage
    private void createStage() {
        this.scene = new Scene(root, width, height);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        this.stage.setScene(scene);
        this.stage.show();
    }


    // Main
    @Override
    public void switchToMain(ActionEvent event) throws IOException {
         width = Integer.parseInt(ConfigurationManager.getConfigPropertyValue("stage_width"));
         height = Integer.parseInt(ConfigurationManager.getConfigPropertyValue("stage_height"));
        this.root = FXMLLoader.load(getClass().getResource(ConfigurationManager.getConfigPropertyValue("main_page")));
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        createStage();
    }

    //ERROR
    @Override
    public void switchToErrorPage(ActionEvent event) throws IOException {
        width = 922;
        this.root = FXMLLoader.load(getClass().getResource(ConfigurationManager.getConfigPropertyValue("error_page")));
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        createStage();
    }

    // Tour - Suchleiste
    @Override
    public void switchToSearchBar(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("search-main.fxml"));
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        createStage();
    }

    // Tour - Erstellen
    @Override
    public void switchToCreateTour(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("create-tour-view.fxml"));
        height = height -30;
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        createStage();
    }

    // Tour + TourLogger Einträge - Anzeigen
    @Override
    public void switchToShowTour(ActionEvent event, TourViewModel tourViewModel) throws IOException {
        width = 1311;
        height = 849;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show-tour-view.fxml"));
        this.root = loader.load();
        ShowTourController showTourController = loader.<ShowTourController>getController();
        showTourController.initialize(tourViewModel);
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        createStage();

    }
    @Override
    public void switchToShowTour(MouseEvent event, TourViewModel tourViewModel) throws IOException {
        width = 1311;
        height = 849;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show-tour-view.fxml"));
        this.root = loader.load();
        ShowTourController showTourController = loader.<ShowTourController>getController();
        showTourController.initialize(tourViewModel);
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        createStage();
    }

    // TourLogger - Erstellen
    @Override
    public void switchToCreateTourLog(ActionEvent event) throws IOException {
        width = 800;
        height = 638;
        this.root = FXMLLoader.load(getClass().getResource("show-tour-view.fxml"));
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        createStage();
    }

    // TourListe + Tour Erstellen Button - Anzeigen
    @Override
    public void switchToShowTourList(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("show-tour-list-view.fxml"));
        width = 1169;
         height = 700;
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        createStage();
    }

}
