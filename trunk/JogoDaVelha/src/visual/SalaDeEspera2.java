/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual;


import controle.ControlaSalaDeEspera;
import java.util.ArrayList;
import javax.swing.*;
import modelo.Cliente;

/**
 *
 * @author douglas
 */
public class SalaDeEspera2 {

    JFrame frame;
    JPanel panel;
    JList lista;
    DefaultListModel itens;
    private ArrayList<Cliente> clientes;

    public SalaDeEspera2(ControlaSalaDeEspera ctrl, ArrayList conectados){
        clientes = new ArrayList<Cliente>();
        clientes = conectados;
        MontarListaDeConectados();
        mostraJanela(/*ctrlCli*/);
    }

    public void mostraJanela(){
       
       frame = new JFrame("Jogo da Velha --Sala de Espera");
       panel = new JPanel();     

       frame.getContentPane().add(panel);
       lista = new JList(itens);       

       //Propriedades da Janela
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(140, 90);
       frame.setSize(305, 408);
       frame.setVisible(true);
       frame.setResizable(false); //não maximiza a janela
       panel.setSize(305, 408);
       panel.setLayout(null);
       panel.setVisible(true);      
       
       lista.setBounds(10, 10, 300, 30);
       panel.add(lista);

    }

    /**
     *
     * Método para preencher a lista de conectados
     */
    public void MontarListaDeConectados(){

        itens = new DefaultListModel();
        for(int i = 0; i < clientes.size(); i++ ){
            itens.addElement(clientes.get(i).getNick());
        }
    }

    public void visible(boolean b){

        frame.setVisible(b);
    }


}
