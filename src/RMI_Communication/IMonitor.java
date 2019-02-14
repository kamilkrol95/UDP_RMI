package RMI_Communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IMonitor extends Remote{

    // Events
    void communicate(String message, String name) throws RemoteException;
    boolean registerEvent(String message) throws RemoteException;
    Event read(int index) throws RemoteException;
    List<Event> eventsList() throws RemoteException;
    int amountEvents() throws RemoteException;

    // Users
    void registerUsers(String name, String message) throws RemoteException;
    List<User> usersList() throws RemoteException;
}
