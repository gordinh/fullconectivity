/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.net.InetAddress;

/**
 * Classe que modela um jogador. Sua função é armazenar informações sobre um usuário do sistema.
 * 
 * @author douglas
 */
public class Jogador {

    private String nick;
    private String strIP;
    private InetAddress inetIP;
    private int porta;
    private int status;
    private int pontuacao;

    public Jogador(String nick, String addr, int porta, int status, int pontuacao) {
        this.nick = nick;
        this.strIP = addr;
        this.porta = porta;
        this.status = status;
        this.pontuacao = pontuacao;
    }

    public Jogador(String nick, InetAddress addr, int porta, int status) {

        this.nick = nick;
        this.inetIP = addr;
        this.porta = porta;
        this.status = status;
        strIP = inetIP.toString();
        this.pontuacao = 0;

    }

    /**
     * Retorna o status do jogador. O método retona 1 (um) se o jogador estiver conectado (online)
     * e 0 (zero) se o jogador estiver desconectado (offline).
     *
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     *  Altera o status do cliente para 1, informando que o usuário está conectado
     */
    public void setStatusON() {
        status = 1;
    }

    /**
     *  Altera o status do cliente para 0, informando que o usuário está desconectado
     */
    public void setStatusOFF() {
        status = 0;
    }

    /**
     * Retona uma string com o ip do Jogador
     * @return 
     */
    public String getIp() {
        return strIP;
    }

    /**
     * Modifica o campo ip do jogador
     * @param String ip 
     */
    public void setIp(String ip) {
        this.strIP = ip;
    }

    /**
     * Retorna o nick do jogador 
     * @return 
     */
    public String getNick() {
        return nick;
    }

    /**
     * Modifica o campo nick do jogador. 
     * @param nick 
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Retorna um inteiro com a porta do jogador
     * @return 
     */
    public int getPorta() {
        return porta;
    }

    /**
     * Modifica o campo porta do jogador
     * @param porta 
     */
    public void setPorta(int porta) {
        this.porta = porta;
    }

    /**
     * Retorna um int com a pontuação do jogador
     * @return 
     */
    public int getPontuacao() {
        return pontuacao;
    }

    /**
     * Aletera a pontuação do jogador. O parametro define se o jogador ganhou ou perdeu a partida.
     * Se vitória, a pontuação é somada de 2. Se derrota a pontuação é subtraida de 1.
     * 
     * @param controle 
     */
    public void setPontuacao(String situacao) {

        if (situacao.equalsIgnoreCase("vitoria")) {
            pontuacao =+2;
        } else if (situacao.equalsIgnoreCase("derrota")) {
            pontuacao =-1;
        }

    }
}
