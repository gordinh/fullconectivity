/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.Observable;
import java.util.Observer;
import modelo.Matriz;
import visual.JanelaPrincipal;

/**
 *
 * @author andre
 */
public class Controla_Janela_Principal implements Observer {

    JanelaPrincipal janela;
    Matriz mat;
    int round = 0;

    public Controla_Janela_Principal() {

        janela = new JanelaPrincipal();
        janela.addObserver(this);
        janela.setText("Player 1 jogando...");

        mat = new Matriz();
    }

    public void update(Observable o, Object arg) {

        String crtl = (String) arg;

       /* char a = crtl.charAt(0);
        char b = crtl.charAt(1);

        String a2 = Character.toString(a);
        String b2 = Character.toString(b);

        int i = Integer.parseInt(a2);
        int j = Integer.parseInt(b2);
        
        int enableRound = mat.checaPosicao(i, j); */

       // boolean enableRound = mat.checaBooleanMatriz2(crtl);

       

      //  if (enableRound == false) {

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
       // }




    }
}
