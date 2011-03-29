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

    /**
     *
     * Método para montar a String que será enviada para o cliente receptor
     * contendo os dados dos usuários conectados até então.
     * @param nick
     * @return String ja convertida em bytes
     */
    public byte[] MontarStringClientes(String nick){

        String s = new String();
        // A String no caso vai conter os seguintes dados:
        //Nick|Ip|Porta e uma virgula para separara usuarios
        //diferentes. Ex.: André|10.65.99.33|5000, Douglas|10.65.128.75|2495, etc...
        
        for(int i = 0;i < clientes.size(); i++){
            if(!clientes.get(i).getNick().equalsIgnoreCase(nick))
                s = s + (clientes.get(i).getNick())+ "|" + clientes.get(i).getIp()+ "|" + clientes.get(i).getPorta() + ",";
        }
        return s.getBytes();
    }

    /**
     *
     * Método para enviar a lista de pessoas conectadas
     * para alguma cliente que requisite
     * @param dp
     * @param nick
     */
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


    /**
     * Método para recebimento da mensagem
     * @return PacoteRecebido
     */
    public DatagramPacket receberMensagem(){

        byte[] receiveData = new byte[1024];

        try {
            
            serverSocket = new DatagramSocket(2495);

        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
        }

        DatagramPacket receivePacket = new DatagramPacket(receiveData,
                receiveData.length);

            try {
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        return receivePacket;
    }    
    
    /**
     *
     * Método de inicialização da rotina de recepção de dados do servidor
     *
     */
    public void run() {

        int estado = 0;
        String[] s = new String[1];
        DatagramPacket dp = null;

        while(true){

            switch(estado){

                case 0:
                    dp = receberMensagem();
                    s = LerMensagem(dp);
                    if(s[0].equals("Login")){
                        estado = 1;
                    } else if(s[0].equals("Lista")){
                        estado = 2;
                    }
                    break;
                case 1:
                    addNaLista(s[1], dp.getAddress(), dp.getPort());
                    retornaLista(dp, s[1]);
                    estado = 0;
                    break;
                case 2:
                    retornaLista(dp, s[1]);
                    estado = 0;
                    break;
            }
        }
   }

    /**
     * Método para receber a mensagem e quebrar com o split.
     * @param DatagramPacket PacoteRecebido
     * @return s
     */
    public String[] LerMensagem(DatagramPacket pacoteRecebido){

        String sentenca = (new String(pacoteRecebido.getData()));
        String[] s = new String[1];
        s = sentenca.split("|");
        return s;

    }    
}  