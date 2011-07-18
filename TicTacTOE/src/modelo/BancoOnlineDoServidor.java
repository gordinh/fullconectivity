/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 * Esta classe se baseia no padrão de projeto SingleTon, e visa concentrar todas as informações do servidor.
 * A ideia principal é facilitar o gerenciamento de recurusos, uma vez que seus métodos e/ou blocos considerados críticos
 * serão syncronized.
 * 
 * @author AndreLuiz
 */
public class BancoOnlineDoServidor {

    private static BancoOnlineDoServidor serverBank;
    private ArrayList<Jogador> jogadoresCadastrados; // Lista de jogadores cadastrados

    private BancoOnlineDoServidor() {

        // Colocar o banco pra carregar as informações de um arquivo de dados.

        jogadoresCadastrados = new ArrayList<Jogador>(); // iniciar lista de jogadores cadastrados.
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
     * @param addr
     * @param porta 
     */
    public void cadastroNaLista(String nick, String addr, int porta, int status) {

        System.out.println("\n [metodo cadastro na lista] BancoOnlineDoServidor diz: cadastrando " + nick + " na lista");

        Jogador novo = new Jogador(nick, addr, porta, status, 0);

        synchronized (jogadoresCadastrados) {
            jogadoresCadastrados.add(novo);
        }

        int tamanhoDaLista = jogadoresCadastrados.size();

        System.out.println("\n [metodo cadastro na lista] BancoOnlineDoServidor diz:" + jogadoresCadastrados.get(tamanhoDaLista - 1).getNick() + " está cadastrado!");

        //retornaLista(receivePacket, nick);
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

        synchronized (jogadoresCadastrados) {

            for (int i = 0; i < jogadoresCadastrados.size(); i++) {
                if (jogadoresCadastrados.get(i).getNick().equalsIgnoreCase(nick)) {
                    jogadoresCadastrados.get(i).setPontuacao(situacao);
                }
            }

        }

    }
}