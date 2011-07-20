/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controle.StaticControlaJogador;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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

            if (split[1].equalsIgnoreCase("ListaDeOponentes,")) {
                chamarOUatualizarSalaDeEspera(sentencaMod);
            } else if (split[1].equalsIgnoreCase("TeDesafio")) {
                recebiConvite(sentencaMod);
            } else if (split[1].equalsIgnoreCase("AceitoDesafio")) {
                oponenteAceitouDesafio(sentencaMod);
            } else if (split[1].equalsIgnoreCase("Jogada")) {
                jogadaDoOponente(sentencaMod);
            } else if (split[1].equalsIgnoreCase("RespostaLogin")){
                confirmacaoDoLogin(split[2]);
            } else if (split[1].equalsIgnoreCase("CadastroRealizadoComSucesso")){
                confirmacaoDoCadastro();
            }


        } else if (tipoDeDado == 1) {

            System.out.println(" \n [metodo decodificador] DecodificadorDeAcoesDoCliente diz, recebi uma string seu conteúdo é: " + controle);

            split = controle.split(":");

            if (split[1].equalsIgnoreCase("Login")) // FAZER LOGIN //
            {
                efetuarLogin(split[2], split[3]);
            } else if (split[1].equalsIgnoreCase("enviarConvite")) // ENVIAR CONVITE //
            {
                desafiarOponente(split[2]); // NA POSIÇÃO 2 DEVE ESTAR O NICK DO ADVERSÁRIO //
            }else if (split[1].equalsIgnoreCase("Cadastro")) // ENVIAR CONVITE //
            {
                String temp = ":novoCadastro:" + split[2] + ":" + split[3] + ":" + split[4];
                cadastroNoServidor(temp, split[5]); // NA POSIÇÃO 5 DEVE ESTAR O IP DO SERVER //
            }
               
        }


    }

    /**
     * Método responsável por enviar as informações do cliente para fazer login no servidor.
     * @param nick 
     */
    public void efetuarLogin(String nick, String senha) {


        try {
            String mensagem = ":" + "Login" + ":" + nick + ":" + senha + ":" + StaticControlaJogador.getInstance().getMeuIP();

            System.out.println("\n [metodo cadastro no servidor] Controla Jogador diz: Enviando soliticação de castastro no servidor."); // 10.65.98.134
            Thread fazLogin = new Thread(new EmissorUDP(mensagem, InetAddress.getByName(StaticControlaJogador.getInstance().getIPdoServidor()), 2495));
            fazLogin.start();

        } catch (UnknownHostException ex) {
            System.out.println("Erro em: ControlaJogador.CadastroNoServidor");
        }
    }

    /**
     * Este método trata a resposta do servidor quanto a solicitação de login do usuário.
     * O método esconde a janela de login caso as informações passadas tiverem corretas,
     * caso contrário, informa ao usuário que seu login ou senha podem estar errados.
     * 
     * @param validade 
     */
    public void confirmacaoDoLogin(String validade) {

        if (validade.equalsIgnoreCase("Valido")) {
            StaticControlaJogador.getInstance().escondeJanelaLogin(false);
        } else if (validade.equalsIgnoreCase("Invalido")) {
            JOptionPane.showMessageDialog(null, "Seu usuário ou senha nao estão corretos."
                    + "\n  Tente Novamente", "Login Inválido", 0);
        }

    }
    
    /**
     * Este método trata a resposta do servidor quanto a solicitação de cadastro do usuário.
     * O metodo deve informar ao usuário se o cadastro foi realizado com sucesso ou não.
     * 
     * 
     */
    public void confirmacaoDoCadastro() {
            
            JOptionPane.showMessageDialog(null, "Cadastro Realizado Com Sucesso", "Login Inválido", 0);

    }
    

    /**
     *  A função deste método é informar à "sala de espera" a situação dos oponentes, em relação ao seu status atual.
     *  
     * @param lista 
     */
    public void chamarOUatualizarSalaDeEspera(String lista) {

        StaticControlaJogador.getInstance().atualizaListaDeOponentes(lista);
        StaticControlaJogador.getInstance().chamaSalaDeEspera();

    }

    /**
     * Esse método é responsável por mostrar ao usuário que ele foi desafiado,quem o desafiou ,
     * e force ao mesmo opções de reposta ao convite.
     *
     * @param sentencaMod
     */
    public void recebiConvite(String sentencaMod) {

        String[] split = sentencaMod.split(":");


        int resp = JOptionPane.showConfirmDialog(null, split[2] + " desafiou você para uma partida. ", "Desfio", 0);

        if (resp == 0) { // Aceitei o desafio

            StaticControlaJogador.getInstance().adicionaNovaPartida(split[2], split[3], false);
            try {
                String control = ":AceitoDesafio:" + StaticControlaJogador.getInstance().getNick() + ":" + StaticControlaJogador.getInstance().getMeuIP();
                Thread aceiteiPartida = new Thread(new EmissorUDP(control, InetAddress.getByName(split[3]), 9090));
                aceiteiPartida.start();

            } catch (UnknownHostException ex) {
                Logger.getLogger(DecodificadorDeAcoesDoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (resp == 1) { // Neguei o desafio
            JOptionPane.showMessageDialog(null, "Eu não aceito o desafio ", "Informação", 1);
        }

    }

    /**
     * Este método é responsável por enviar uma solicitação de desafio a um oponente.
     *
     * @param nick
     */
    public void desafiarOponente(String nick) {

        System.out.println("[metodo desafiarOponente] DecodificadorDeAcoesDoCliente diz, Vou enviar o convite");

        Jogador temp = StaticControlaJogador.getInstance().retornaOponenteDaLista(nick);

        String ctrl = ":" + "TeDesafio:" + StaticControlaJogador.getInstance().getNick() + ":" + StaticControlaJogador.getInstance().getMeuIP();


        if (temp.getStatus() == 1) {
            try {

                //InetAddress ip = InetAddress.getByName(temp.getIp());

                Thread desafiaOponente = new Thread(new EmissorUDP(ctrl, InetAddress.getByName(temp.getIp()), 9090));
                desafiaOponente.start();

            } catch (UnknownHostException ex) {
                Logger.getLogger(DecodificadorDeAcoesDoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, nick + " está desconectado. \nVocê não pode "
                    + "desafiar jogadores desconectados!", "Informação", 1);
        }

        System.out.println("[metodo desafiarOponente] DecodificadorDeAcoesDoCliente diz, Convite enviado, aguardado resposta...");

    }

    /**
     * Se o oponente aceitar o desafio, esse método é chamado para instanciar
     * uma nova partida para quem fez o desafio.
     * 
     * @param sentencaMod
     */
    public void oponenteAceitouDesafio(String sentencaMod) {

        String[] split = sentencaMod.split(":");

        StaticControlaJogador.getInstance().adicionaNovaPartida(split[2], split[3], true);
    }

    /**
     * Método que propicia ao Decodificador de ações do cliente, informar ao controlador geral (StaticControlaJogador)
     * que ele deve atualizar uma janela.
     * 
     * @param sentencaMod 
     */
    public void jogadaDoOponente(String sentencaMod) {

        String[] split = sentencaMod.split(":");

        StaticControlaJogador.getInstance().atualizaJanelaJogo(split[2], split[3]);
    }

    public void cadastroNoServidor(String conteudo, String ipServer) {
        try {
            Thread cadastroNoServer = new Thread(new EmissorUDP(conteudo, InetAddress.getByName(ipServer), 2495));
            cadastroNoServer.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(DecodificadorDeAcoesDoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
