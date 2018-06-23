package cli;

import cli.Commands.*;
import com.github.rvesse.airline.Cli;
import com.github.rvesse.airline.builder.CliBuilder;
import com.github.rvesse.airline.parser.errors.*;
import io.nem.xpx.facade.connection.RemotePeerConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Main class. Define the commands we have in a CLI.
 */
public class ProximaXCli {

    public static RemotePeerConnection remotePeerConnection = new RemotePeerConnection("http://dev-gateway.internal.proximax.io:8881");
    public static String publicKey;
    public static String privateKey;

    private static void readCredentials() {
        File file = new File("./credentials");
        if (file.exists()) {
            Scanner input = null;
            try {
                input = new Scanner(file);
                privateKey = input.nextLine();
                publicKey = input.nextLine();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        }
    }

    public static void main(String... args) {

        Cli<ProximaXCommand> proximaxCommandCLI = getProximaXCommandCLI();
        readCredentials();
        ProximaXCommand command;

        try {
            command = proximaxCommandCLI.parse(args);
            command.run();
        } catch (ParseCommandMissingException e) {
            main("help");
        } catch (ParseCommandUnrecognizedException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Run `proximax help` to see the help");
        } catch (ParseArgumentsUnexpectedException | ParseOptionMissingException e) {
            System.err.println("Error: " + e.getMessage());
            String commandName = args[0];
            System.err.format("Run `proximax help %s` to see the help for the command `%s`\n", commandName, commandName);
        } catch (ParseException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Run `proximax help` to see the help");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Cli<ProximaXCommand> getProximaXCommandCLI() {
        //noinspection unchecked
        CliBuilder<ProximaXCommand> builder = Cli.<ProximaXCommand>builder("proximax")
                .withDescription("A CLI to access ProximaX Storage")
                .withCommands(
                    ProximaXVersion.class,
                    ProximaXHelp.class,
                    ProximaXAnnounce.class,
                    ProximaXWhoami.class,
                    ProximaXClear.class,
                    ProximaXUpload.class,
                    ProximaXDownload.class,
                    ProximaXSearch.class
                );

        return builder.build();
    }
}