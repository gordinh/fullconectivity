/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import modelo.Servidor;

/**
 *
 * @author douglas
 */
public class StartServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Servidor s = new Servidor();
        Thread t = new Thread(s);
        t.start();
    }

}
