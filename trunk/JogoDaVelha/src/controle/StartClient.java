/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

/**
 *
 * @author andre
 */
public class StartClient{

    public static void main(String[] args) {

        ControlaCliente controla = new ControlaCliente();
        Thread t = new Thread(controla);
        t.start();            

    }
}
