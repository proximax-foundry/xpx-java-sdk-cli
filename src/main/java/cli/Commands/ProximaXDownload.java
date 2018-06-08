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

import static cli.ProximaXCli.remotePeerConnection;

@Command(name = "download",
        description = "Download the file")
public class ProximaXDownload implements ProximaXCommand {

    @Option(type = OptionType.COMMAND,
            name = {"-s", "--secure"},
            title = "secure download",
            description = "Apply Nem keys privacy strategy")
    protected boolean isSecure = false;

    @Override
    public void run()  {
        JSONParser parser = new JSONParser();
        File file = new File(".");
        String privateKey = new String();
        String publicKey = new String();
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
            Object credentials = parser.parse(new FileReader(file.getAbsoluteFile() + "/configs/credentials.json"));
            JSONObject credObject = (JSONObject) credentials;
            privateKey = (String) credObject.get("privateKey");
            publicKey = (String) credObject.get("publicKey");

            if (isSecure) {
                DownloadResult result = download.downloadTextData(DownloadParameter.create()
                        .nemHash(nemHash)
                        .securedWithNemKeysPrivacyStrategy(privateKey, publicKey)
                        .build());
                System.out.println(result.getData());
            } else {
                DownloadResult result = download.downloadTextData(DownloadParameter.create()
                        .nemHash(nemHash)
                        .build());
                System.out.println(result.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}