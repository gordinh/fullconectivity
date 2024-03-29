/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.ComunicadorUDP;
import modelo.Matriz;
import visual.JanelaJogo;

/**
 * Classe responsável por gerenciar todas interações entre jogador e aplicação.
 * @author andre
 */
public class ControlaJanelaJogo extends ComunicadorUDP implements Observer  {

    private JanelaJogo janela;
    private Matriz mat;
    private int round = 0;
    private DatagramSocket socketJogo;
    private Cliente oponente;
    private boolean fimRodada;

    public ControlaJanelaJogo(Cliente c) {

        oponente = c;
        janela = new JanelaJogo();
        janela.addObserver(this);
        janela.setText("Player 1 jogando...");

        mat = new Matriz();
        fimRodada = false;
    }

    public void update(Observable o, Object arg) {

        String ctrl = (String) arg;

        String controle = "#" +"|" + ctrl;

        try {
            enviaPacoteUDP(controle, InetAddress.getByName(oponente.getIp()), oponente.getPorta());
        } catch (UnknownHostException ex) {
            Logger.getLogger(ControlaJanelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        refresh(ctrl);

       fimRodada = true;

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
