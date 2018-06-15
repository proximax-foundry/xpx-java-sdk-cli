package cli.Commands;

import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;

public abstract class ProximaXCredentials implements ProximaXCommand {

    @Option(type = OptionType.COMMAND, name = {"-p", "--private"}, title = "Sender Private Key",
            description = "private key to announce yourself to NEM blockchain")
    protected String privateKey;

    @Option(type = OptionType.COMMAND, name = {"-b", "--public"}, title = "Receiver Public Key",
            description = "public key")
    protected String publicKey;
}
