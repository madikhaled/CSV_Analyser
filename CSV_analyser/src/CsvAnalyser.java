// Description: Classe pour lire un fichier csv
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvAnalyser {
    private static final char DEFAULT_SEPARATOR = ','; //declaration de la virgule comme separateur
    private static final char DEFAULT_QUOTE = '"'; //declaration de la quote comme quote

    public List<List<String>> parse(String csvFile) throws IOException { //methode pour lire le fichier csv
        List<List<String>> result = new ArrayList<>();  //declaration d'une liste de liste
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) { //ouverture du fichier
            String line;    //declaration d'une chaine
            List<String> row = new ArrayList<>();   //declaration d'une liste
            StringBuilder field = new StringBuilder();  //declaration d'une chaine
            boolean inQuotes = false;

            while ((line = br.readLine()) != null) {    //lire le fichier ligne par ligne
                for (int i = 0; i < line.length(); i++) { //parcourir chaque caractere de la ligne
                    char c = line.charAt(i); //recuperer le caractere a la position i
                    switch (c) { //switch pour chaque cas
                        case DEFAULT_QUOTE: //si le caractere est une quote
                            if (!inQuotes) {    //si on est pas dans une quote
                                inQuotes = true;   //on passe a true
                            } else if (i < line.length() - 1 && line.charAt(i + 1) == DEFAULT_QUOTE) { //si on est dans une quote et que le caractere suivant est une quote

                                field.append(DEFAULT_QUOTE);    //ajouter la quote a la chaine
                                    i++;    //incrementer i pour passer a la quote suivante
                            } else {   //sinon
                                inQuotes = false;  //on passe a false
                            }
                            break;
                        case DEFAULT_SEPARATOR: //si le caractere est une virgule
                            if (inQuotes) { //si on est dans une quote
                                field.append(c); //ajouter le caractere a la chaine
                            } else {
                                row.add("'"+field.toString()+"'"); //ajouter la chaine a la liste
                                field.setLength(0);  //vider la chaine
                            }
                            break;
                        default:
                            field.append(c);   //ajouter le caractere a la chaine
                            break;
                    }
                }

                // si on est dans une quote a la fin de la ligne, cela signifie que le champ continue sur la ligne suivante
                if (inQuotes) {
                    field.append("\\n"); // ajouter un retour a la ligne a la chaine
                } else {
                    row.add("'"+field.toString()+"'"); //ajouter la chaine a la liste
                    result.add(row); //ajouter la liste a la liste de liste
                    row = new ArrayList<>(); //creer une nouvelle liste
                    field.setLength(0);  //    vider la chaine
                }
            }

            // si on est dans une quote a la fin du fichier, cela signifie que le champ n'est pas fermé correctement
            if (inQuotes) {
                throw new IOException("Unclosed quoted field at end of CSV file");
            }
        }
        return result;
    }
}

