/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import javax.swing.JOptionPane;
import visual.SalaDeEspera2;




/**
 *
 * @author andre
 */
public class StartClient implements Runnable{

    public static void main(String[] args) {

        ControlaCliente controla = new ControlaCliente();
        Thread t = new Thread(controla);
        t.start();
            

    }

    public void run() {
        for(int i = 0; i == 5; i++){
            System.out.println(i);
        }
    }

}
