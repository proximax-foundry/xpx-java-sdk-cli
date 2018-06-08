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

import java.util.List;
import java.util.concurrent.ExecutionException;

import static cli.ProximaXCli.remotePeerConnection;

@Command(name = "search",
        description = "Search using Keywords")
public class ProximaXSearch implements ProximaXCommand {

    @Option(type = OptionType.COMMAND,
            name = {"-k", "--keyword"},
            title = "keyword",
            description = "Keyword to search")
    protected String keyword;

    @Override
    public void run() {
        String private_key = "deaae199f8e511ec51eb0046cf8d78dc481e20a340d003bbfcc3a66623d09763";
        String public_key = "36e6fbc1cc5c3ef49d313721650b98d7d7d126a4f731d70071f4f3b4798cdc85";
        try {
            Search search = new Search(remotePeerConnection);
            List<ResourceHashMessageJsonEntity> result = search.searchByKeyword(private_key, public_key, keyword);
            Assert.checkNonNull(result);
        } catch (ApiException | InterruptedException | ExecutionException | PeerConnectionNotFoundException e) {
            e.printStackTrace();
        }
    }
}