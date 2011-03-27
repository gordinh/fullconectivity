/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author douglas
 */
public class Cliente {

    private String nick;
    private InetAddress ip;
    private int porta;

    public Cliente (String nick, int porta){
        this.nick = nick;
        try {
            this.ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
        this.porta = porta;
    }

    public Cliente (){

    }
    
    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
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
