
//importação das blibiotecas


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Bingo extends JFrame {
    private Collection<Cartela> lista;

    public Bingo() {
        lista = new ArrayList<Cartela>();
    }

    //inclusão de uma nova cartela
    public boolean incluirCartela(Cartela cartela) {
        String nome = cartela.getNome();

        //se o nome informado for nulo ou esiver em branco, retorna false
        if(nome == null || nome == "") {
            return false;
        }

        //se ja existir alguma cartela com o mesmo nome, retorna false
        for (Cartela c: lista) {
            if(c.getNome().trim().equals(nome)){
                return false;
            }
        }

        // se não tiver nenhum nome repetido, adiciona a cartela e retorna true
        lista.add(cartela);

        return true;
    }

    //alterar uma cartela
    public boolean alterarCartela(Cartela cartela){
        String nome = cartela.getNome();

        System.out.println(nome);

        /* for each em cada cartela da lista, quando achar uma
        cartela o nome informado, altera a cartela e retorna true */
        for (Cartela c: lista) {
            if(c.getNome().trim().equals(nome)){
                c.setNumeros(cartela.getNumeros());
                return true;
            }
        }

        return true;
    }

    //remoção da cartela
    public boolean removerCartela(String nome){

        /* for each para cada cartela da lista, quando encontrar uma
        cartela com o nome informado, a cartela é removida */
        for (Cartela cartela: lista) {
            if(cartela.getNome().trim().equals(nome)){
                lista.remove(cartela);
                return true;
            }
        }
        return false;
    }

    // localiza e retorna a cartela por nome
    public Cartela getNome(String nome){

        for (Cartela c: lista) {
            if(c.getNome().trim().equals(nome)){
                return c;
            }
        }

        //se não encontrar, retorna null
        return null;
    }


    //retorna um array tipo Cartela com todas as cartelas
    public Cartela[] getCartelas() {
        Cartela[] cartela = lista.toArray(new Cartela[lista.size()]);

        int i = 0;
        for(Cartela car: lista){
            cartela[i] = car;
            i++;
        }

        return cartela;
    }

    /* Verifica qual das cartelas teve todos os seus numeros Sorteados(Removidos),
     * para aquela cartela que tiver todos os seus numeros removidos, está será considerada Vencedora */
    public boolean verificar(){
        String nome = "";
        int count = 0;
        for (Cartela c: lista){
            if(c.getNumeros().size() == 0){
                count++;
                nome += c.getNome()+" ";
            }

        }
        if(count > 1){
            //System.out.println("Empate entre: "+nome);


            return true;
        }
        if(count == 1){
            //System.out.println("O ganhador é: "+nome);
            return true;
        }



        return false;
    }

    public void ganhador(){

        JFrame ganhados = new JFrame("Ganhador / Empate");
        ganhados.setLayout(new GridBagLayout());
        ganhados.setSize(600,300);
        ganhados.setLocationRelativeTo(null); //centralizar a box
        ganhados.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ganhados.setVisible(true);

        JLabel texto = new JLabel("Ganhador ou Empate: ");
        ganhados.add(texto);

        String nome = "";
        int count = 0;
        for (Cartela c: lista){
            if(c.getNumeros().size() == 0){
                count++;
                nome += c.getNome()+" ";
            }

        }
        if(count > 1){
            JLabel empate = new JLabel("Empate entre: "+nome);
            ganhados.add(empate);
            ganhados.repaint();
            ganhados.validate();

        }
        if(count == 1){
            JLabel ganhadoss = new JLabel("O ganhador é: "+nome);
            ganhados.add(ganhadoss);
            ganhados.repaint();
            ganhados.validate();
        }


    }

    /*ajeita a ordenação da listagem final das cartelas após o sorteio
     * as colocando de forma decrescente dos numeros que sobraram. */
    public void descrescente(){

        JFrame finals = new JFrame("final");
        finals.setLayout(new FlowLayout());
        finals.setSize(600, 300);
        finals.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        finals.setVisible(true);

        JLabel cartela = new JLabel("cartelas restantes: ");
        finals.add(cartela);



        for (Cartela c: lista) {
            Collections.sort(c.getNumeros() ,  Collections.reverseOrder());
            //System.out.println(c.getNome()+" "+c.getNumeros());
            JLabel cartelas = new JLabel(c.getNome()+""+c.getNumeros());
            finals.add(cartelas);
            finals.repaint();
            finals.validate();
        }
    }


    /* Remove os numeros das cartelas conforme o numero que acaba de ser
     * sorteado pelo gerador do sorteio. */
    public void Sortear(int num){
        for(Cartela cartela: lista){
            ArrayList numeros = cartela.getNumeros();
            boolean contem = numeros.contains(num);
            if(contem){
                numeros.remove(new Integer(num));
                //System.out.println("A cartela de "+cartela.getNome()+" contem a pedra "+ num);
            }
        }
    }
}
