/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import javax.swing.JOptionPane;
import modelo.Cliente;
import visual.JanelaLogin;

/**
 *
 * Essa classe deve gerenciar todas as ações do jogador. sua função é gerir o fluxo
 * das ações do jogador.
 *
 * @author andre
 */
public class ControlaCliente implements ActionListener{

   JanelaLogin jLogin;
   DatagramSocket clienteSocket;
   String nick;
   

    public ControlaCliente(){

        login();
        
        
    }

    public void login(){

        jLogin = new JanelaLogin(this);

    }

    public void actionPerformed(ActionEvent e) {
        
        nick = jLogin.getNick().getText();

        if(e.getSource() == jLogin.getEnter()){

            if(!nick.equalsIgnoreCase("") && nick.length() <= 8 ){                
                
                jLogin.Visible(false);
                conectaServidor();
                
            } else{

                JOptionPane.showMessageDialog(null, "Seu nick deve conter de 1 a 8 caracteres", "Login Inválido", 0);                
            }
        }     
    }

    /**
     * Metodo que fará a conexão do usuário com o servidor
     * @
     */
    public void conectaServidor(){

        montarPacote();
        cadastrarNaLista();
        
    }

    public void montarPacote(){
        
        InetAddress serverip = null;
        try {

            // Pôr nome do servidor
            serverip = InetAddress.getByName("linux-wo7e");
            clienteSocket = new DatagramSocket(5000, serverip);
            System.out.println(serverip.toString());          

            System.out.println("Conectando \"" +  nick + "\" ao server...");

        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o servidor", "Server Não Localizado", 0);
        }
        catch (SocketException se){
            JOptionPane.showMessageDialog(null, "Verificar criação do Socket", "Erro no Socket", 0);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void cadastrarNaLista(){

        
        String pacote = "Login" + "|" + nick;

        byte[] b = new byte[pacote.getBytes().length];
        b = pacote.getBytes();
        
        try {
            DatagramPacket packet = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), 5000);
            clienteSocket.send(packet);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro no Envio da requisição da lista", "Erro na Requisição", 0);
        }        
    }

    public void requisitarLista(){

        String pacote = "Lista" + "|" + nick;

        byte[] b = new byte[pacote.getBytes().length];
        b = pacote.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), 5000);
            clienteSocket.send(packet);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro no Envio da requisição da lista", "Erro na Requisição", 0);
        }
    }

}
