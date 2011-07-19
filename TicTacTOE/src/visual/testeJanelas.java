/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual;

import controle.StaticControlaJogador;

/**
 *
 * @author douglas
 */
public class testeJanelas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JanelaChat jc = new JanelaChat(StaticControlaJogador.getInstance());
        jc.Visible(true);
    }

}
