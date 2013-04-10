
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Guilherme
 */
public class Palavra {

    public String geraRandomica(ArrayList<String[]> gramatica, String variavel, String frase) {

        chomsky testa_variavel = new chomsky();
        Integer[] probs = new Integer[100];
        ArrayList<String[]> encontradas = new ArrayList<String[]>();
        String[] encontrada = new String[2];
        String aux = null;
        encontrada = null;
        int randomico = -1;
        int j = 0;
        int i = 0;
        int cont = 0;
        double prob = 0;
        int tamanho = 0;

        for (i = 0; i < gramatica.size(); i++) {
            if (gramatica.get(i)[0].indexOf(variavel.replace(" [", "[").replace("] ","]")) >= 0) {
           // if (gramatica.get(i)[0].contains(variavel.replace(" [", "[").replace("] ","]"))) {
                encontradas.add(gramatica.get(i));
            }
        }
        i = 0;
        cont = 0;
        for (j = 0; j < encontradas.size(); j++) {
            prob = Double.valueOf(encontradas.get(j)[2]) * 100;
            tamanho = (int) prob;
            while (cont < tamanho) {
                probs[i] = j;
                i++;
                cont++;
            }
            cont = 0;
        }
        randomico = -1;
        while ((randomico < 0) | (randomico > 100) ) {
            randomico = (int) (Math.random() * 100);
            randomico--;
            //if (probs[randomico] == null){
            //    randomico = -1;
            //}
        }


        aux = encontradas.get(probs[randomico])[1].trim().replace("]", "]%");
        encontrada = aux.split("%");
        aux = encontradas.get(probs[randomico])[1];
        if (!testa_variavel.teste_variavel(encontrada[0])) {
            frase = frase.replace(variavel, aux);
            return frase;
        } else {
            frase = frase.replace(variavel, aux);
            frase = (geraRandomica(gramatica, encontrada[0], frase));
            frase = (geraRandomica(gramatica, encontrada[1], frase));
        }
        return frase;


    }
}
///*
// * def gera_frase():
//	f = ""
//	rand = random.randint(1,10)
//	for e in range(rand):
//		rand2 = random.randint(0,len(terminais)-1)
//		f = f + " " + terminais[rand2]
//
//	return f
// f = frase gerada
// terminais = vetor com os terminais
// tamanho da frase tera no maximo um tamanho de 10 palavras
//
// */

