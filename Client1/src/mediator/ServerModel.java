package mediator;

import java.io.IOException;

public interface ServerModel {
    public void connect() throws IOException;

    public void disconnect() throws IOException;

    public void sendMessage(String source) throws IOException, InterruptedException;
    public void sendMessageToServer(String source) throws IOException, InterruptedException;
}
