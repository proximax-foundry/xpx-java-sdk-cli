package cli;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;

import java.io.IOException;
import java.io.PrintWriter;

@Command(name = "announce",
        description = "Set credentials for ProximaX SDK")
public class ProximaXAnnounce extends ProximaXCredentials {

    @Override
    public void run() {
        if (private_key != null) {
            try {
                PrintWriter file = new PrintWriter("../private_key");
                file.println(private_key);
                file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Private key has been successfully set");
        } else {
            System.out.println("You have to set the private key. Run `proximax help announce` to see the help.");
        }
    }

}
