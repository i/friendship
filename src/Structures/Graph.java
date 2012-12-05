package Structures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner; 

import Structures.Graph.Friend;

public class Graph {

	int size;  //number of edges
	ArrayList<Edge> edges;
	ArrayList<Person> people;
	Hashtable<String , Integer>  hash;
	int dfscount;
	

	public Graph(Scanner sc) {
		this.size = Integer.parseInt(sc.nextLine().trim());
		this.people = new ArrayList<Person>(this.size);
		this.hash = new Hashtable<String, Integer>(this.size);
		this.edges = new ArrayList<Edge>();
		this.dfscount = 1;
		
		//Creates vertex for each student and fills 'people' array
		for(int i=0; i<this.size; i++){
			String t = sc.nextLine().trim().toLowerCase();
			if (t.charAt(t.indexOf('|')+1) == 'y'){
				people.add(new Person(t.substring(0, t.indexOf('|')),  
						t.substring((t.indexOf('|'))+3, t.length()), null));
			}
			else{
				people.add(new Person(t.substring(0, t.indexOf('|')), null, null));
			}
			hash.put(t.substring(0, t.indexOf('|')), i);
		}
		//reads edges
		while(sc.hasNextLine()){
			String t = sc.nextLine().trim().toLowerCase();
			int pipe = t.indexOf('|');
			int v1 = indexForName(t.substring(0,pipe));
			int v2 = indexForName(t.substring(pipe+1));
			
			people.get(v1).friends = new Friend(v2, people.get(v1).friends);
			people.get(v2).friends = new Friend(v1, people.get(v2).friends);
			edges.add(new Edge(people.get(v1), people.get(v2)));

			System.out.println(nameForIndex(v1) + " is now friends with " + nameForIndex(v2)+'.');
		}

	}
	
	String nameForIndex(int index){
		return people.get(index).name;
	}
	
	int indexForName(String name){
		return hash.get(name);
	}
	
	class Edge{
		public Person p1;
		public Person p2;
		public Edge(Person p1, Person p2){
			this.p1 = p1;
			this.p2 = p2;
		}
	}
		
	public class Person {
		String name;	
		String school;
		Friend friends;
		int dfsnum;
		int back;
		
		public Person(String name, String school, Friend friends ) {
			this.name = name;
			this.school = school;
			this.friends = friends;
			this.dfsnum = 0;
			this.back = 0;
		}
	}
	
	class Friend{
		public int index; 
		public Friend next;
		public Friend possiblefriends;
		public String name;
		public String school;
	
		public Friend(int index, Friend next){
			this.index = index;
			this.next = next;
			this.school = people.get(index).school;
			this.name = people.get(index).name;
		}		
	}	

	/**
	 * Makes a graph consisting only of the students at a certain school.
	 * Prints the graph in the same format as input file.
	 * 
	 */	
	public ArrayList<Person> atSchool(String schoolName){
		ArrayList<Person> students = new ArrayList<Person>();
		for (int i = 0; i < people.size(); i++){			
			if(people.get(i).school != null && people.get(i).school.equals(schoolName)){
				Person temp = new Person(people.get(i).name, schoolName, null);
				Friend newfriends = null;
				Friend curr = people.get(i).friends;
				while(curr != null){
					if(curr.school != null && curr.school.equals(schoolName)){
						newfriends = new Friend(curr.index, newfriends);
					}
					curr = curr.next;
				}
				temp.friends = newfriends;
				students.add(temp);		
			}
		}
		for(Person p : students){System.out.println(p.name + "|y|" + p.school);}
		
		for(int v = 0; v < students.size(); v++){
			Friend ptr = students.get(v).friends;
			while(ptr != null){
				System.out.println(students.get(v).name + "|" + nameForIndex(ptr.index));
				ptr = ptr.next;
			}
		}
		return students;
	}

	/**
	 *  Finds the shortest path between two people.
	 *  Prints the sequence of names.
	 *  Greedy Algorithm maybe?
	 */	
	public void shortestChain(String source, String end){
		
		
	}
	
	/**
	 * Prints a textual representation of the graph
	 */
	public void printGraph(){
		System.out.println(people.size());
		for(int v = 0; v < people.size(); v++){
			if(people.get(v).school != null){
				System.out.println(people.get(v).name + "|y|" + people.get(v).school);
			}
			else{
				System.out.println(people.get(v).name + "|n");
			}
		}
		for(int v = 0; v < people.size(); v++){
			Friend ptr = people.get(v).friends;
			while(ptr != null){
				System.out.println(people.get(v).name + "|" + nameForIndex(ptr.index));
				ptr = ptr.next;
			}
			
		}
	}
	
	/**
	 * depth first search... what else?
	 */
//	public void dfs() {
//		boolean[] visited = new boolean[people.size()];
//		for (int v = 0; v < visited.length; v++) {
//			visited[v] = false;
//		}
//		for (int v = 0; v < visited.length; v++) {
//			if (!visited[v]) {
//				System.out.println("Starting at " + people.get(v).name);
//				dfs(v, visited);
//			}
//		}
//	}
	// recursive DFS

	/**
	 * finds cliques (separate groups) at a particular school.
	 * prints the subgraphs in format of input file.
	 */
	public void cliques(String school){
		int numcliques = 0;
		for(int i=0; i<numcliques; i++){
			System.out.println("Clique " + i + ": ");
			
		}
	}

	/**
	 * finds and prints the names of all people who are connectors
	 * separated by commas, in any order.
	 */
	
	public void connectors(){
		boolean[] cons = new boolean[people.size()];
		boolean[] visited = new boolean[people.size()];
		
		
		for (int v = 0; v < visited.length; v++) {
			if (!visited[v]) {
				System.out.println("starting dfs again");
				dfs(v, visited, cons);
			}
		}
		System.out.println("Connectors:");
		for(int i=0; i<cons.length; i++){
			if(cons[i]){
				System.out.println(people.get(i).name);
			}
		}
	}
	private void  dfs(int v, boolean[] visited, boolean[] cons) {
		visited[v] = true;
		people.get(v).dfsnum = dfscount;	people.get(v).back = dfscount;
		dfscount++;
		System.out.println("visiting " + people.get(v).name + " num: " + people.get(v).dfsnum);

		for (Friend w = people.get(v).friends; w != null; w=w.next) {
			if (!visited[w.index]) {
				System.out.println(people.get(v).name + "--" + people.get(w.index).name);
				dfs(w.index, visited, cons);
				if(people.get(v).dfsnum > people.get(w.index).back) {
					people.get(v).back = Math.min(people.get(v).back , people.get(w.index).back);
				}
				else{
					if(people.get(v).dfsnum != 1){
						cons[v] = true; System.out.println(people.get(v).name + " is a connector.");
					}
					else{
						System.out.println("!!!!!!!!!!!!!!!"+ people.get(v).name);
					}
				}
			}
			else{//w is already visited
				people.get(v).back = Math.min(people.get(w.index).back, people.get(v).dfsnum);
				System.out.println(people.get(w.index).name+" is visited. making " + people.get(v).name + "'s back: "
							+ Math.min(people.get(w.index).back, people.get(v).dfsnum));
			}	
		}
			
	}
	
}
