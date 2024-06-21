public class maintomato{
    public static void main (String[] args){
        System.out.println("Filler text");
        System.out.println("||||||||||||||||||");

        // initialize list
        Rental blockbuster = new Rental();

        // add items to list
        blockbuster.addItem("0002", "Movie 2: The Sequel");
        blockbuster.addItem("0001", "Movie: The Movie");

        // Print the movies list
        blockbuster.printMap();

        // Sorting line
        System.out.println("||||||||||||||||||");
    }
}