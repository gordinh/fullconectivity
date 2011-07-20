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
import javax.swing.JPasswordField;
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
    JButton configuracao;
    JButton cadastro;
    JTextField senha;

    public JanelaLogin(StaticControlaJogador ctrl) {
        mostraJanela(ctrl);
    }

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
    
    public JTextField getSenha() {
        return senha;
    }
    


    public void mostraJanela(StaticControlaJogador ctrlCLI){

       // Instaciação dos componentes
       frame = new JFrame("Jogo da Velha --Login");
       panel = new JPanel();
       logo = new JLabel();
       nick = new JTextField("usuario");
       enter = new JButton("Entrar");
       configuracao = new JButton();
       cadastro = new JButton("C");
       senha = new JPasswordField("senha");
       

       //Amarrando os componentes
       frame.getContentPane().add(panel);
       panel.add(logo);
       panel.add(nick);
       panel.add(enter);
       panel.add(configuracao);
       panel.add(cadastro);
       panel.add(senha); 

       //Propriedades da Janela
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(140, 90);
       frame.setSize(305, 508);
       frame.setVisible(true);
       frame.setResizable(false); //não maximiza a janela
       panel.setSize(305, 508);
       panel.setLayout(null);
       panel.setVisible(true);
       logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/tictactoe.png")));

       //Posicionamento dos Componentes
       logo.setBounds( 25, -35, 400, 330);
       nick.setBounds(100, 270, 100, 20);
       senha.setBounds(100, 300, 100, 20);
       enter.setBounds(110, 400, 85, 40);
       configuracao.setBounds(230, 430, 48, 42);
       configuracao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/Little-blue-gear-icon.png")));
       cadastro.setBounds(10, 430, 48, 42);

       //Adicionando disparador de eventos (ouvinte)
       enter.addActionListener(ctrlCLI);
       configuracao.addActionListener(ctrlCLI);
       cadastro.addActionListener(ctrlCLI);
       
    }

    /**
     * Retorna um JButon para verificação de disparo de evento
     * @return 
     */
    public JButton getEnter(){        
        return enter;
    }    

    /**
     * Metedo que altera a visiblidade da janela de Login.
     * Se o parametro, um booleano, for true a janela é exibida. 
     * Se for false, a janela é escondida. 
     * 
     * @param simOUnao 
     */
    public void visibilidadeDajanela(boolean simOUnao){
        
       frame.setVisible(simOUnao);
    }
    
    /**
     * Retorna um JButon para verificação de disparo de evento
     * @return 
     */
    public JButton getConfiguracao(){
        return configuracao;
    }
    
    public JButton getCadastro(){
        return cadastro;
    }
}
