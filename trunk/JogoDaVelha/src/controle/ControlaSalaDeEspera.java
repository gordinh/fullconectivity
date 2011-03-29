/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cliente;
import visual.SalaDeEspera2;

/**
 *
 * @author douglas
 */
public class ControlaSalaDeEspera implements ActionListener {

    private SalaDeEspera2 salaDeEspera;

    public ControlaSalaDeEspera(ArrayList<Cliente> array){
        
        salaDeEspera = new SalaDeEspera2(this, array);

    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == salaDeEspera.getConvidar()){

            /* Se verdadeiro trataremos dentro do if o envio do convite */
            if(salaDeEspera.lista.getSelectedValue() != null){

                System.out.println(salaDeEspera.lista.getSelectedValue());
            }
            else{
                JOptionPane.showMessageDialog(null, "Selecione um oponente da lista e clique no botão para desafiá-lo \n"
                        + "ou aguarde ser desafiado por outro jogador", "Erro", 0);
            }
            
            

        }

    }

    public SalaDeEspera2 getSalaDeEspera() {
        return salaDeEspera;
    }

    public void setSalaDeEspera(SalaDeEspera2 salaDeEspera) {
        this.salaDeEspera = salaDeEspera;
    }
    

}
