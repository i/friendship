package Structures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner; 

public class Graph {

	int size;  //number of edges
	int edges;
	Person[] people;
	Hashtable<String , Integer>  hash;
	

	public Graph(Scanner sc) {
		this.people = new Person[Integer.parseInt(sc.nextLine().trim())];
		this.hash = new Hashtable<String, Integer>(this.people.length);
		
		//Creates vertex for each student and fills 'people' array
		for(int i=0; i<people.length; i++){
			String t = sc.nextLine().trim();
			if(t.charAt(t.indexOf('|')+1) == 'y'){
				people[i] = new Person(t.substring(0, t.indexOf('|')),  
						t.substring((t.indexOf('|'))+3, t.length()), null);
			}
			else{people[i] = new Person(t.substring(0, t.indexOf('|')), null, null);}
			hash.put(t.substring(0, t.indexOf('|')), i);
		}
		
		
		//reads edges
		while(sc.hasNextLine()){
			String t = sc.nextLine().trim();
			int pipe = t.indexOf('|');
			int v1 = indexForName(t.substring(0,pipe));
			int v2 = indexForName(t.substring(pipe+1));
			
			people[v1].friends = new Friend(v2, people[v1].friends);
			people[v2].friends = new Friend(v1, people[v2].friends);

			System.out.println(nameForIndex(v1) + " is now friends with " + nameForIndex(v2)+'.');
			edges++;
		}
	
	}
	
	String nameForIndex(int index){
		return people[index].name;
	}
	
	int indexForName(String name){
		return hash.get(name);
	}
		

	class Friend{
		public int vnum;
		public Friend next;
		public Friend(int vnum, Friend nbr){
			this.vnum = vnum;
			next = nbr;
		}
	}

	public class Person {
		String name;	
		String school;
		Friend friends;
		
		public Person(String name, String school, Friend friends ) {
			this.name = name;
			this.school = school;
			this.friends = friends;
		}
	}
	


	/**
	 * Makes a graph consisting only of the students at a certain school.
	 * Prints the graph in the same format as input file.
	 * 
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

		//OLD SHORTEST CHAIN METHOD
//		int[] distance = new int[people.length];
//		Person vsource	= people[indexForName(sName)];
//		Person vend		= people[indexForName(eName)];
//		ArrayList<Person> 	seen	= new ArrayList<Person>(people.length);
//		ArrayList<Person> 	unseen	= new ArrayList<Person>(people.length);
//		ArrayList<Person> 	done	= new ArrayList<Person>(people.length);
//		ArrayList<Friend> fringe	= new ArrayList<Friend>(people.length);
//		
//		// step 1 - Put all people in unseen
//		for(Person v : unseen)
//			{unseen.add(v);}
//		
//		// step 2 - transfer vsource to done
//		unseen.remove(vsource);
//		done.add(vsource);
//		
//		// step 3 - 
//		Friend friendsPTR = vsource.friends;
//		for(Friend w = vsource.friends; w != null; w = w.next){
//			fringe.add(w);
//			distance[w.vnum]++;
//		}
//		
	}
	
	/**
	 * Prints a textual representation of the graph
	 */
	public void printGraph(){
		System.out.println(people.length);
		for(int v = 0; v < people.length; v++){
			if(people[v].school != null)
				System.out.println(people[v].name + "|y|" + people[v].school);
			else{
				System.out.println(people[v].name + "|n");
			}
		}
		for(int v = 0; v < people.length; v++){
			Friend ptr = people[v].friends;
			while(ptr != null){
				System.out.println(people[v].name + "|" + nameForIndex(ptr.vnum));
				ptr = ptr.next;
			}
			
		}
	}
	
	/**
	 * depth first search... what else?
	 */
	public void dfs() {
		boolean[] visited = new boolean[people.length];
		for (int v=0; v < visited.length; v++) {
			visited[v] = false;
		}
		for (int v=0; v < visited.length; v++) {
			if (!visited[v]) {
				System.out.println("Starting at " + people[v].name);
				dfs(v, visited);
			}
		}
	}
	
	// recursive DFS
	private void dfs(int v, boolean[] visited) {
		visited[v] = true;
		System.out.println("visiting " + people[v].name);
		for (Friend e=people[v].friends; e != null; e=e.next) {
			if (!visited[e.vnum]) {
				System.out.println(people[v].name + "--" + people[e.vnum].name);
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
