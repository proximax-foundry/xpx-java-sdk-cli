package cli.Commands;

import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;


/**
 * A class to implement credentials setting.
 */
public abstract class ProximaXCredentials implements ProximaXCommand {

    @Option(type = OptionType.COMMAND, name = {"-p", "--private"}, title = "Sender private key",
            description = "Private key to announce yourself to NEM blockchain")
    protected String privateKey;

    @Option(type = OptionType.COMMAND, name = {"-b", "--public"}, title = "Receiver public key",
            description = "Receiver public key")
    protected String publicKey;
}
