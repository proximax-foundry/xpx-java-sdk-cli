package cli.Commands;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import io.nem.xpx.exceptions.ApiException;
import io.nem.xpx.exceptions.PeerConnectionNotFoundException;
import io.nem.xpx.facade.search.Search;
import io.nem.xpx.model.ResourceHashMessageJsonEntity;
import org.nem.core.crypto.KeyPair;
import org.nem.core.crypto.PrivateKey;
import org.nem.core.crypto.PublicKey;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static cli.ProximaXCli.*;

@Command(name = "search",
        description = "Search using Keywords")
public class ProximaXSearch implements ProximaXCommand {

    @Option(type = OptionType.COMMAND,
            name = {"-k", "--keyword"},
            title = "keyword",
            description = "Search by keyword")
    protected String keyword = "";

    @Option(type = OptionType.COMMAND,
            name = {"-n", "--name"},
            title = "name",
            description = "Search by name")
    protected String name = "";

    @Option(type = OptionType.COMMAND,
            name = {"-key"},
            title = "meta data key/value",
            description = "Key to search")
    protected String key = "";

    @Option(type = OptionType.COMMAND,
            name = {"-value"},
            title = "meta data key/value",
            description = "Value to search")
    protected String value = "";

    @Option(type = OptionType.COMMAND,
            name = {"-r", "--remote"},
            title = "remote connection",
            description = "Remote connection")
    protected boolean isRemote = false;

    @Option(type = OptionType.COMMAND,
            name = {"-l", "--local"},
            title = "local connection",
            description = "Local connection")
    protected boolean isLocal = false;

    @Override
    public void run() {
        Search search = null;
        if (privateKey != null && publicKey != null) {
            try {
                if (!isLocal && isRemote) {
                    search = new Search(remotePeerConnection);
                } else if (isLocal && !isRemote) {
                    search = new Search(localPeerConnection);
                } else {
                    System.out.println("You have to choose either `-r` (remote connection) or `-l` (local connection). Run `proximax help search` to see the help.");
                    System.exit(0);
                }
                if (!keyword.equals("") && name.equals("") && key.equals("") && value.equals("")) {
                    List<ResourceHashMessageJsonEntity> result = search.searchByKeyword(privateKey, publicKey, keyword);
                    System.out.println(result);
                } else if (keyword.equals("") && !name.equals("") && key.equals("") && value.equals("")) {
                    List<ResourceHashMessageJsonEntity> result = search.searchByName(privateKey, publicKey, name);
                    System.out.println(result);
                } else if (keyword.equals("") && keyword.equals("") && !key.equals("") && !value.equals("")) {
                    List<ResourceHashMessageJsonEntity> result = search.searchByMetaDataKeyValue(privateKey, publicKey, key, value);
                    System.out.println(result);
                } else {
                    System.out.println("Check your query or run `proximax help search` to see the help.");
                }
            } catch (ApiException | InterruptedException | ExecutionException | PeerConnectionNotFoundException e) {
                e.printStackTrace();
            }
            System.exit(0);
        } else {
            System.out.println("You have to set the private/public key. Run `proximax help announce` to see the help.");
        }
    }

}