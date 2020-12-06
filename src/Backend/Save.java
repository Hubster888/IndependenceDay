package Backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that contains method that are used for saving into the file.
 * @author Owen Warner, Robbie Southman
 * @version 1.0
 */
public class Save {
    public static final String DATA_PERSISTENCE = "src/Data";
    private static final String FILE_DELIM = ",";
    private static final String FILE_EXT = ".txt";
    private static final String NEW_LINE = "\n";
    private static final String GEN_ERROR = "An error occurred.";
    private static final String FILE_DIR = "src/Saves/";

    /**
     * Saves the game data to an incremented file.
     * @param silkBag The silk bag to save.
     * @param board   The board to save.
     */
    public static void newIncrementingFile(Board board) {
        int fileNumber = 0;

        try {
            File newFile = new File(FILE_DIR + fileNumber + FILE_EXT);

            while (newFile.exists()) {
                fileNumber++;
                newFile = new File(FILE_DIR + fileNumber + FILE_EXT);
            }

            formatBoard(board, FILE_DIR + fileNumber + FILE_EXT);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Turns the board into a savable format.
     * @param board     The board to save.
     * @param arrayList The profiles playing on the board to save.
     * @param fileName  The file name of the board.
     */
    public static void formatBoard(Board board,
                                   String fileName) {
        ArrayList<String> BoardAList = new ArrayList<String>();
        FloorTile[][] T = board.getBoard();
        ArrayList<Player> players = board.getListOfPlayers();

        BoardAList.add(T.length + FILE_DELIM + T[0].length);
        BoardAList.add(board.getNoOfActions() + FILE_DELIM + board.getNoOfFloors());
        BoardAList.add(Integer.toString(players.size()));

        for (int i = 0; i < players.size(); i++) {
            String playerDetails = "";
            int[] lastPosition = players.get(i).getLastPosition();

            playerDetails = players.get(i).getName() + FILE_DELIM +
            lastPosition[0] + FILE_DELIM + lastPosition[1];
            
            ArrayList<ArrayList<String>> playersHand = players.get(i).getActionTiles();

            for (ArrayList<String> tile : playersHand)
                playerDetails += FILE_DELIM + tile.get(0) + FILE_DELIM + tile.get(1);

            BoardAList.add(playerDetails);
        }


        for (int y = 0; y < T[0].length; y++) {
            for (int x = 0; x < T.length; x++) {
                BoardAList.add(x + FILE_DELIM + y + FILE_DELIM +
                        T[x][y].getTileType() + FILE_DELIM + T[x][y].isOnFire()
                        + FILE_DELIM + T[x][y].isFrozen() + FILE_DELIM +
                        T[x][y].getOrientation() + FILE_DELIM +
                        T[x][y].getTimer() + FILE_DELIM + T[x][y].isFixed());
            }
        }
        WriteToFile(BoardAList, fileName);
    }


    /**
     * Writes the given data to the save file.
     * Will save to data persistence file or incremented file depending on how
     * its called.
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
     * @param fileName Name of the save file.
     * @return The board data.
     */
    public static ArrayList<ArrayList<String>> getBoardData(String fileName) {
        ArrayList<String> contents = new ArrayList<>();
        ArrayList<ArrayList<String>> boardDetails = new ArrayList<>();

        File dataPersistence = new File(DATA_PERSISTENCE);
        File specifiedFile;
        if (dataPersistence.exists()) {
            specifiedFile = dataPersistence;
        } else {
            specifiedFile = new File(FILE_DIR + fileName + FILE_EXT);
        }

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
            ArrayList<String> t = new ArrayList<>();
            for (String detail : line.split(FILE_DELIM)) {
                t.add(detail);
            }
            boardDetails.add(t);
        }

        return boardDetails;
    }

    /**
     * Deletes a file from given fileName.
     */
    public static void DeleteFile(String fileName) {
        File f = new File(fileName);
        f.delete();
    }
}