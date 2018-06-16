package cli.Commands;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import io.nem.xpx.facade.upload.*;
import static io.nem.xpx.facade.DataTextContentType.TEXT_HTML;
import static io.nem.xpx.facade.DataTextContentType.TEXT_PLAIN;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import static java.util.Collections.singletonMap;
import static cli.ProximaXCli.remotePeerConnection;

@Command(name = "upload",
        description = "Upload the file")
public class ProximaXUpload implements ProximaXCommand {
    private String publicKey;
    private String privateKey;

    @Option(type = OptionType.COMMAND,
            name = {"-b", "--binary"},
            title = "Binary file",
            description = "Upload binary file")
    protected boolean isBinary;

    @Option(type = OptionType.COMMAND,
            name = {"-f", "--file"},
            title = "File",
            description = "Upload plain file")
    protected boolean isFile;

    @Option(type = OptionType.COMMAND,
            name = {"-z", "--zip"},
            title = "ZipFile",
            description = "Upload ZipFile")
    protected boolean isZip;

    @Option(type = OptionType.COMMAND,
            name = {"-m", "--multiple"},
            title = "Multiple files",
            description = "Upload multiple files")
    protected boolean isMultiple;

    @Option(type = OptionType.COMMAND,
            name = {"-t", "--text"},
            title = "Text file",
            description = "Upload text file")
    protected boolean isText;

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
        Upload upload = new Upload(remotePeerConnection);
        readCredentials();
        JSONParser parser = new JSONParser();
        File file = new File(".");
        String keywords;
        Map<String, String> metadata;
        try {
            if (isBinary && !isFile && !isZip && !isMultiple && !isText) {
                String data, name, contentType;
                try {
                    Object fileObj = parser.parse(new FileReader(file.getAbsoluteFile() + "/configs/uploadBinary.json"));
                    JSONObject object = (JSONObject) fileObj;
                    data = (String) object.get("data");
                    name = (String) object.get("name");
                    contentType = (String) object.get("contentType");
                    keywords = (String) object.get("keywords");
//                    metadata = (String) object.get("metadata");

                    UploadBinaryParameter parameter = UploadBinaryParameter.create()
                            .senderPrivateKey(privateKey)
                            .receiverPublicKey(publicKey)
                            .data(FileUtils.readFileToByteArray(new File(file.getAbsoluteFile() + data)))
                            .name(name)
                            .contentType(contentType)
                            .keywords(keywords)
//                            .metadata(metadata)
                            .build();

                    final UploadResult uploadResult = upload.uploadBinary(parameter);
                    System.out.println("Your NEM hash is: " + uploadResult.getNemHash());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!isBinary && isFile && !isZip && !isMultiple && !isText) {
                String _file;
                try {
                    Object fileObj = parser.parse(new FileReader(file.getAbsoluteFile() + "/configs/uploadPlain.json"));
                    JSONObject object = (JSONObject) fileObj;
                    _file = (String) object.get("file");
                    keywords = (String) object.get("keywords");
//                    metadata = (String) object.get("metadata");

                    UploadFileParameter parameter = UploadFileParameter.create()
                            .senderPrivateKey(privateKey)
                            .receiverPublicKey(publicKey)
                            .file(file)
                            .keywords(keywords)
//                            .metadata(metadata)
                            .build();

                    final UploadResult uploadResult = upload.uploadFile(parameter);
                    System.out.println("Your NEM hash is: " + uploadResult.getNemHash());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!isBinary && !isFile && isZip && !isMultiple && !isText) {
                String zipFileName;
                ArrayList<String> files = new ArrayList<>();
                try {
                    Object fileObj = parser.parse(new FileReader(file.getAbsoluteFile() + "/configs/uploadZip.json"));
                    JSONObject object = (JSONObject) fileObj;
                    zipFileName = (String) object.get("zipFileName");
                    JSONArray fileNames = (JSONArray) object.get("file");
                    Iterator<String> iterator = fileNames.iterator();
                    while (iterator.hasNext()) {
                        files.add(iterator.next());
                    }
                    keywords = (String) object.get("keywords");
//                    metadata = (String) object.get("metadata");

                    UploadFilesAsZipParameter parameter = UploadFilesAsZipParameter.create()
                            .senderPrivateKey(privateKey)
                            .receiverPublicKey(publicKey)
                            .zipFileName(zipFileName)
                            .keywords(keywords)
//                            .metadata(metadata)
                            .build();

                    final UploadResult uploadResult = upload.uploadFilesAsZip(parameter);
                    System.out.println("Your NEM hash is: " + uploadResult.getNemHash());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!isBinary && !isFile && !isZip && isMultiple && !isText) {
                String zipFileName;
                ArrayList<String> files = new ArrayList<>();
                try {
                    Object fileObj = parser.parse(new FileReader(file.getAbsoluteFile() + "/configs/uploadMultiple.json"));
                    JSONObject object = (JSONObject) fileObj;
                    JSONArray fileNames = (JSONArray) object.get("file");
                    Iterator<String> iterator = fileNames.iterator();
                    while (iterator.hasNext()) {
                        files.add(iterator.next());
                    }
                    keywords = (String) object.get("keywords");
//                    metadata = (String) object.get("metadata");

                    UploadMultipleFilesParameter parameter = UploadMultipleFilesParameter.create()
                            .senderPrivateKey(privateKey)
                            .receiverPublicKey(publicKey)
                            .keywords(keywords)
//                            .metadata(metadata)
                            .build();

                    final MultiFileUploadResult uploadResult = upload.uploadMultipleFiles(parameter);
                    System.out.println(uploadResult.getFileUploadResults());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!isBinary && !isFile && !isZip && !isMultiple && isText) {
                String data, name, contentType, encoding;
                try {
                    Object fileObj = parser.parse(new FileReader(file.getAbsoluteFile() + "/configs/uploadText.json"));
                    JSONObject object = (JSONObject) fileObj;
                    data = (String) object.get("data");
                    name = (String) object.get("name");
                    contentType = (String) object.get("contentType");
                    encoding = (String) object.get("encoding");
                    keywords = (String) object.get("keywords");
//                    metadata = (String) object.get("metadata");

                    UploadTextDataParameter parameter = UploadTextDataParameter.create()
                            .senderPrivateKey(privateKey)
                            .receiverPublicKey(publicKey)
                            .data(data)
                            .name(name)
                            .contentType(TEXT_PLAIN.toString())
                            .encoding(encoding)
                            .keywords(keywords)
//                            .metadata(metadata)
                            .build();

                    final UploadResult uploadResult = upload.uploadTextData(parameter);
                    System.out.println("Your NEM hash is: " + uploadResult.getNemHash());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("You have to choose either `-b`, `-f`, `-z`, `-m` or `-t`. Run `proximax help upload` to see the help.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}