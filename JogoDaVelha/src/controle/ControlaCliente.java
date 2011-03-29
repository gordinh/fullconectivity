/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Cliente;
import visual.JanelaLogin;

/**
 *
 * Essa classe deve gerenciar todas as ações do jogador. gerindo o fluxo
 * dos dados entre doig jogadores e entre o servidor.
 *
 * @author andre
 */
public class ControlaCliente implements ActionListener, Runnable {

    JanelaLogin jLogin;
    private DatagramSocket socket;
    private String nick;
    private ArrayList<Cliente> clientes;
    private boolean emJogo;
    private ControlaSalaDeEspera controlaSalaDeEspera;
    private ControlaJanelaJogo controlaJanelaJogo;
    private Cliente oponente;

    public ControlaCliente() {

        clientes = new ArrayList<Cliente>();
        emJogo = false;
        oponente = null;
        login();
    }

    public void login() {

        jLogin = new JanelaLogin(this);

    }

    public void inicio(){
        DatagramPacket desafio = new DatagramPacket(new byte[1024], 1024);

        while (true) {

            try {
                socket.receive(desafio);
            } catch (IOException ex) {
                Logger.getLogger(ControlaCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = new String(desafio.getData());

            String[] t = s.split("|");

        }
    }

    public void run() {

        DatagramPacket desafio = new DatagramPacket(new byte[1024], 1024);
        int estado = 0;

        switch(estado){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;

        }

        while (true) {

            try {
                socket.receive(desafio);
            } catch (IOException ex) {
                Logger.getLogger(ControlaCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = new String(desafio.getData());

            String[] t = s.split("|");



        }
    }

    public void actionPerformed(ActionEvent e) {

        nick = jLogin.getNick().getText();

        if (e.getSource() == jLogin.getEnter()) {

            if (!nick.equalsIgnoreCase("") && nick.length() <= 8) {

                jLogin.Visible(false);
                conectaServidor();

            } else {

                JOptionPane.showMessageDialog(null, "Seu nick deve conter de 1 a 8 caracteres", "Login Inválido", 0);
            }
        }
    }

    /**
     * Metodo que fará a conexão do usuário com o servidor
     * 
     */
    public void conectaServidor() {

        montarPacote();
        cadastrarNaLista();
        receberLista();
        //verificarDesafio();
        //entrarNoJogo();

    }

    /**
     * Método para montagem do pacote a ser enviado ao servidor, requisitando
     * para ser adicionado na lista
     */
    public void montarPacote() {

        InetAddress serverip = null;
        try {

            // Pôr nome do servidor
            serverip = InetAddress.getByName("linux-wo7e");

            // Setando o socket para mandar mensagem para o servidor.
            socket = new DatagramSocket(5000, serverip);

            // System.out.println(serverip.toString());

            System.out.println("Conectando \"" + nick + "\" ao server...");

        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o servidor", "Server Não Localizado", 0);
        } catch (SocketException se) {
            JOptionPane.showMessageDialog(null, "Verificar criação do Socket", "Erro no Socket", 0);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método encarregado de enviar a requisição para entrar na lista
     */
    public void cadastrarNaLista() {

        String pacote = "Login" + "|" + nick;

        byte[] b = new byte[pacote.getBytes().length];
        b = pacote.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), 5000);
            socket.send(packet);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro no Envio da requisição da lista", "Erro na Requisição", 0);
        }
    }

    public void requisitarLista() {

        String pacote = "Lista" + "|" + nick;

        byte[] b = new byte[pacote.getBytes().length];
        b = pacote.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), 5000);
            socket.send(packet);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro no Envio da requisição da lista", "Erro na Requisição", 0);
        }
    }

