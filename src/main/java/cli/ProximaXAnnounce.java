package cli;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;

@Command(name = "announce",
        description = "Set credentials for ProximaX SDK")
public class ProximaXAnnounce extends ProximaXCredentials {

    @Override
    public void run() {
        if (private_key != null) {
            System.out.println("Private key has been successfully set");
        } else {
            System.out.println("You have to set the private key. Run `proximax help announce` to see the help.");
        }
    }
}
