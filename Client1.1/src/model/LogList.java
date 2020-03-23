package model;

import java.util.ArrayList;

public class LogList {
    private ArrayList<String> logList;

    public LogList() {
        this.logList = new ArrayList<>();
    }

    public void addLog(String txt) {
        logList.add(txt);
    }

    public int getLogSize() {
        return logList.size();
    }
}
