/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual;


import controle.ControlaSalaDeEspera;
import java.awt.Color;
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
   public JList lista;
    DefaultListModel itens;
    private ArrayList<Cliente> clientes;
    public JScrollPane scrollpane;
    JLabel logo, info;
    JButton convidar;

    public SalaDeEspera2(ControlaSalaDeEspera ctrl, ArrayList conectados){
        clientes = new ArrayList<Cliente>();
        clientes = conectados;
        MontarListaDeConectados();
        mostraJanela(ctrl);
    }

    public void mostraJanela(ControlaSalaDeEspera ctrl){
       
       frame = new JFrame("Jogo da Velha --Sala de Espera");
       panel = new JPanel();     
       logo = new JLabel();
       convidar = new JButton();
       info = new JLabel("Desafie alguém =>");

       frame.getContentPane().add(panel);
       lista = new JList(itens);       
       scrollpane = new JScrollPane(lista);

       //Propriedades da Janela
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(140, 90);
       frame.setSize(350, 408);
       frame.setVisible(true);
       frame.setResizable(false); //não maximiza a janela
       panel.setSize(305, 408);
       panel.setLayout(null);
       panel.setVisible(true);      
       
       lista.setBounds(10, 10, 300, 30);
       panel.add(scrollpane);
       panel.add(logo);
       panel.add(convidar);
       panel.add(info);
       panel.setBackground(Color.white);

       scrollpane.setBounds(210, 100, 130, 90);
       scrollpane.setToolTipText("Escolha se adversário");

       logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/sala.jpg")));
       logo.setBounds( -2, -10, 400, 330);

       convidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/cabo3.png")));
       convidar.setBounds(220, 280, 110, 90);
       convidar.setToolTipText("Clique para convidar");

       convidar.addActionListener(ctrl);

       info.setBounds(80, 300, 200, 70);
       


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

    /**
     * Esse método retorna o botão de convite a fim de verificar a validade do
     * evento disparado na sala de espera.
     */
    public JButton getConvidar(){

        return convidar;
    }


}
