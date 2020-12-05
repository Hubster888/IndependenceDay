package Backend;

import Frontend.GameController;
import java.io.*;
import java.util.Scanner;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Save {
    private static final String FILE_DELIM = ",";
    private static final String FILE_EXT = ".txt";
    private static final String NEW_LINE = "\n";
    private static final String GEN_ERROR = "An error occurred.";
    private static final String FILE_DIR = "src/Saves/";

    /**
     * Saves the game data to an incremented file.
     * @param board The board to save.
     */
    public static void newIncrementingFile(Board board) {
        int fileNumber = 0;

        try {
            File newFile = new File(FILE_DIR + fileNumber + FILE_EXT);

            while (newFile.exists()) {
                fileNumber++;
                newFile = new File(FILE_DIR + fileNumber + FILE_EXT);
            }

            FormatBoard(board, board.getListOfPlayers(),
                FILE_DIR + fileNumber + FILE_EXT);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Turns the board into a savable format.
     * @param board The board to save.
     * @param arrayList The profiles playing on the board to save.
     * @param fileName The file name of the board.
     */
    public static void FormatBoard(Board board, ArrayList<Player> arrayList, String fileName) {
        ArrayList<String> BoardAList = new ArrayList<String>();
        FloorTile[][] T = board.getBoard();
        ArrayList<Player> Players = board.getListOfPlayers();
        BoardAList.add(T.length + FILE_DELIM + T[0].length);

        BoardAList.add(Integer.toString(arrayList.size()));
        for (int i = 0; i < Players.size(); i++) {
            int[] lastPosition = Players.get(i).getLastPosition();
            BoardAList.add(Players.get(i).getName() + FILE_DELIM +
                lastPosition[0] + FILE_DELIM + lastPosition[1]);
        }

        for (int y = 0; y < T[0].length; y++) {
            for (int x = 0; x < T.length; x++) {
                BoardAList.add(x + FILE_DELIM + y + FILE_DELIM +
                    T[x][y].getTileType() + FILE_DELIM + T[x][y].isOnFire() +
                    FILE_DELIM + T[x][y].isFrozen() + FILE_DELIM +
                    FILE_DELIM + T[x][y].getFireTime() + FILE_DELIM +
                    T[x][y].getFrozenTime() + FILE_DELIM);

            }
        }
        WriteToFile(BoardAList, fileName);
    }

    /**
     * Writes the given data to the save file.
     * 
     * @param aList      The list of data.
     * @param namingFile The name of the file to write to.
     */
    public static void WriteToFile(ArrayList<String> aList, String namingFile) {
        File file = new File(namingFile);

        try {
            FileWriter fw = new FileWriter(file);
            Writer output = new BufferedWriter(fw);
            int sz = aList.size();

            for (int i = 0; i < sz; i++) {
                output.write(aList.get(i) + NEW_LINE);
            }

            output.close();
        } catch (Exception e) {
            System.out.println(GEN_ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Gets the board data in file to be used when instantiating a board.
     * 
     * @param fileName Name of the save file.
     * @return The board data.
     */
    public static ArrayList<ArrayList<String>> getBoardData(String fileName) {
        ArrayList<String> contents = new ArrayList<String>();
        ArrayList<ArrayList<String>> boardDetails = new ArrayList<ArrayList<String>>();
        File specifiedFile = new File(FILE_DIR + fileName + FILE_EXT);

        try (Scanner myReader = new Scanner(specifiedFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                contents.add(data);
            }
        } catch (Exception e) {
            System.out.println(GEN_ERROR);
            e.printStackTrace();
        }

        for (String line : contents) {
            ArrayList<String> t = new ArrayList<String>();
            for (String detail : line.split(FILE_DELIM))
                t.add(detail);
            boardDetails.add(t);
        }

        return boardDetails;

    }

    /*
     * Check saving works 
     * public static void main(String[] args) {
     * 
     * ArrayList<Profile> profs = new ArrayList<Profile>(); profs.add(new
     * Profile("Robbie")); Board board = new Board(1, profs);
     * 
     * newIncrementingFile(board);
     * 
     * 
     * }
     */

    /* Check loading works
    public static void main(String[] args) {
        Board board = new Board(getBoardData("0"));
        
        // Test Player has been loaded in properly.
        System.out.println(board.getListOfPlayers().get(0).getName());
        System.out.println(board.getListOfPlayers().get(0).getLastPosition()[0]);
        System.out.println(board.getListOfPlayers().get(0).getLastPosition()[1]);

        // Test board has been loaded in properly.
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++){
                switch(board.getBoard()[x][y].getTileType()){
                    case "corner":
                        System.out.print("¬");
                        break;
                    case "straight":
                        System.out.print("-");
                        break;
                    case "tShape":
                        System.out.print("T");
                        break;
                    default:
                        System.out.print("*");
                            break;
                }
            }
                System.out.println();
        }
    }
    */
}