import Backend.*;
import java.util.ArrayList;

import Backend.Board;
import Backend.Profile;

public class Main {

	public static void main(String[] args) {
		Profile prof = new Profile("Hub");
		ArrayList<Profile> listOfProfs = new ArrayList<Profile>();
		listOfProfs.add(prof);
		int[] pos = new int[2];
		pos[0] = 0;
		pos[1] = 0;
		Board bor = new Board(4, 4, listOfProfs, pos);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(bor.getTile(i, j).getTileType() + " ");
			}
			System.out.println("\n");
		}
	}

}
