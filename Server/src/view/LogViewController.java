package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewmodel.LogViewModel;

public class LogViewController {
    @FXML ListView<String> logList;
    @FXML TextField inputField;
    @FXML Label message;
    private Region root;
    private LogViewModel viewModel;
    private ViewHandler viewHandler;

    public LogViewController(){}

    public void init(ViewHandler viewHandler, LogViewModel viewModel, Region root){
        this.viewHandler=viewHandler;
        this.viewModel=viewModel;
        this.root=root;
        logList.setItems(viewModel.getLogs());
       message.textProperty().bind(viewModel.serverMessageProperty());
    }

    public Region getRoot() {
        return root;
    }

    public void reset(){}
    @FXML public void onEnter(){
        viewModel.setMessage(inputField.getText());
        inputField.clear();
    }

    public void onBack() {

    }
}
