package mediator;

import model.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UppercaseConnector implements Runnable
{
  private final int PORT = 6789;
  private Model model;
  private boolean running;
  private ServerSocket welcomeSocket;

  private static int clientsConnected;

  public UppercaseConnector(Model model)
  {
    this.model = model;
    this.clientsConnected=0;
  }

  private void start() throws IOException
  {
    model.addLog("Starting Server...");
    welcomeSocket = new ServerSocket(PORT);

    running = true;
    while (running)
    {
      model.addLog("Waiting for a client...");
      Socket socket = welcomeSocket.accept();
      UppercaseClientHandler client = new UppercaseClientHandler(socket, model);
      Thread clientThread = new Thread(client);
      clientThread.setDaemon(true);
      clientThread.start();
      model.addLog("Client connected.");
      clientsConnected++;
    }
  }

  public static int getClientsConnected() {
    return clientsConnected;
  }
  public static void setClientsConnected(int nr){
    clientsConnected=nr;
  }

  @Override public void run()
  {
    try
    {
      start();
    }
    catch (IOException e)
    {
      model.addLog("Error: " + e.getMessage());
    }
  }

  public void stop()
  {
    running = false;
    try
    {
      welcomeSocket.close();
    }
    catch (Exception e)
    {
      //
    }
  }

}
