package model;

import utility.UnnamedPropertyChangeSubject;

import java.io.IOException;

public interface Model extends UnnamedPropertyChangeSubject
{
  void sentMessage(String source) throws Exception;
  void setMessage(String message);
  void setRequest(String message);
  void setReceivedMessage(String message);
  String getId();
  public void addLog(String log);
  void  setId(String id);
  void sendToServer(String request) throws IOException, InterruptedException;

}
