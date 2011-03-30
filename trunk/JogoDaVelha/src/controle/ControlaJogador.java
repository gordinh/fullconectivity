
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
import modelo.ComunicadorUDP;
import visual.JanelaLogin;


/**
 *
 * Essa classe deve gerenciar todas as ações do jogador. gerindo o fluxo
 * dos dados entre doig jogadores e entre o servidor.
 *
 * @author andre
 */
public class ControlaJogador extends ComunicadorUDP implements ActionListener, Runnable {

    JanelaLogin jLogin;
    private DatagramSocket socket;
    private String nick;
    private ArrayList<Cliente> clientes;
    private boolean emJogo;
    private ControlaSalaDeEspera controlaSalaDeEspera;
    private ControlaJanelaJogo controlaJanelaJogo;
    private Cliente oponente;
    private boolean convidei;

    public ControlaJogador() {

        clientes = new ArrayList<Cliente>();
        emJogo = false;
        oponente = null;
        login();
    }

    /**
     * Esse métdo chama a janela de login, início da aplicação do usuário.
     */
    public void login() {

        jLogin = new JanelaLogin(this);

    }

    /**
     * Esse método é o resposável por checar se o evento disparado foi o botão
     * da jenela de login. Caso verdadeiro pega-se as informações do usuário e as
     * verifica se são válidas. Sendo essas informações válidas, são enviadas ao servidor.
     * 
     * @param e
     */
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
     * Metodo que fará a conexão do usuário com o servidor.
     *
     */
    public void conectaServidor() {

        montarPacote();
        cadastrarNaLista();

    }

