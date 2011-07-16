/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.DecodificadorDeAcoesDoCliente;
import modelo.Jogador;
import modelo.ReceporDeMensagensDoCliente;
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
public class StaticControlaJogador implements ActionListener{
    
    private static StaticControlaJogador controladorEstatico;
    private JanelaLogin jLogin;
    private String nick = "";
    private ArrayList<Jogador> oponentes;
    private SalaDeEspera sala = null;
    private String oponenteSelecionado;
    private Thread escuta;
    private String IPdoServidor;
    private String meuIP;
    private ControlaPartida[] minhasPartidas;
 
    
    private StaticControlaJogador(){
        escuta = new Thread(new ReceporDeMensagensDoCliente());
        escuta.start();
        oponentes = new ArrayList<Jogador>();
        
        inializaVetorDePartidas(10);
    }
     /**
     * Retorna uma instância do objeto estatico StaticControlaJogador
     * 
     * @return 
     */   
    public static StaticControlaJogador getInstance(){
        
        if( controladorEstatico == null)
           controladorEstatico  = new StaticControlaJogador();
        return controladorEstatico;
        
    }
    
    /**
     *  Metodo que instancia a janela de login
     */
    public synchronized void mostraJanelaDeLogin(){
        jLogin = new JanelaLogin(this);
    }
    
    /**
     * Método que instacia a janela da "sala de espera"
     */
    public synchronized void chamaSalaDeEspera(){
        sala = new SalaDeEspera(this, oponentes);
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
    public synchronized void atualizaListaDeOponentes(String listaConcatenada){
       
       // if(oponentes != null)
            //oponentes.clear(); // limpa a lista //
        
        String[] aux = listaConcatenada.trim().split(","); // cada posição do vetor contém informação de um oponente //

        for (int i = 1; i <= aux.length-1; i++) { // 0ª célula do vetor: lixo, 1ª célula: palavra de controle, 2ª célua: primeiro oponente //
            String[] u = aux[i].split(":");
                                  // Na ordem: u[1] == NICK, u[2] == IP, u[3] == PORTA, u[4] == STARUS e u[5] == PONTUACAO //
            Jogador novoOponente = new Jogador(u[1], u[2], Integer.parseInt(u[3]), Integer.parseInt(u[4]), Integer.parseInt(u[5])); 
            oponentes.add(novoOponente);
        }
        
    }
    
    
    /**
     * Retorna um oponente da lista local mantida pelo cliente.
     * 
     * @param nick
     * @return 
     */
    public synchronized Jogador retornaOponenteDaLista(String nick){
        Jogador oponente = null;

        for(int i=0; i<= oponentes.size(); i++){
            if(oponentes.get(i).getNick().equalsIgnoreCase(nick) ){
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
    public synchronized String getNick(){
        return nick;
    }
    
    public synchronized String getIPdoServidor(){
        return IPdoServidor;
    }

    
    /**
     * Neste método é feita a indentificação e o tratamento inicial de todos os eventos 
     * gerados na interface gráfica do programa. A classe que faz o tratamento mais refinado é DecodificadorDeAcoesDoCliente.
     * 
     * @param e 
     */
     public void actionPerformed(ActionEvent e) {

        nick = jLogin.getNick().getText();

        if (e.getSource() == jLogin.getEnter()) {

            if (!nick.equalsIgnoreCase("") && nick.length() <= 8) {

                jLogin.Visible(false);
                
                String controle = ":Login:"+ nick + ":"; // String de controle, indica qual ação deve ser executada pelo decodificador //
                
                Thread loginNoServidor = new Thread(new DecodificadorDeAcoesDoCliente(controle));
                loginNoServidor.start();

            } else {
                JOptionPane.showMessageDialog(null, "Seu nick deve conter de 1 a 8 caracteres", "Login Inválido", 0);
            }

        }else if(e.getSource() == jLogin.getConfiguracao()) {
            
            IPdoServidor = JOptionPane.showInputDialog("Digite o IP do servidor: ");
            //System.out.println("IPdoServidor " + IPdoServidor);
            meuIP = JOptionPane.showInputDialog("Digite o IP do seu computador: ");

                        
        } else if (e.getSource() == sala.getConvidar()) {

            if (sala.lista.getSelectedValue() != null) {

                oponenteSelecionado = sala.lista.getSelectedValue().toString();
                
                String controle =  ":enviarConvite:" + oponenteSelecionado;

                Thread convidarOponente = new Thread(new DecodificadorDeAcoesDoCliente(controle));
                convidarOponente.start();
                

            } else {
                JOptionPane.showMessageDialog(null, "Selecione um oponente da lista e clique no botão para desafiá-lo \n"
                        + "ou aguarde ser desafiado por outro jogador", "Erro", 0);
            }

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
    private void inializaVetorDePartidas(int tamVetor) {
       minhasPartidas  = new ControlaPartida[tamVetor];
        
       for(int i=0; i < minhasPartidas.length; i++){
           minhasPartidas[i] = null;
       }
           
    }
    
    /**
     * Cria uma nova partida e adiciona no vetor de partidas do jogador.
     * 
     * @param nickOponente
     * @param ipOponente
     * @param euComeco 
     */
    public void adicionaNovaPartida(String nickOponente, String ipOponente, boolean euComeco){
        
        ControlaPartida novaPartida = new ControlaPartida(nickOponente, ipOponente, euComeco);
        
        for(int i=0; i <= minhasPartidas.length; i++){
            if(minhasPartidas[i] == null){
                minhasPartidas[i] = novaPartida;
                break;
            }
       }
    }
    
}
