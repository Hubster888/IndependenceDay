public class ProfileSave{

  private static Bool fileExists(){
    File file  = new File("desktop/profileList.txt");
    if (file.exists()) {
      return true;
    }else {
      return false;
    }
  }

  private static void createFile(){
    File file  = new File("desktop/profileList.txt");
    file.createNewFile();
  }

  private static Bool profileExists(Profile profile){
    File file  = new File("desktop/profileList.txt");

    try {
      Scanner scanner = new Scanner(file);

      //now read the file line by line...
      int lineNum = 0;
      while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          lineNum++;
          if(line.contains(profile.getName())) {
              return true;
            }
          }
        } catch(FileNotFoundException e) {
          //handle this
        }
        return false;
  }

  public static void addProfile(Profile profile){
    if(/*profileExists(profile)*/true){
      final JFrame parent = new JFrame();
      JButton button = new JButton();

      button.setText("Click me to show dialog!");
      parent.add(button);
      parent.pack();
      parent.setVisible(true);

      String name = JOptionPane.showInputDialog(parent,
              "What is your name?", null);
    }
  }
  // Add profile if not in file

  // Update profile if in file
  public static void main(String args[]){
    addProfile()
  }
}

// File format
// String Int Int /n
// eg.
//Hubert 23 43
//Jhon 34 1
