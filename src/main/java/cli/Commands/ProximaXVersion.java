package cli.Commands;

import com.github.rvesse.airline.annotations.Command;

@Command(name = "version",
        description = "Show the version of ProximaX SDK")

public class ProximaXVersion implements ProximaXCommand {

    @Override
    public void run() {
        System.out.println("The version of ProximaX SDK is {version}");
    }
}
