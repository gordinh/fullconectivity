/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Siloe
 */
public class Auxiliador {

    public static void novoArquivo(String diretorio, String nomeDoarquivo) {
        String conteudo = "Teste";
        try {
            // o true significa q o arquivo será constante  
            FileWriter x = new FileWriter("./arquivos/padrao/" + nomeDoarquivo + ".txt", true);

            conteudo += "\n\r"; // criando nova linha e recuo no arquivo
            x.write(conteudo); // armazena o texto no objeto x, que aponta para o arquivo           
            x.close(); // cria o arquivo           
            JOptionPane.showMessageDialog(null, "Arquivo gravado com sucesso", "Concluído", JOptionPane.INFORMATION_MESSAGE);
        } // em caso de erro apreenta mensagem abaixo  
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void subirArquivo(String usuario) {
        final JFileChooser fc = new JFileChooser();
        fc.showDialog(fc, "Abrir");
        fc.setVisible(true);
        File file = fc.getSelectedFile();
//        File file = new File(fTmp.toString().replace(" ", "_"));
        
        System.out.println("Subindo arquivo:" + file);
        try {
            Auxiliador.copiar(file, new File("./arquivos/" + usuario + "/" + file.getName()));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static File pegarNomeDoArquivo() {
        final JFileChooser fc = new JFileChooser();
        //fc.showDialog(fc, "Abrir");
        fc.showSaveDialog(fc);
        fc.setVisible(true);
        File file = fc.getSelectedFile();
        
        return file;        
    }

//    public static void baixarArquivo(InputStream fonte, File destino) throws IOException {
////        InputStream in = new FileInputStream(fonte);
//        OutputStream out = new FileOutputStream(destino);
//
//        byte[] buf = new byte[1024];
//        int len;
//        while ((len = fonte.read(buf)) > 0) {
//            out.write(buf, 0, len);
//        }
//        fonte.close();
//        out.close();
//    }

    public static void copiar(File fonte, File destino) throws IOException {
        InputStream in = new FileInputStream(fonte);
        System.out.println(""+destino);
        OutputStream out = new FileOutputStream(destino);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static void addVizualizacaoDoArquivo(JTable tabela, String arquivo) {
        ImageIcon image = null;
        String diretorio = "images/icons/";
        //Pegando a extensao do arquivo
        String temp[] = arquivo.split("\\.");
        String extensao = temp[temp.length - 1];

        switch (extensao) {
            //Textos
            case "txt":
                image = new ImageIcon(diretorio + "text.png");
                break;
            case "doc":
                image = new ImageIcon(diretorio + "text.png");
                break;
            case "odt":
                image = new ImageIcon(diretorio + "text.png");
                break;
            //pdf
            case "pdf":
                image = new ImageIcon(diretorio + "book_red.png");
                break;
            //Imagens
            case "jpg":
                image = new ImageIcon(diretorio + "image.png");
                break;
            case "bitmap":
                image = new ImageIcon(diretorio + "image.png");
                break;
            case "png":
                image = new ImageIcon(diretorio + "image.png");
                break;
            case "gif":
                image = new ImageIcon(diretorio + "image.png");
                break;
            //Videos
            case "mpg":
                image = new ImageIcon(diretorio + "movie.png");
                break;
            case "avi":
                image = new ImageIcon(diretorio + "movie.png");
                break;
            //Musicas
            case "mp3":
                image = new ImageIcon(diretorio + "music.png");
                break;
            //Executaveis    
            case "exe":
                image = new ImageIcon(diretorio + "executable.png");
                break;
            default:
                image = new ImageIcon(diretorio + "default.png");
                break;
        }

        ((DefaultTableModel) tabela.getModel()).addRow(
                new Object[]{image, arquivo});
    }

    /*Usando botões
     * JButton fileBtn = new JButton(arquivo, image);
    
    fileBtn.setHorizontalTextPosition(SwingConstants.CENTER);
    fileBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
    
    fileBtn.setVisible(true);
    panel.add(fileBtn);
     * 
     */
    public static void executarArquivo(String arquivo) {
        try {
            
            String comando ="cmd start /c " +"arquivos\\" +arquivo;
//            String comando = "cmd start /c C:/Users/Siloe/Documents/NetBeansProjects/gerArqServer/arquivos/padrao/as_ninas.jpg";
//            String comando = "cmd start /c .\\arquivos\\padrao\\as_ninas.jpg";

            System.out.println("Comando = "+comando);
            Runtime.getRuntime().exec(comando);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
    }
}
