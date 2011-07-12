/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import modelo.Servidor;

/**
 *
 * @author andre
 */
public class StartServer {

     public static void main(String[] args){

         Thread serverThread = new Thread(new Servidor());
         serverThread.start();

     }

}
