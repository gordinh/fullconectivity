/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import controle.ControlaJogador;
import java.io.IOException;


/**
 *
 * @author douglas
 */
public abstract class ComunicadorUDP {

    private DatagramSocket socket;

    public ComunicadorUDP(){
        try {
            socket = new DatagramSocket(3495);
        } catch (SocketException ex) {
            Logger.getLogger(ComunicadorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para receber a mensagem e quebrar com o split.
     * @param DatagramPacket PacoteRecebido
     * @return s
     */
    public String[] lerMensagem(DatagramPacket pacoteRecebido) {

        String sentenca = (new String(pacoteRecebido.getData()));
        String[] s = new String[1];
        s = sentenca.split("|");
        return s;

    }

    /**
     * Este método codificará as palavras-chaves da aplicação com a finalidade de usar este
     * retorno no case do metodo run().
     * @param mensagem
     * @return
     */
    public int codificaPalavraChave(String mensagem) {

        // 1. Desafio // 2. Lista // 3. Jogada

        //  4. Aceito // 5. Negado //

        if (mensagem.equalsIgnoreCase("Login")) {
            return 1;
        } else if (mensagem.equalsIgnoreCase("Lista")) {
            return 2;
        } else if (mensagem.equalsIgnoreCase("Jogada")) {
            return 3;
        } else if (mensagem.equalsIgnoreCase("Aceito")) {
            return 4;
        } else if (mensagem.equals("Negado")){
            return 5;
        }

        //Retorna zero no caso de erro
        return 0;
    }

    /**
     * Método criado para consdensar num único local a função de enviar o um pacote UDP.
     * Apenas as informações essenciais (mensagem, ip e porta) são requeridos.
     *
     * @param mensagem
     * @param ip
     * @param porta
     */
    public int enviaPacoteUDP(String mensagem, InetAddress ip, int porta) {

        int count = 0;
        byte[] dadosDeRecepcao = new byte[1024];
        byte[] dadosDeEnvio = new byte[1024];

        dadosDeRecepcao = mensagem.getBytes();

        DatagramPacket pacoteEnvio = new DatagramPacket(dadosDeRecepcao, dadosDeRecepcao.length, ip, porta);
        DatagramPacket pacoteRecepcao = new DatagramPacket(dadosDeEnvio, dadosDeEnvio.length);
        do{
            try {
            socket.send(pacoteEnvio);
            Thread.sleep(800);
            socket.receive(pacoteRecepcao);

            } catch (IOException ex) {
            Logger.getLogger(ControlaJogador.class.getName()).log(Level.SEVERE, null, ex);
            } catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }

            String s = (new String(pacoteRecepcao.getData()));
            s = s.trim();
            String[] t = lerMensagem(pacoteRecepcao);

            if(pacoteRecepcao.equals(pacoteEnvio)){
                //Atualizar tabuleiro do jogo //Iniciar jogo //
                return codificaPalavraChave(t[0]);

            }
        }while(count != 6);

        return 10;
    }

    /**
     * Método para recepção do pacote UDP que estiver vindo, e saber o que fazer depois, setando
     * a váriavel de estado corresponde ao código da instrução.
     * @return
     */
    public int recebePacoteUDP() {


        byte[] dadosDeRecepcao = new byte[1024];
        byte[] dadosDeEnvio = new byte[1024];
        String s = null;
        String[] t = null;

        DatagramPacket pacoteRecepcao = new DatagramPacket(dadosDeRecepcao, dadosDeRecepcao.length);

            try {

            socket.receive(pacoteRecepcao);

            s = (new String(pacoteRecepcao.getData()));
            s = s.trim();
            t = lerMensagem(pacoteRecepcao);

            InetAddress endIP = pacoteRecepcao.getAddress();
            int porta = pacoteRecepcao.getPort();

            DatagramPacket pacoteEnvio = new DatagramPacket(dadosDeEnvio, dadosDeEnvio.length, endIP, porta);
            socket.send(pacoteEnvio);

            } catch (IOException ex) {
            Logger.getLogger(ControlaJogador.class.getName()).log(Level.SEVERE, null, ex);

            }

        return codificaPalavraChave(t[0]);
    }
}
