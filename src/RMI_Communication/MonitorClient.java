package RMI_Communication;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class MonitorClient {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Usage: MonitorClient <server host name> <user name> <msg>");
            System.exit(-1);
        }
        IMonitor remoteObject;
        Registry reg;
        List<Event> events;
        List<User> users;
        try{
            reg = LocateRegistry.getRegistry(args[0]);
            remoteObject = (IMonitor) reg.lookup("MonitorServer");

            remoteObject.communicate(args[2],args[1]);
            remoteObject.registerEvent(args[2]);
            remoteObject.registerUsers(args[1], args[2]);
            int cnt = remoteObject.amountEvents();
            System.out.println("There are: "+ cnt +" events");
            events = remoteObject.eventsList();
            for(Event element : events)
                System.out.println("-> " + element.toString());

//			Event back = remoteObject.read(0);
//			System.out.println("BACK: " + back.toString());
            users = remoteObject.usersList();
            for(User user : users)
                System.out.println(user.getName() + ": " + user.msgToString());

        } catch(RemoteException ex){
            ex.printStackTrace();
        } catch(NotBoundException ex){
            ex.printStackTrace();
        }
    }
}
