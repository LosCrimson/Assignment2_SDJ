package mediator;

import com.google.gson.Gson;
import javafx.scene.control.skin.CellSkinBase;
import model.Message;

import java.io.BufferedReader;
import java.io.IOException;

public class UppercaseClientReceiver implements Runnable{
    private BufferedReader in;
    private UppercaseClient client;

    public UppercaseClientReceiver(UppercaseClient client, BufferedReader in){
        this.client=client;
        this.in=in;
    }


    @Override
    public void run() {
        Gson gson = new Gson();
        while (true){
            try {
                String line = in.readLine();
                Message replyMessage = gson.fromJson(line,Message.class);
                if(replyMessage.getId().equalsIgnoreCase("Broadcast"))
                    client.setMessage(replyMessage.getBody());
                else if(replyMessage.getId().equals("Server")){
                    client.setRequest(replyMessage.getClientId(),replyMessage.getBody());
                }
                else
                    client.received(replyMessage.getBody(),replyMessage.getClientId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
