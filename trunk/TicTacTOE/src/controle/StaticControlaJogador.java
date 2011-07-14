/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.DecodificadorDeAcoesDoCliente;
import modelo.Jogador;
import modelo.ReceporDeMensagensDoCliente;
import visual.JanelaLogin;
import visual.SalaDeEspera;

/**
 *
 * @author AndreLuiz
 */
public class StaticControlaJogador implements ActionListener{
    
    private static StaticControlaJogador controladorEstatico;
    private JanelaLogin jLogin;
    private String nick = "";
    private ArrayList<Jogador> oponentes;
    private SalaDeEspera sala = null;
    private String oponenteSelecionado;
    private Thread escuta;
  
    
    private StaticControlaJogador(){
        escuta = new Thread(new ReceporDeMensagensDoCliente());
        escuta.start();
        oponentes = new ArrayList<Jogador>();
        
    }
        
    public static StaticControlaJogador getInstance(){
        
        if( controladorEstatico == null)
           controladorEstatico  = new StaticControlaJogador();
        return controladorEstatico;
        
    }
    
    public synchronized void mostraJanelaDeLogin(){
        jLogin = new JanelaLogin(this);
    }
    
    public synchronized void chamaSalaDeEspera(){
        sala = new SalaDeEspera(this, oponentes);
    }
    
    /**
     * Esse método limpa a lista atual, isto é, apaga todos os elementos dela e a
     * seguir preenche-a armazenando os novos dados.
     * 
     */
    public synchronized void atualizaListaDeOponentes(String listaConcatenada){
       
       // if(oponentes != null)
            //oponentes.clear(); // limpa a lista //
        
        String[] aux = listaConcatenada.trim().split(","); // cada posição do vetor contém informação de um oponente //

        for (int i = 1; i <= aux.length-1; i++) { // 0ª célula do vetor: lixo, 1ª célula: palavra de controle, 2ª célua: primeiro oponente //
            String[] u = aux[i].split(":");
            Jogador novoOponente = new Jogador(u[1], u[2], Integer.parseInt(u[3])); // Na ordem: u[0] = Nick, u[1] = ip e u[2] = porta //
            oponentes.add(novoOponente);
        }
        
    }

    public synchronized Jogador retornaOponenteDaLista(String nick){
        Jogador oponente = null;

        for(int i=0; i<= oponentes.size(); i++){
            if(oponentes.get(i).getNick().equalsIgnoreCase(nick) ){
                oponente = oponentes.get(i);
                break;
            }
        }
        return oponente;
    }

    
     public void actionPerformed(ActionEvent e) {

        nick = jLogin.getNick().getText();

        if (e.getSource() == jLogin.getEnter()) {

            if (!nick.equalsIgnoreCase("") && nick.length() <= 8) {

                jLogin.Visible(false);
                
                String controle = ":Login:"+ nick + ":"; // String de controle, indica qual ação deve ser executada pelo decodificador //
                
                Thread loginNoServidor = new Thread(new DecodificadorDeAcoesDoCliente(controle));
                loginNoServidor.start();

            } else {
                JOptionPane.showMessageDialog(null, "Seu nick deve conter de 1 a 8 caracteres", "Login Inválido", 0);
            }

        } else if (e.getSource() == sala.getConvidar()) {

            if (sala.lista.getSelectedValue() != null) {

                oponenteSelecionado = sala.lista.getSelectedValue().toString();
                
                String controle =  ":enviarConvite:" + oponenteSelecionado;

                Thread convidarOponente = new Thread(new DecodificadorDeAcoesDoCliente(controle));
                convidarOponente.start();
                

            } else {
                JOptionPane.showMessageDialog(null, "Selecione um oponente da lista e clique no botão para desafiá-lo \n"
                        + "ou aguarde ser desafiado por outro jogador", "Erro", 0);
            }

        }
    }
    
}
