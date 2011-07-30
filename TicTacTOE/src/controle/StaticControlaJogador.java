/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.DecodificadorDeAcoesDoCliente;
import modelo.EmissorUDP;
import modelo.Jogador;
import modelo.ReceptorDeMensagensDoCliente;
import visual.JanelaDePontuacao;
import visual.JanelaLogin;
import visual.SalaDeEspera;

/**
 * Esta classe tem a função de ouvir, processar e realizar ações com relação
 * a todos os eventos gerados pela interface gráfica.
 * Neste programa, só esta classe tem poder de alterar/modificar a interface gráfica.
 * Não era desejado que todas as vezes que fosse preciso modificar a interface gráfica,
 * uma instância desta classe fosse criada. Por isso, essa classe é estática e usa o padrão
 * SingleTon. 
 * Pela existência da concorrência, todos os seus métodos são sincronizados. Para que, dessa
 * forma, os recursos desejados sejam compartilhados de forma eficiênte.
 * 
 * @author AndreLuiz
 */
public class StaticControlaJogador implements ActionListener, WindowListener {

    private static StaticControlaJogador controladorEstatico;
    private JanelaLogin jLogin;
    private String MeuNick;
    private String senha;
    private ArrayList<Jogador> oponentes;
    private SalaDeEspera sala = null;
    private String[] oponenteSelecionado;
    private Thread escuta;
    private String IPdoServidor;
    private String meuIP;
    private ControlaPartida[] minhasPartidas;
    private ControlaChat[] meusChats;
    private boolean meuStatus;

    private StaticControlaJogador() {
        escuta = new Thread(new ReceptorDeMensagensDoCliente());
        escuta.start();
        oponentes = new ArrayList<Jogador>();
        MeuNick = "";
        senha = "";
        IPdoServidor = "192.168.0.146";
        meuIP = "192.168.0.146";
        meuStatus = false;

        inicializaVetorDePartidasEchat(10);
    }

    /**
     * Retorna uma instância do objeto estatico StaticControlaJogador
     * 
     * @return 
     */
    public static StaticControlaJogador getInstance() {

        if (controladorEstatico == null) {
            controladorEstatico = new StaticControlaJogador();
        }
        return controladorEstatico;

    }

    /**
     *  Metodo que instancia a janela de login
     */
    public synchronized void mostraJanelaDeLogin() {
        jLogin = new JanelaLogin(this);
    }

    /**
     * Método que instacia a janela da "sala de espera"
     */
    public synchronized void chamaSalaDeEspera() {
        
        if (sala == null) {
            System.out.println("\n[metodo chamaSalaDeEspera] Static controla jogador diz, não existia sala, vou chamar a sala de espera");
            sala = new SalaDeEspera(this, oponentes);
            
        } else {
            System.out.println("\n[metodo chamaSalaDeEspera] Static controla jogador diz, a sala existe vou atualizá-la.");
            sala.MontarListaDeConectados(oponentes);
            sala.refresh();
        }
    }

    /**
     * Esse método limpa a lista local, isto é, apaga todos os elementos dela e a
     * seguir preenche-a armazenando os novos dados.
     * 
     * A string recebida tem o seguinte formato ":PalavraDeControle:Nick:IP:Porta:Status:Pontuacao,".
     * O caracter dois pontos é usado para identificar cada atributo do jogador, e o caracter virgula é usado para identificar o final das informações referentes
     * a um jogaodor.
     * 
     */
    public synchronized void atualizaListaDeOponentes(String listaConcatenada) {

        System.out.println("\n[metodo atualizaListaDeOponentes] Static controla jogador diz, recebi uma nova lista de conectados");
        
        if (!oponentes.isEmpty()) {
            oponentes.clear(); // limpa a lista //
            System.out.println("\n[metodo atualizaListaDeOponentes] Static controla jogador diz, apaguei a lista velha. Vou adicionar os novos componentes");
        }
        
        String[] aux = listaConcatenada.trim().split(","); // cada posição do vetor contém informação de um oponente //

        for (int i = 1; i <= aux.length - 1; i++) { // 0ª célula do vetor: lixo, 1ª célula: palavra de controle, 2ª célua: primeiro oponente //
            String[] u = aux[i].split(":");
            // Na ordem: u[1] == NICK, u[2] == IP, u[3] == PORTA, u[4] == STATUS e u[5] == PONTUACAO //
            if (!u[1].equalsIgnoreCase(MeuNick)) {
                Jogador novoOponente = new Jogador(u[1], u[2], Integer.parseInt(u[3]));
                oponentes.add(novoOponente);
            }
        }
        System.out.println("\n[metodo atualizaListaDeOponentes] Static controla jogador diz, a lista foi atualizada!");
    }

