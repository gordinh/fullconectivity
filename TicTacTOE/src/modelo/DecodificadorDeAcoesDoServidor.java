/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Essa classe tem o objetivo de decodificar todas a mensagens que chegarem ao servidor.
 * A ideia principal é desacoplar o estado de escuta (do servidor) do estado de envio de mensagens
 * aos clientes, maximiznado por tanto, o poder de escuta, processamento e envio de mensagens do servidor.
 * 
 * @author AndreLuiz
 */
public class DecodificadorDeAcoesDoServidor implements Runnable {

    DatagramPacket receivePacket;
    DatagramSocket tempSocket;

    public DecodificadorDeAcoesDoServidor(DatagramPacket pacote) {
        this.receivePacket = pacote;

        //validaRecepção();
    }

    public void run() {
        decodificador();
    }

    /**
     * Esse método analisa a mensagem de chegada, confirma ao cliente sua chegada e retorna ao cliente o que ele solicitou.
     */
    public void decodificador() {

        String sentencaMod = (new String(receivePacket.getData()).trim());
        System.out.println(" \n [metodo decodificador] DecodificadorDeAcoesDoServidor diz, recebi uma mensagem seu conteúdo é: " + sentencaMod);


        String[] split = sentencaMod.split(":");


        if (split[1].trim().equalsIgnoreCase("novoCadastro")) {
            validaRecepção();
            realizaCadastro(split[2], split[3], split[4]);
        } else if (split[1].trim().equalsIgnoreCase("novoEscore")) {
            validaRecepção();
            BancoOnlineDoServidor.getInstance().atualizaEscore(split[2], split[3]);
        } else if (split[1].trim().equalsIgnoreCase("Login")) {
            validaRecepção();
            validaLogin(split[2], split[3], split[4]);
        } else if(split[1].trim().equalsIgnoreCase("verScore")){
            validaRecepção();
            System.out.println("[metodo decodificador] DecodificadorDeAcoesDoServidor diz, Recebi a solicitação de retornar escore"); 
            retornaClassificação(receivePacket.getAddress());
        }


    }

