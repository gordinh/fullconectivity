package mensageiros;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author San
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;
import utils.Auxiliador;

public class MensageiroImpl extends UnicastRemoteObject implements mensageiros.Mensageiro {

    public MensageiroImpl() throws RemoteException {
        super();
    }

    public void enviarMensagem(String msg) throws RemoteException {
        System.out.println(msg);
    }

    public String lerMensagem() throws RemoteException {
        return "This is not a Hello World! message";
    }

    @Override
    public void subirArquivos(String usuario) throws RemoteException {
        Auxiliador.subirArquivo(usuario);
    }

    @Override
    public byte[] baixarArquivos(String usuario, String nomeDoArquivo) throws RemoteException {
        byte[] buffer = null;
        try {
            InputStream is = null;
            boolean success = true;
            is = new FileInputStream(new File("./arquivos/" + usuario + "/" + nomeDoArquivo));
            buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            System.out.println("BUFF= " + buffer);
            return buffer;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("BUFF= " + buffer);
        return buffer;
    }

    @Override
    public File[] pegarArquivos(String usuario) throws RemoteException {
        File diretorio = new File("./arquivos/" + usuario + "/");
        File flist[] = diretorio.listFiles();

        return flist;
    }

    @Override
    public void criarArquivosDeTexto(String usuario) throws RemoteException {
        String nomeDoArquivo = JOptionPane.showInputDialog("Qual o nome do arquivo", "arquivo");
        Auxiliador.novoArquivo(usuario, nomeDoArquivo);
    }

    @Override
    public void editarArquivosDeTexto(String usuario) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void executarArquivos(String usuario) throws RemoteException {
        Auxiliador.executarArquivo(usuario);
    }

    @Override
    public void execluirArquivos(String usuario, String nomeDoArquivo) throws RemoteException {
        File file = new File("./arquivos/" + usuario + "/" + nomeDoArquivo);
        file.delete();
    }
}