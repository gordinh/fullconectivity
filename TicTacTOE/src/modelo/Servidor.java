/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Esta classe atuará como a instância de escuta do servidor.
 * Sua finalidade é receber as mensagens dos clientes e iniciar o antendimento a eles.
 * 
 * @author douglas
 */
public class Servidor implements Runnable{

    public int count;
    public DatagramSocket serverSocket;
    DatagramPacket receivePacket;
    
    
    public Servidor(){
        // Para fins de teste. Não manter a lista vazia.
        try {
            BancoOnlineDoServidor.getInstance().cadastroNaLista("fixo",InetAddress.getLocalHost(), 8080);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receberMensagens() throws Exception{
        
        serverSocket  = new DatagramSocket(2495);
        
        while (true) {
            
            byte[] receiveData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            System.out.println("\n [ metodo receber mensagens] Servidor diz: Aguardando uma mensagem...");
            serverSocket.receive(receivePacket);
             
            System.out.println("\n [ metodo receber mensagens] Servidor diz: Recebi uma mensagem, iniciando o atendimento");
            Thread atendimentoAoCliente = new Thread(new DecodificadorDeAcoesDoServidor(receivePacket));
            atendimentoAoCliente.start();
        
        }
    }
    
    public void run() {
        try {
            receberMensagens();
        } catch (Exception ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
}
