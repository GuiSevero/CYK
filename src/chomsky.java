
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Juliano
 */
public class chomsky {

    public ArrayList<String[]> criachomsky(String gramatica) {


        ArrayList<String> varia = new ArrayList<String>();
        ArrayList<String[]> gra = new ArrayList<String[]>();
        ArrayList<String[]> novasvar = new ArrayList<String[]>();
        varia.clear();
        gra.clear();
        novasvar.clear();
        int i = 0;

        FileReader gram = null;
        try {
            gram = new FileReader(gramatica);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(chomsky.class.getName()).log(Level.SEVERE, null, ex);
        }


        Scanner leitor = new Scanner(gram);

        String teste = leitor.nextLine();

        if (teste.indexOf("#") >= 0) {
            teste = teste.substring(0, teste.indexOf("#"));
        }
        if (!teste.contains("Terminais")) {
            JOptionPane.showMessageDialog(null, "ERRO! Confira os terminais!");
        } else {
            while (!teste.contains("Variaveis")) {
                if (!(leitor.hasNext())) {
                    JOptionPane.showMessageDialog(null, "ERRO! Confira as variaveis!");
                }
                teste = leitor.nextLine();
                if (teste.indexOf("#") >= 0) {
                    teste = teste.substring(0, teste.indexOf("#"));
                }
            }
            String linha = new String();
            i = 0;
            linha = leitor.nextLine();
            if (linha.indexOf("#") >= 0) {
                linha = linha.substring(0, linha.indexOf("#") - 1);
            }
            while (!(linha.contains("Inicial"))) {
                varia.add(i, linha);
                i++;
                linha = leitor.nextLine();
                if (linha.indexOf("#") >= 0) {
                    linha = linha.substring(0, linha.indexOf("#") - 1);
                }
            }
            linha = leitor.nextLine();
            linha = leitor.nextLine();
            while ((leitor.hasNext())) {
                linha = leitor.nextLine();
                if (linha.indexOf("#") >= 0) {
                    linha = linha.substring(0, linha.indexOf("#") - 1);
                }

                String[] aux = null;
                aux = linha.replace(";", ">").split(">");
                //  int testes = Integer.parseInt(aux[2].replace(".",""));
                //  probs.add(testes);
                gra.add(aux);
            }
            leitor.close();
            try {
                gram.close();
            } catch (IOException ex) {
                Logger.getLogger(chomsky.class.getName()).log(Level.SEVERE, null, ex);
            }


        }

        return passo1(varia, gra);
    }

    public ArrayList<String[]> passo1(ArrayList<String> varia, ArrayList<String[]> gra) {
        String aux[] = null;
        ArrayList<String[]> salvareg = new ArrayList<String[]>();
        int i = 0;
        double prob1 = 0;
        double prob2 = 0;
        double prob = 0;

        while (i < gra.size()) {
            String[] producao = gra.get(i);
            String linha = producao[1];

            ArrayList<String> variaveis = divide_prod(linha);
            if (variaveis.size() == 0) {
                gra.remove(i);
            } else {
                if (variaveis.size() == 1) {
                    salvareg.clear();
                    String testavazio = variaveis.get(0).replace("[", "");
                    testavazio = testavazio.replace("]", "");
                    testavazio = testavazio.replace(" ", "");
                    if (testavazio.length() > 0) {
                        if (teste_variavel(variaveis.get(0))) {
                            prob = Double.parseDouble(producao[2].trim()) * 100;

                            int cont = 0;
                            for (cont = 0; cont < gra.size(); cont++) {
                                aux = gra.get(cont);

                                if ((aux[0].contains(producao[0])) & (i != cont)) {
                                    salvareg.add(aux);/*
                                    aux[0] = aux[0].replace(variaveis.get(0), producao[0]);
                                    prob1 = Double.parseDouble(aux[2].trim()) * 100;
                                    prob2 = Double.parseDouble(producao[2].trim()) * 100;
                                    prob = ((prob1 * prob2) / 100);
                                    prob = Math.round(prob);
                                    aux[2] = String.valueOf(prob / 100);*/
                                }
                            }
                            prob2 = (Integer) salvareg.size();
                            prob1 = prob / prob2;
                            prob1 = Math.round(prob1);
                            prob1 = prob1 / 100;
                            for (cont = 0; cont < salvareg.size(); cont++) {
                                aux = salvareg.get(cont);
                                prob1 = Double.parseDouble(aux[2].trim()) + prob1;
                                aux[2] = String.valueOf(prob1);
                                gra.set(gra.indexOf(aux), aux);
                            }
                            gra.remove(i);
                        }


                    }
                }
            }
            i++;
        }
        return passo2(varia, gra, 0);
    }

