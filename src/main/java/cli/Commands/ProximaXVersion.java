package cli.Commands;

import com.github.rvesse.airline.annotations.Command;

/**
 * Shows an actual version of a ProximaX SDK.
 */
@Command(name = "version",
        description = "Show the version of a ProximaX SDK")

public class ProximaXVersion implements ProximaXCommand {

    @Override
    public void run() {
        System.out.println("The version of a ProximaX SDK is 0.1.0-beta.1-SNAPSHOT");
    }
}
