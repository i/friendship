//Ian Lozinski
//Elizabeth Gao

package apps;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import structures.Graph;



public class Friends {
	
	
static Scanner userin = new Scanner(System.in);
	
	static char getOption() {
		System.out.print("Choose action: \n");
		System.out.print("\t1) Students at school\n");
		System.out.print("\t2) Shortest intro chain\n");
		System.out.print("\t3) Cliques at school\n");
		System.out.print("\t4) Connectors\n");
		System.out.print("\tq) Quit\n");
		char response = userin.nextLine().toLowerCase().charAt(0);
		while (response != '1' && response != '2' && response != '3' && response != '4' && response != 'q') {
			System.out.print("\tYou must enter one of 1, 2, 3, 4, 5, or q! ");
			response = userin.nextLine().toLowerCase().charAt(0);
		}
		return response;
	}

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("Enter graph file name: ");
		String graphFile = userin.nextLine();
		Graph graph;
		try{
			graph = new Graph(new Scanner(new File(graphFile)));

			char option;
			while((option = getOption()) != 'q'){
				if(option == '1'){
					System.out.println("Enter school name: ");
					Graph school = graph.atSchool(userin.nextLine().toLowerCase());
					school.printGraph();
				}
				else if(option == '2'){
					System.out.println("Enter name of person:");
					String sName = userin.nextLine().toLowerCase();
					System.out.println("Enter name of person they want to meet:");
					String eName = userin.nextLine().toLowerCase();
					graph.shortestChain(sName, eName);					
				}
				else if(option == '3'){
					System.out.println("Enter school name: ");
					Graph subgraph = graph.atSchool(userin.nextLine().toLowerCase());
					ArrayList<Graph> cliques = subgraph.cliques();
					for(int k = 1; k < cliques.size()+1; k++){
						System.out.println("Clique " + k + ":");
						cliques.get(k-1).printGraph();
					}					
				}
				else if(option == '4'){
					graph.connectors();
//					do connectors
				}
			}
		}catch(Exception FileNotFoundException){
			System.err.println("Not valid file! Closing program!");
			
		}
	}
}
