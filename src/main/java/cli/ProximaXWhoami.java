package cli;

import com.github.rvesse.airline.annotations.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@Command(name = "whoami",
        description = "Check the private key you have")
public class ProximaXWhoami implements ProximaXCommand {

    @Override
    public void run() {
        File file = new File("../private_key");
        if (file.exists()) {
            Scanner input = null;
            try {
                input = new Scanner(file);
                while (input.hasNextLine()) {
                    String contents = input.nextLine();
                    System.out.println("Your private key is: " + contents);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } else {
            System.out.println("You have to set the private key. Run `proximax help announce` to see the help.");
        }
    }

}