    public ArrayList<String[]> passo2(ArrayList<String> varia, ArrayList<String[]> gra, int nvar) {
        int cont2 = 0;
        boolean existe = false;


        String[] ttt;
        int i1;

        int i = 0;
        while (i < gra.size()) {
            String[] producao = gra.get(i);
            String linha = producao[1];
            int cont = 0;
            ArrayList<String> variaveis = divide_prod(linha);
            if (variaveis.size() >= 2) {
                while (cont < variaveis.size()) {
                    String aux[] = new String[3];
                    if (!teste_variavel(variaveis.get(cont))) {

                        //
                        cont2 = 0;
                        while ((cont2 < varia.size()) & (!existe)) {
                            if (varia.get(cont2).contains("A" + nvar)) {
                                existe = true;
                                nvar++;
                            }
                            cont2++;
                        }
                        //

                        producao[1] = producao[1].replace(variaveis.get(cont), "[ A" + nvar + " ]");
                        gra.set(i, producao);
                        aux[0] = "[ A" + nvar + " ]";
                        aux[1] = variaveis.get(cont);
                        aux[2] = "1";
                        gra.add(aux);
                        nvar++;
                    }
                    cont++;
                }
            }
            i++;
        }
        return passo3(varia, gra, nvar);
    }

    public ArrayList<String[]> passo3(ArrayList<String> varia, ArrayList<String[]> gra, int nvar) {
        int cont2 = 0;
        boolean existe = false;
        int i = 0;
        int cont = 0;

        while (i < gra.size()) {
            String[] producao = gra.get(i);
            String linha = producao[1];
            ArrayList<String> variaveis = divide_prod(linha);
            if (variaveis.size() > 2) {
                int tamanho = variaveis.size();

                String prod1 = producao[1].substring(producao[1].indexOf(variaveis.get(1)));
                //

                cont2 = 0;
                while ((cont2 < varia.size()) & (!existe)) {
                    if (varia.get(cont2).contains("A" + nvar)) {
                        existe = true;
                        nvar++;
                    }
                    cont2++;
                }
                //
                producao[1] = producao[1].replace(prod1, "[ A" + nvar + " ]");
                gra.set(i, producao);
                tamanho--;
                cont = 1;
                while (tamanho > 2) {
                    String aux1[] = new String[3];
                    aux1[0] = "[ A" + nvar + " ]";
                    nvar++;

                    //

                    cont2 = 0;
                    while ((cont2 < varia.size()) & (!existe)) {
                        if (varia.get(cont2).contains("A" + nvar)) {
                            existe = true;
                            nvar++;
                        }
                        cont2++;
                    }
                    //

                    aux1[1] = variaveis.get(cont) + " " + "[ A" + nvar + " ]";
                    aux1[2] = "1";
                    gra.add(gra.size(), aux1);
                    tamanho--;
                    cont++;
                }
                if (tamanho == 2) {
                    String aux1[] = new String[3];
                    aux1[0] = "[ A" + nvar + " ]";
                    aux1[1] = variaveis.get(variaveis.size() - 2) + " " + variaveis.get(variaveis.size() - 1);
                    aux1[2] = "1";
                    gra.add(gra.size(), aux1);
                    nvar++;
                }
            }
            i++;
        }
        return gra;
    }

    public boolean teste_variavel(String string) {
        if (string.indexOf(".") >= 0) {
            string = string.replace(".", "a");
        }
        if (string.indexOf(",") >= 0) {
            string = string.replace(",", "a");
        }
        if (string.indexOf("!") >= 0) {
            string = string.replace("!", "a");
        }
        if (string.indexOf("?") >= 0) {
            string = string.replace("?", "a");
        }
        return string.toUpperCase().equals(string);
    }

    public ArrayList<String> divide_prod(String palavra) {
        ArrayList<String> aux = new ArrayList<String>();
        aux.clear();
        String auxiliar = null;
        while (palavra.trim().length() > 0) {
            auxiliar = palavra.substring(palavra.indexOf("["),
                    palavra.indexOf("]") + 1);
            palavra = palavra.substring(palavra.indexOf("]") + 1,
                    palavra.lastIndexOf("]") + 1);
            aux.add(auxiliar);
        }


        return aux;
    }

    /* public void existe_var(ArrayList<String> varia) {
    boolean existe = false;
    int cont = 0;
    while ((cont < varia.size()) & (!existe)) {
    if (varia.get(cont).contains("A" + nvar)) {
    existe = true;
    nvar++;
    }
    cont++;

    }
    }*/
}
