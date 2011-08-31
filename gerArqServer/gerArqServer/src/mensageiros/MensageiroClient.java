package mensageiros;


/**
 *
 * @author San
 */
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import javax.swing.JOptionPane;
import view.MenuPrincipal;

public class MensageiroClient {

    public static void main( String args[] ) {
        try {
//            Mensageiro m = (Mensageiro) Naming.lookup( "rmi://localhost/MensageiroService" );
            Mensageiro m = (Mensageiro) Naming.lookup( "rmi://192.168.1.77/MensageiroService" );
            
            new MenuPrincipal(m).setVisible(true);
        }
        catch( MalformedURLException e ) {
            System.out.println();
            System.out.println( "MalformedURLException: " + e.toString() );
            JOptionPane.showMessageDialog(null, "ERRO: "+e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        catch( RemoteException e ) {
            System.out.println();
            System.out.println( "RemoteException: " + e.toString() );
            JOptionPane.showMessageDialog(null, "ERRO: "+e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        catch( NotBoundException e ) {
            System.out.println();
            System.out.println( "NotBoundException: " + e.toString() );
            JOptionPane.showMessageDialog(null, "ERRO: "+e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        catch( Exception e ) {
            System.out.println();
            System.out.println( "Exception: " + e.toString() );
            JOptionPane.showMessageDialog(null, "ERRO: "+e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }
}  