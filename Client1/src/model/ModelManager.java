package model;

import mediator.ServerModel;
import mediator.UppercaseClient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class ModelManager implements Model {
    private PropertyChangeSupport property;
    private ServerModel serverModel;
    private String clientId;
    private LogList logList;

    public ModelManager() throws IOException {
        this.property = new PropertyChangeSupport(this);
        serverModel = new UppercaseClient(this);
        serverModel.connect();
        this.logList = new LogList();
    }


    @Override
    public void sentMessage(String source) throws Exception {
        serverModel.sendMessage(source);
    }

    @Override
    public synchronized void addLog(String log) {
        String logValue = log;
        logList.addLog(logValue);
        property.firePropertyChange("log", null, logValue);
    }

    @Override
    public void setMessage(String message) {
        property.firePropertyChange("Broadcast", null, message);
    }

    @Override
    public void setRequest(String message) {
        property.firePropertyChange("Server",null,message);
    }

    @Override
    public void setReceivedMessage(String message) {
        property.firePropertyChange("Message", null, message);
    }

    @Override
    public String getId() {
        return clientId;
    }

    @Override
    public void setId(String id) {
        clientId = id;
    }

    @Override
    public void sendToServer(String request) throws IOException, InterruptedException {
        serverModel.sendMessageToServer(request);
    }


    @Override
    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }

}
