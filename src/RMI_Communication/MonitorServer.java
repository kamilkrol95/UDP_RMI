package RMI_Communication;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MonitorServer {
    Registry reg;
    MonitorServant servant;

    public static void main(String[] args) {
        try{
            MonitorServer s = new MonitorServer();
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    protected MonitorServer() throws RemoteException {
        try{
            reg = LocateRegistry.createRegistry(1099);
            servant = new MonitorServant();
            reg.rebind("MonitorServer", servant);
            System.out.println("Server READY");
        } catch (RemoteException ex){
            ex.printStackTrace();
            throw ex;
        }
    }
}
