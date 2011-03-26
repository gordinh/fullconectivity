/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author douglas
 */
public class Cliente {

    private String nick;
    private String ip;
    private String porta;

    public Cliente (String nick, String ip, String porta){
        this.nick = nick;
        this.ip = ip;
        this.porta = porta;
    }

    public Cliente (){

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

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    
}
