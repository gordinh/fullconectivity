package mensageiros;

/**
 *
 * @author San
 */
import java.io.File;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Mensageiro extends Remote {

    public void enviarMensagem( String msg ) throws RemoteException;
    public String lerMensagem() throws RemoteException;
    
    
    public void subirArquivos(String usuario) throws RemoteException;
    public byte[] baixarArquivos(String usuario, String nomeDoArquivo) throws RemoteException;
    
    public File[] pegarArquivos(String usuario) throws RemoteException;
    
    public void criarArquivosDeTexto(String usuario) throws RemoteException;
    public void editarArquivosDeTexto(String usuario) throws RemoteException;
    
    public void executarArquivos(String usuario) throws RemoteException;
    public void execluirArquivos(String usuario, String nomeDoArquivo) throws RemoteException;
}