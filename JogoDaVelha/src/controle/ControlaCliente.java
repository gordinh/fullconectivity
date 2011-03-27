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
   Cliente cliente;
   

    public ControlaCliente(){

        login();
        cliente = new Cliente();
        
    }

    public void login(){

        jLogin = new JanelaLogin(this);

    }

    public void actionPerformed(ActionEvent e) {
        
        String nick = jLogin.getNick().getText();

        if(e.getSource() == jLogin.getEnter()){

            if(!nick.equalsIgnoreCase("") && nick.length() <= 8 ){
                
                cliente.setNick(nick);
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
        requisitarLista();
        
    }

    public void montarPacote(){
        
        InetAddress serverip = null;
        try {

            // Pôr nome do servidor
            serverip = InetAddress.getByName("linux-wo7e");
            clienteSocket = new DatagramSocket(5000, serverip);
            System.out.println(serverip.toString());          

            System.out.println("Conectando \"" +  cliente.getNick() + "\" ao server...");

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

    public void requisitarLista(){

        byte[] b = new byte[1024];
        String pacote = "Login" + cliente.getNick() + "\n";
        b = pacote.getBytes();
        DatagramPacket packet = new DatagramPacket(b, b.length, cliente.getIp(), cliente.getPorta());
        try {
            clienteSocket.send(packet);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro no Envio da requisição da lista", "Erro na Requisição", 0);
        }
    }

}
