import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        //Si jamais le chemin relatif ne fonctionne pas, mettez le fichier en chemin absolu
        String filename = "formulas/testSet0/formula0.txt";
        if (0 < args.length) {
            filename = args[0];
        }

        //normalMain(filename);
        verboseMain(filename);
    }

    //normalMain = main en mode normal (satisfaisabilité)
    public static void normalMain(String filename) throws Exception {
        GraphConstructor graphConstructor = new GraphConstructor(filename);

        Graph<String> graph = graphConstructor.constructGraph();
        Graph<String> transpose = graph.transposeGraph();

        List<Integer> vertexInOrder = graph.runDFS(false);
        Collections.reverse(vertexInOrder);
        List<List<Integer>> components = transpose.runDFSscc(vertexInOrder,false);
        graph.isSatisfiable(components);

    }

    //verboseMain = main avec affichages des algorithmes utilisés
    public static void verboseMain(String filename) throws Exception {
        GraphConstructor graphConstructor = new GraphConstructor(filename);

        System.out.println("Graphe généré à partir du fichier :");
        Graph<String> graph = graphConstructor.constructGraph();
        System.out.println(graph.toString());
        System.out.println("Graphe transposé :");
        Graph<String> transpose = graph.transposeGraph();
        System.out.println(transpose.toString()); //Ok

        System.out.println("Appel de la fonction Kosaraju :");
        List<Integer> vertexInOrder = graph.runDFS(true); //
        System.out.println("Ordre des sommets après le premier parcours en profondeur itéré : ");
        System.out.println(vertexInOrder + "\n");

        Collections.reverse(vertexInOrder);

        System.out.println("Ordre des sommets après le reverse :");
        System.out.println(vertexInOrder + "\n");

        List<List<Integer>> components = transpose.runDFSscc(vertexInOrder,true);

        System.out.println("\nIl y a " + components.size() + " composantes fortements connexes, voici la liste :");
        int i = 1;
        for (List<Integer> component : components) {

            System.out.println("Composante numéro " + i + " : " + component);
            i++;
        }
        graph.isSatisfiable(components);
    }
}
