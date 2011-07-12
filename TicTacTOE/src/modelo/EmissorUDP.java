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
public class EmissorUDP implements Runnable{

    public int count;
    InetAddress ip = null;
    int porta = 0;
    String conteudo;

    public EmissorUDP(String conteudo, InetAddress ip, int porta){
        count = 0;
        this.ip = ip;
        this.porta = porta;
        this.conteudo = conteudo;
    }
    
    public void run(){
        try {
            enviar(conteudo,ip, porta);

        } catch (Exception ex) {
            Logger.getLogger(EmissorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
       
  }

    public void enviar(String conteudo, InetAddress ip, int porta) throws Exception{

        String sentenca = conteudo.trim();

        System.out.println("\n [metodo enviar] EmissorUDP diz: Sou uma Thread!!! Fui chamado para enviar um pacote. Enviando...");

        DatagramSocket soqueteDoCliente = new DatagramSocket();
        InetAddress endIP = ip; //InetAddress.getByName("linux-wo7e");
        byte[] dadosDeEnvio = new byte[sentenca.length()];
        byte[] dadosDeRecepcao = new byte[1024];
        dadosDeEnvio = sentenca.getBytes();
        DatagramPacket pcteDeEnvio = new DatagramPacket(dadosDeEnvio, dadosDeEnvio.length, endIP, porta ); //2495);
        DatagramPacket pcteDeRecepcao = new DatagramPacket(dadosDeRecepcao, dadosDeRecepcao.length);
        do{            
            soqueteDoCliente.send(pcteDeEnvio);
            Thread.sleep(3000);
            soqueteDoCliente.receive(pcteDeRecepcao);
            String s = (new String(pcteDeRecepcao.getData()));
            s = s.trim();
            System.out.println("\n [metodo enviar] EmissorUDP diz, o destinatário disse: " + s);
            if(s.equals(":ok")){
                System.out.println("\n [metodo enviar] EmissorUDP diz: Destinatário confirmou a entrega da mensagem!");
                
                break;
            }
            count++;
        }while(count < 5);
        soqueteDoCliente.close();
    }

}
