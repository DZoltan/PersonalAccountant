package accountant.controller;

import accountant.model.Category;
import accountant.model.CategoryHandler;
import accountant.model.ProfileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryController {
    int profile_id;
    ProfileHandler profileHandler = new ProfileHandler();

    CategoryHandler handler = new CategoryHandler();
    List<Category> category = new ArrayList<>();
    @FXML
    public ListView<String> OwnCategory = new ListView<>();

    @FXML
    public void setOwnCategory(int id){
        profile_id = id;
        category = handler.selectOwnCategory(id);
        ObservableList<String> DataList = FXCollections.observableArrayList();
        for (Category value : category) {
            DataList.add(value.getData());
        }
        OwnCategory.setItems(DataList);
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxs/menu.fxml"));
        Parent root = fxmlLoader.load();
        MenuController menu = fxmlLoader.<MenuController>getController();
        menu.initalize(profileHandler.getProfileFromId(profile_id));
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void ModifyDialog(Category category){
        Dialog<Pair<String, Boolean>> dialog = new Dialog<>();
        dialog.setTitle("Kategória");
        dialog.setHeaderText("Módosítd, vagy töröld a kiválaszott kategóriát!");

        ButtonType modifyButtonType = new ButtonType("Módosítás", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Törlés", ButtonBar.ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(modifyButtonType,deleteButtonType,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField categoryName = new TextField();
        categoryName.setPromptText(category.getCategory_name());
        CheckBox inOut = new CheckBox();

        grid.add(new Label("Név:" + profile_id), 0, 0);
        grid.add(categoryName, 1, 0);
        grid.add(new Label("Bevétel:"), 0, 1);
        grid.add(inOut, 1, 1);

        /*Node modifyButton = dialog.getDialogPane().lookupButton(modifyButtonType);
        modifyButton.setDisable(true);*/

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == modifyButtonType) {
                handler.updateCategory(category.getId(), categoryName.getText(), inOut.isSelected());
            }
            else if(dialogButton == deleteButtonType){
                handler.deleteCategory(category.getId());
            }
            return null;
        });

        Optional<Pair<String, Boolean>> result = dialog.showAndWait();
        setOwnCategory(profile_id);
    }

    public void selectItem() {
        OwnCategory.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                ModifyDialog(category.get(OwnCategory.getSelectionModel().getSelectedIndex()));
            }
        });

    }


    public void newCategory() {
        Dialog<Pair<String, Boolean>> dialog = new Dialog<>();
        dialog.setTitle("Kategória");
        dialog.setHeaderText("Új Kategória hozzáadása");

        ButtonType addButtonType = new ButtonType("Hozzáad", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField categoryName = new TextField();
        categoryName.setPromptText("Kategória");
        CheckBox inOut = new CheckBox();

        grid.add(new Label("Név:"), 0, 0);
        grid.add(categoryName, 1, 0);
        grid.add(new Label("Bevétel:"), 0, 1);
        grid.add(inOut, 1, 1);



        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                handler.setNewCategory(inOut.isSelected(),  categoryName.getText(), profile_id);
            }
            return null;
        });

        Optional<Pair<String, Boolean>> result = dialog.showAndWait();
        setOwnCategory(profile_id);
    }
}
