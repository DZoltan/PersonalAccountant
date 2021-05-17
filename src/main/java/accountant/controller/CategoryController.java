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
import org.tinylog.Logger;

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
        dialog.setTitle("Category");
        dialog.setHeaderText("Update or Delete the selected category");

        ButtonType modifyButtonType = new ButtonType("Update!", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(modifyButtonType,deleteButtonType,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField categoryName = new TextField();
        categoryName.setPromptText(category.getCategory_name());
        CheckBox inOut = new CheckBox();

        grid.add(new Label("Name:" + profile_id), 0, 0);
        grid.add(categoryName, 1, 0);
        grid.add(new Label("In(checked) or Out(unchecked):"), 0, 1);
        grid.add(inOut, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == modifyButtonType) {
                handler.updateCategory(category.getId(), categoryName.getText(), inOut.isSelected());
                Logger.info(categoryName.getText() + " has modified.");
            }
            else if(dialogButton == deleteButtonType){
                handler.deleteCategory(category.getId());
                Logger.info(categoryName.getText() + " has deleted.");
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
        dialog.setTitle("Category");
        dialog.setHeaderText("Add new Category");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField categoryName = new TextField();
        categoryName.setPromptText("Category");
        CheckBox inOut = new CheckBox();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(categoryName, 1, 0);
        grid.add(new Label("In(checked) or Out(unchecked):"), 0, 1);
        grid.add(inOut, 1, 1);



        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                handler.setNewCategory(inOut.isSelected(),  categoryName.getText(), profile_id);
                Logger.info("New Category stored: " + categoryName.getText());
            }
            return null;
        });

        Optional<Pair<String, Boolean>> result = dialog.showAndWait();
        setOwnCategory(profile_id);
    }
}
