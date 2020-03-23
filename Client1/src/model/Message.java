package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String id;
    private String clientId;
    private String messageBody;


    public Message(String id,String clientId, String message) {
        this.id = id;
        this.clientId=clientId;
        this.messageBody = message;
    }

    public Message(String message) {
        this("0","client", message);
        setId("" + (int) (messageBody.hashCode() * Math.random()));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getBody() {
        return messageBody;
    }



    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm:ss");
        return "id=" + id  + ", message=\"" + messageBody + "\"";
    }
}