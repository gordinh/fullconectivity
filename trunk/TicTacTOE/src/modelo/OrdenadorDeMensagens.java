/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controle.StaticControlaJogador;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AndreLuiz
 */
public class OrdenadorDeMensagens implements Runnable {

    ArrayList<Mensagem> temp;

    public void run() {
        int indice = 0;
        int valor = 50;

        while (true) {
            valor = 50;
            temp = StaticControlaJogador.getInstance().retornaBufferDeMesagnsRecebidas();
            if (!temp.isEmpty()) {
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getNumeroDaMensasagem() <= valor) {
                        indice = i;
                        valor = temp.get(i).getNumeroDaMensasagem();
                    }
                }

                StaticControlaJogador.getInstance().atualizaJanelaChat(2, temp.get(indice).getEmissor(), "", temp.get(indice).getConteudo(), temp.get(indice).getHoraDaMensage());
                StaticControlaJogador.getInstance().removerDoBufferDeMensagensRecebidas(indice);
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(OrdenadorDeMensagens.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            


        }

    }
}
