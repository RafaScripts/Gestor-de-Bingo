import java.util.ArrayList;

public class Cartela {
    private String nome;
    private ArrayList<Integer> numeros;

    //iniciação do construtor da cartela
    public Cartela(String nome, ArrayList<Integer> numeros){
        this.nome = nome;
        this.numeros = numeros;
    }

    //metodos gets e sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Integer> getNumeros() { return numeros;    }

    public void setNumeros(ArrayList<Integer> numeros) { this.numeros = numeros;   }

    public void imprimir(){
        System.out.println("[Nome: " + nome + ", Numeros: " + numeros + "]");
    }
}
