import java.util.ArrayList;
import java.io.Serializable;

public class Cartela implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public String toString(){
        return "[Nome: " + nome + ", Numeros: " + numeros + "]";
    }
}
