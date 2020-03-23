package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewmodel.ConvertViewModel;

public class ConvertViewController {
    @FXML TextField requestField;
    @FXML TextField replyField;
    @FXML Label errorLabel;
    @FXML TextField idField;
    @FXML TextField serverField;


    private Region root;
    private ConvertViewModel viewModel;
    private ViewHandler viewHandler;

    public ConvertViewController(){}

    public void init(ViewHandler viewHandler, ConvertViewModel viewModel, Region root){
        this.viewHandler=viewHandler;
        this.viewModel=viewModel;
        this.root=root;
        requestField.textProperty().bindBidirectional(viewModel.requestProperty());
        replyField.textProperty().bind(viewModel.replyProperty());
        errorLabel.textProperty().bind(viewModel.errorProperty());
        idField.textProperty().bindBidirectional(viewModel.idProperty());
        serverField.textProperty().bindBidirectional(viewModel.serverProperty());
    }

    public Region getRoot() {
        return root;
    }

    public void reset(){}

    @FXML private void onSet(){viewModel.setId();}
    @FXML private void onSubmit(){
        viewModel.convert();
    }
    @FXML private void onEnter(){
        onSubmit();
    }
    @FXML private void toChat(){
        viewHandler.openView("log");
    }
    @FXML private void onSendToServer(){
        viewModel.sendToServer();
    }

}
