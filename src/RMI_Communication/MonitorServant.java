package RMI_Communication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class MonitorServant extends UnicastRemoteObject
        implements IMonitor {

    private List<Event> eventList;
    private List<User> userList;

    public MonitorServant() throws RemoteException {
        eventList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    public void communicate(String message, String name)  {
        System.out.println("Server comunicate: <" + name + ": " + message + ">");
    }

    public boolean registerEvent(String message)  {
        eventList.add(new Event("<Message registered>: " + message));
        System.out.println("Server register: " + message);
        return true;
    }

    public Event read(int index)  {
        if(index < 0 || index > eventList.size())
            return null;
        return eventList.get(index);
    }

    public List<Event> eventsList() {
        return eventList;
    }

    public int amountEvents() {
        return eventList.size();
    }

    public void registerUsers(String name, String message)  {
        for(User user : userList) {
            if(name.equals(user.name)) {
                user.addMsg(message);
                return;
            }
        }
        userList.add(new User(name,message));
    }

    public List<User> usersList() {
        return userList;
    }
}
