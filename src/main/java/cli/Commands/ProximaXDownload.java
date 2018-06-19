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

import static cli.ProximaXCli.remotePeerConnection;

@Command(name = "download",
        description = "Download the file")
public class ProximaXDownload implements ProximaXCommand {
    private String publicKey;
    private String privateKey;

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

    private void readCredentials() {
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
        } else {
            System.out.println("You have to set the private/public key. Run `proximax help announce` to see the help.");
        }
    }

    @Override
    public void run()  {
        readCredentials();
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

        Download download = new Download(remotePeerConnection);

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
    }
}