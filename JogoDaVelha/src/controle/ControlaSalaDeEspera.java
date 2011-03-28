/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.util.ArrayList;
import modelo.Cliente;
import visual.SalaDeEspera2;

/**
 *
 * @author douglas
 */
public class ControlaSalaDeEspera {

    /*private*/ public SalaDeEspera2 salaDeEspera;

    public ControlaSalaDeEspera(/*ControlaSalaDeEspera ctrl,*/ ArrayList<Cliente> array){
        salaDeEspera = new SalaDeEspera2(this, array);
    }

    
    

}
