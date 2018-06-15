package cli.Commands;

import com.github.rvesse.airline.annotations.Command;

import java.io.File;

@Command(name = "clear",
        description = "Remove your private/public key")
public class ProximaXClear implements ProximaXCommand {

    @Override
    public void run() {
        File file = new File("./credentials");
        if (file.exists()) {
            file.delete();
        } else {
            System.out.println("You are trying to delete nonexistent private/public key file.");
        }
    }
}
