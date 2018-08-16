package cli;

import cli.Commands.*;
import com.github.rvesse.airline.Cli;
import com.github.rvesse.airline.builder.CliBuilder;
import com.github.rvesse.airline.parser.errors.*;
import io.proximax.xpx.facade.connection.LocalHttpPeerConnection;
import io.proximax.xpx.facade.connection.RemotePeerConnection;
import io.proximax.xpx.factory.ConnectionFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.nem.core.node.NodeEndpoint;
import static java.lang.Math.toIntExact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


/**
 * Main class. Define the commands we have in a CLI.
 */
public class ProximaXCli {

    private static RemotePeerConnection remotePeerConnection;
    private static LocalHttpPeerConnection localPeerConnection;

    public static RemotePeerConnection createRemotePeerConnection() {
        JSONParser parser = new JSONParser();
        File file = new File("");
        try {
            Object fileObj = parser.parse(new FileReader(file.getAbsolutePath() + "/configs/connection.json"));
            JSONObject object = (JSONObject) fileObj;

            JSONObject remote = (JSONObject) object.get("remote");
            String url = (String) remote.get("url");
            remotePeerConnection = new RemotePeerConnection(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return remotePeerConnection;
    }

    public static LocalHttpPeerConnection createLocalHttpPeerConnection() {
        JSONParser parser = new JSONParser();
        File file = new File("");
        try {
            Object fileObj = parser.parse(new FileReader(file.getAbsolutePath() + "/configs/connection.json"));
            JSONObject object = (JSONObject) fileObj;

            JSONObject local = (JSONObject) object.get("local");
            String network = (String) local.get("network");
            String protocol = (String) local.get("protocol");
            String host = (String) local.get("host");
            int port = toIntExact((Long) local.get("port"));
            String multiaddress = (String) local.get("multiaddress");
            localPeerConnection = new LocalHttpPeerConnection(
                    ConnectionFactory.createNemNodeConnection(network, protocol, host, port),
                    ConnectionFactory.createIPFSNodeConnection(multiaddress)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localPeerConnection;
    }

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
        System.exit(0);
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