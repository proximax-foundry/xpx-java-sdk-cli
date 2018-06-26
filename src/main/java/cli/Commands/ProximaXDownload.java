package cli.Commands;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import io.nem.xpx.facade.download.Download;
import io.nem.xpx.facade.download.DownloadParameter;
import io.nem.xpx.facade.download.DownloadResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static cli.ProximaXCli.*;

/**
 * Downloads the file.
 */
@Command(name = "download",
        description = "Download the file")
public class ProximaXDownload implements ProximaXCommand {

    @Option(type = OptionType.COMMAND,
            name = {"-s", "--secure"},
            title = "secure download",
            description = "Apply NEM keys privacy strategy")
    protected boolean isSecure = false;

    @Option(type = OptionType.COMMAND,
            name = {"-b", "--binary"},
            title = "download binary file",
            description = "Download binary file")
    protected boolean isBinary = false;

    @Option(type = OptionType.COMMAND,
            name = {"-f", "--file"},
            title = "download file",
            description = "Download file")
    protected boolean isFile = false;

    @Option(type = OptionType.COMMAND,
            name = {"-t", "--text"},
            title = "download text file",
            description = "Download text file")
    protected boolean isText = false;

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
        Download download = null;
        if (privateKey != null && publicKey != null) {
            JSONParser parser = new JSONParser();
            File file = new File(".");
            String nemHash = new String();

            try {
                Object fileObj = parser.parse(new FileReader(file.getAbsoluteFile() + "/configs/download.json"));
                JSONObject downloadObject = (JSONObject) fileObj;
                nemHash = (String) downloadObject.get("nemHash");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!isLocal && isRemote) {
                download = new Download(remotePeerConnection);
            } else if (isLocal && !isRemote) {
                download = new Download(localPeerConnection);
            } else {
                System.out.println("You have to choose either `-r` (remote connection) or `-l` (local connection). Run `proximax help search` to see the help.");
                System.exit(0);
            }

            try {
                if (isSecure) {
                    if (isBinary && !isFile && !isText) {
                        DownloadResult result = download.downloadBinary(DownloadParameter.create()
                                .nemHash(nemHash)
                                .securedWithNemKeysPrivacyStrategy(privateKey, publicKey)
                                .build());
                        System.out.println(result.getData());
                    } else if (!isBinary && isFile && !isText) {
                        DownloadResult result = download.downloadFile(DownloadParameter.create()
                                .nemHash(nemHash)
                                .securedWithNemKeysPrivacyStrategy(privateKey, publicKey)
                                .build());
                        System.out.println(result.getData());
                    } else if (!isBinary && !isFile && isText) {
                        DownloadResult result = download.downloadTextData(DownloadParameter.create()
                                .nemHash(nemHash)
                                .securedWithNemKeysPrivacyStrategy(privateKey, publicKey)
                                .build());
                        System.out.println(result.getData());
                    } else {
                        System.out.println("You have to choose either `-b`, `-t` or `-f` only.");
                    }
                } else {
                    if (isBinary && !isFile && !isText) {
                        DownloadResult result = download.downloadBinary(DownloadParameter.create()
                                .nemHash(nemHash)
                                .build());
                        System.out.println(result.getData());
                    } else if (!isBinary && isFile && !isText) {
                        DownloadResult result = download.downloadFile(DownloadParameter.create()
                                .nemHash(nemHash)
                                .build());
                        System.out.println(result.getData());
                    } else if (!isBinary && !isFile && isText) {
                        DownloadResult result = download.downloadTextData(DownloadParameter.create()
                                .nemHash(nemHash)
                                .build());
                        System.out.println(result.getData());
                    } else {
                        System.out.println("You have to choose either `-b`, `-t` or `-f` only.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        } else {
            System.out.println("You have to set the private/public key. Run `proximax help announce` to see the help.");
        }
    }
}