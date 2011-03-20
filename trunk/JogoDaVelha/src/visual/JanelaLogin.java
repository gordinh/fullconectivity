/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual;

import controle.ControlaCliente;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Essa classe é resposável por mostrar o usuário a opção de fazer login no jogo.
 * Ela deve retornar o login fornecido pelo usuário.
 *
 * @author andre
 */
public class JanelaLogin {

    JFrame frame;
    JPanel panel;
    JLabel logo;
    JTextArea nick;
    JButton enter;
   public String nickk;

    public JanelaLogin (ControlaCliente ctrlCLI){

        mostraJanela(ctrlCLI);
    }


    public void mostraJanela(ControlaCliente ctrlCLI){

       // Instaciação dos componentes
       frame = new JFrame("Jogo da Velha --Login");
       panel = new JPanel();
       logo = new JLabel();
       nick = new JTextArea();
       enter = new JButton("Entrar");

       //Amarrando os componentes
       frame.getContentPane().add(panel);
       panel.add(logo);
       panel.add(nick);
       panel.add(enter);

       //Propriedades da Janela
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(140, 90);
       frame.setSize(305, 408);
       frame.setVisible(true);
       frame.setResizable(false); //não maximiza a janela
       panel.setSize(305, 408);
       panel.setLayout(null);
       panel.setVisible(true);
       logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/tictactoe.png")));

       //Posicionamento dos Componentes
       logo.setBounds( 25, -35, 400, 330);
       nick.setBounds(100, 270, 100, 20);
       enter.setBounds(110, 300, 85, 40);

       //Adicionando disparador de eventos (ouvinte)
       enter.addActionListener(ctrlCLI);
       
    }

    public JButton getEnter(){
        InternalGetNick();
        return enter;
    }

    private void InternalGetNick(){
        nickk = nick.getText();
    }

    public String getNick(){
        return nickk;
    }

    public void Visible(boolean b){
        
       frame.setVisible(b);
    }
}
