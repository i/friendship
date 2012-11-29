package Structures;

import java.util.Hashtable;
import java.util.Scanner; 

public class Graph {

	int size;
	Vertex[] vertices;
	
	public Graph(Scanner sc) {
		this.vertices = new Vertex[Integer.parseInt(sc.nextLine().trim())];
		
		
		//Creates vertex for each student and fills 'vertices' array
		for(int i=0; i<vertices.length; i++){
			String t = sc.nextLine();
			if(t.charAt(t.indexOf('|')+1) == 'y'){
				vertices[i] = new Vertex(t.substring(0, t.indexOf('|')),  
						t.substring((t.indexOf('|'))+3, t.length()-1), null);
			}
			else{vertices[i] = new Vertex(t.substring(0, t.indexOf('|')), null, null);}
		}
		System.out.println(this.vertices.toString());
	
		while(sc.hasNext()){
			String t = sc.nextLine();
			int pipe = t.indexOf('|');
			int v1 = indexForName(t.substring(0,pipe));
			int v2 = indexForName(t.substring(pipe+1));
			adjLists[v1].neighbors = new Neighbor(v2, adjLists[v1].neighbors);
			adjLists[v2].neighbors = new Neighbor(v1, adjLists[v2].neighbors);
		}
	
	}
	
	int indexForName(String name){
		for(int i=0; i<vertices.length; i++){
			if(vertices[i].equals(name))
				return i;
		}
		return -1;
	}
		

	class Neighbor{
		public int vnum;
		public Neighbor next;
		public Neighbor(int vnum, Neighbor nbr){
			this.vnum = vnum;
			next = nbr;
		}
	}

	public class Vertex {
		String name;	
		String school;
		Neighbor neighbors;
		
		public Vertex(String name, String school, Neighbor neighbors ) {
			this.name = name;
			this.school = school;
			this.neighbors = neighbors;
		}
	}
	Vertex[] adjLists;
	


	/**
	 * Makes a graph consisting only of the students at a certain school.
	 * Prints the graph in the same format as input file.
	 */	
	public String atSchool(String school){
		return null;
	
	}

	/**
	 *  Finds the shortest path between two people.
	 *  Prints the sequence of names.
	 */	
	public String shortestChain(String sName, String eName){
		return null;
		
	}
	
	/**
	 * finds cliques (separate groups) at a particular school.
	 * prints the subgraphs in format of input file.
	 */
	public String cliques(String school){
		return null;
		
	}

	/**
	 * finds and prints the names of all people who are connectors
	 * separated by commas, in any order.
	 */
	public String connectors(){
		return null;
	}

}
