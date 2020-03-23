package mediator;

import com.google.gson.Gson;
import model.Message;
import model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UppercaseClientHandler implements Runnable, PropertyChangeListener {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Model model;
    private boolean running;

    public UppercaseClientHandler(Socket socket, Model model)
            throws IOException {
        this.model = model;
        model.addListener(this);
        this.socket = socket;
        this.in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        running = true;
        Gson gson = new Gson();
        while (running) {
            try {
                String request = in.readLine();
                Message requestMessage = gson.fromJson(request, Message.class);
                Message replyMessage = null;
                if(requestMessage.getId().equals("Message")) {

                    if (requestMessage.getClientId() != null) {
                        model.addLog(requestMessage.getClientId() + "> " + requestMessage.getBody());
                        replyMessage = new Message("Message", requestMessage.getClientId(), requestMessage.getBody());
                    } else {
                        model.addLog("Client> " + requestMessage.getBody());
                        replyMessage = new Message("Message", "Client", requestMessage.getBody());
                    }

                    String replyJson = gson.toJson(replyMessage);
                    out.println(replyJson);
                }
                else{
                    model.addLog(requestMessage.getClientId() + "> Requesting data from server...");
                    replyMessage = new Message("Server",requestMessage.getClientId(),UppercaseConnector.getClientsConnected()+"");
                    String replyJson = gson.toJson(replyMessage);
                    out.println(replyJson);
                }
                if (request.contentEquals("quit")) {
                    close();
                }
            } catch (Exception e) {
                model.addLog("Client error. Client has logged out.");
                int nr=UppercaseConnector.getClientsConnected();
                UppercaseConnector.setClientsConnected(--nr);
                close();
            }
        }
        close();
    }

    public void close() {
        running = false;
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            //
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      if(evt.getPropertyName().equalsIgnoreCase("BroadcastServer")) {
        Gson gson = new Gson();
        Message replyMessage = new Message("Broadcast","Server", (String)evt.getNewValue());
        String replyJson = gson.toJson(replyMessage);
        out.println(replyJson);
      }
    }
}
