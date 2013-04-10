
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Juliano
 */
public class cyk {

    public ArrayList<String[]> cyk(String[] terminais, int n, ArrayList<String[]> gra, Object[][] nodos) {
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        ArrayList<String> termis = new ArrayList<String>();
        resultado.clear();
        nodos = new Object[n + 1][n + 1];
        String[] terms = new String[terminais.length];
        String[] aux = new String[3];
        String[] teste = null;
        boolean aceitou = true;
        int i = 0;
        int linha = 0;
        int a = 0;
        int b = 0; //são os valores de linha e coluna na tabela do cyk


        for (i = 1; i <= n; i++) {
            if (!terminais[i - 1].trim().isEmpty()) {
                terms = procura(terminais[i - 1], gra);
                if (terms.length == 0) {
                    aceitou = false;
                }
                resultado.add(terms);
                nodos[i][i] = resultado.get(resultado.indexOf(terms));
            }
        }
        if (aceitou) {

            for (linha = 1; linha <= n; linha++) {
                for (a = 1; a <= n - linha; a++) {
                    b = a + linha;
                    terms = concatena(linha, a, b, gra, nodos);
                    resultado.add(terms);
                    nodos[a][b] = resultado.get(resultado.indexOf(terms));
                }
            }

            teste = (String[]) nodos[a][b];
            String[] inicial = gra.get(0);
            i = 0;
            while ((i < teste.length) & (!aceitou)) {
                if (teste[i].trim().contains(inicial[0].trim())) {
                    aceitou = true;
                }
                i++;
            }
            if (aceitou) {
                JOptionPane.showMessageDialog(null, "Palavra aceita!");
            } else {
                JOptionPane.showMessageDialog(null, "Palavra rejeitada!");
            }
            return resultado;
        } else {
            JOptionPane.showMessageDialog(null, "Palavra rejeitada!");
            return null;
        }

    }

    public String[] procura(String direita, ArrayList<String[]> gra) {
        int i = 0;
        String[] teste;
        ArrayList<String> terms = new ArrayList<String>();
        terms.clear();

        for (i = 0; i < gra.size(); i++) {
            teste = gra.get(i);
            teste[1] = teste[1].replace(" ", "");
            direita = direita.replace(" ", "");
            if (teste[1].contains(direita)) {
                if (teste[1].replace(direita, "").replace("[]", "").trim().isEmpty()) {
                    terms.add(teste[0]);
                }
            }
        }

        String[] result = new String[terms.size()];
        for (i = 0; i < terms.size(); i++) {
            result[i] = terms.get(i);
        }
        return result;
    }

    public String[] concatena(int limite, int a, int b, ArrayList<String[]> gra, Object[][] nodo) {
        ArrayList<String> prod = new ArrayList<String>();
        String[] testa1 = null;
        String[] testa2 = null;
        String[] result = null;
        ArrayList<String> resultado = new ArrayList<String>();

        int i = 0;
        while (i < limite) {
            Object nodos = nodo[a][b - limite + i];
            testa1 = (String[]) nodos;
            nodos = nodo[b - limite + i + 1][b];
            testa2 = (String[]) nodos;
            int x = 0;
            int y = 0;
            for (x = 0; x < testa1.length; x++) {
                for (y = 0; y < testa2.length; y++) {
                    if (testa1[x] == null) {
                        testa1[x] = " ";
                    }
                    if (testa2[y] == null) {
                        testa2[y] = " ";
                    }
                    prod.add(testa1[x].concat(testa2[y]));

                }
            }
            i++;
        }
        i = 0;
        int cont1 = 0;
        while (i < prod.size()) {
            if (prod.get(i) != null) {
                testa1 = procura(prod.get(i), gra);
                while (cont1 < testa1.length) {
                    resultado.add(testa1[cont1]);
                    cont1++;
                }
            }
            i++;
        }

        if (resultado.size() == 0) {
            result = new String[1];
        } else {
            result = new String[resultado.size()];
        }


        result = testa_repetido(result);
        for (cont1 = 0; cont1 < resultado.size(); cont1++) {
            result[cont1] = resultado.get(cont1);
        }
        return result;
    }

    public String[] testa_repetido(String[] result) {
        int i = 0;
        int j = 0;
        int cont = 0;
        for (i = 0; i < result.length - 1; i++) {
            for (j = 1; j < result.length; j++) {
                if (result[i] == result[j]) {
                    String[] testa = new String[result.length];
                    for (cont = 0; cont < j; cont++) {
                        testa[cont] = result[cont];
                    }
                    for (cont = j; cont < testa.length - 1; cont++) {
                        testa[cont] = result[cont + 1];
                    }
                    result = testa;
                }
            }
        }
        return result;
    }
}
