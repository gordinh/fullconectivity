/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controle.StaticControlaJogador;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Essa classe tem o objetivo de decodificar todas a mensagens que chegarem ao cliente.
 * A ideia principal é desacoplar o estado de escuta (do cliente) do estado de envio de mensagens
 * ao servidor e aos clientes, maximiznado por tanto, o poder de escuta, processamento e envio de mensagens do cliente.
 * Quando a mensagem for decodificada, a interação com o usuário se dará por meio do controlador de jogador 
 * (que será estático), pois é ele que tem permições para alterar a interface gráfica. 
 * 
 * @author AndreLuiz
 */
public class DecodificadorDeAcoesDoCliente implements Runnable {

    private DatagramPacket receivedPacket;
    private String controle;
    private int tipoDeDado;

    public DecodificadorDeAcoesDoCliente(DatagramPacket receivedPacket) {
        this.receivedPacket = receivedPacket;
        tipoDeDado = 0; // Dado que chegou foi um pacote
        
    }

    public DecodificadorDeAcoesDoCliente(String controle) {
        this.controle = controle;
        tipoDeDado = 1; // Dado que chegou foi uma string
    }

    public void run() {
        decodificador();
    }

    public void decodificador() {
        String[] split = null;

        if (tipoDeDado == 0) {
            String sentencaMod = (new String(receivedPacket.getData()).trim());
            System.out.println(" \n [metodo decodificador] DecodificadorDeAcoesDoCliente diz, recebi um pacote seu conteúdo é: " + sentencaMod);
            split = sentencaMod.split(":");
            
            if(split[1].equalsIgnoreCase("ListaDeOponentes,")){
                chamarOUatualizarSalaDeEspera(sentencaMod);
            }
            
        } else if (tipoDeDado == 1) {
            
            System.out.println(" \n [metodo decodificador] DecodificadorDeAcoesDoCliente diz, recebi uma string seu conteúdo é: " + controle);
            
            split = controle.split(":");
            
            if (split[1].equalsIgnoreCase("Login")) { // FAZER LOGIN
                
                efetuarLogin(split[2]);
            }
        }

    }

    /**
     * Método responsável por enviar as informações do cliente para fazer login no servidor.
     * @param nick 
     */
    public void efetuarLogin(String nick) {


        try {
            String mensagem = ":" + "Login" + ":" + nick + ":";

            System.out.println("\n [metodo cadastro no servidor] Controla Jogador diz: Enviando soliticação de castastro no servidor.");
            Thread envioPersistente = new Thread(new EmissorUDP(mensagem, InetAddress.getByName("10.65.98.11"), 2495));
            envioPersistente.start();

        } catch (UnknownHostException ex) {
            System.out.println("Erro em: ControlaJogador.CadastroNoServidor");
        }
    }
    
    /**
     * A função deste método é informar à "sala de espera" a situação dos oponentes, em relação ao seu status atual.
     */
    public void chamarOUatualizarSalaDeEspera(String lista){
        
        StaticControlaJogador.getInstance().atualizaListaDeOponentes(lista);
        StaticControlaJogador.getInstance().chamaSalaDeEspera();
        
    }
           
}
