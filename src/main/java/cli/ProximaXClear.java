package cli;

import com.github.rvesse.airline.annotations.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Scanner;

@Command(name = "clear",
        description = "Remove your private key")
public class ProximaXClear implements ProximaXCommand {

    @Override
    public void run() {
        File file = new File("../private_key");
        if (file.exists()) {
            file.delete();
        } else {
            System.out.println("You are trying to delete nonexistent private key file.");
        }
    }

}
