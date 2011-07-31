/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 * Essa classe concentra todas as informações sobre a mensagem offline, como 
 * por exemplo, emissor, destinatário e hora do envio da mensagem.
 * 
 * @author AndreLuiz
 */
public class Mensagem implements Serializable{
    
    private String emissor;
    private String destinatario;
    private String horaDaMensage;
    private String conteudo;
    private int numeroDaMensasagem;
    private int quem;
    
    /**
     * Contrutor usado em mensagens offline.
     * 
     * @param emissor
     * @param destinataio
     * @param conteudo
     * @param horaDaMensagem 
     */
    public Mensagem (int numeroDaMensgem, String emissor, String destinataio, String conteudo ,String horaDaMensagem ){
        this.numeroDaMensasagem = numeroDaMensgem;
        this.emissor = emissor;
        this.destinatario = destinataio;
        this.conteudo = conteudo;
        this.horaDaMensage = horaDaMensagem;
        
    }
    
    /**
     * Contrutor usado no buffer de mensagens do cliente.
     * 
     * @param quem
     * @param numeroDaMensagem
     * @param emissor
     * @param conteudo 
     */
    public Mensagem(int quem, int numeroDaMensagem, String emissor, String conteudo){
        this.quem = quem;
        this.numeroDaMensasagem = numeroDaMensagem;
        this.emissor = emissor;
        this.conteudo = conteudo;
        this.horaDaMensage = "";
        
    }
    

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getHoraDaMensage() {
        return horaDaMensage;
    }

    public void setHoraDaMensage(String horaDaMensage) {
        this.horaDaMensage = horaDaMensage;
    }

    public int getNumeroDaMensasagem() {
        return numeroDaMensasagem;
    }

    public void setNumeroDaMensasagem(int numeroDaMensasagem) {
        this.numeroDaMensasagem = numeroDaMensasagem;
    }
    
    
}
