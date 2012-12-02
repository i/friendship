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
	

	public Graph(Scanner sc) {
		this.size = Integer.parseInt(sc.nextLine().trim());
		this.people = new ArrayList<Person>(this.size);
		this.hash = new Hashtable<String, Integer>(this.size);
		this.edges = new ArrayList<Edge>();
		
		//Creates vertex for each student and fills 'people' array
		for(int i=0; i<this.size; i++){
			String t = sc.nextLine().trim();
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
			String t = sc.nextLine().trim();
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
		
		public Friend add(Friend newfriend, Friend old){
			if(this == null){
				return newfriend;
			}
			newfriend.next = old;
			return newfriend;
		}
	}
	
	public static void add(Friend newf, Friend old){
		newf.next = old;
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
		Person vsource = people.get(indexForName(source));
		Person vend    = people.get(indexForName(end));
		int [] distance = new int[people.size()];
		ArrayList<Person> seen   = new ArrayList<Person>();
		ArrayList<Person> unseen = new ArrayList<Person>();
		ArrayList<Person> done   = new ArrayList<Person>();
		ArrayList<Friend> fringe = new ArrayList<Friend>();
		
		for(Person p : people){unseen.add(p);}	 	//step 1. put all vertices in unseen
		unseen.remove(vsource); done.add(vsource);  //step 2. transfer vsource to done
		for(Friend w = vsource.friends; w != null; w = w.next){
			fringe.add(w); 							//step 3. for each neighbor, put w into fringe
			distance[w.index]++;
			while(!fringe.isEmpty()){
				
			}
		}
		
		
		

		
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
	public void dfs() {
		boolean[] visited = new boolean[people.size()];
		for (int v=0; v < visited.length; v++) {
			visited[v] = false;
		}
		for (int v=0; v < visited.length; v++) {
			if (!visited[v]) {
				System.out.println("Starting at " + people.get(v).name);
				dfs(v, visited);
			}
		}
	}
	// recursive DFS
	private void dfs(int v, boolean[] visited) {
		visited[v] = true;
		System.out.println("visiting " + people.get(v).name);
		for (Friend e=people.get(v).friends; e != null; e=e.next) {
			if (!visited[e.index]) {
				System.out.println(people.get(v).name + "--" + people.get(e.index).name);
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
