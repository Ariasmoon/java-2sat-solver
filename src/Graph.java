import java.util.*;

public class Graph<Label>  {

    private class Edge {
        public int source;
        public int destination;
        public Label label;

        public Edge(int from, int to, Label label) {
            this.source = from;
            this.destination = to;
            this.label = label;
        }
    }

    public LinkedList<Integer> edgeToString(Edge edge){
        LinkedList<Integer> A = new LinkedList<>();
        A.add(edge.source);
        A.add(edge.destination);
        return A;
    }

    public ArrayList<LinkedList<Integer>> sommetToString(int sommet){
        ArrayList<LinkedList<Integer>> B = new ArrayList<>();
        for(Edge edge1 : incidency.get(sommet))
            B.add(edgeToString(edge1));
        return B;
    }

    private final int cardinal;
    private final ArrayList<LinkedList<Edge>> incidency;

    public Graph(int size) {
        cardinal = size;
        incidency = new ArrayList<>(size+1);
        for (int i = 0 ; i<cardinal ; i++) {
            incidency.add(i, new LinkedList<Edge>());
        }
    }

    public int order() {
        return cardinal;
    }

    public void addArc(int source, int dest, Label label) throws Exception {
	if (Math.max(source,dest) >= this.cardinal){
	    throw new Exception("Sommets trop gros pour la taille du graphe");
	}
        this.incidency.get(source).addLast(new Edge(source,dest,label));
    }

    public String toString() {
        String result = "";
        result = result.concat("Nombre sommets : " + cardinal + "\n");
        result = result.concat("Sommets : \n");
        for (int i = 0 ; i < cardinal  ; i++) {
	    result = result.concat(i + " ");
		}
        result = result.concat("\nArcs : \n");
        for (int i = 0 ; i < cardinal ; i++) {
            for (Edge e : incidency.get(i)) {
                result = result.concat(e.source + " -> " + e.destination + ", étiquette : "
				       + e.label.toString() + "\n");
            }
        }
        return result;
    }

    //Transforme un littéral en sommet; 1->0 2->1 3->2 -1->3 -2->4 -3->5
    public int litteralToVertex(int vertex) {
        if (vertex > 0) {
            vertex = vertex - 1;
        } else {
            vertex = -(vertex) + (this.order()/ 2) - 1;
        }
        return vertex;
    }

    //Crée le transposé du graphe des implications, à partir de son fichier texte
    public Graph<Label> transposeGraph() throws Exception {
        Graph<Label> transposedGraph = new Graph<Label>(this.order());
        for (LinkedList<Edge> vertex : this.incidency){
            for (Edge edge : vertex) {
                transposedGraph.addArc(edge.destination, edge.source, edge.label);
            }
        }
        return transposedGraph;
    }

    //Effectue le parcours en profondeur et retourne les sommets dans l'ordre de fin de traitement
    public List<Integer> runDFS(boolean verbose) {
        List<Integer> traveled = new ArrayList<>();
        List<Integer> marked = new ArrayList<>();

        if(verbose){
            System.out.println("Affichage du premier parcours en profondeur :");
        }
        for (int i = 0; i < this.cardinal; i++) {
            if (!(traveled.contains(i))) {
                exploreDFS(i, traveled, marked, verbose); //Faire ca en mode tra
            }
        }
        return traveled;
    }

    //Explore récursivement le graphe (DFS)
    public void exploreDFS(int vertex, List<Integer> traveled, List<Integer> marked, boolean verbose) {
        marked.add(vertex);
        for (Edge e : this.incidency.get(vertex)) {
            if (!(marked.contains(e.destination))) {
                if(verbose)
                    System.out.println("On est au sommet " + vertex + " et on a les edges  " + sommetToString(vertex) + " et tu vas sur l'edge " + edgeToString(e) + ".");
                exploreDFS(e.destination, traveled, marked, verbose);
            }
        }

        if(!(traveled.contains(vertex))){
            traveled.add(vertex);
        }
    }

    //Retourne toutes les composantes fortement connexes d'un graphe
    public List<List<Integer>> runDFSscc(List<Integer> order, boolean verbose) {
        List<List<Integer>> stronglyConnectedComponent = new ArrayList<>();
        List<Integer> marked = new ArrayList<>();
        List<Integer> vertexInComponent = new ArrayList<>();
        List<Integer> traveled = new ArrayList<>();
        int currentComponent = 0;
        if (verbose){
            System.out.println("Parcours du graphe sur le transposé ;");
        }
        for (int i = 0; i < order.size(); i++) {
            if (!(traveled.contains(order.get(i)))) {
                stronglyConnectedComponent.add(new ArrayList<>());
                stronglyConnectedComponent.get(currentComponent).add(order.get(i));
                vertexInComponent.add(order.get(i));
                traveled.add(order.get(i));
                exploreDFSscc(order.get(i), traveled, stronglyConnectedComponent, currentComponent, vertexInComponent, marked, verbose);
                currentComponent++;
            } else if (!(vertexInComponent.contains(order.get(i)))) {
                stronglyConnectedComponent.get(currentComponent).add(order.get(i));
                vertexInComponent.add(order.get(i));
                if (i != order.size() - 1) {
                    currentComponent++;
                    stronglyConnectedComponent.add(new ArrayList<>());
                }
            }
        }
        return stronglyConnectedComponent;
    }

    //Fonction récursive pour DFSscc, détecte les cycles partant d'un sommet
    public void exploreDFSscc(int index, List<Integer> traveled, List<List<Integer>> stronglyConnectedComponent,
                              int currentComponent, List<Integer> vertexInComponent, List<Integer> marked, boolean verbose) {
        List<Integer> component = new ArrayList<>();
        marked.add(index);
        for (Edge edge1 : incidency.get(index)) {
            if (!(marked.contains(edge1.destination))) {
                if (verbose) {
                    System.out.println("On est au component " + currentComponent + " au sommet " + index + " et on a les edges " + sommetToString(index) + " et tu vas sur l'edge " + edgeToString(edge1) + ".");
                }
                traveled.add(edge1.destination);

                if (!(component.contains(edge1.destination)) && !(vertexInComponent.contains(edge1.destination))) {
                    component.add(edge1.destination);
                }
                exploreDFSscc(edge1.destination, traveled, stronglyConnectedComponent, currentComponent, vertexInComponent, marked, verbose);
            }
        }
        stronglyConnectedComponent.get(currentComponent).addAll(component);
        traveled.addAll(component);
        vertexInComponent.addAll(component);
    }


    //Vérifie si une formule est satisfaisable à partir des composantes fortement connexes du graphe associé
    public void isSatisfiable(List<List<Integer>> resultsFromKosaraju) {
        int k = 0;
        for (List<Integer> component : resultsFromKosaraju) {
            k=k+1;
            for (int i = 0; i < cardinal / 2; i++) {
                if (component.contains(i) && component.contains(i + (cardinal / 2))) {
                    System.out.println("Graphe non-satisfiable par la composante : " + component);
                    return;
                }
            }
        }
        System.out.println("La formule est satisfiable.");
    }
}