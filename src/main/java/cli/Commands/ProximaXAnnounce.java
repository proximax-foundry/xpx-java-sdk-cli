package cli.Commands;

import com.github.rvesse.airline.annotations.Command;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Private and public keys setting to use a ProximaX SDK.
 * Run `proximax announce -p privateKey -b publicKey` to set credentials.
 */
@Command(name = "announce",
        description = "Set credentials to use a ProximaX SDK")
public class ProximaXAnnounce extends ProximaXCredentials {

    @Override
    public void run() {
        if (privateKey != null || publicKey != null) {
            try {
                PrintWriter file = new PrintWriter("./credentials");
                file.println(privateKey);
                file.println(publicKey);
                file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Private key and public key have been successfully set");
        } else {
            System.out.println("You have to set the private/public key. Run `proximax help announce` to see the help.");
        }
    }

}
