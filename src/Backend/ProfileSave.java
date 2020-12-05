
package Backend;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileSave {

  /**
   * @return
   */
  private static Boolean fileExists() {
    File file  = new File("profileList.txt");
    if (file.exists()) {
      return true;
    } else {
      return false;
    }
  }

  public static void createFile() {
    File file  = new File("profileList.txt");
    try {
      file.createNewFile();
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  private static Boolean profileExists(Profile profile) {
    File file  = new File("profileList.txt");
    try(Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (isContain(line, profile.getName())) {
              return true;
            }
        }
    } catch(FileNotFoundException e) {
          System.out.println(e);
    }
        return false;
  }

  public static void addProfile(Profile profile) {
    if(fileExists() && profileExists(profile)) {
      final JFrame parent = new JFrame();

      parent.pack();
      parent.setVisible(true);

      JOptionPane.showMessageDialog(parent,"This profile already exists!");
    } else {
      if(fileExists()) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("profileList.txt", true))) {
          bw.write(profile.getName() + " " + profile.getWins() + " " + profile.getLosses());
          bw.newLine();
          bw.close();
        }catch(Exception e) {
          System.out.println(e);
        }
      } else {
        System.out.println("creating file");
        createFile();
        addProfile(profile);
      }

    }
  }

  public static void updateProfile(Profile profile, Boolean playerWon) throws IOException {
      if(profileExists(profile)) {

    	  Profile tempProfile = new Profile();
    	  String inputFileName = "profileList.txt";
    	  String outputFileName = "tempList.txt";

    	  try {
    	      File inputFile = new File(inputFileName);
    	      File outputFile = new File(outputFileName);

    	      try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    	               BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

    	          String line = null;
    	          while ((line = reader.readLine()) != null) {
    	              if (!isContain(line, profile.getName())) {
    	                  writer.write(line);
    	                  writer.newLine();
    	              }else {
    	            	  String[] lineBreakDown = line.split(" ");
    	                  tempProfile.setName(lineBreakDown[0]);
    	                  tempProfile.setWins(Integer.parseInt(lineBreakDown[1]));
    	                  tempProfile.setLosses(Integer.parseInt(lineBreakDown[2]));
    	                  if(playerWon){tempProfile.addWin();}else{tempProfile.addLoss();}
    	              }
    	          }
    	          writer.write(tempProfile.getName() + " " + tempProfile.getWins() + " " + tempProfile.getLosses() + "\n");
    	      }

    	      if (inputFile.delete()) {
    	          // Rename the output file to the input file
    	          if (!outputFile.renameTo(inputFile)) {
    	              throw new IOException("Could not rename " + outputFileName + " to " + inputFileName);
    	          }
    	      } else {
    	          throw new IOException("Could not delete original input file " + inputFileName);
    	      }
    	  } catch (IOException ex) {
    	      ex.printStackTrace();
    	  }

    } else {
      addProfile(profile);
      updateProfile(profile,playerWon);
    }

  }

  private static boolean isContain(String source, String subItem) {
         String pattern = "\\b" + subItem + "\\b";
         Pattern p = Pattern.compile(pattern);
         Matcher m = p.matcher(source);
         return m.find();
    }

  public static Profile getProfile(String profileName) {
	  File file  = new File("profileList.txt");
	    try(Scanner scanner = new Scanner(file)) {
	      while (scanner.hasNextLine()) {
	        String line = scanner.nextLine();
	        if (isContain(line, profileName)) {
	              String[] splitLine = line.split(" ");
	              return new Profile(profileName, Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]));
	            }
	        }
	    } catch(FileNotFoundException e) {
	          System.out.println(e);
	    }
	    Profile prof = new Profile(profileName, 0, 0);
	    addProfile(prof);
	    return prof;
  }
}
