package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LogViewModel implements PropertyChangeListener {
    private Model model;
    private ObservableList<String> logs;
    private StringProperty serverMessage;

    public LogViewModel(Model model){
        this.model=model;
        model.addListener(this);
        logs= FXCollections.observableArrayList();
        serverMessage=new SimpleStringProperty();
    }

    public ObservableList<String> getLogs(){
        return logs;
    }

    public StringProperty serverMessageProperty() {
        return serverMessage;
    }

    public void setMessage(String message){
        model.setMessage(message);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equalsIgnoreCase("Broadcast")) {
            serverMessage.set((String) evt.getNewValue());
            Platform.runLater(()->logs.add("Server broadcast: "+ evt.getNewValue()));
        }
        else if(evt.getPropertyName().equals("Server")){
            Platform.runLater(()->logs.add("Server info: "+ evt.getNewValue()+" clients logged in right now."));
        }
        else {
            Platform.runLater(() -> logs.add((String) evt.getNewValue()));
        }
    }


}
