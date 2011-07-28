/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 *
 * @author AndreLuiz
 */
public class JanelaDePontuacao extends JFrame{
    
    //exemplo que cria um JList
private JList classificacao;
private JScrollPane scrollpane;
//private String[] pontuacao;

public JanelaDePontuacao(String[] pontuacao){
super("Classificação Dos Jogadores");

Container pane = this.getContentPane();

classificacao = new JList(pontuacao);
scrollpane = new JScrollPane(classificacao);

pane.add(scrollpane);


this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
this.setSize(310, 150);
this.setResizable(false); //formulário não pode ter seu tamanho redefinido
this.setVisible(true);

}
    
}
