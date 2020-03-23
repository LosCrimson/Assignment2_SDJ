package mediator;

import com.google.gson.Gson;
import model.Message;
import model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UppercaseClient implements ServerModel {
    private final static String HOST = "localhost";
    private final static int PORT = 6789;
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;
    private Model model;
    private String receivedMessage;


    public UppercaseClient(String host, int port, Model model) {
        this.host = host;
        this.port = port;
        gson = new Gson();
        this.model = model;

    }

    public UppercaseClient(Model model) {
        this.host = HOST;
        this.port = PORT;
        gson = new Gson();
        this.model = model;
    }

    @Override
    public void connect() throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        UppercaseClientReceiver clientReceiver = new UppercaseClientReceiver(this, in);
        Thread thread = new Thread(clientReceiver);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void disconnect() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public synchronized void sendMessage(String source) {
        System.out.println(model.getId() + " :" + source);
        Message requestMessage = new Message("Message", model.getId(), source);
        String requestJson = gson.toJson(requestMessage);
        model.addLog("Me: " + source);
        out.println(requestJson);
    }

    @Override
    public void sendMessageToServer(String source) throws IOException, InterruptedException {
        System.out.println(model.getId() + " :" + source);
        Message requestMessage = new Message("Request", model.getId(), source);
        String requestJson = gson.toJson(requestMessage);
        model.addLog("Request: " + source);
        out.println(requestJson);
    }


    public void setMessage(String message) {
        model.setMessage(message);
    }

    public void setRequest(String clientId, String receivedMessage) {
        if (clientId.equals(model.getId()))
            model.setRequest(receivedMessage);
    }

    public void received(String message, String clientId) {
        if (!model.getId().equals(clientId)) {
            receivedMessage = clientId + ": " + message;
            model.setReceivedMessage(receivedMessage);
        }
    }
}