    /**
     * Retorna um oponente da lista local mantida pelo cliente.
     * 
     * @param nick
     * @return 
     */
    public synchronized Jogador retornaOponenteDaLista(String nick) {
        Jogador oponente = null;

        for (int i = 0; i < oponentes.size(); i++) {
            String[] apenasNick = oponentes.get(i).getNick().split("-");
            if (apenasNick[0].equalsIgnoreCase(nick)) {
                oponente = oponentes.get(i);
                break;
            }
        }
        return oponente;
    }

    /**
     * Retorna o nick do jogador
     * @return 
     */
    public synchronized String getNick() {
        return MeuNick;
    }

    public synchronized String getIPdoServidor() {
        return IPdoServidor;
    }

    /**
     * Neste método é feita a indentificação e o tratamento inicial de todos os eventos 
     * gerados na interface gráfica do programa. A classe que faz o tratamento mais refinado é DecodificadorDeAcoesDoCliente.
     * 
     * @param e 
     */
    public void actionPerformed(ActionEvent e) {

        MeuNick = jLogin.getNick().getText();
        senha = jLogin.getSenha().getText();

        if (e.getSource() == jLogin.getEnter()) {

            if (!MeuNick.equalsIgnoreCase("") && !senha.equalsIgnoreCase("") && MeuNick.length() <= 8 && !IPdoServidor.equalsIgnoreCase("") && !meuIP.equalsIgnoreCase("")) {
                String controle = ":Login:" + MeuNick + ":" + senha + ":" + meuIP; // String de controle, indica qual ação deve ser executada pelo decodificador //

                Thread loginNoServidor = new Thread(new DecodificadorDeAcoesDoCliente(controle));
                loginNoServidor.start();

            } else {
                JOptionPane.showMessageDialog(null, "Um erro foi encontrado no procimento de login."
                        + "\n Verifique as seguites opções: \n "
                        + "\n * O campo nick não está vazio e tem menos que 8 caracteres; "
                        + "\n * O campo de senha não está vazio; "
                        + "\n * As configurações (IPs) foram armazenados (esse item é temporário);", "Falha no Login", 0);
            }

        } else if (e.getSource() == jLogin.getConfiguracao()) {

            IPdoServidor = JOptionPane.showInputDialog("Digite o IP do servidor: ");
            meuIP = JOptionPane.showInputDialog("Digite o IP do seu computador: ");

        } else if (e.getSource() == jLogin.getCadastro()) {

            if (MeuNick.equalsIgnoreCase("") || MeuNick.length() > 8 || senha.equalsIgnoreCase("") || IPdoServidor.equalsIgnoreCase("") || meuIP.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Um erro foi encontrado no procimento de cadastro."
                        + "\n Verifique as seguites opções: \n "
                        + "\n * O campo nick não está vazio e tem menos que 8 caracteres; "
                        + "\n * O campo de senha não está vazio; "
                        + "\n * As configurações (IPs) foram armazenados (esse item é temporário);"
                        + "\n \n OBS.: Para solicitar cadastro preencha os campos de usuário e senha.\n"
                        + "Clique no botão de cadastro e aguarde a confirmação.", "Falha no Login", 0);

            } else {
                String strCrtl = ":Cadastro:" + MeuNick + ":" + senha + ":" + meuIP + ":" + IPdoServidor;

                Thread solicitaCadastro = new Thread(new DecodificadorDeAcoesDoCliente(strCrtl));
                solicitaCadastro.start();
            }

        } else if (e.getSource() == sala.getConvidar()) {

            if (sala.lista.getSelectedValue() != null) {

                String temp = sala.lista.getSelectedValue().toString(); // VETOR COM 3 POSIÇOES: [ NICK | -> | ON/OFF ]
              /*  System.out.println("antes do split: "+temp);
                
                oponenteSelecionado=temp.split("-");
                
                System.out.println("Depois do split P0:"+ oponenteSelecionado[0] + "P1:" + oponenteSelecionado[1]);*/

                String controle = ":enviarConvite:" + temp;//oponenteSelecionado[0];

                Thread convidarOponente = new Thread(new DecodificadorDeAcoesDoCliente(controle));
                convidarOponente.start();

            } else {
                JOptionPane.showMessageDialog(null, "Selecione um oponente da lista e clique no botão para desafiá-lo \n"
                        + "ou aguarde ser desafiado por outro jogador", "Erro", 0);
            }
        } else if (e.getSource() == sala.getMensagem()) {

            if (sala.lista.getSelectedValue() != null) {

                String[] selected = sala.lista.getSelectedValue().toString().split("-");
                String temp[] = verificaChatAberto(selected[0]).split(":");

                if (temp[0].equalsIgnoreCase("false")) {

                    Jogador j = retornaOponenteDaLista(selected[0]);
                    criaJanelaChat(selected[0], j.getIp());

                } else if (temp[0].equalsIgnoreCase("true")) {

                    meusChats[Integer.parseInt(temp[1])].janela.Visible(true);

                }
            }

        } else if (e.getSource() == sala.getScore()) {
            //JOptionPane.showMessageDialog(null, "Quero ver a pontuação", "Info", 1);

            Thread verClassificacao = new Thread(new DecodificadorDeAcoesDoCliente(":verScore"));
            verClassificacao.start();

        }
    }
    
    /**
     * Retorna o ip do jogador
     * @return 
     */
    public String getMeuIP() {
        return meuIP;
    }

    /**
     * Inicializa o vetor de partida do jogador
     * @param tamVetor 
     */
    private void inicializaVetorDePartidasEchat(int tamVetor) {
        minhasPartidas = new ControlaPartida[tamVetor];
        meusChats = new ControlaChat[tamVetor];

        for (int i = 0; i < tamVetor; i++) {
            minhasPartidas[i] = null;
            meusChats[i] = null;
        }


    }

    /**
     * Cria uma nova partida e adiciona no vetor de partidas do jogador.
     * 
     * @param nickOponente
     * @param ipOponente
     * @param euComeco 
     */
    public void adicionaNovaPartida(String nickOponente, String ipOponente, boolean euComeco) {

        ControlaPartida novaPartida = new ControlaPartida(nickOponente, ipOponente, euComeco);

        for (int i = 0; i <= minhasPartidas.length; i++) {
            if (minhasPartidas[i] == null) {
                minhasPartidas[i] = novaPartida;
                if (euComeco) {
                    JOptionPane.showMessageDialog(null, "Você é o jogador 1. \nFaça sua jogada", "Informação", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Você é o jogador 2. \nAguarde sua rodada", "Informação", 1);
                }
                break;
            }
        }
    }

    /**
     * Atualiza uma partida com a informação do oponente
     *
     * @param nick
     * @param coordenada
     */
    public void atualizaJanelaJogo(String nick, String coordenada) {

        for (int i = 0; i <= minhasPartidas.length; i++) {
            if (minhasPartidas[i].getNickOponente().equalsIgnoreCase(nick)) {
                minhasPartidas[i].refresh(coordenada);
                break;
            }
        }

    }

    /**
     * Atualiza a janela de chat ou cria uma nova janela de chat quando uma mensagem de chat é envida pelo oponente.
     * 
     * @param nickOpnt
     * @param msg
     */
    public void atualizaJanelaChat(int quem, String nickOpnt, String ipOpnt, String msg) {

        String verificaExistencia[] = verificaChatAberto(nickOpnt.concat("-> ON")).split(":");

        if (verificaExistencia[0].equalsIgnoreCase("true")) {

            meusChats[Integer.parseInt(verificaExistencia[1])].janela.refresh(quem, msg);
            meusChats[Integer.parseInt(verificaExistencia[1])].janela.Visible(true);
            meusChats[Integer.parseInt(verificaExistencia[1])].janela.janelaSinalizaQuandoChegaMensagemNova();

        } else if (verificaExistencia[0].equalsIgnoreCase("false")) {
            int local = criaJanelaChat(nickOpnt.concat("-> ON"), ipOpnt);
            meusChats[local].janela.refresh(quem, msg);
        }


    }

    public void escondeJanelaLogin(boolean simOUnao) {
        jLogin.visibilidadeDajanela(simOUnao);
    }

    /**
     * 
     * 
     * @param classificacao 
     */
    public void mostraClassificacao(String classificacao) {

        System.out.println("[metodo mostrarClassificacao] StaticControlaJogador diz, Vou exibir a tela de pontuacao...");

        String[] aux = classificacao.trim().split(","); // cada posição do vetor contém informação de um oponente //

        JanelaDePontuacao ranking = new JanelaDePontuacao(aux);
        ranking.setVisible(true);

    }

    public ControlaChat[] getMeusChats() {
        return meusChats;
    }

    /**
     * Metodo destinado a criar uma janala de chat.
     * Se já existir uma janela com um oponente qualquer,
     * uma nova janela não deve ser criada.
     * 
     * @param nickOponente
     * @param ipOpnente 
     */
    public int criaJanelaChat(String nickOponente, String ipOponente) {

        String temp[] = verificaChatAberto(nickOponente).split(":");
        int posicaoDaNovaJanelaDeChat = -1;

        if (temp[0].equalsIgnoreCase("false")) {
            for (int i = 0; i < meusChats.length - 1; i++) {
                if (meusChats[i] == null) {
                    meusChats[i] = new ControlaChat(nickOponente, ipOponente);
                    posicaoDaNovaJanelaDeChat = i;
                    break;
                }
            }
        }
        return posicaoDaNovaJanelaDeChat;
    }

    /**
     * Procura no array de chats se eu (o usuário) já tem 
     * uma janela de chat com um determinado oponente.
     * 
     * @param nickOponente
     * @return 
     */
    public String verificaChatAberto(String nickOponente) {

        String deRetorno;
        boolean existe = false;
        int posicaoDaJanelaChatNoArray = -1;


        for (int i = 0; i < meusChats.length - 1 && existe == false; i++) {

            if (meusChats[i] != null) {
                String[] apenasNick = meusChats[i].getNickOponente().split("-");
                if (apenasNick[0].equalsIgnoreCase(nickOponente)) {
                    posicaoDaJanelaChatNoArray = i;
                    existe = true;
                    break;
                }
            }
        }

        if (existe) {
            deRetorno = "true:" + posicaoDaJanelaChatNoArray;
        } else {
            deRetorno = "false:";
        }
        return deRetorno;
    }

    
    //********************************************************* Controle do fechamento da janela ************************************************
    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
   
        JOptionPane.showMessageDialog(null, "Fechando sistema", "info", 1);
        
        //System.exit(0);
        String desconectar = ":desconectar:" + MeuNick;
        
        try {
            Thread ficarOFF = new Thread(new EmissorUDP(desconectar, InetAddress.getByName(IPdoServidor), 2495));
            ficarOFF.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(StaticControlaJogador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
       
    }
//*****************************************************************************************************************************************************************
  
    /**
     * Retorna (localmente) o meu status.
     * 
     * @return 
     */
    public boolean getMeuStatus() {
        return meuStatus;
    }
    
    /**
     * Atualiza (localemnte) se estou online ou offline.
     * 
     * @param meuStatus 
     */
    public void setMeuStatus(boolean meuStatus) {
        this.meuStatus = meuStatus;
    }
    
    
}
