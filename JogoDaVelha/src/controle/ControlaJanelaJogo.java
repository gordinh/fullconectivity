/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.Observable;
import java.util.Observer;
import modelo.Matriz;
import visual.JanelaJogo;

/**
 *
 * @author andre
 */
public class ControlaJanelaJogo implements Observer {

    private JanelaJogo janela;
    private Matriz mat;
    private int round = 0;

    public ControlaJanelaJogo() {

        janela = new JanelaJogo();
        janela.addObserver(this);
        janela.setText("Player 1 jogando...");

        mat = new Matriz();
    }

    public void update(Observable o, Object arg) {

        String crtl = (String) arg;     

            round++; // atualiza contador da partida

            janela.setButtonIcon(round, crtl); // atualiza a interface gráfica

            mat.atualizaMatriz(round, crtl); // atualiza a matriz de dados


            int player = round % 2;

            if (player == 0) {
                janela.setText("Player 1 jogando...");
            } else {
                janela.setText("Player 2 jogando...");
            }

            if (round >= 5) { // checa Vitória
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

}
