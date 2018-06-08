package cli.Commands;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;

@Command(name = "upload",
        description = "Upload the file")
public class ProximaXUpload implements ProximaXCommand {

    @Override
    public void run() {

    }
}

/*

  binary
    Upload upload = new Upload(remotePeerConnection); // or localPeerConnection

    Map<String, String> metaData = new HashMap<String, String>();
metaData.put("key1", "value1");

        UploadBinaryParameter parameter = UploadBinaryParameterBuilder
        .messageType(MessageTypes.PLAIN)  // or SECURE
        .senderPrivateKey(xPvkey).receiverPublicKey(xPubkey)
        .name("test_pdf_file_v1")
        .data(FileUtils.readFileToByteArray(new File("src//test//resources//test_pdf_file_v1.pdf")))
        .contentType("application/pdf").keywords("test_pdf_file_v1")
        .metadata(JsonUtils.toJson(metaData))
        .build();

//  Return the NEM Hash
        String nemhash = upload.uploadFile(parameter).getNemHash();

text

Upload upload = new Upload(remotePeerConnection); // or localPeerConnection

Map<String, String> metaData = new HashMap<String, String>();
metaData.put("key1", "value1");

UploadBinaryParameter parameter = UploadBinaryParameterBuilder
        .messageType(MessageTypes.PLAIN)  // or SECURE
		.senderPrivateKey(xPvkey).receiverPublicKey(xPubkey)
		.name("test_pdf_file_v1")
		.data(FileUtils.readFileToByteArray(new File("src//test//resources//test_pdf_file_v1.pdf")))
		.contentType("application/pdf").keywords("test_pdf_file_v1")
		.metadata(JsonUtils.toJson(metaData))
		.build();

//  Return the NEM Hash
String nemhash = upload.uploadFile(parameter).getNemHash();

*/