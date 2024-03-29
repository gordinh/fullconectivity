/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.EmissorUDP;
import modelo.Jogador;
import visual.JanelaChat;

/**
 *
 * @author douglas
 */
public class ControlaChat implements ActionListener {

    String nickOponente;
    //String ipOponente[];
    JanelaChat janela;
    int numeroDaMensagem;

    public ControlaChat(String nick) {
        nickOponente = nick;
      //  ipOponente = ip.split("/");
        janela = new JanelaChat(this);
        // janela.addObserver(this);
        numeroDaMensagem = 0;
    }

    /* public void update(Observable o, Object arg) {
    
    String ctrl = (String) arg; //nick do chat do jogador que disparou o evento
    
    String euEscrevi =  ":ReceberMsgChat:" + StaticControlaJogador.getInstance().getNick() + ":" + "TESTE!!!!!!!!!";
    
    janela.refresh(ctrl);
    System.out.println("Método update do observer da janela de chat!!");
    System.out.println("ctrl é: " + ctrl);
    
    try {
    Thread EnviarMsgChat = new Thread(new EmissorUDP(euEscrevi, InetAddress.getByName("192.168.0.160"), 9090));
    EnviarMsgChat.start();
    } catch (UnknownHostException ex) {
    Logger.getLogger(ControlaChat.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    }*/
    

    public String getNickOponente() {
        return nickOponente;
    }

    public void actionPerformed(ActionEvent e) {

        String euDisse = janela.getMensagem();

        System.out.println("O conteúdo é:" + janela.getMensagem());

        janela.refresh(1, janela.getMensagem(), "");
        
        String[] nickSemStatus = nickOponente.split("-");
        Jogador verificaStatus = StaticControlaJogador.getInstance().retornaOponenteDaLista(nickSemStatus[0]);

        if (verificaStatus.getStatus() == 0) { // manda ao servidor (mensagem offline)
            

            System.out.println("[metodo actionPerformed] ControlaChat diz, enviando ao servidor mensagem offline de "
                    + StaticControlaJogador.getInstance().getNick() + " para " + nickSemStatus[0]);

            try {
                String conteudo = ":msgOffline:"+ numeroDaMensagem +":"+StaticControlaJogador.getInstance().getNick() + ":" + nickSemStatus[0] + ":" + euDisse;
                Thread EnviarMsgChatOffline = new Thread(new EmissorUDP(conteudo, InetAddress.getByName(StaticControlaJogador.getInstance().getIPdoServidor()), 2495));
                EnviarMsgChatOffline.start();
                numeroDaMensagem++;
            } catch (UnknownHostException ex) {
                Logger.getLogger(ControlaChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (verificaStatus.getStatus() == 1) { // manda ao oponente (mensagem online)
            try {
                Jogador temp = StaticControlaJogador.getInstance().retornaOponenteDaLista(nickSemStatus[0]);
                
                String conteudo = ":receberMsgChat:"+ numeroDaMensagem +":" +StaticControlaJogador.getInstance().getNick() + ":" + euDisse;
                Thread EnviarMsgChat = new Thread(new EmissorUDP(conteudo, InetAddress.getByName(temp.getIp()), 9090));
                EnviarMsgChat.start();
                numeroDaMensagem++;
            } catch (UnknownHostException ex) {
                Logger.getLogger(ControlaChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
