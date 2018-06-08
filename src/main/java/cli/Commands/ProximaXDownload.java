package cli.Commands;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;

@Command(name = "download",
        description = "Upload the file")
public class ProximaXDownload implements ProximaXCommand {

    @Override
    public void run() {

    }
}


/*
binary
Download download = new Download(localPeerConnection);
String timeStamp = System.currentTimeMillis() + "";
DownloadResult message = download.downloadBinaryOrFile("e0ca6d958ba01592ddeaa40e9d810a4314707f6673c2271e5d0eeb018a4be997");

//  Get the byte[] data
byte[] data =  message.getData();



text data

Download download = new Download(localPeerConnection);
DownloadResult message = download.downloadTextData("627e3b70b2e902c8ca33447216535c5f0cc90da408a3db9b5b7ded95873bb47c");

//  Get the Text data
String data = new String(message.getData(), "UTF-8");

 */