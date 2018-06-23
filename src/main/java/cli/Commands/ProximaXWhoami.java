package cli.Commands;

import cli.ProximaXCli;
import com.github.rvesse.airline.annotations.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Shows your public and private keys.
 */
@Command(name = "whoami",
        description = "Check the private key that you have")
public class ProximaXWhoami implements ProximaXCommand {

    @Override
    public void run() {
        File file = new File("./credentials");
        if (file.exists()) {
            Scanner input = null;
            try {
                input = new Scanner(file);
                System.out.println("Your private key is: " + input.nextLine());
                System.out.println("Your public key is: " + input.nextLine());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } else {
            System.out.println("You have to set the private/public key. Run `proximax help announce` to see the help.");
        }
    }

}