    public void receberLista() {

        if (!clientes.isEmpty()) { // No caso do usuário ja possuir uma lista, a zera e refaz toda.
            clientes.clear();
        }

        byte[] b = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(b, b.length);

        try {
            socket.receive(receivePacket);
        } catch (IOException ex) {
            Logger.getLogger(ControlaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        String s = new String(receivePacket.getData());

        String[] t = s.split(","); // O tamanho desse vetor vai ser igual a quantidade de pessoas conectadas ao servidor

        for (int i = 0; i < t.length; i++) {
            String[] u = t[i].split("|");
            Cliente c = new Cliente(u[0], u[1], Integer.parseInt(u[2])); // Na ordem: u[0] = Nick, u[1] = ip e u[2] = porta
            clientes.add(c);
        }
        //Devolver o cliente selecionado ou receber o desafio
        controlaSalaDeEspera = new ControlaSalaDeEspera(clientes);

    }

    /**
     * Esse método faz o tratamento dos convites que chegam para o usuário do aplicativo.
     * Detectado que chegou uma mensagem de convite, o aplicativo pergunta ao usuário deseja aceitar
     * e a seguir, devolve a reposta para quem o convidou.
     */
    public void verificarDesafio(String nickOpp, DatagramPacket packet) {


        DatagramPacket desafio = new DatagramPacket(new byte[1024], 1024);

        desafio = packet;



        String mensagem = "Você recebeu um convite de jogo de " + nickOpp + "\n" + "Aceitar?";
        int i = JOptionPane.showConfirmDialog(null, mensagem, "Cofirmação de Desafio", 1);

        if (i == 0) {

            byte[] b = new byte[1024];
            b = "Aceito".getBytes();
            DatagramPacket pacoteEnvio = new DatagramPacket(b, b.length, desafio.getAddress(), desafio.getPort());

            try {
                socket.send(pacoteEnvio);
            } catch (IOException ex) {
                Logger.getLogger(ControlaCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            //EntrarNoJogo(t[1], desafio);

        } else {

            byte[] b = new byte[1024];
            b = "Negado".getBytes();
            DatagramPacket pacoteEnvio = new DatagramPacket(b, b.length, desafio.getAddress(), desafio.getPort());

            try {
                socket.send(pacoteEnvio);
            } catch (IOException ex) {
                Logger.getLogger(ControlaCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }

    public void Desafiar(InetAddress ia, int porta) {

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024, ia, porta);
        try {
            socket.send(dp);
        } catch (IOException ex) {
            Logger.getLogger(ControlaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        //verificarDesafio();
    }

    public void entrarNoJogo(String nick, DatagramPacket desafio) {

        //Algoritmo de quem convidou
        try {
            socket.receive(desafio);
        } catch (IOException ex) {
            Logger.getLogger(ControlaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] sentenca = LerMensagem(desafio);
        if (sentenca[0].equals("Aceito")) {
            oponente = new Cliente(sentenca[1], desafio.getAddress().getHostAddress(), desafio.getPort());
            controlaJanelaJogo = new ControlaJanelaJogo();
        } else if (sentenca[0].equals("Negado")) {
        }

        //Algoritmo de quem foi convidado
        emJogo = true;
        controlaJanelaJogo = new ControlaJanelaJogo();
        oponente = new Cliente(nick, desafio.getAddress().getHostAddress(), desafio.getPort());
        controlaJanelaJogo.getJanela().setEditFrame(false);

    }

    /**
     * Método para receber a mensagem e quebrar com o split.
     * @param DatagramPacket PacoteRecebido
     * @return s
     */
    public String[] LerMensagem(DatagramPacket pacoteRecebido) {

        String sentenca = (new String(pacoteRecebido.getData()));
        String[] s = new String[1];
        s = sentenca.split("|");
        return s;

    }

    public void escalonadorDePacotes(DatagramPacket packet) {
    }

    /**
     * Este método codificará as palavras-chaves da aplicação com a finalidade de usar tais números
     * no case do metodo
     * @param mensagem
     * @return
     */
    public int codificaMensagem(String mensagem) {

        // 1. Desafio // 2. Lista // 3. Jogada       

        if (mensagem.equalsIgnoreCase("Login")) {
            return 1;
        } else if (mensagem.equalsIgnoreCase("Lista")) {
            return 2;
        } else if (mensagem.equalsIgnoreCase("Jogada")) {
            return 3;
        }
        
        //Retorna zero no caso de erro
        return 0;
    }

    public DatagramSocket getClienteSocket() {
        return socket;
    }

    public void setClienteSocket(DatagramSocket clienteSocket) {
        this.socket = clienteSocket;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean isEmJogo() {
        return emJogo;
    }

    public void setEmJogo(boolean emJogo) {
        this.emJogo = emJogo;
    }

    public JanelaLogin getjLogin() {
        return jLogin;
    }

    public void setjLogin(JanelaLogin jLogin) {
        this.jLogin = jLogin;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
