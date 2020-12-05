package Backend;

import Frontend.GameController;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Save {
    private static final String FILE_DELIM = ",";
    private static final String FILE_EXT = ".txt";

    public void newIncrementingFile(Board board, ArrayList<Profile> profiles) {
        String fileName = "Testing";
        int fileNumber = 0;

        try {
            File newFile = new File(fileName + fileNumber + FILE_EXT);

            if (newFile.createNewFile()) {
                FormatBoard(board, profiles, fileName + fileNumber + FILE_EXT);
            } else {
                while(newFile.createNewFile() == false) {
                    fileNumber = fileNumber + 1;
                    newFile = new File(fileName + fileNumber + FILE_EXT);
                }

                FormatBoard(board, profiles, fileName + fileNumber + FILE_EXT);
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Turns the board into a savable format.
     * @param board The board to save.
     * @param profiles The profiles playing on the board to save.
     * @param fileName The file name of the board.
     */
    public void FormatBoard(
        Board board, ArrayList<Profile> profiles, String fileName
        ) {
        ArrayList<String> BoardAList = new ArrayList<String>();
        FloorTile[][] T = board.getBoard();
        ArrayList<Player> Players = board.getListOfPlayers();
        BoardAList.add(T.length + FILE_DELIM + T[0].length);

        BoardAList.add(Integer.toString(profiles.size()));
        for (int i = 0; i < Players.size(); i++) {
            int[] lastPosition = Players.get(i).getLastPosition();
            BoardAList.add(Players.get(i).getName() + FILE_DELIM +
                lastPosition[0] + FILE_DELIM + lastPosition[1]);
        }


        for (int y = 0; y < T[0].length; y++) {
            for (int x = 0; x < T.length; x++) {
                BoardAList.add(
                    x + FILE_DELIM + y + FILE_DELIM + T[x][y].getTileType() + FILE_DELIM +
                    T[x][y].isOnFire() + FILE_DELIM + T[x][y].isFrozen() + FILE_DELIM +
                    FILE_DELIM + T[x][y].getTimer() + FILE_DELIM +
                    T[x][y].getTimer() + FILE_DELIM
                    );
                 

            }
        }
        WriteToFile(BoardAList, fileName);
    }


    /**
     * Writes the given data to the save file.
     * @param aList The list of data.
     * @param namingFile The name of the file to write to.
     */
    public void WriteToFile(ArrayList<String> aList, String namingFile) {
        File fileName = new File(namingFile);
        try {
            FileWriter fw = new FileWriter(fileName);
            Writer output = new BufferedWriter(fw);
            int sz = aList.size();
            for (int i = 0; i < sz; i++) {
                output.write(aList.get(i) + "\n");
            }

            output.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void DeleteFile(String fileName){
        //File
    }

  /**
   * Gets the board data in file to be used when instantiating a board.
   * @param fileName Name of the save file.
   * @return The board data.
   */
    public ArrayList<ArrayList<String>> getBoardData(String fileName) {
        ArrayList<String> contents = new ArrayList<String>();
        ArrayList<ArrayList<String>> boardDetails =
            new ArrayList<ArrayList<String>>();
        File specifiedFile = new File(fileName);

        try (Scanner myReader = new Scanner(specifiedFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                contents.add(data);
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
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
    //Not needed
    public int[] getBoardSize() {
        ArrayList<ArrayList<String>> cList;
        String tempString;
        cList = getBoardData("Testing.txt");
        tempString = cList.get(0).toString();
        String[] stringToArray = tempString.split(",", 0);
        int[] stringAToIntA = {Integer.parseInt(stringToArray[0]), Integer.parseInt(stringToArray[1])};
        return (stringAToIntA);
    }

}