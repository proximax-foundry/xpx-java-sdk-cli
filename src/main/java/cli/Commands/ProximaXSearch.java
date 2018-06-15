package cli.Commands;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import com.sun.tools.javac.util.Assert;
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
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static cli.ProximaXCli.remotePeerConnection;

@Command(name = "search",
        description = "Search using Keywords")
public class ProximaXSearch implements ProximaXCommand {
    private String publicKey;
    private String privateKey;


    @Option(type = OptionType.COMMAND,
            name = {"-k", "--keyword"},
            title = "keyword",
            description = "Keyword to search")
    protected String keyword;

    private void readCredentials() {
        File file = new File("./credentials");
        if (file.exists()) {
            Scanner input = null;
            try {
                input = new Scanner(file);
                publicKey = input.nextLine();
                privateKey = input.nextLine();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } else {
            System.out.println("You have to set the private/public key. Run `proximax help announce` to see the help.");
        }
    }

    @Override
    public void run() {
        try {
            readCredentials();
            Search search = new Search(remotePeerConnection);
            List<ResourceHashMessageJsonEntity> result = search.searchByName(privateKey, publicKey, keyword);
            System.out.println(result);
        } catch (ApiException | InterruptedException | ExecutionException | PeerConnectionNotFoundException e) {
            e.printStackTrace();
        }
    }
}