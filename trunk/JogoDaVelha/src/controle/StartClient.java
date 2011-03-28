/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.util.ArrayList;
import modelo.Cliente;

/**
 *
 * @author andre
 */
public class StartClient{

    public static void main(String[] args) {
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

       Cliente um = new Cliente("um", "192.168.0.0", 2020);
       Cliente dois = new Cliente("dois", "192.168.0.1", 2020);
       Cliente tres = new Cliente("tres", "192.168.0.2", 2020);
       Cliente quatro = new Cliente("quatro", "192.168.0.3", 2020);
       Cliente cinco = new Cliente("cinco", "192.168.0.3", 2020);
       Cliente seis = new Cliente("seis", "192.168.0.3", 2020);
       Cliente sete = new Cliente("sete", "192.168.0.3", 2020);
       Cliente oito = new Cliente("oito", "192.168.0.3", 2020);
       Cliente nove = new Cliente("nove", "192.168.0.3", 2020);
       Cliente dez = new Cliente("dez", "192.168.0.3", 2020);

       clientes.add(um);
       clientes.add(dois);
       clientes.add(tres);
       clientes.add(quatro);
       clientes.add(cinco);
       clientes.add(seis);
       clientes.add(sete);
       clientes.add(nove);
       clientes.add(dez);
       
       
        ControlaSalaDeEspera controlaSala = new ControlaSalaDeEspera(clientes);
             
        System.out.println(controlaSala.salaDeEspera.lista.getSelectedValue());
        
       /* ControlaCliente controla = new ControlaCliente();
        Thread t = new Thread(controla);
        t.start(); */

    }
}
