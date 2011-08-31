package mensageiros;

/**
 *
 * @author San
 */
import java.rmi.Naming;

public class MensageiroServer {

    public MensageiroServer() {
        try {
            Mensageiro m = new MensageiroImpl();
//            Naming.rebind("rmi://localhost:1099/MensageiroService", m);
            Naming.rebind("rmi://192.168.1.77:1099/MensageiroService", m);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    public static void main(String[] args) {
        try {      //Fazer o registo para o porto desejado
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (Exception e) {
            System.out.println("Exception starting RMI registry:");
            e.printStackTrace();
        }
        new MensageiroServer();

    }
}