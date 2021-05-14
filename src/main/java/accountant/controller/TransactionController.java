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
import java.security.cert.CertificateRevokedException;

public class TransactionController {

    CategoryHandler categoryHandler = new CategoryHandler();
    CashHandler cashHandler = new CashHandler();
    ProfileHandler profileHandler = new ProfileHandler();

    int profile_id;

    @FXML
    private ChoiceBox categorieList;

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
        categorieList.setItems(CategoryName);
    }


    public void addTransaction(ActionEvent event) {
        //TODO: A CashID nak muszáj értéket adni, kell bele egy ág, ami üres tábla mellett vissza ad 0-t.

        Cash transaction = Cash.builder()
                .cashId(cashHandler.setCashId())
                .category_id(categoryHandler.selectCategoryIdbyName(categorieList.getSelectionModel().getSelectedItem().toString(), profile_id ))
                .description(description.getText())
                .money(ParseMoney(summary.getText(),categoryHandler.selectCategorybyName(categorieList.getSelectionModel().getSelectedItem().toString(), profile_id) ))
                .profile_id(profile_id)
                .build();
        if(transaction.getMoney() != 0) {
            cashHandler.setNewTransaction(transaction);
            setHistory(profile_id);
            calculateBalance();
        }
    }

    public int ParseMoney(String summaryText, Category category){
        int money = 0;

        try{
            money = Integer.parseInt(summary.getText());

            if(category.isIn_out()){
                return 0 + Math.abs(money);
            }
            else{
                return 0-Math.abs(money);
            }

        }
        catch (NumberFormatException e){
            Alert inputAlert = new Alert(Alert.AlertType.WARNING);
            inputAlert.setHeaderText("Hibás adat");
            inputAlert.setContentText("A megadott összeg nem szám!");
            inputAlert.showAndWait();
        }
        return 0;
    }

    private void calculateBalance() {
        int balance = 0;
        for(int i = 0; i< cashHandler.getOwnTransaction(profile_id).size(); i++){
            balance += cashHandler.getOwnTransaction(profile_id).get(i).getMoney();
        }
        profileHandler.updateBalance(profile_id, balance);
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
