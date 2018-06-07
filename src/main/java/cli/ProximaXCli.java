package cli;

import com.github.rvesse.airline.Cli;
import com.github.rvesse.airline.builder.CliBuilder;
import com.github.rvesse.airline.parser.errors.ParseArgumentsUnexpectedException;
import com.github.rvesse.airline.parser.errors.ParseCommandMissingException;
import com.github.rvesse.airline.parser.errors.ParseCommandUnrecognizedException;
import com.github.rvesse.airline.parser.errors.ParseException;
import com.github.rvesse.airline.parser.errors.ParseOptionMissingException;

public class ProximaXCli {

    public static void main(String... args) {

        Cli<ProximaXCommand> proximaxCommandCLI = getProximaXCommandCLI();

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
                .withDescription("CLI to access ProximaX Storage")
                .withCommands(
                    ProximaXVersion.class,
                    ProximaXHelp.class,
                    ProximaXAnnounce.class
                );

        return builder.build();
    }
}