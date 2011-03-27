/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 *
 * @author douglas
 */

public class Cliente {

    private String nick;
    private String ip;
    private int porta;
    private boolean emJogo;

    public Cliente (String nick,String ip, int porta){
        this.nick = nick;
        this.ip = ip;
        this.porta = porta;
        this.emJogo = false;
    }

    public Cliente (){

    }

    public boolean isEmJogo() {
        return emJogo;
    }

    public void setEmJogo(boolean emJogo) {
        this.emJogo = emJogo;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
