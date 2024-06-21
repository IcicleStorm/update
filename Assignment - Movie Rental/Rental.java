import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class Rental {

    // Create a map object called data with a key and the movie object
    public Map<String, MovieOb> data;
  
    public Rental () {
        File file = new File("T:/OSS-ICS4U1-1/coll4870/Assignment - Movie Rental/rentalfile.txt");
        if (file.exists() && !file.isDirectory()) {
            System.out.println("File exists");
            loadData();
        } else {
            System.out.println("The file does not exist");
            // Data created into hashmap
            data = new HashMap<String, MovieOb>();
        }
    }

    public void addItem (String i, String j) {
        // Create a new MovieOb to use in the hashmap
        MovieOb ob = new MovieOb(j);
        data.put(i, ob);

        this.writeData();
    }

    // This writes data to the file
    private void writeData() {
        try { 
            // create an output stream for the file and use it in the object output stream
            FileOutputStream fileOut = new FileOutputStream("T:/OSS-ICS4U1-1/coll4870/Assignment - Movie Rental/rentalfile.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            // write the data to the file via output stream
            objectOut.writeObject(data);

            // close the output streams
            fileOut.close();
            objectOut.close();

        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    // Stops warnings
    @SuppressWarnings("unchecked")
    // This loads the file into the Map
    private void loadData() {
        try {
          // create an output stream for the file and use it in the object output stream
            FileInputStream fileIn = new FileInputStream("T:/OSS-ICS4U1-1/coll4870/Assignment - Movie Rental/rentalfile.txt");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            data = (HashMap<String, MovieOb>)objectIn.readObject();

            // close the input stream
            fileIn.close();
            objectIn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Stops warnings
    @SuppressWarnings("rawtypes")
    public void printMap() {
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + ((MovieOb)pair.getValue()).name + " is signed out by: " + ((MovieOb)pair.getValue()).rentedBy); // change if needed
        } 
        
    }
}