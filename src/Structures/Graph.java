package Structures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner; 

import Structures.Graph.Friend;

public class Graph {

	int size;  //number of edges
	int numEdges;
	ArrayList<Edge> edges;
	Person[] people;
	Hashtable<String , Integer>  hash;
	

	public Graph(Scanner sc) {
		this.people = new Person[Integer.parseInt(sc.nextLine().trim())];
		this.hash = new Hashtable<String, Integer>(this.people.length);
		this.edges = new ArrayList<Edge>();
		
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
			edges.add(new Edge(v1, v2));

			System.out.println(nameForIndex(v1) + " is now friends with " + nameForIndex(v2)+'.');
			numEdges++;
		}

	}
	
	String nameForIndex(int index){
		return people[index].name;
	}
	
	int indexForName(String name){
		return hash.get(name);
	}
	
	class Edge{
		public int v1;
		public int v2;
		public Edge(int v1, int v2){
			this.v1 = v1;
			this.v2 = v2;
		}
	}
		

	class Friend{
		public int index;
		public Friend friend;
		public Friend possiblefriends;
		public Friend(int index, Friend friend){
			this.index = index;
			this.friend = friend;
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
	public Person[] subgraph(String school){
		ArrayList<Integer> studentnums = new ArrayList<Integer>();
		for (Person p : people){
			if(p.school == school){
				studentnums.add(indexForName(p.name));
			}
		}
		
		Person[] students = new Person[studentnums.size()];
		for (int i=0; i<students.length; i++){
			Friend possiblefriends = people[i].friends;
			Friend schoolFriends;
			while(possiblefriends!=null){
				if(people[possiblefriends.index].school.equals(school)){
					schoolFriends.friend = new Friend(index, friend)
				}
				possiblefriends = possiblefriends.friend;
			}
			Person p = new Person(nameForIndex(studentnums.get(i)), school, friends)
		}
		return null;
	}

	/**
	 *  Finds the shortest path between two people.
	 *  Prints the sequence of names.
	 *  Greedy Algorithm maybe?
	 */	
	public void shortestChain(String source, String end){
		ArrayList<Person> unseen = new ArrayList<Person>();
		ArrayList<Person> done   = new ArrayList<Person>();
		
		

		
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
				System.out.println(people[v].name + "|" + nameForIndex(ptr.index));
				ptr = ptr.friend;
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
		for (Friend e=people[v].friends; e != null; e=e.friend) {
			if (!visited[e.index]) {
				System.out.println(people[v].name + "--" + people[e.index].name);
				dfs(e.index, visited);
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
