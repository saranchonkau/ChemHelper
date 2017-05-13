package by.bsu.chemistry.util;

import by.bsu.chemistry.sample.FormulaManager;
import by.bsu.chemistry.sample.Main;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Ivan on 11.05.2017.
 */

public class BoxUtils {

    @Autowired
    FormulaManager manager;

    private final static BoxUtils OUR_INSTANCE = new BoxUtils();

    public static BoxUtils getInstance() {
        return OUR_INSTANCE;
    }

    private BoxUtils() {
    }



    public BorderPane createBorderPane(){
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(800, 400);

        Menu file = new Menu("File");
        file.getItems().addAll(new MenuItem("new File"), new MenuItem("Open..."));

        Menu edit = new Menu("Edit");
        edit.getItems().addAll(new MenuItem("Options..."));

        Menu help = new Menu("Help");
        help.getItems().addAll(new MenuItem("About"));

        MenuBar menuBar = new MenuBar(file, edit, help);

        borderPane.setTop(menuBar);
        return borderPane;
    }

    public VBox createStartCenterBox() throws IOException {
        VBox startVBox = new VBox();
        startVBox.setPrefSize(640.0, 305.0);

        Image bsu_logo = new Image("/pictures/BSU_logo.jpg");
        ImageView imageView = new ImageView(bsu_logo);

        startVBox.getChildren().add(imageView);
        startVBox.setAlignment(Pos.CENTER);
        return startVBox;
    }

    public Pane getVBoxWithTreeView(){

        TreeView<String> treeView = new TreeView<>();
        treeView.setPrefSize(146.0, 344.0);
        treeView.setShowRoot(false);
        treeView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> changeVBox(newValue.getValue()));


        TreeItem<String> root = new TreeItem<>("Выберите формулу:");
        root.setExpanded(true);
        TreeItem<String> nuclearChemistryItem = new TreeItem<>("Nuclear Chemistry");
        fillNuclearChemistryBranch(nuclearChemistryItem);

        TreeItem<String> radiationChemistryItem = new TreeItem<>("Radiation Chemistry");
        TreeItem<String> dosimetryItem = new TreeItem<>("Dosimetry");

        root.getChildren().setAll(nuclearChemistryItem, radiationChemistryItem, dosimetryItem);
        treeView.setRoot(root);
        VBox vBox = new VBox(treeView);
        vBox.setPrefSize(163.0, 375.0);
        vBox.setVgrow(treeView, Priority.ALWAYS);
        return vBox;
    }

    private void changeVBox(String title) {
        try {
            Objects.requireNonNull(manager);
            Main.borderPane.setCenter(manager.getFormulaView(title));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            VBox vBox = new VBox();
            vBox.setPrefSize(437.0, 328.0);

            Label label = new Label(title);
            label.setPrefSize(600.0, 31.0);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setFont(Font.font("Times New Roman", 60.0));
            label.setAlignment(Pos.CENTER);
            vBox.getChildren().add(label);
            Main.borderPane.setCenter(vBox);
            vBox.setAlignment(Pos.CENTER);
            System.out.println(title);
        }
    }


    private void fillNuclearChemistryBranch(TreeItem<String> mainBranch){
        mainBranch.getChildren().setAll(Arrays.asList(
                new TreeItem<String>("Formula #1"),
                new TreeItem<String>("WeizsaeckerFormula"),
                new TreeItem<String>("Formula #3")));
    }
}
