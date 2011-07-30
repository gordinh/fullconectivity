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
public class MensagemOffline implements Serializable{
    
    private String emissor;
    private String destinatario;
    private String horaDaMensage;
    private String conteudo;
    
    public MensagemOffline (String emissor, String destinataio, String conteudo ,String horaDaMensagem ){
        this.emissor = emissor;
        this.destinatario = destinataio;
        this.conteudo = conteudo;
        this.horaDaMensage = horaDaMensagem;
        
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
    
    
}
