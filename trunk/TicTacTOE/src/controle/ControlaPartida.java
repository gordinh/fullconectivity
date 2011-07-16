/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.EmissorUDP;
import modelo.Matriz;
import visual.JanelaJogo;

/**
 * Classe responsável por escutar a interface gráfica da partida propriamente dita.
 * 
 * @author andre
 */
public class ControlaPartida implements Observer  {

    private JanelaJogo janela;
    private Matriz mat;
    private int round = 0;
    private String nickOponente;
    private String ipOponente;
    private boolean fimRodada;
    private boolean euComeco;
    

    public ControlaPartida(String nick, String ip, boolean euComeco) {
               
        this.nickOponente = nick;
        this.ipOponente = ip;
        this.euComeco = euComeco;
        
        janela = new JanelaJogo(nickOponente, euComeco);
        janela.addObserver(this);
        janela.setText("Player 1 jogando...");

        mat = new Matriz();
        fimRodada = false;
    }

    public void update(Observable o, Object arg) {

       String ctrl = (String) arg;

       String controle = ":Jogada:" + StaticControlaJogador.getInstance().getNick() + ":" +ctrl;

       Thread enviaJogada;
        try {
            enviaJogada = new Thread(new EmissorUDP(controle, InetAddress.getByName(ipOponente) , 9090));
            enviaJogada.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ControlaPartida.class.getName()).log(Level.SEVERE, null, ex);
        }
       

       refresh(ctrl);
     
    }

    public void refresh(String coordenada){

        round++; // atualiza contador da partida
        
        janela.setButtonIcon(round, coordenada); // atualiza a interface gráfica

        mat.atualizaMatriz(round, coordenada); // atualiza a matriz de dados

        int player = round % 2;

        if (player == 0) {
            janela.setText("Player 1 jogando...");
        } else {
            janela.setText("Player 2 jogando...");
        }

        janela.setEditFrame(false);
        testaVitoria();
    }

    public void testaVitoria(){

        if (round >= 5) { 
            int win = mat.calculaVitoria();

            if (win == 3) {
                janela.setEditFrame(false);
                janela.setText("Fim de Jogo: Player 1 Venceu!");
            } else if (win == 6) {
                janela.setEditFrame(false);
                janela.setText("Fim de Jogo: Player 2 Venceu!");
            } else if (round >= 9) {
                janela.setEditFrame(false);
                janela.setText("Fim de Jogo: Empate");
            }

        }
    }






    public JanelaJogo getJanela() {
        return janela;
    }

    public void setJanela(JanelaJogo janela) {
        this.janela = janela;
    }

    public Matriz getMat() {
        return mat;
    }

    public void setMat(Matriz mat) {
        this.mat = mat;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isFimRodada() {
        return fimRodada;
    }

    public void setFimRodada(boolean fimRodada) {
        this.fimRodada = fimRodada;
    }

}
