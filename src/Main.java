//importação das Blibiotecas
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Main {
    //iniciação das classes
    private static Scanner ler = new Scanner (System.in);
    private static Bingo bingo = new Bingo();

    public static void main(String[] args) {
        int opcao;

        //menu de console
        do {
            System.out.println();
            System.out.println("-----Bem-vindo ao jogo de bingo-----");
            System.out.println("------------------------------------");

            System.out.println("-----O que deseja fazer?-----");
            System.out.println();
            System.out.println("-----1- Incluir uma cartela-----");
            System.out.println("-----2- Alterar uma cartela-----");
            System.out.println("-----3- Excluir uma cartela-----");
            System.out.println("-----4- Listar cartelas-----");
            System.out.println("-----5- Começar o jogo-----");
            System.out.println("------------------------------------");

            System.out.println("\nInforme sua opção (1-5): ");
            opcao = ler.nextInt();

            //switch para opções
            switch (opcao) {
                case 1:
                    //executa o metodo de criar e incluir cartela
                    incluirCartela();
                    break;
                case 2:
                    //executa o metodo de alterar uma cartela
                    alterarCartela();
                    break;
                case 3:
                    //executa o metodo de excluir uma cartela
                    removerCartela();
                    break;
                case 4:
                    //executa o metodo de listar todas as cartelas existentes
                    listarCartelas();
                    break;
                case 5:
                    //executa o iniciar jogo
                    comecarJogo();
            }
        } while (opcao != 5); //cria um loop infinito nas opções, para qualquer numero diferente das opções
    }


    /* verificar se nova cartela ja possui nome, repassa para metodo alterar,
    * se houver, apenas adiciona os numeros
    * se não, gera numeros e adiciona nome e numeros a nova cartela. */
    public static Cartela verificar(Cartela cartelanova){
        String nome;
        ArrayList<Integer> numeros = new ArrayList<>();
        Random gerador = new Random();
        boolean contem;
        int i = 0;

        if(cartelanova != null){
            nome = cartelanova.getNome();
        } else {
            ler.nextLine();
            System.out.print("-----Digite um nome------: ");
            nome = ler.nextLine().trim();
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

        Cartela novaCartela = verificar(null);

        if (bingo.incluirCartela(novaCartela)) {
            System.out.println("\nCadastrado com sucesso!");
            novaCartela.imprimir();
        }
        else{
            System.out.println("\nFalha! Nome ja cadastrado.");
        }

    }

    /* Executa a alteração da cartela */
    public static void alterarCartela(){
        String nome;

        ler.nextLine();

        System.out.print("\nInforme o nome para alterar a cartela: ");
        nome = ler.nextLine();

        Cartela cartelaantiga = bingo.getNome(nome);

        if(cartelaantiga != null){

            Cartela cartelaAtualizada = verificar(cartelaantiga);

            if (bingo.alterarCartela(cartelaAtualizada)) {
                System.out.println("\nCartela alterada!");
            }
            else {
                System.out.println("\nFalha ao alterar cartela");
            }
        }

        else{
            System.out.println("Falha");
        }

    }

    /* Executa a remoção da cartela */
    public static void removerCartela(){
        String nome;

        ler.nextLine();

        System.out.print("\nInforme o nome para remover a cartela: ");
        nome = ler.nextLine().trim();


        if (bingo.removerCartela(nome)) {
            System.out.println("\nCartela removida!");
        }
        else {
            System.out.println("\nFalha! Nenhuma cartela registrada com esse nome");
        }
    }

    /* Executa a listagem e demonstração de todas as cartelas */
    public static void listarCartelas() {
        System.out.println("Listagem");
        bingo.getCartelas();
    }


    /* Executa o jogo */
    public static void comecarJogo() {
        Random rand = new Random();
        ArrayList<Integer> numSorteados = new ArrayList<>();
        boolean contem;

        boolean verificar = false;

        while(!verificar){
            int num = rand.nextInt(99) + 1;

            contem = numSorteados.contains(num);

            if(!contem) {
                numSorteados.add(num);
                System.out.println("O numero sorteado foi: "+num);
                bingo.Sortear(num);
                try {
                    TimeUnit.MILLISECONDS.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            verificar = bingo.verificar();

        }

        Collections.sort(numSorteados);
        System.out.println("numeros sorteados: " +numSorteados);

        bingo.descrescente();

    }
}