    /**
     * Método para montagem do pacote a ser enviado ao servidor, requisitando
     * ser adicionado na lista de conectados.
     */
    public void montarPacote() {

        InetAddress serverip = null;
        try {

            // Pôr nome do servidor
            serverip = InetAddress.getByName("douglas-desktop"/*nome da máquina*/);

            // Setando o socket para mandar mensagem para o servidor.
            socket = new DatagramSocket(5000, serverip);

        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o servidor", "Server Não Localizado", 0);
        } catch (SocketException se) {
            JOptionPane.showMessageDialog(null, "Verificar criação do Socket", "Erro no Socket", 0);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método encarregado de enviar a requisição de adição à lista de conectados ao servidor.
     *
     */
    public void cadastrarNaLista() {

        String mensagem = "Login" + "|" + nick;

        try {
            enviaPacoteUDP(mensagem, InetAddress.getByName("server"), 5000);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível enviar sua mensagem ao servidor", "Erro na Requisição", 0);
        }

    }

    /**
     * Esse método run() que será rodado na thread, conterá um escalonador de pacotes.
     * Tal escalonador será reponsável por receber, decodificar e chamar os métodos apropriados
     * para cada tipo possível de mensagem.
     *
     */
    public void run() {

        DatagramPacket pacote = new DatagramPacket(new byte[1024], 1024);
        int estado = 0;
        String[] t = null;

        while (true) {
            
            //t = lerMensagem(pacote);

            //estado = codificaPalavraChave(t[0]);
            if(controlaSalaDeEspera.isConvidou())
                estado = 6;

            switch (estado) { // Escalonador de Pacotes

                case 0:

                    estado = super.recebePacoteUDP();
                    break;

                case 1: { // 1. Desafio

                    verificarDesafio(t[1], pacote);
                    estado = 0;
                    break;
                }
                case 2: { // 2. Lista

                    receberListaDeConectados(pacote);
                    estado = 0;
                    break;
                }
                case 3: { // 3. Começar Jogo
                    
                    break;
                }
                case 4: { // 4. Aceito

                    JOptionPane.showMessageDialog(null, " Você é o player 1 ", "Convite Aceito", 3);
                    convidei = true;
                    entrarNoJogo(oponente.getNick(), pacote);                    
                    estado = 0;
                    break;

                }
                case 5: { // 5. Negado

                    JOptionPane.showMessageDialog(null, " Parece que alguém não quer jogar... ", "Convite Negado", 3);
                    controlaSalaDeEspera.setConvidou(false);
                    estado = 0;
                    break;
                }

                case 6: //6. Desafiar

                    Cliente aux = procurarCliente(controlaSalaDeEspera.getOponenteSelecionado());
                
                    try {
                    estado = desafiar(InetAddress.getByName(aux.getIp()), aux.getPorta());
                    } catch (UnknownHostException ex) {
                    Logger.getLogger(ControlaJogador.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }

        }
    }

    /**
     * Este método faz uma requisição ao servidor de uma nova lista de jogadores
     * conectados. Esté método deve ser utilizado para atualização da lista de conectados
     * existente local e dincamicamente num aplicativo.
     *
     */
    public void requisitarLista() {

        String mensagem = "Lista" + "|" + nick;

        try {
            enviaPacoteUDP(mensagem, InetAddress.getByName("server"), 5000);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro no Envio da requisição da lista", "Erro na Requisição", 0);
        }
    }

    /**
     * Esse método recebe a lista de jogadores conectados que veio do servidor,
     * recria a mesma lista para o aplicativo e chama a tela de sala de espera, a qual
     * recebe como parâmetro a lista de conectadados.
     * 
     * @param receivePacket
     */
    public void receberListaDeConectados(DatagramPacket receivePacket) {

        if (!clientes.isEmpty()) { // No caso do usuário ja possuir uma lista, a zera e refaz toda.
            clientes.clear();
        }

        String s = new String(receivePacket.getData());

        String[] t = s.split(","); // O tamanho desse vetor vai ser igual a quantidade de pessoas conectadas ao servidor

        for (int i = 0; i < t.length; i++) {
            String[] u = t[i].split("|");
            Cliente c = new Cliente(u[0], u[1], Integer.parseInt(u[2])); // Na ordem: u[0] = Nick, u[1] = ip e u[2] = porta
            clientes.add(c);
        }

        controlaSalaDeEspera = new ControlaSalaDeEspera(clientes);

    }

    /**
     * Esse método faz o tratamento dos convites que chegam para o usuário do aplicativo.
     * Detectado que chegou uma mensagem de convite, o aplicativo pergunta ao usuário deseja aceitar
     * e a seguir, devolve a reposta para quem o convidou.
     */
    public void verificarDesafio(String nickOpp, DatagramPacket packet) {

        String mensagem = "Você recebeu um convite de jogo de " + nickOpp + "\n" + "Aceitar?";
        int i = JOptionPane.showConfirmDialog(null, mensagem, "Cofirmação de Desafio", 1);

        if (i == 0) {
            enviaPacoteUDP("Aceito", packet.getAddress(), packet.getPort());

            JOptionPane.showMessageDialog(null, " Você é o player 2 ", "Convite Aceito", 3);
            controlaSalaDeEspera.getSalaDeEspera().visible(false);
            
            //EntrarNoJogo(t[1], desafio);

        } else {

            enviaPacoteUDP("Negado", packet.getAddress(), packet.getPort());
            
        }


    }

    /**
     * Esse método é utilizado para enviar a mensagem de desafio ao jogador selecionado
     * na sala de espera.
     * 
     * @param ia
     * @param porta
     */
    public int desafiar(InetAddress ia, int porta) {

        String mensagem =  "Desafio" + "|" + nick;

        return enviaPacoteUDP(mensagem, ia, porta);
        
    }

    public void entrarNoJogo(String nick, DatagramPacket desafio) {

        int estado = 0;

        switch(estado){

            case 0: // Estado para configuração dos parâmetros iniciais
                
                if(convidei){

                oponente = new Cliente("", desafio.getAddress().getHostAddress(), desafio.getPort());
                emJogo = true;
                controlaJanelaJogo = new ControlaJanelaJogo(oponente);
                controlaSalaDeEspera.setConvidou(false);
                estado = 1;
                }
                else{
            
                //Algoritmo de quem foi convidado

                emJogo = true;
                controlaJanelaJogo = new ControlaJanelaJogo(oponente);
                oponente = new Cliente(nick, desafio.getAddress().getHostAddress(), desafio.getPort());
                controlaJanelaJogo.getJanela().setEditFrame(false);
                estado = 2;
                }
                break;

            case 1: //Estado do jogador 1, ou seja, a interface espera a jogada.
                // Detectar botao pressionado e mandar coordenadas para o outro jogador

                boolean sai = true;

                while(sai)
                {
                    if(controlaJanelaJogo.isFimRodada())
                        sai = false;
                }
                estado = 2;
                controlaJanelaJogo.setFimRodada(false);
                break;

            case 2: // Estado do jogador 2, ou seja, aguardando jogada do oponente.

                DatagramPacket temp = recebePacoteUDPJogo();

                String [] msg = lerMensagem(temp);

                if(msg[0].trim().equalsIgnoreCase("#")){

                    controlaJanelaJogo.refresh(msg[1]);
                }


                estado = 1;
                break;
        }
    }    


    /**
     * Método para recepção do pacote UDP que estiver vindo, e saber o que fazer depois, setando
     * a váriavel de estado corresponde ao código da instrução.
     * @return
     */    
    
    public DatagramPacket recebePacoteUDPJogo() {

        
        byte[] dadosDeRecepcao = new byte[1024];
        byte[] dadosDeEnvio = new byte[1024];
        String s = null;
        String[] t = null;

        DatagramPacket pacoteRecepcao = new DatagramPacket(dadosDeRecepcao, dadosDeRecepcao.length);
        
            try {
                
            socket.receive(pacoteRecepcao);
            
            s = (new String(pacoteRecepcao.getData()));
            s = s.trim();
            t = lerMensagem(pacoteRecepcao);

            InetAddress endIP = pacoteRecepcao.getAddress();
            int porta = pacoteRecepcao.getPort();

            DatagramPacket pacoteEnvio = new DatagramPacket(dadosDeEnvio, dadosDeEnvio.length, endIP, porta);
            socket.send(pacoteEnvio);

            } catch (IOException ex) {
            Logger.getLogger(ControlaJogador.class.getName()).log(Level.SEVERE, null, ex);

            }

        return pacoteRecepcao;
    }

    public Cliente procurarCliente(String nick){

        for(int i = 0 ;i < clientes.size(); i++)
            if(clientes.get(i).getNick().equals(nick))
                return clientes.get(i);

        return null;
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

    public boolean isConvidei() {
        return convidei;
    }

    public void setConvidei(boolean convidei) {
        this.convidei = convidei;
    }

}

/* // backup

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
}*/
