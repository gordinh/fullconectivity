/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual;

import controle.ControlaChat;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author aluno
 */
public class JanelaChat extends Observable{


    JFrame frame;
    JPanel panel;    
    JTextArea historico;
    JTextField mensagem;
    JButton send;
    JScrollPane spHistorico, spMensagem;
    
    
    public JanelaChat(ControlaChat ouvinte) {
        
        mostraJanela(ouvinte);
    }

    public void mostraJanela(ControlaChat ouvinte){
        
       // Instaciação dos componentes
       frame = new JFrame("chat com: " + ouvinte.getNickOponente());
       panel = new JPanel();
       historico = new JTextArea();
       mensagem = new JTextField();
       send = new JButton("Enviar");
       spMensagem = new JScrollPane(mensagem);
       spHistorico = new JScrollPane(historico);

       //Amarrando os componentes
       frame.getContentPane().add(panel);
       
       panel.add(spMensagem); 
       //panel.add(mensagem);
       panel.add(spHistorico);
      // panel.add(historico);
       panel.add(send);

       //Propriedades da Janela
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frame.setLocation(140, 90);
       frame.setSize(480, 400);
       frame.setVisible(true);
       frame.setResizable(false); //não maximiza a janela
       panel.setSize(480, 400);
       panel.setLayout(null);
       panel.setVisible(true);
       

       //Posicionamento dos Componentes       
       spHistorico.setBounds( 10, 10, 280, 200);
       spMensagem.setBounds(10, 215, 280, 80);
       send.setBounds(110, 295, 80, 20);


       send.addActionListener(ouvinte);
       
       

    }
    /**
     * Atualiza um tela de chat com uma nova mensagem.
     * O parametro int quem, indica de quem é a mensagem.
     * Se quem == 0, eu abri a janela de chat, mas não enviei nada ainda.
     * Se quem == 1, eu escrevi a mensagem.
     * E se quem == 2, meu opoente escreveu a mensagem.
     * 
     * @param quem
     * @param msg 
     */
    public void refresh(int quem, String msg, String hora){
        
        if(quem == 0 ){
            historico.setText(msg);}
        else if(quem ==1){
            //historico.setText(historico.getText() + "\n Eu disse: \n" + msg + "\n");
            historico.append("\n Eu Disse: \n " + msg);
            
            mensagem.setText("");
        }else if(quem ==2){
           // historico.setText(historico.getText() + "\n Ele disse: \n" + msg + "\n");
            historico.append("\n Ele Disse: \n " + msg);
        }else if(quem ==3){
            String[] hhmmss = hora.split(";");
            //historico.setText(historico.getText() + "\n MENSAGEM OFFLINE: \n ["+ hora+"]" + msg + "\n");
            historico.append("\n MENSAGEM OFFLINE: \n [" +hhmmss[0]+":"+hhmmss[1]+":"+hhmmss[2] +"] " + msg);
        }
        
    }

    public JButton getSend(){
        return send;
    }

    public void Visible(boolean b){

       frame.setVisible(b);
    }

    public String getMensagem() {
        return mensagem.getText();
    }
    
    public void janelaSinalizaQuandoChegaMensagemNova(){
        //frame.repaint();
        frame.requestFocus();
        //frame.requestFocusInWindow();
        
    }

    

}
