package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class ConvertViewModel implements PropertyChangeListener {
    private Model model;
    private StringProperty request, reply, error, id,server;

    public ConvertViewModel(Model model){
        this.model=model;
        model.addListener(this);
        this.request=new SimpleStringProperty();
        this.reply=new SimpleStringProperty();
        this.error=new SimpleStringProperty();
        this.id = new SimpleStringProperty();
        this.server = new SimpleStringProperty();
    }

    public StringProperty replyProperty() {
        return reply;
    }

    public StringProperty requestProperty() {
        return request;
    }

    public StringProperty errorProperty() {
        return error;
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty serverProperty() {
        return server;
    }

    public void convert(){
        try {
            if(request.get().replaceAll("\\s","").equals(""))
                error.set("Field is empty.");
            else {
                model.sentMessage(request.get());

                //TODO: change this later on maybe?
                reply.set(request.get());

                error.set(null);
            }
        } catch (Exception e) {
            error.set("No value in field.");
        }

    }

    public void setId(){
        if(id.get()!=null)
            model.setId(id.get());
        else
            model.setId("Client");
    }

    public void sendToServer(){
        try {
            model.sendToServer(server.get());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equalsIgnoreCase("Broadcast")){
            reply.set("Broadcast: "+evt.getNewValue());
        }
        else if(evt.getPropertyName().equals("Server")){
            reply.set(evt.getNewValue()+" clients logged in right now.");
        }
        else
            reply.set(evt.getNewValue()+"");
    }
}
