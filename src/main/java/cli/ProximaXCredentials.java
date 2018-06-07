package cli;

import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;

public abstract class ProximaXCredentials implements ProximaXCommand {

    @Option(type = OptionType.COMMAND, name = {"-p", "--private"}, title = "sender private key",
            description = "private key to announce yourself to NEM blockchain")
    protected String private_key;
}
