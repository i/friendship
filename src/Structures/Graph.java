package Structures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner; 

public class Graph {

	int size;
	Vertex[] vertices;
	

	public Graph(Scanner sc) {
		this.vertices = new Vertex[Integer.parseInt(sc.nextLine().trim())];
		
		//Creates vertex for each student and fills 'vertices' array
		for(int i=0; i<vertices.length; i++){
			String t = sc.nextLine().trim();
			if(t.charAt(t.indexOf('|')+1) == 'y'){
				vertices[i] = new Vertex(t.substring(0, t.indexOf('|')),  
						t.substring((t.indexOf('|'))+3, t.length()), null);
			}
			else{vertices[i] = new Vertex(t.substring(0, t.indexOf('|')), null, null);}
		}
		
	
		while(sc.hasNextLine()){
			String t = sc.nextLine().trim();
			int pipe = t.indexOf('|');
			int v1 = indexForName(t.substring(0,pipe));
			int v2 = indexForName(t.substring(pipe+1));
			
			vertices[v1].neighbors = new Neighbor(v2, vertices[v1].neighbors);
			vertices[v2].neighbors = new Neighbor(v1, vertices[v2].neighbors);

			System.out.println(nameForIndex(v1) + " is now friends with " + nameForIndex(v2)+'.');
		}
	
	}
	
	String nameForIndex(int index){
		return vertices[index].name;
	}
	
	int indexForName(String n){
		for(int i=0; i<vertices.length; i++){
			if(vertices[i].name.equals(n))
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
	 *  Greedy Algorithm maybe?
	 */	
	public void shortestChain(String sName, String eName){
		int[] distance = new int[vertices.length];
		Vertex vsource	= vertices[indexForName(sName)];
		Vertex vend		= vertices[indexForName(eName)];
		ArrayList<Vertex> 	seen	= new ArrayList<Vertex>(vertices.length);
		ArrayList<Vertex> 	unseen	= new ArrayList<Vertex>(vertices.length);
		ArrayList<Vertex> 	done	= new ArrayList<Vertex>(vertices.length);
		ArrayList<Neighbor> fringe	= new ArrayList<Neighbor>(vertices.length);
		
		// step 1 - Put all vertices in unseen
		for(Vertex v : unseen)
			{unseen.add(v);}
		
		//transfer vsource to done
		unseen.remove(vsource);
		done.add(vsource);
		
		Neighbor neighborPTR = vsource.neighbors;
		for(Neighbor w = vsource.neighbors; w != null; w = w.next){
			fringe.add(w);
			distance[w.vnum]++;
		}
		
	}
	
	
	/**
	 * Prints a textual representation of the graph
	 */
	public void printGraph(){
		System.out.println(vertices.length);
		for(int v = 0; v < vertices.length; v++){
			if(vertices[v].school != null)
				System.out.println(vertices[v].name + "|y|" + vertices[v].school);
			else{
				System.out.println(vertices[v].name + "|n");
			}
		}
		for(int v = 0; v < vertices.length; v++){
			Neighbor ptr = vertices[v].neighbors;
			while(ptr != null){
				System.out.println(vertices[v].name + "|" + nameForIndex(ptr.vnum));
				ptr = ptr.next;
			}
			
		}
	}
	
	/**
	 * depth first search... what else?
	 */
	public void dfs() {
		boolean[] visited = new boolean[vertices.length];
		for (int v=0; v < visited.length; v++) {
			visited[v] = false;
		}
		for (int v=0; v < visited.length; v++) {
			if (!visited[v]) {
				System.out.println("Starting at " + vertices[v].name);
				dfs(v, visited);
			}
		}
	}
	
	// recursive DFS
	private void dfs(int v, boolean[] visited) {
		visited[v] = true;
		System.out.println("visiting " + vertices[v].name);
		for (Neighbor e=vertices[v].neighbors; e != null; e=e.next) {
			if (!visited[e.vnum]) {
				System.out.println(vertices[v].name + "--" + vertices[e.vnum].name);
				dfs(e.vnum, visited);
			}
		}
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
