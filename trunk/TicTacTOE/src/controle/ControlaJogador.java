/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.BancoTemporarioDePacotes;
import modelo.EmissorUDP;
import modelo.Jogador;
import modelo.ReceptorUDP;
import modelo.ServidorEspecifico;
import visual.JanelaLogin;
import visual.SalaDeEspera;

/**
 * http://www.javabeginner.com/learn-java/java-threads-tutorial/2
 *
 * @author andre
 */
public class ControlaJogador implements ActionListener {

    JanelaLogin jLogin;
    String nick = "";
    private ArrayList<Jogador> oponentes;
    SalaDeEspera sala;
    String oponenteSelecionado;
    int estadoPreJogo = -1;

    public ControlaJogador() {

      //  jLogin = new JanelaLogin(this);
        oponentes = new ArrayList<Jogador>();
        sala = null;

    }

    public void actionPerformed(ActionEvent e) {

        nick = jLogin.getNick().getText();

        if (e.getSource() == jLogin.getEnter()) {

            if (!nick.equalsIgnoreCase("") && nick.length() <= 8) {

                jLogin.Visible(false);
                cadastroNoServidor();

            } else {
                JOptionPane.showMessageDialog(null, "Seu nick deve conter de 1 a 8 caracteres", "Login Inválido", 0);
            }

        } else if (e.getSource() == sala.getConvidar()) {

            if (sala.lista.getSelectedValue() != null) {

                oponenteSelecionado = sala.lista.getSelectedValue().toString();

                try {
                    convidar();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ControlaJogador.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Selecione um oponente da lista e clique no botão para desafiá-lo \n"
                        + "ou aguarde ser desafiado por outro jogador", "Erro", 0);
            }

        }
    }

    public void cadastroNoServidor() {

        try {
            String mensagem = ":" + "Login" + ":" + nick + ":";

            System.out.println("\n [metodo cadastro no servidor] Controla Jogador diz: Enviando soliticação de castastro no servidor.");
            Thread envioPersistente = new Thread(new EmissorUDP(mensagem, InetAddress.getByName("192.168.0.146"), 2495));
            envioPersistente.start();

        } catch (UnknownHostException ex) {
            Logger.getLogger(ControlaJogador.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro em: ControlaJogador.CadastroNoServidor");
        }

        recebeListaConectados(9090);

    }

    /**
     * Este método é responável por receber a lista de jogadores conectados.
     * Criar a lista de oponentes local e chamar a sala de espera.
     * @param porta
     */
    public void recebeListaConectados(int porta) {

        System.out.println("\n [metodo recebe lista conectados] Controla Jogador diz: Estou aguardando a lista de jogadores conectados");


        Thread recepcaoPersistente = new Thread(new ReceptorUDP(porta));
        recepcaoPersistente.start();


        DatagramPacket listaEmpacotada = null;

        System.out.println("\n [metodo recebe lista conectados] Controla Jogador diz: Vou resgatar o pacote no banco");
        boolean condicao = true;
        while (condicao) {
            System.out.println(" \n [metodo recebe lista conectados] Controla Jogador diz: Aguardando recurso acesso ao banco");
            if (BancoTemporarioDePacotes.getInstance().acessoAoBanco() == 1) {
                System.out.println(" \n [metodo recebe lista conectados] Controla Jogador diz: Acesso ao banco concedido! Vou resgatar o pacote agora!");
                BancoTemporarioDePacotes.getInstance().semaforoUP();
                
                listaEmpacotada = BancoTemporarioDePacotes.getInstance().getPacote();

                BancoTemporarioDePacotes.getInstance().semaforoDOWN();

               condicao = false;
            }
            else{
                for(float i=0; i<100; i+=0.1){}
            }

        }

        String a = new String(listaEmpacotada.getData());
        System.out.println("\n [metodo recebe lista conectados] Controla Jogador diz: Resgatei o pacote, seu conteudo é:" + a);

        String[] aux = a.trim().split(",");

        for (int i = 0; i < aux.length; i++) {
            String[] u = aux[i].split(":");
            Jogador novoOponente = new Jogador(u[0], u[1], Integer.parseInt(u[2])); // Na ordem: u[0] = Nick, u[1] = ip e u[2] = porta
            oponentes.add(novoOponente);
        }


        chamaSala();

    }

    public void chamaSala() {

       // sala = new SalaDeEspera(this, oponentes);

        Thread jogo = new Thread(new ServidorEspecifico());
        jogo.start();

    }

    public void convidar() throws UnknownHostException {

        System.out.println("Oponente Selecionado" + oponenteSelecionado);

        Jogador temp = null;

        for (int i = 0; i <= oponentes.size(); i++) {
            if (oponentes.get(i).getNick().equalsIgnoreCase(oponenteSelecionado)) {
                temp = oponentes.get(i);
                break;
            }
        }

        String mensagem = "Desafio" + temp.getNick();


        Thread convidar = new Thread(new EmissorUDP(mensagem, InetAddress.getByName(temp.getIp()), 2011));
        convidar.start();
    }
}
