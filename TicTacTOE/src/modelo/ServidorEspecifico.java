/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import controle.ControlaPartida;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author andre
 */
public class ServidorEspecifico implements Runnable {

    public DatagramSocket serverSocket;
    DatagramPacket receivePacket;
    ControlaPartida janelaJogo;

    public ServidorEspecifico(){

        serverSocket = null;
        receivePacket  = null;
        janelaJogo =  null;
    }


    public void run() {

        try {

            receberMensagens();

        } catch (Exception ex) {
            Logger.getLogger(ServidorEspecifico.class.getName()).log(Level.SEVERE, null, ex);
        }

     }


    public void receberMensagens() throws Exception{

        String sentencaMod;
        serverSocket  = new DatagramSocket(2011);

        while (true) {

            byte[] receiveData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);

            System.out.println("\n [ metodo receber mensagens] Servidor Específico diz: Aguardando uma mensagem...");
            serverSocket.receive(receivePacket);

            sentencaMod = (new String(receivePacket.getData()).trim());
            System.out.println(" \n [metodo receber mensagens] Servidor Específico diz, recebi uma mensagem seu conteúdo é: " + sentencaMod);

           String [] split = sentencaMod.split(":");



           if(split[1].trim().equalsIgnoreCase("Desafio")){
               validaRecepção();
               fuiDesafiado(split[1],receivePacket);

           }
            else if(split[1].trim().equalsIgnoreCase("Aceito")){
                validaRecepção();
                desafioAceito();
                //janelaJogo = new ControlaPartida(receivePacket);
           }
            else if(split[1].trim().equalsIgnoreCase("Jogada")){
                validaRecepção();
                jogadaDoOponente(split[2]);
           }
            else if(split[1].trim().equalsIgnoreCase("Negado")){
                desafioNegado();
            }
        }

    }

    public void validaRecepção(){
        try {


            System.out.println("\n [metodo valida recepção] Servidor Específico diz: Mensgem válida! Vou avisar que chegou!");

            String Result = ":ok";
            InetAddress endIP = receivePacket.getAddress();
            int port = receivePacket.getPort();
            byte[] sendData = Result.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, endIP, port);
            serverSocket.send(sendPacket);


        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void fuiDesafiado(String nickOpp, DatagramPacket receive ){
         String mensagem = "Você recebeu um convite de jogo de " + nickOpp + "\n" + "Aceitar?";
        int i = JOptionPane.showConfirmDialog(null, mensagem, "Cofirmação de Desafio", 1);

         if (i == 0) {

             Thread desafio = new Thread(new EmissorUDP("Aceito", receivePacket.getAddress(), 2011));
             desafio.start();

            JOptionPane.showMessageDialog(null, " Você é o player 2 ", "Convite Aceito", 3);

            //EntrarNoJogo(t[1], desafio);

        } else {

           Thread desafio = new Thread(new EmissorUDP("Negado", receivePacket.getAddress(), 2011));
           desafio.start();


        }
    }

    public void desafioAceito(){

         JOptionPane.showMessageDialog(null, " Você é o player 1 ", "Convite Aceito", 3);
    }
    
    public void desafioNegado(){
         
        JOptionPane.showMessageDialog(null, " Parece que alguém não quer jogar... ", "Convite Negado", 3);
        
    }

    public void jogadaDoOponente(String jogada){

        janelaJogo.refresh(jogada);
        janelaJogo.getJanela().setEditFrame(true);
    }
}
