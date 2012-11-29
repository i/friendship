package Structures;

import java.util.Scanner; // sup

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
