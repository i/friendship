//Ian Lozinski
//Elizabeth Gao

package structures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner; 
import java.util.Stack;

import structures.Graph.Friend;


public class Graph {

	int size;  //number of vertices
	ArrayList<Person> people;
	Hashtable<String , Integer>  hash;
	int dfscount;
	int sccount;
	String schoolname;
	

	public Graph(Scanner sc) {
		this.size = Integer.parseInt(sc.nextLine().trim());
		this.people = new ArrayList<Person>(this.size);
		this.hash = new Hashtable<String, Integer>(this.size);
		this.dfscount = 1;
		this.sccount = 1;
		this.schoolname = null;
		
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

//			System.out.println(nameForIndex(v1) + " is now friends with " + nameForIndex(v2)+'.');
		}

	}
	
	//alternate graph constructor -- takes in arrayList instead of scanner
	public Graph (ArrayList<Person> people){
		this.dfscount = 1;
		this.sccount = 1;
		this.size = people.size();
		this.hash = new Hashtable<String, Integer>(this.size);
		this.people = people;
		try{
			this.schoolname = people.get(0).school;
		}catch (Exception indexOutOfBoundsException){
			System.out.println("Invalid school name!");
			return;
		}

		for(int i = 0; i < people.size(); i++) {
			this.hash.put(people.get(i).name, i);
		}
		for(int i = 0; i<this.people.size(); i++){
			this.hash.put(this.people.get(i).name, i);
			Friend newfriends = null;
			Friend ptr = (this.people.get(i).friends);
			while(ptr != null){
				
				ptr.index = this.indexForName(ptr.name);
				if(ptr.index != -1){
					newfriends = new Friend(ptr.index, newfriends);
				}
				ptr = ptr.next;
			}
			this.people.get(i).friends = newfriends;
		}
	}
	
	String nameForIndex(int index){
		return people.get(index).name;
	}
	
	int indexForName(String name){
		try{
			return hash.get(name);
		}catch(Exception NullPointerException){
			return -1;
		}
	}
	
	public class Person {
		String name;	
		String school;
		Friend friends;
		int dfsnum;
		int back;
		int scnum;
		
		public Person(String name, String school, Friend friends ) {
			this.scnum = -1;
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
		public String name;
		public String school;
	
		public Friend(String name, Friend next){
			this.index = indexForName(name);
			this.name = name;
			this.school = people.get(index).school;
			this.next = next;
		}
		
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
	public Graph atSchool(String schoolName){
		ArrayList<Person> students = new ArrayList<Person>();

		for (int i = 0; i < people.size(); i++){			
			Person p = people.get(i);
			if(p.school != null && p.school.equals(schoolName)){
				Friend newfriends = null;
				Friend temp = p.friends;
				while(temp != null){
					if(temp.school != null && temp.school.equals(schoolName)){
						newfriends = new Friend(temp.index, newfriends);
					}
					temp = temp.next;
				}
				students.add(new Person(p.name, schoolName, newfriends));
			}
		}
		Graph subgraph = new Graph(students);
		return subgraph;
	}

	/**
	 *  Finds the shortest path between two people.
	 *  Prints the sequence of names.
	 *  Greedy Algorithm maybe?
	 */	
	public void shortestChain(String sname, String ename){
		if(indexForName(sname) == -1 || indexForName(ename) == -1){
			System.out.println("invalid name!");
			return;
		}
		Stack<String> chain = new Stack<String>();
		int snum = indexForName(sname);
		Person start = people.get(snum);
		Person end = people.get(indexForName(ename));
		boolean[] visited = new boolean[size];
		
		people.get(indexForName(sname)).scnum = 0;
		scdfs(indexForName(sname), visited);
		if(end.scnum == -1){
			System.out.println("They can NEVER be friends!!!");
			return;
		}
		Person ptr = end;
		while(ptr.scnum != 0){
			chain.push(ptr.name);
			Friend t = ptr.friends;
			int min = ptr.scnum;
			while(t != null){
				if(people.get(t.index).scnum < min){
					ptr = people.get(t.index);
					break;
				}
				t = t.next;
			}
		}
		chain.push(start.name);
		String ret = "";
		while(chain.size() > 1){
			ret = ret + chain.pop() + ", ";
		}
		ret = "Shortest path: " + ret + chain.pop() + ".";
		System.out.println(ret);
		
		//resets scnumbers
		for(int i = 0; i < people.size(); i++){
			people.get(i).scnum = -1;
		}
	}
	private void scdfs(int v, boolean[] visited){
		visited[v] = true;
		for (Friend e = people.get(v).friends; e != null; e=e.next){
			if(!visited[e.index]){
				people.get(e.index).scnum = sccount;
				sccount++;	
				scdfs(e.index, visited);
				sccount--;
				people.get(v).scnum = Math.min(people.get(v).scnum, people.get(e.index).scnum + 1);	
			}
			else{//already visited
				people.get(v).scnum = Math.min(people.get(e.index).scnum + 1, people.get(v).scnum);
			}
		}
	}
	
	/**
	 * Prints a textual representation of the graph
	 */
	public void printGraph(){
		if(size == 0){
			System.out.println("Empty graph.");
			return;
		}
		System.out.println(size);
		for(int v = 0; v < size; v++){
			if(people.get(v).school != null){
				System.out.println(people.get(v).name + "|y|" + people.get(v).school);
			}
			else{
				System.out.println(people.get(v).name + "|n");
			}
		}
		for(int v = 0; v < size; v++){
			Friend ptr = people.get(v).friends;
			while(ptr != null){
				if(v < ptr.index){
					System.out.println(people.get(v).name + "|" + nameForIndex(ptr.index));
				}
				ptr = ptr.next;
			}
		}
	}
	
	/**
	 * finds cliques (separate groups) at a particular school.
	 * prints the subgraphs in format of input file.
	 */
	public ArrayList<Graph> cliques(){
		ArrayList<Graph> cliqueslist = new ArrayList<Graph>();
		boolean[] visited = new boolean[size];
		boolean[] vtemp = new boolean[size];
		
		for(int i=0; i<size; i++){
			ArrayList<Person> clique = new ArrayList<Person>();
			if (!visited[i]){		
				System.out.println("Starting on new clique");
				cliquedfs(vtemp, i, visited);
				for(int j=0; j<visited.length; j++){
					if(vtemp[j])
						clique.add(new Person(nameForIndex(j), schoolname, people.get(j).friends));
						vtemp[j] = false;
				}
				Graph tempgraph = new Graph(clique);
				cliqueslist.add(tempgraph);
			}
		}
		return cliqueslist;
	}
	
	private void cliquedfs(boolean[] newlyVisited, int v, boolean[] visited) {
		visited[v] = true;
		newlyVisited[v] = true;
		for (Friend e=people.get(v).friends; e != null; e=e.next) {
			if (!visited[e.index]) {
				cliquedfs(newlyVisited, e.index, visited);
			}
		}
	}
	

	/**
	 * finds and prints the names of all people who are connectors
	 * separated by commas, in any order.
	 */
	
	public void connectors(){
		boolean[] knex = new boolean[size];
		boolean[] visited = new boolean[size];
		
		for (int v = 0; v < visited.length; v++) {
			if (!visited[v]) {
				dfscon(v, visited, knex);
			}
		}
		int min = 0;
		for(int n = people.size()-1; n >= 0; n--){
			if(people.get(n).back == 1 && n != 0){
				min++;
				if(min >= 2){knex[0] = true;}
			}
		}
		System.out.print("Connectors:");
		String s = "";
		for(int i=0; i<knex.length; i++){
			if(knex[i]){
				s = people.get(i).name + ", "+ s;
			}
		}
		System.out.println(s);
		dfscount = 1;
		for(Person p: people){
			p.dfsnum = 0;
			p.back = 0;
		}
	}
	private void  dfscon(int v, boolean[] visited, boolean[] knex) {
		visited[v] = true;
		people.get(v).dfsnum = dfscount;
		people.get(v).back = dfscount;
		dfscount++;
//		System.out.println("Visiting " + people.get(v).name + " num: " + people.get(v).dfsnum);

		for (Friend w = people.get(v).friends; w != null; w = w.next) {
			if (!visited[w.index]) {
				dfscon(w.index, visited, knex);
				if(people.get(v).dfsnum > people.get(w.index).back) {
					people.get(v).back = Math.min(people.get(v).back , people.get(w.index).back);
				}
				if(people.get(v).dfsnum <= people.get(w.index).back && v != 0){
					knex[v]=true;
				}
			}
			else{//w is already visited
				people.get(v).back = Math.min(people.get(v).back, people.get(w.index).dfsnum);
				//System.out.println(people.get(w.index).name + " " + people.get(w.index).dfsnum + "/" + people.get(w.index).back);
			}	
		}
		
	}
	
}
