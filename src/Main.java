import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {
    private static Bingo bingo = new Bingo();
    private static String nomeArquivo = "cartelas.txt";

    public static void main(String[] args) {
        String[] opçoes = { "Incluir uma cartela", "Alterar uma cartela", "Excluir uma cartela", "Listar cartelas", "Começar o jogo" };
        String op = null;

        carregarDados();   // carrega os dados das cartelas inseridos anteriormente

        do {
            op = (String) JOptionPane.showInputDialog(null,
                    "O que deseja fazer?", "Escolha uma opção",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    opçoes, opçoes[0]);


            if ("Incluir uma cartela".equals(op)) {
                //executa o metodo de criar e incluir cartela
                incluirCartela();
            } else if ("Alterar uma cartela".equals(op)) {
                //executa o metodo de alterar uma cartela
                alterarCartela();
            } else if ("Excluir uma cartela".equals(op)) {
                //executa o metodo de excluir uma cartela
                removerCartela();
            } else if ("Listar cartelas".equals(op)) {
                //executa o metodo de listar todas as cartelas existentes
                listarCartelas();
            } else if ("Começar o jogo".equals(op)) {
                //executa o iniciar jogo
                comecarJogo();
            }

        } while (op != null);

        salvarDados();   // salva dados das cartelas em arquivo
    }

    public static void carregarDados() {
        // instanciação de objeto representativo de arquivo
        File arquivo = new File(nomeArquivo);

        if (arquivo.exists()) {
            // se o arquivo existir
            // sentinela para controle de leitura de objetos
            boolean leituraObjeto = true;

            try {
                // fluxo de entrada de arquivo sem que haja sobreposição de fluxo de bytes já inserido
                FileInputStream fluxoArquivo = new FileInputStream(arquivo);

                // fluxo de entrada de objetos
                ObjectInputStream fluxoEntradaObjetos = new ObjectInputStream(fluxoArquivo);

                try {
                    // leitura de objetos em arquivo
                    while (leituraObjeto) {
                        // leitura de objeto de cartela
                        Cartela cartela = (Cartela)fluxoEntradaObjetos.readObject();

                        System.out.println(cartela);

                        // inserção da cartela na lista
                        bingo.incluirCartela(cartela);
                    }
                }
                catch(IOException e) { }

                fluxoEntradaObjetos.close();  // fechamento de fluxo de entrada de objetos

                fluxoArquivo.close();         // fechamento de fluxo de entrada de arquivo
            }
            catch (FileNotFoundException e) {
                System.out.println("Arquivo [" + nomeArquivo + "] não encontrado!");
            }
            catch(IOException e) {
                System.out.println("Erro em operação de entrada e/ou saída em arquivo!");
            }
            catch(ClassNotFoundException e) {
                System.out.println("Leitura de objeto pertencente à classe não identificada!");
            }
        }
    }

    public static void salvarDados() {
        // instanciação de objeto representativo de arquivo
        File arquivo = new File(nomeArquivo);

        try {
            // fluxo de saída de arquivo
            FileOutputStream fluxoArquivo = new FileOutputStream(arquivo);

            // fluxo de saida de objetos
            ObjectOutputStream fluxoSaidaObjetos = new ObjectOutputStream(fluxoArquivo);

            Cartela[] cartela = bingo.getCartelas();

            for (Cartela car: cartela){
                fluxoSaidaObjetos.writeObject(car);
            }

            fluxoSaidaObjetos.close();  // fechamento de fluxo de saida de objetos

            fluxoArquivo.close();       // fechamento de fluxo de saída de arquivo
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo [" + nomeArquivo + "] não encontrado!");
        }
        catch(IOException e) {
            System.out.println("Erro em operação de entrada e/ou saída em arquivo!");
        }


    }

    public static Cartela verificar(Cartela cartela){
        String nome;
        ArrayList<Integer> numeros = new ArrayList<>();
        Random gerador = new Random();
        boolean contem;
        int i = 0;

        if(cartela != null) {
            nome = cartela.getNome();
        } else {
            nome = JOptionPane.showInputDialog(null, "Informe o seu nome");
            nome = nome.trim();
        }

        while(i < 24){
            int num = gerador.nextInt(99) + 1;

            contem = numeros.contains(num);

            if(!contem) {
                numeros.add(num);
                i++;
            }
        }
        Collections.sort(numeros);
        return new Cartela(nome, numeros);
    }

    /* Executa a inclusão da cartela */
    public static void incluirCartela(){

        Cartela cartela = verificar(null);

        if (bingo.incluirCartela(cartela)) {
            JOptionPane.showMessageDialog(null,"Cadastrado com sucesso!");
            //JOptionPane.showMessageDialog(cartela.toString());
        }
        else{
            JOptionPane.showMessageDialog(null, "Falha! Nome inserido inválido ou nome ja cadastrado.");
        }

    }

    /* Executa a alteração da cartela */
    public static void alterarCartela(){
        String nome;

        nome = JOptionPane.showInputDialog(null, "Informe o nome para alterar a cartela");

        Cartela cartela = bingo.getNome(nome);

        if(cartela != null){

            Cartela cartelaAtualizada = verificar(cartela);

            if (bingo.alterarCartela(cartelaAtualizada)) {
                JOptionPane.showMessageDialog(null, "Cartela alterada!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Falha ao alterar cartela");
            }
        }

        else{
            JOptionPane.showMessageDialog(null, "Falha! Nenhuma cartela registrada com esse nome");
        }

    }

    /* Executa a remoção da cartela */
    public static void removerCartela(){
        String nome;

        nome = JOptionPane.showInputDialog(null, "Informe o nome para remover a cartela: ");

        if (bingo.removerCartela(nome)) {
            JOptionPane.showMessageDialog(null, "Cartela removida!");
        }
        else {
            JOptionPane.showMessageDialog(null, "Falha! Nenhuma cartela registrada com esse nome");
        }
    }

    /* Executa a listagem e demonstração de todas as cartelas */
    public static void listarCartelas() {
        JOptionPane.showMessageDialog(null,"Listagem");
        Cartela[] cartela = bingo.getCartelas();
        String listagem="";

        for (Cartela car: cartela){
            listagem+= car+"\n";
        }

        JOptionPane.showMessageDialog(null, listagem);
    }

    /* Executa o jogo */
    public static void comecarJogo() {
        Random rand = new Random();
        ArrayList<Integer> numSorteados = new ArrayList<>();
        boolean contem;

        JFrame janela = new JFrame("Bingo");
        JLabel principal = new JLabel("O numero sorteado foi: ");
        janela.add(principal);
        janela.setSize(600, 300);
        janela.setLayout(new FlowLayout());
        janela.setDefaultCloseOperation(EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null); //centraliza a janela ao centro da tela
        janela.setVisible(true);


        JFrame janela2 = new JFrame("Bingo");
        janela2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel principal2 = new JLabel("Os numeros sorteados foram: ");
        janela2.add(principal2);
        janela2.setSize(600, 300);
        janela2.setLayout(new FlowLayout());
        janela2.setVisible(true);


        boolean verificar = false;

        while(!verificar){
            int num = rand.nextInt(99) + 1;

            contem = numSorteados.contains(num);

            if(!contem) {
                numSorteados.add(num);
                //System.out.println("O numero sorteado foi: "+num);
                JLabel numero = new JLabel(String.valueOf(num));
                janela.add(numero);
                janela.repaint();
                janela.validate();
                bingo.Sortear(num);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            verificar = bingo.verificar();

        }


        Collections.sort(numSorteados);
        //System.out.println("numeros sorteados: " +numSorteados);
        JLabel sorteados = new JLabel(String.valueOf(numSorteados));
        janela2.add(sorteados);
        janela2.repaint();
        janela2.validate();
        bingo.descrescente();


        bingo.ganhador();

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit (0);

    }
}