    /**
     *  Avisa ao cliente que sua mensagem chegou
     */
    public void validaRecepção() {

        System.out.println("Validando para ip: " + receivePacket.getAddress() + " e porta: " + receivePacket.getPort());

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
    public void retornaListaAoCliente(String ip, String nick) {

        String lista = MontarStringJogadores(nick);

        System.out.println("\n [metodo retorna lista de conectados] DecodificadorDeAcoesDoServidor diz: lista montada: " + lista);

        System.out.println("\n [metodo retorna lista de conectados] DecodificadorDeAcoesDoServidor diz: vou retornar a lista para " + nick);

        try {
            Thread enviaLista = new Thread(new EmissorUDP(lista, InetAddress.getByName(ip), 9090));
            enviaLista.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(DecodificadorDeAcoesDoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(" \n [metodo retorna lista de conectados] DecodificadorDeAcoesDoServidor diz: Já chamei o EmissorUDP,"
                + " em instantes ele executará e fará a entrega e confirmação da entrega");

    }

    /**
     * Monta a lista que será retornada a um jogador. O parametro entrada impede que um jogador tenha a sí proprio na sua lista de adversários.
     * A string retornada tem o seguinte formato ":PalavraDeControle:Nick:IP:Porta:Status:Pontuacao,".
     * O caracter dois pontos é usado para identificar cada atributo do jogador, e o caracter virgula é usado para identificar o final das informações referentes
     * a um jogaodor
     * 
     * @param nick
     * @return String lista
     */
    public String MontarStringJogadores(String nick) {

        System.out.println("\n [metodo montar string jogadores] DecodificadorDeAcoesDoServidor diz: vou montar lista de jogadores conectados ");

        ArrayList<Jogador> listaDeRetorno = BancoOnlineDoServidor.getInstance().retornaListaDeCasdastrados();

        String s = ":ListaDeOponentes,"; // a virgula aqui é importante visto que quando o cliente for remontar a lista ele precisa eliminar a palavra de controle //
        // A String enviada conterá os os seguintes dados:
        // Nick:Ip:Porta e uma virgula para separara usuarios direrentes
        // Ex.: João:10.65.99.33:5000, Douglas|10.65.128.75|2495, etc...

        for (int i = 0; i < listaDeRetorno.size(); i++) {
            if (!listaDeRetorno.get(i).getNick().equalsIgnoreCase(nick)) {
                s = s + ":" + (listaDeRetorno.get(i).getNick()) + ":" + listaDeRetorno.get(i).getIp() + ":" + listaDeRetorno.get(i).getPorta() + ":"
                        + listaDeRetorno.get(i).getStatus() + ":" + listaDeRetorno.get(i).getPontuacao() + ",";
            }

        }

        return s;
    }

    /**
     * Faz a verificação de usuário e senha no "banco" do servidor e responde ao 
     * usuário do programa se o dados informados são válidos ou não. Se as informações do usuário
     * estiverem corretas, o este método já chama o método que retorna a lista para o usuário.
     * 
     * @param nick
     * @param senha
     * @param ip 
     */
    public void validaLogin(String nick, String senha, String ip) {

        boolean valido = BancoOnlineDoServidor.getInstance().fazerLogin(nick, senha);

        Thread respostaLogin;
        if (valido) {
            String contole = ":RespostaLogin:Valido";
            //try {
            respostaLogin = new Thread(new EmissorUDP(contole, receivePacket.getAddress(), 9090)); //InetAddress.getByName(ip)
            respostaLogin.start();
            retornaListaAoCliente(ip, nick);
            /*} catch (UnknownHostException ex) {
            Logger.getLogger(DecodificadorDeAcoesDoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        } else {
            String contole = ":RespostaLogin:Invalido";
            try {
                respostaLogin = new Thread(new EmissorUDP(contole, InetAddress.getByName(ip), 9090));
                respostaLogin.start();
            } catch (UnknownHostException ex) {
                Logger.getLogger(DecodificadorDeAcoesDoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Este método cadastra um novo jogador no "banco" do servidor e informa 
     * ao usuário se o cadastro foi bem sucedido.
     * 
     * @param nick
     * @param senha
     * @param ip 
     */
    public void realizaCadastro(String nick, String senha, String ip) {

        boolean cadastroEfetuado = BancoOnlineDoServidor.getInstance().cadastroNaLista(nick, senha, ip);

        if (cadastroEfetuado) {
            try {
                Thread cadastroRealizado = new Thread(new EmissorUDP(":CadastroRealizadoComSucesso", InetAddress.getByName(ip), 9090));
                cadastroRealizado.start();
            } catch (UnknownHostException ex) {
                Logger.getLogger(DecodificadorDeAcoesDoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
            try {
                Thread cadastroRealizado = new Thread(new EmissorUDP(":LoginJaCadastrado", InetAddress.getByName(ip), 9090));
                cadastroRealizado.start();
            } catch (UnknownHostException ex) {
                Logger.getLogger(DecodificadorDeAcoesDoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
    }

    /**
     * Metodo responsável por retornar a lista de escore ao usuário
     */
    public void retornaClassificação(InetAddress ipRetorno) {
       
        System.out.println("\n [metodo retornar classificacao] DecodificadorDeAcoesDoServidor diz:  Pegar a lista dos jogadores");
           
        Jogador[] jogadorEpontuacao = new Jogador[BancoOnlineDoServidor.getInstance().tamanhoDoArrayDeCadastrados()];

        ArrayList<Jogador> copiaDaLista = BancoOnlineDoServidor.getInstance().retornaListaDeCasdastrados();
        
        
        for (int i = 0; i < copiaDaLista.size(); i++) {
            jogadorEpontuacao[i] = copiaDaLista.get(i);
            System.out.println("i =" + i);
        }

        System.out.println("\n [metodo retornar classificacao] DecodificadorDeAcoesDoServidor diz: Já montei o vetor de jogadores e agora vou ordená-lo ");
        // ORDENAÇÃO POR INSERTSORT
        int n = jogadorEpontuacao.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (jogadorEpontuacao[j - 1].getPontuacao() > jogadorEpontuacao[j].getPontuacao()) {
                    Jogador temp = jogadorEpontuacao[j];
                    jogadorEpontuacao[j] = jogadorEpontuacao[j - 1];
                    jogadorEpontuacao[j - 1] = temp;
                }
            }
        }

        String classificacao = ":Classificacao:";
        for (int k = 0; k < jogadorEpontuacao.length; k++) {
        // System.out.println("Score: " + jogadorEpontuacao[k].getNick() + jogadorEpontuacao[k].getPontuacao());
            classificacao = classificacao + "," + jogadorEpontuacao[k].getNick() + " -> " + jogadorEpontuacao[k].getPontuacao() + ",";
        }
        
        System.out.println("\n [metodo retornar classificacao] DecodificadorDeAcoesDoServidor diz:  O escore é: " + classificacao);
        
        Thread enviaClassificacao = new Thread(new EmissorUDP(classificacao, ipRetorno, 9090));
        enviaClassificacao.start();
        
        
    }
}
