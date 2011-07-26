/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author douglas
 */
public class ReceptorUDP implements Runnable {

    public int count;
    public DatagramSocket serverSocket;
    public DatagramPacket receivePacket;
    private int portaDeChegada;

    public ReceptorUDP(int porta) {
        count = 0;
        this.portaDeChegada = porta;
    }

    public void ReceberMensagens(int porta) throws Exception {

        String sentencaMod;
        serverSocket = new DatagramSocket(porta);
        
         // BancoTemporarioDePacotes.getInstance().semaforoUP();

        System.out.println("\n [metodo receber mensagens] ReceptorUPD diz: Sou uma Thread!!! Fui chamado para rebecer uma mensagem. Aguardando...");

        while (true) {


            byte[] receiveData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println(new String(receivePacket.getData()));
            serverSocket.receive(receivePacket);
            sentencaMod = (new String(receivePacket.getData()));

            System.out.println("\n [metodo receber mensagens] ReceptorUDP diz: Recebi uma mensagem seu contéudo é:" + sentencaMod.trim());
            String sm = sentencaMod.trim();

            System.out.println("\n [metodo receber mensagens] ReceptorUPD diz: Vou confrimar a chegada da mensagem!");
            String Result = ":ok";
            InetAddress endIP = receivePacket.getAddress();
            int port = receivePacket.getPort();
            byte[] sendData = Result.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, endIP, port);
            serverSocket.send(sendPacket);


            repassaPacote(receivePacket);

            if (sm != null) {
                System.out.println("\n [metodo receber mensagens] ReceptorUPD diz: Saindo do while(true)");
                break;
            }
        }
    }

    public void run() {
        try {
            ReceberMensagens(portaDeChegada);

        } catch (Exception ex) {
            Logger.getLogger(ReceptorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void repassaPacote(DatagramPacket receivePkt) {

        System.out.println("\n [metodo repassa pacote] ReceptorUDP diz: Vou repassar o pacote recebido para o banco de pacotes");

        System.out.println("\n [metodo repassa pacote] ReceptorUDP diz: O conteúdo do pacote é :" + new String(receivePkt.getData()).trim());

       // boolean condicao = true;
        //while (condicao) {
          //  if (BancoTemporarioDePacotes.getInstance().acessoAoBanco() == 1) {
                //BancoTemporarioDePacotes.getInstance().semaforoUP();
                BancoTemporarioDePacotes.getInstance().setPacote(receivePkt);
                //BancoTemporarioDePacotes.getInstance().semaforoDOWN();
            //    condicao = false;
          //  }
        //}
        System.out.println("\n [metodo repassa pacote] ReceptorUDP diz: Pacote repassado");



    }
}
