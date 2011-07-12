/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author andre
 */
public class JanelaJogo extends Observable {


// Declarando os componentes gráficos

    JFrame frame;
    JPanel panel;
    JButton button11, button12, button13,
            button21, button22, button23,
            button31, button32, button33;
    JTextArea text;
    
    public JanelaJogo() {

        mostraJanela();
    }

    /**
     * Esse método fará a instaciação dos componentes gráficos, bem como da
     * arrumação dos componentes na tela. As propriedades de todos os elementos
     * gráficos utilizados são ajustadas aqui.
     */
    public void mostraJanela(){

    // Instanciando os componentes gráficos
        frame = new JFrame("Jogo da Velha");
        panel = new JPanel();
        button11 = new JButton();
        button12 = new JButton();
        button13 = new JButton();
        button21 = new JButton();
        button22 = new JButton();
        button23 = new JButton();
        button31 = new JButton();
        button32 = new JButton();
        button33 = new JButton();
        text = new JTextArea("Jogo da Velha");


    // Adicionando componentes
        frame.getContentPane().add(panel);
        panel.add(button11);
        panel.add(button12);
        panel.add(button13);
        panel.add(button21);
        panel.add(button22);
        panel.add(button23);
        panel.add(button31);
        panel.add(button32);
        panel.add(button33);
        panel.add(text);

   // Propriedades da janela
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(140, 90);
       frame.setSize(305, 408);
       frame.setVisible(true);
       frame.setResizable(false); //não maximiza a janela

        panel.setSize(305, 408);
        panel.setLayout(null);
        panel.setVisible(true);


    // Posicionamento dos Componentes na janela
        button11.setBounds(10, 50, 85, 86);
        button12.setBounds(110, 50, 85, 86);
        button13.setBounds(210, 50, 85, 86);
        button21.setBounds(10, 156, 85, 86);
        button22.setBounds(110, 156, 85, 86);
        button23.setBounds(210, 156, 85, 86);
        button31.setBounds(10, 260, 85, 86);
        button32.setBounds(110, 260, 85, 86);
        button33.setBounds(210, 260, 85, 86);

        //button11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
        //button22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));

        text.setBounds(10, 360, 250, 20);
        text.setEditable(true);
        //text.enable(true);

   // Adicionando disparador de eventos aos botões

        button11.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "11";

                setChanged();
                notifyObservers(aux);

            }
        });

        button12.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "12";

                setChanged();
                notifyObservers(aux);

            }
        });

        button13.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "13";

                setChanged();
                notifyObservers(aux);

            }
        });

        button21.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "21";
                
                setChanged();
                notifyObservers(aux);
                
            }
        });

        button22.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "22";

                setChanged();
                notifyObservers(aux);

            }
        });

        button23.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "23";

                setChanged();
                notifyObservers(aux);

            }
        });

        button31.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "31";

                setChanged();
                notifyObservers(aux);

            }
        });

        button32.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "32";

                setChanged();
                notifyObservers(aux);

            }
        });

        button33.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String aux = "33";

                setChanged();
                notifyObservers(aux);

            }
        });




    } // mostra jenela

   
    public void setText(String string){

      text.setText(string);
   }

   public void setButtonIcon(int round, String position){
   
       int selectRound = round%2;

       int sw = Integer.parseInt(position);

       switch(sw){
           case 11:
               if( selectRound != 0 ){
                button11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                button11.setEnabled(false);
                 break;}
               else{
                    button11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button11.setEnabled(false);
                    break;}
           case 12:
               if( selectRound != 0 ){
                button12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                button12.setEnabled(false);
                break;}
               else{
                    button12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button12.setEnabled(false);
                    break;}
           case 13:
               if( selectRound != 0 ){
                button13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                button13.setEnabled(false);
                break;}
               else{
                    button13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button13.setEnabled(false);
                    break;}
           case 21:
               if( selectRound != 0 ){
                button21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                 button21.setEnabled(false);
                break;}
               else{
                    button21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button21.setEnabled(false);
                    break;}
           case 22:
               if( selectRound != 0 ){
                button22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                 button22.setEnabled(false);
                break;}
               else{
                    button22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button22.setEnabled(false);
                    break;}
            case 23:
               if( selectRound != 0 ){
                button23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                 button23.setEnabled(false);
                break;}
               else{
                    button23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button23.setEnabled(false);
                    break;}
             case 31:
               if( selectRound != 0 ){
                button31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                button31.setEnabled(false);
                break;}
               else{
                    button31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button31.setEnabled(false);
                    break;}
           case 32:
               if( selectRound != 0 ){
                button32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                 button32.setEnabled(false);
                break;}
               else{
                    button32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button32.setEnabled(false);
                    break;}
           case 33:
               if( selectRound != 0 ){
                button33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundox2.png")));
                 button33.setEnabled(false);
                break;}
               else{
                    button33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/fundoO2.png")));
                    button33.setEnabled(false);
                    break;}
       }
  }

   public void setEditFrame(Boolean bolean){
       frame.setEnabled(bolean);
   }

}
