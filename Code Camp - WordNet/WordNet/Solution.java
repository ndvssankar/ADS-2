import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String synsetsFileName = scan.nextLine();
		String hypernymFileName = scan.nextLine();
		String line = scan.nextLine();
		WordNet wordNet = new WordNet("Files/" + synsetsFileName, 
			"Files/" + hypernymFileName);

		switch(line) {
			case "Graph":
			wordNet.printGraph();
			break;
			case "Queries":
			break;
			default:
			break;
		}

	}
}