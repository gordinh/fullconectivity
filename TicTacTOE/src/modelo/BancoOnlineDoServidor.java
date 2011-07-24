/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta classe se baseia no padrão de projeto SingleTon, e visa concentrar todas as informações do servidor.
 * A ideia principal é facilitar o gerenciamento de recurusos, uma vez que seus métodos e/ou blocos considerados críticos
 * serão syncronized. Para fins de armazenamento dos dados cadastrais o ArrayList de jogadores será serializado cada vez
 * que uma nova informação for adicionada ao array.
 * 
 * @author AndreLuiz
 */
public class BancoOnlineDoServidor {

    private static BancoOnlineDoServidor serverBank;
    private ArrayList<Jogador> jogadoresCadastrados; // Lista de jogadores cadastrados

    private BancoOnlineDoServidor() {



        // Colocar o banco pra carregar as informações de um arquivo de dados.
        if (recuperaListaSerializada() != null) {
            jogadoresCadastrados = recuperaListaSerializada();
            System.out.println("[ metodo contrutor] BancoOnlineDoServidor diz: Recuperei a lista do arquivo");
        } else {
            jogadoresCadastrados = new ArrayList<Jogador>(); // iniciar lista de jogadores cadastrados.
            System.out.println("[ metodo contrutor] BancoOnlineDoServidor diz: Não existia lista no arquivo. ");
        }
    }

    /**
     * Sempre retorna a única instância do banco
     * @return  
     */
    public static BancoOnlineDoServidor getInstance() {
        if (serverBank == null) {
            serverBank = new BancoOnlineDoServidor();
        }
        return serverBank;
    }

    /**
     * Cadastra um novo jogador.
     * A operação de adição na lista é "syncronized" para evitar inconsistencias
     * de dados tipo escritas simultâneas.
     * 
     * @param nick
     * @param senha
     * @param addr 
     * @return cadastrado
     */
    public boolean cadastroNaLista(String nick, String senha, String addr) {

        boolean jaCadastrado = false;
        boolean retorno = false;
        
        // laço para verificar a duplicidade de nick
        for(int i=0; i < jogadoresCadastrados.size(); i++){
            if(jogadoresCadastrados.get(i).getNick().equalsIgnoreCase(nick)){
               // System.out.println("\n nick procurado é: " + nick + "\n nick atual da lista é: " + jogadoresCadastrados.get(i).getNick());
                jaCadastrado = true;}
        }

        // Se ja cadastrado continuar false é sinal de que não existia outro nick igual
        if(jaCadastrado == false){
        
        Jogador novo = new Jogador(nick, senha, addr);

        synchronized (jogadoresCadastrados) {
            jogadoresCadastrados.add(novo);
            serializaLista(); // Salva a nova lista em arquivo
        }

        int tamanhoDaLista = jogadoresCadastrados.size();

        System.out.println("\n [metodo cadastro na lista] BancoOnlineDoServidor diz:" + jogadoresCadastrados.get(tamanhoDaLista - 1).getNick() + " está cadastrado!");

        retorno = true;
        }
        return retorno;
    }

    /**
     * Metodo responsável por fazer o login do jogador no servidor. 
     * O login no servidor consiste na verificação dos dados do usuário, s
     * e estiverem corretos este método retorna um booelano true
     * e caso estejam errados ou não existam retora um booelano false.
     * 
     * @param nick
     * @param senha
     * @return loginValido
     */
    public boolean fazerLogin(String nick, String senha) {
        boolean loginValido = false;

        synchronized (jogadoresCadastrados) {

            for (int i = 0; i < jogadoresCadastrados.size(); i++) {

                if (jogadoresCadastrados.get(i).getNick().equalsIgnoreCase(nick)
                        && jogadoresCadastrados.get(i).getSenha().equalsIgnoreCase(senha)) {
                    jogadoresCadastrados.get(i).setStatusON();
                    loginValido = true;
                    break;
                } else {
                    loginValido = false;
                }
            }
        }
        return loginValido;
    }

    /**
     * Metodo responsável por retornar uma cópia da lista de jogdores cadastrados.
     * A operação de returno da lista é "syncronized" para evitar que escrita 
     *  seja feita ao mesmo tempo leitura na lista.
     * 
     * @return 
     */
    public ArrayList retornaListaDeCasdastrados() {

        synchronized (jogadoresCadastrados) {
            return jogadoresCadastrados;
        }
    }

    /**
     * Atualiza o escore de um jogador. Se sua situação for de vitoria, 2 pontos são somados ao seu escore.
     * Caso seja de derrota, 1 ponto é subtraído do seu escore.
     * 
     * @param nick
     * @param situacao 
     */
    public void atualizaEscore(String nick, String situacao) {

        System.out.println("\n [metodo atualizaEscore] Banco online do Servidor diz: vou atualizar o escore de" + nick);
        synchronized (jogadoresCadastrados) {

            for (int i = 0; i < jogadoresCadastrados.size(); i++) {
                if (jogadoresCadastrados.get(i).getNick().equalsIgnoreCase(nick)) {
                    System.out.println("\n O Score antigo era:" + jogadoresCadastrados.get(i).getPontuacao());
                    jogadoresCadastrados.get(i).setPontuacao(situacao);
                    System.out.println("\n O Score novo é:" + jogadoresCadastrados.get(i).getPontuacao());
                }
            }

            serializaLista();
        }

    }

    /**
     * 
     * Este método serializa a lista de jogadores cadastrados na pasta onde
     * o projeto está salvo.
     * 
     */
    public void serializaLista() {
        ObjectOutputStream salvaEmarquivo;
        try {
            salvaEmarquivo = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir") + File.separator + "Lista.bin"));
            //                            out = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir") + File.separator + "Lista.bin"));
            salvaEmarquivo.writeObject(jogadoresCadastrados);
            salvaEmarquivo.flush();
            salvaEmarquivo.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível salvar a lista", "Erro", 0);
        }


    }

    /**
     * Este método recupera lista de jogadores cadastrados salva previamente
     * na pasta do projeto TicTacTOE.
     */
    public ArrayList<Jogador> recuperaListaSerializada() {

        ArrayList<Jogador> temp = null;

        ObjectInputStream recuperaDoArquivo;

        try {

            recuperaDoArquivo = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir") + File.separator + "Lista.bin"));

            try {

                temp = (ArrayList<Jogador>) recuperaDoArquivo.readObject();
                recuperaDoArquivo.close();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BancoOnlineDoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(BancoOnlineDoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return temp;
    }
}
    