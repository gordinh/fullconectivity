/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.net.DatagramPacket;

/**
 * Essa classe usa o padrão Singleton para armazenar temporariamente a informação de um pacote recebido
 * @author andre
 */
public class BancoTemporarioDePacotes {


private static BancoTemporarioDePacotes banco;

    private DatagramPacket pacoteTemporario;
    byte[] b;
    
    private int semaforoDoBanco = 0;


    private BancoTemporarioDePacotes(){

        b = new byte[1024];
        pacoteTemporario = new DatagramPacket(b, b.length);

    }


    public static BancoTemporarioDePacotes getInstance(){

        if(banco == null)
             banco = new BancoTemporarioDePacotes();
        return banco;
    }
    
    public int acessoAoBanco(){
        
        return semaforoDoBanco;
        
        /*if(semaforoDoBanco == 1){
            semaforoUP();}
        else
            return 0;
        return 1;*/
    }
    

    public synchronized DatagramPacket getPacote(){

        System.out.println("\n [metodo get pacote] Banco Temporário de Pacotes diz: Pacote resgatado, seu conteudo é "
                           + new String(pacoteTemporario.getData()).trim());
       semaforoUP(); //  O banco é binario, sempre que uma pacote é resgatado o banco fecha.
        return this.pacoteTemporario;
    }

    public synchronized void setPacote(DatagramPacket pkt){

        pacoteTemporario = pkt;
        System.out.println( "\n [metodo set pacote] Banco Temporário de Pacotes diz: O conteúdo do pacote é: "+new String(pacoteTemporario.getData()).trim());
        semaforoDOWN(); // O banco é reaberto sempre que um novo dado é deposistado.
        
    }
    
    /**
     * Pega o recurso de acesso ao banco
     */
    public void semaforoUP(){
        semaforoDoBanco = 0;
    }
    
    /**
     * Devolve o recurso de acesso ao banco
     */
    public void semaforoDOWN(){
        semaforoDoBanco = 1;
    }
            
    
}
