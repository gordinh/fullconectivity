/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author douglas
 */
public class Servidor implements Runnable{

    private ArrayList<Cliente> clientes;
    private String ip;
    private int porta;
    private DatagramSocket serverSocket;

    public Servidor(){

       clientes = new ArrayList<Cliente>();
        try {
            ip = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
        porta = 5000;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public void addNaLista(String nick, InetAddress addr, int porta){

        Cliente c = new Cliente(nick, addr.toString(), porta);
        clientes.add(c);
    }

    public byte[] MontarStringClientes(String nick){

        String s = new String();

        for(int i = 0;i < clientes.size(); i++){
            if(!clientes.get(i).getNick().equalsIgnoreCase(nick))
                s = s + (clientes.get(i).getNick())+ "|" + clientes.get(i).getIp()+ "|" + clientes.get(i).getPorta() + ",";
        }
        return s.getBytes();
    }

    public void retornaLista(DatagramPacket dp, String nick){

            //Depois de adicionar na lista, responde com o retorno da lista atualizada
            
            byte[] sendData = new byte[MontarStringClientes(nick).length];
            sendData = MontarStringClientes(nick);
            InetAddress endIP = dp.getAddress();
            int port = dp.getPort();            
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, endIP, port);
            
            try {
                serverSocket.send(sendPacket);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }      

    }
    public void run() {
        
        String sentencaMod;

        byte[] receiveData = new byte[1024];        

        try {

            serverSocket = new DatagramSocket(2495);

        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
        }
        
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
            sentencaMod = (new String(receivePacket.getData()));
            System.out.println(sentencaMod);

            String[] s = new String[1];
            s =  sentencaMod.split("|");

            if(s[0].equalsIgnoreCase("Login")){

                addNaLista(s[1], receivePacket.getAddress(), receivePacket.getPort());
                retornaLista(receivePacket, s[1]);
            }

            else if(s[0].equalsIgnoreCase("Lista")){
                    retornaLista(receivePacket, s[1]);
        }
      }
   }
}

    

