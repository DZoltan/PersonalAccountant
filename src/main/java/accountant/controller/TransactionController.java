package accountant.controller;

import accountant.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionController {

    CategoryHandler categoryHandler = new CategoryHandler();
    CashHandler cashHandler = new CashHandler();
    ProfileHandler profileHandler = new ProfileHandler();

    int profile_id;

    @FXML
    private ChoiceBox categoriesList;

    @FXML
    private TextField summary;

    @FXML
    private TextArea description;

    @FXML
    private ListView<String> history;

    @FXML
    public void initalize(int profile_id){
        this.profile_id = profile_id;
        setCategory(profile_id);
        setHistory(profile_id);

    }

    private void setHistory(int profile_id) {
        ObservableList<String> TransactionHistory = FXCollections.observableArrayList();
        for(int i = 0; i < cashHandler.getOwnTransaction(profile_id).size();i++){
            Cash historyCash = cashHandler.getOwnTransaction(profile_id).get(i);

            String category = categoryHandler.selectCategorybyId(historyCash.getCategory_id());

            TransactionHistory.add(historyCash.getDate() + " " + category + " " + historyCash.getMoney() + " " + historyCash.getDescription() );

        }
        history.setItems(TransactionHistory);

    }

    public void setCategory(int profile_id){
        ObservableList<String> CategoryName = FXCollections.observableArrayList();
        for(int i = 0; i < categoryHandler.selectOwnCategory(profile_id).size();i++){
            CategoryName.add(categoryHandler.selectOwnCategory(profile_id).get(i).getCategory_name());
        }
        categoriesList.setItems(CategoryName);
    }


    public void addTransaction(ActionEvent event) {
        Cash transaction = Cash.builder()
                .cashId(cashHandler.setCashId(cashHandler.getAllTransaction()))
                .category_id(categoryHandler.selectCategoryIdByName(categoriesList.getSelectionModel().getSelectedItem().toString(), profile_id ))
                .description(description.getText())
                .money(ParseMoney(summary.getText(),categoryHandler.selectCategorybyName(categoriesList.getSelectionModel().getSelectedItem().toString(), profile_id) ))
                .profile_id(profile_id)
                .build();
        if(transaction.getMoney() != 0) {
            cashHandler.setNewTransaction(transaction);
            setHistory(profile_id);
            cashHandler.calculateBalance(profile_id);
        }
    }

    public int ParseMoney(String summaryText, Category category){
        int money = 0;
        try{
            money = cashHandler.ParseMoney(summaryText, category);
        }
        catch (NumberFormatException e){
            Alert inputAlert = new Alert(Alert.AlertType.WARNING);
            inputAlert.setHeaderText("Invalid Number");
            inputAlert.setContentText("Please enter a valid number");
            inputAlert.showAndWait();
            return 0;
        }
        return money;
    }

    public void back(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxs/menu.fxml"));
        Parent root = fxmlLoader.load();
        MenuController menu = fxmlLoader.<MenuController>getController();
        menu.initalize(profileHandler.getProfileFromId(profile_id));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
