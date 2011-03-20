/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import visual.JanelaLogin;

/**
 *
 * Essa classe deve gerenciar todas as ações do jogador. sua função é gerir o fluxo
 * das ações do jogador.
 *
 * @author andre
 */
public class ControlaCliente implements ActionListener{

   JanelaLogin jLogin;

    public ControlaCliente(){

        login();
    }

    public void login(){

        jLogin = new JanelaLogin(this);

    }

    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == jLogin.getEnter()){

            if(!jLogin.nickk.equalsIgnoreCase("")){
                //System.out.println(jLogin.getNick());
                jLogin.Visible(false);
                conectaServidor(jLogin.getNick());
            }
        }

     
    }

    /**
     * Metodo que fará a conexão do usuário com o servidor
     * @param nick
     */
    public void conectaServidor(String nick){
        
        System.out.println("Conectando \"" +  nick + "\" ao server...");


    }
}
