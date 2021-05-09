package accountant.controller;

import accountant.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class TransactionController {

    CategoryHandler categoryHandler = new CategoryHandler();
    CashHandler cashHandler = new CashHandler();
    ProfileQuarry profileQuarry = new ProfileQuarry();

    int profile_id;

    @FXML
    private ChoiceBox categorieList;

    @FXML
    private DatePicker selectedDate;

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate;
        String dateString;
        for(int i = 0; i < cashHandler.getOwnTransaction(profile_id).size();i++){
            Cash historyCash = cashHandler.getOwnTransaction(profile_id).get(i);

            dateString = format.format(historyCash.getDate());
            String category = categoryHandler.selectCategorybyId(historyCash.getCategory_id());

            TransactionHistory.add(dateString + " " + category + " " + historyCash.getMoney() + " " + historyCash.getDescription() );

        }
        categorieList.setItems(TransactionHistory);

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
                .cashId(cashHandler.getAllTransaction().stream().mapToInt(Cash::getCashId).max().orElseThrow())
                .category_id(categoryHandler.selectCategoryIdbyName(categorieList.getSelectionModel().getSelectedItem().toString(), profile_id ))
                .description(description.getText())
                .money(Integer.parseInt(summary.getText()))
                .date(selectedDate.getValue())
                .build();

        cashHandler.setNewTransaction(transaction);
        setHistory(profile_id);
    }
}
