/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual;

import controle.StaticControlaJogador;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author aluno
 */
public class JanelaChat {
    JFrame frame;
    JPanel panel;    
    JTextField historico;
    JTextField mensagem;
    JButton send;

    public JanelaChat(StaticControlaJogador ctrl) {
        mostraJanela(ctrl);
    }

    public void mostraJanela(StaticControlaJogador ctrlCLI){

       // Instaciação dos componentes
       frame = new JFrame("Jogo da Velha --Chat");
       panel = new JPanel();
       historico = new JTextField();
       mensagem = new JTextField();
       send = new JButton("Enviar");

       //Amarrando os componentes
       frame.getContentPane().add(panel);
       
       panel.add(mensagem);
       panel.add(historico);
       panel.add(send);

       //Propriedades da Janela
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(140, 90);
       frame.setSize(305, 350);
       frame.setVisible(true);
       frame.setResizable(false); //não maximiza a janela
       panel.setSize(305, 350);
       panel.setLayout(null);
       panel.setVisible(true);
       

       //Posicionamento dos Componentes       
       historico.setBounds( 10, 10, 280, 200);

       mensagem.setBounds(10, 215, 280, 80);
       send.setBounds(110, 295, 80, 20);

       //Adicionando disparador de eventos (ouvinte)
       send.addActionListener(ctrlCLI);

    }

    public JButton getSend(){
        return send;
    }

    public void Visible(boolean b){

       frame.setVisible(b);
    }
}
