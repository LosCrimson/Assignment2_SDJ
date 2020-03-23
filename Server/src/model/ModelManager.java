package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelManager implements Model{
    private Converter converter;
    private PropertyChangeSupport property;

    public ModelManager() {
        this.converter = new Converter();
        this.property=new PropertyChangeSupport(this);
    }

    /*@Override
    public synchronized String convert(String source) {
        String reply = converter.toUpperCase(source);
        addLog("Converting: " + source);
        return reply;
    }
*/
    @Override
    public synchronized void addLog(String log) {
        String logValue = converter.getLogSize() + ": " + log;
        converter.addLog(logValue);
        property.firePropertyChange("log",null,logValue);
    }

    @Override
    public void setMessage(String message) {
        property.firePropertyChange("BroadcastServer",null,message);
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
