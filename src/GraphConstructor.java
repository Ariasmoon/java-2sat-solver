import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class GraphConstructor {
    public String file;

    public List<String> lines = new ArrayList<>();

    public List<String> getLines(){
        return lines;
    }

    public GraphConstructor(String file) throws FileNotFoundException {
        this.file = file;
        Scanner sc;
        sc = new Scanner(new File(this.file));
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            this.lines.add(s);
        }
        sc.close();
    }

    public int findNumber(int line, int index){
        String[] number = lines.get(line).split(" ");
        return Integer.parseInt(number[index]);
    }

    //Crée le graphe des implications, à partir d'un fichier texte
    public Graph<String> constructGraph() {
        Graph<String> graph = new Graph<>(2 * this.findNumber(1, 2));
        for (int i = 2; i < this.getLines().size(); i++){
            try {
                graph.addArc(graph.litteralToVertex( - this.findNumber(i, 0)), graph.litteralToVertex(this.findNumber(i, 1)), "");
                graph.addArc(graph.litteralToVertex( - this.findNumber(i, 1)), graph.litteralToVertex(this.findNumber(i, 0)), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return graph;
    }
}
