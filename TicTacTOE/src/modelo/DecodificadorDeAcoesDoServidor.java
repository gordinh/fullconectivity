/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 * Essa classe tem o objetivo de decodificar todas a mensagens que chegarem ao servidor.
 * A ideia principal é desacoplar o estado de escuta (do servidor) do estado de envio de mensagens
 * aos clientes, maximiznado por tanto, o poder de escuta, processamento e envio de mensagens do servidor.
 * 
 * @author AndreLuiz
 */
public class DecodificadorDeAcoesDoServidor implements Runnable{

    DatagramPacket receivePacket;
    DatagramSocket tempSocket;
    
    public DecodificadorDeAcoesDoServidor(DatagramPacket pacote){
     this.receivePacket = pacote;
     
     //validaRecepção();
    }
    
    public void run() {
        decodificador();
    }
    
    /**
     * Esse método analisa a mensagem de chegada, confirma ao cliente sua chegada e retorna ao cliente o que ele solicitou.
     */    
    public void decodificador(){
        
        String sentencaMod = (new String(receivePacket.getData()).trim());
            System.out.println(" \n [metodo decodificador] DecodificadorDeAcoesDoServidor diz, recebi uma mensagem seu conteúdo é: " + sentencaMod);
            
            
            String [] split = sentencaMod.split(":");

            
            if(split[1].trim().equalsIgnoreCase("Login")){
                validaRecepção();
                BancoOnlineDoServidor.getInstance().cadastroNaLista(split[2].trim(), receivePacket.getAddress(), receivePacket.getPort());
                retornaListaAoCliente(receivePacket, split[2]);
            }
           // else if (split[1].trim().equalsIgnoreCase("Lista"))
            //    retornaLista(receivePacket, split[1]);
        
    }
    
    /**
     *  Avisa ao cliente que sua mensagem chegou
     */
    public void validaRecepção(){
        /*try {
            System.out.println("\n [metodo valida recepção] DecodificadorDeAcoesDoServidor diz: Mensgem válida! Vou avisar que chegou!");

            String Result = ":ok";
            InetAddress endIP = receivePacket.getAddress();
            int port = receivePacket.getPort();
            byte[] sendData = Result.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, endIP, port);
            tempSocket.send(sendPacket);
           

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            Thread valida = new Thread(new EmissorUDP(":ok", receivePacket.getAddress(), receivePacket.getPort()));
            valida.start();
    }
    
    /**
     *
     * Método para enviar a lista de pessoas conectadas
     * para alguma cliente que requisite ou quando é cadastrado nessa lista
     * @param dp
     * @param nick
     */
    public void retornaListaAoCliente(DatagramPacket dp, String nick){

           String lista = MontarStringJogadores(nick);

            System.out.println("\n [metodo retorna lista] DecodificadorDeAcoesDoServidor diz: lista montada: " + lista);

            System.out.println("\n [metodo retorna lista] DecodificadorDeAcoesDoServidor diz: vou retornar a lista para " + nick);
            Thread enviaLista = new Thread(new EmissorUDP(lista, dp.getAddress(), 9090));
            enviaLista.start();
            System.out.println(" \n [metodo retorna lista] DecodificadorDeAcoesDoServidor diz: Já chamei o EmissorUDP,"
                    + " em instantes ele executará e fará a entrega e confirmação da entrega");

    }

    /**
     *
     * Método para montar a String que será enviada para o cliente receptor
     * contendo os dados dos usuários conectados até então.
     * @param nick
     * @return String ja convertida em bytes
     */
    public String MontarStringJogadores(String nick){

        System.out.println("\n [metodo montar string jogadores] DecodificadorDeAcoesDoServidor diz: vou montar lista de jogadores conectados ");
        
        ArrayList<Jogador> listaDeRetorno = BancoOnlineDoServidor.getInstance().retornaListaDeCasdastrados();

        String s = ":ListaDeOponentes:";
        // A String enviada conterá os os seguintes dados:
        // Nick:Ip:Porta e uma virgula para separara usuarios direrentes
        // Ex.: João:10.65.99.33:5000, Douglas|10.65.128.75|2495, etc...

        for(int i = 0;i < listaDeRetorno.size(); i++){
            if(!listaDeRetorno.get(i).getNick().equalsIgnoreCase(nick))
                s = s + (listaDeRetorno.get(i).getNick())+ ":" + listaDeRetorno.get(i).getIp()+ ":" + listaDeRetorno.get(i).getPorta() + ",";

        }

        return s;
    }
    
}
