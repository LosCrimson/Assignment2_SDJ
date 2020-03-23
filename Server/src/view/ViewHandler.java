package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;

public class ViewHandler {
    private Stage primaryStage;
    private Scene currentScene;
    private ViewModelFactory viewModelFactory;

    private LogViewController logViewController;

    public ViewHandler(ViewModelFactory viewModelFactory){
        this.viewModelFactory=viewModelFactory;
        this.currentScene= new Scene(new Region());
    }

    public void start(Stage primaryStage){
        this.primaryStage=primaryStage;
        openView();
    }

    public void openView(){
        Region root = loadLogView("LogView.fxml");
        currentScene.setRoot(root);
        String title = "";
        if(root.getUserData()!=null){
            title+=root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();
    }


    private Region loadLogView(String fxmlFile){
        Region root =null;
        if(logViewController==null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                root=loader.load();
                logViewController = loader.getController();
                logViewController.init(this,viewModelFactory.getLogViewModel(),root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            logViewController.reset();
        }
        return logViewController.getRoot();
    }
}
