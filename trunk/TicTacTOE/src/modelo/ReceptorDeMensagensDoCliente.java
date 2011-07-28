/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta classe é responsável por receptar todas as mensagens que forem destinadas
 * ao cliente, e, encaminhá-las para o DecodificadorDeAcoesDoCliente que por sua vez
 * fará o tratamento correto da mensagem. 
 * 
 * @author AndreLuiz
 */
public class ReceptorDeMensagensDoCliente implements Runnable {

    DatagramSocket clientSocket;
    DatagramPacket receivedPacket;

    public ReceptorDeMensagensDoCliente() {
    }

    public void run() {
        try {
            receberMensagem();
        } catch (SocketException ex) {
            Logger.getLogger(ReceptorDeMensagensDoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receberMensagem() throws SocketException {

        clientSocket = new DatagramSocket(9090);


        while (true) {

            byte[] receiveData = new byte[1024];
            receivedPacket = new DatagramPacket(receiveData, receiveData.length);

            System.out.println("\n [ metodo receber mensagens] OuvidosDoCliente diz: Aguardando uma mensagem...");
            
            try {
                clientSocket.receive(receivedPacket);
            } catch (IOException ex) {
                Logger.getLogger(ReceptorDeMensagensDoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("\n [ metodo receber mensagens] OuvidosDoCliente diz: Recebi uma mensagem, iniciando o atendimento");
            Thread tratamentoDaMensagem = new Thread(new DecodificadorDeAcoesDoCliente(receivedPacket));
            tratamentoDaMensagem.start();

        }
    }
}
