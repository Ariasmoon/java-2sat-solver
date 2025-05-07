# java-2sat-solver

![Langage](https://img.shields.io/badge/langage-Java-blue.svg)  

## Description

Ce projet propose un **solveur 2-SAT** en Java :  
1. Lecture d’une formule 2-SAT au format DIMACS CNF.  
2. Construction du **graphe d’implications** (2 × |C| arcs).  
3. Calcul des **composantes fortement connexes** via l’algorithme de Kosaraju.  
4. Détermination de la satisfiabilité (l’existance d’un littéral et de son opposé dans la même SCC rend la formule insatisfiable, sinon elle l’est).

**Contexte :** projet universitaire en L3 – structures de données et algorithmique, projet réalisé en binôme.
