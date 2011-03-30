/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual;


import controle.ControlaJogador;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    JTextField nick;
    JButton enter;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getLogo() {
        return logo;
    }

    public void setLogo(JLabel logo) {
        this.logo = logo;
    }

    public JTextField getNick() {
        return nick;
    }

    public void setNick(JTextField nick) {
        this.nick = nick;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JanelaLogin (ControlaJogador ctrlCLI){

        mostraJanela(ctrlCLI);
    }


    public void mostraJanela(ControlaJogador ctrlCLI){

       // Instaciação dos componentes
       frame = new JFrame("Jogo da Velha --Login");
       panel = new JPanel();
       logo = new JLabel();
       nick = new JTextField();
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
        return enter;
    }    

    public void Visible(boolean b){
        
       frame.setVisible(b);
    }
}
