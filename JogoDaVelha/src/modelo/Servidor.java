/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author douglas
 */
public class Servidor implements Runnable{

    private ArrayList<Cliente> clientes[];
    private String ip;
    private int porta;

    public Servidor(){

       clientes = new ArrayList[20];
        try {
            ip = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
        porta = 5000;
    }

    public ArrayList<Cliente>[] getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente>[] clientes) {
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

    public void run() {
        String sentencaMod, Result;
        DatagramSocket serverSocket = null;
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

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
            Result = "Roger!!";
            InetAddress endIP = receivePacket.getAddress();
            int port = receivePacket.getPort();
            sendData = Result.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, endIP, port);
            try {
                serverSocket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

    

