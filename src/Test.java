import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        try {
            Desktop.getDesktop().browse(new URL("http://www.google.com").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
