/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
   

    public ControlaCliente(){

        login();
        Cliente c = new Cliente();
    }

    public void login(){

        jLogin = new JanelaLogin(this);

    }

    public void actionPerformed(ActionEvent e) {
        
        String nick = jLogin.getNick().getText();

        if(e.getSource() == jLogin.getEnter()){

            if(!nick.equalsIgnoreCase("") && nick.length() <= 8 ){
                //System.out.println(jLogin.getNick());
                jLogin.Visible(false);
                conectaServidor(nick);
            } else{
                JOptionPane.showMessageDialog(null, "Seu nick deve conter de 1 a 8 caracteres", "Login Inválido", 0);
                
            }

        }

     
    }

    /**
     * Metodo que fará a conexão do usuário com o servidor
     * @param nick
     */
    public void conectaServidor(String nick){

        //Montando o pacote
        try {

            //Pegando ip do servidor  // nome da maquina linux-wo7e
            InetAddress addr = InetAddress.getByName("linux-wo7e");

            System.out.println(addr);

            String solicitacao = "Login";
            String pacote = solicitacao + "," + nick + "\n";
            System.out.println(pacote);
            
        } catch (UnknownHostException ex) {
           // Logger.getLogger(ControlaCliente.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o servidor", "Server Não Localizado", 0);
        }



        System.out.println("Conectando \"" +  nick + "\" ao server...");

        
        


    }
}
