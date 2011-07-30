/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import controle.StaticControlaJogador;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.*;
import modelo.Jogador;

/**
 *
 * @author douglas
 */
public class SalaDeEspera {

    JFrame frame;
    JPanel panel;
    public JList lista;
    DefaultListModel itens;
    private ArrayList<Jogador> clientes;
    public JScrollPane scrollpane;
    JLabel logo, info, infoMSG;
    JButton convidar, mensagem, score;

    public SalaDeEspera(StaticControlaJogador ctrl, ArrayList<Jogador> conectados) {
        clientes = (ArrayList<Jogador>) conectados.clone();
        MontarListaDeConectados();
        mostraJanela(ctrl);
    }

    public void mostraJanela(StaticControlaJogador ctrl) {

        frame = new JFrame("Jogo da Velha --Sala de Espera");
        panel = new JPanel();
        logo = new JLabel();
        convidar = new JButton();
        mensagem = new JButton();
        info = new JLabel("Desafie alguém =>");
        infoMSG = new JLabel("Envie mensagem =>");
        score = new JButton();
        
        frame.getContentPane().add(panel);
        lista = new JList(itens);
        scrollpane = new JScrollPane(lista);

        //Propriedades da Janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(140, 90);
        frame.setSize(500, 558);
        frame.setVisible(true);
        frame.setResizable(false); //não maximiza a janela
        panel.setSize(305, 408);
        panel.setLayout(null);
        panel.setVisible(true);

        lista.setBounds(10, 10, 300, 30);
        panel.add(scrollpane);
        panel.add(logo);
        panel.add(convidar);
        panel.add(mensagem);
        panel.add(info);
        panel.setBackground(Color.white);
        panel.add(score);

        scrollpane.setBounds(330, 100, 130, 120);
        scrollpane.setToolTipText("Escolha seu adversário");

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/sala.jpg")));
        logo.setBounds(-2, -10, 400, 330);

        convidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/jogar.png")));
        convidar.setBounds(350, 400, 120, 100);
        convidar.setToolTipText("Clique para convidar");

        convidar.addActionListener(ctrl);

        mensagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/mensagem.png")));
        mensagem.setBounds(200, 400, 110, 90);
        mensagem.setToolTipText("Clique para enviar mensagem");

        mensagem.addActionListener(ctrl);

        info.setBounds(10, 300, 200, 70);
        infoMSG.setBounds(10, 400, 200, 70);
        
        score.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/podium.gif")));
        score.setBounds(10, 400, 173, 100);
        score.addActionListener(ctrl);
        
        frame.addWindowListener(ctrl);
    }

    /**
     * Método para preencher a lista de conectados.
     * Esta versão do método é usado quando a janela da sala de espera é instanciada.
     */
    public void MontarListaDeConectados() {

        itens = new DefaultListModel();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getStatus() == 1) {
                clientes.get(i).setNick(clientes.get(i).getNick().concat("-> ON"));
            } else {
                clientes.get(i).setNick(clientes.get(i).getNick().concat("-> OFF"));
            }
            itens.addElement(clientes.get(i).getNick());
        }
    }

    /**
     * Método para preencher a lista de conectados.
     * Esta versão do método é usado quando a janela da sala de espera é atualizada.
     */
    public void MontarListaDeConectados(ArrayList<Jogador> conectados) {

        itens = new DefaultListModel();
        for (int i = 0; i < conectados.size(); i++) {
            if (conectados.get(i).getStatus() == 1) {
                conectados.get(i).setNick(conectados.get(i).getNick().concat("-> ON"));
            } else {
                conectados.get(i).setNick(conectados.get(i).getNick().concat("-> OFF"));
            }
            itens.addElement(conectados.get(i).getNick());
        }
    }

    public void visible(boolean b) {

        frame.setVisible(b);
    }

    /**
     * Esse método retorna o botão de convite a fim de verificar a validade do
     * evento disparado na sala de espera.
     */
    public JButton getConvidar() {

        return convidar;
    }

    public JButton getMensagem() {
        return mensagem;
    }

    public JButton getScore(){
        return score;
    }
    
    public void refresh(){
        frame.repaint();
        panel.repaint();
        scrollpane.repaint();
    }
}
