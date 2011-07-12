/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.net.InetAddress;


/**
 *
 * @author douglas
 */

public class Jogador {

    private String nick;
    private String strIP;
    private InetAddress inetIP;
    private int porta;
    private boolean emJogo;

    public Jogador (String nick,String ip, int porta){
        this.nick = nick;
        this.strIP = ip;
        this.porta = porta;
        this.emJogo = false;
    }
    public Jogador (String nick, InetAddress addr, int porta){

        this.nick = nick;
        this.inetIP = addr;
        this.porta = porta;
        this.emJogo = false;
        strIP = inetIP.toString();

    }

    public Jogador (){

    }

    public boolean isEmJogo() {
        return emJogo;
    }

    public void setEmJogo(boolean emJogo) {
        this.emJogo = emJogo;
    }
    
    public String getIp() {
        return strIP;
    }

    public void setIp(String ip) {
        this.strIP = ip;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    
}
