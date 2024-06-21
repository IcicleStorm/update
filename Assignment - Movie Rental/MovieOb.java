import java.io.Serializable;

public class MovieOb implements Serializable {
    public boolean signedOut;
    public int SKU;
    public String name;
    public String rentedBy;

    public MovieOb(String j) {
        signedOut = false;
        name = j;
        rentedBy = "nobody";
    }

    public String toString() {
        return name;
    }
}