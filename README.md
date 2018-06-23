A CLI for a ProximaX Java SDK
=============================

## INSTALLATION


1. `mvn package`

2. Get the full path for the /target/xpx-cli-1.0.0-SNAPSHOT.jar and add it in `proximax` script:

    ```
    #!/bin/bash
    # proximax
    # {$} is a directory where you store the project
    java -jar {$}/xpx-cli/target/xpx-cli-1.0.0-SNAPSHOT.jar $@
    ```
3. `chmod +x proximax`
 
4. Copy the script to your local bin to execute 

    ```cp proximax /usr/local/bin```

5. Run `proximax` from anywhere


## CURRENT COMMANDS

```
>proximax help
usage: proximax <command> [ <args> ]

Commands are:
    announce   Set credentials for ProximaX SDK
    clear      Remove your private/public key
    download   Download the file
    help       Display help information
    search     Search using Keywords
    upload     Upload the file
    version    Show the version of ProximaX SDK
    whoami     Check the private key you have

See 'proximax help <command>' for more information on a specific command.
```

#### ANNOUNCE

To use a ProximaX SDK you have to provide a private and public keys.

```
>proximax help announce
NAME
        proximax announce - Set credentials to use a ProximaX SDK

SYNOPSIS
        proximax announce [ {-b | --public} <Receiver public key> ]
                [ {-p | --private} <Sender private key> ]

OPTIONS
        -b <Receiver public key>, --public <Receiver public key>
            Receiver public key

        -p <Sender private key>, --private <Sender private key>
            Private key to announce yourself to NEM blockchain
```

After running `proximax announce -p privateKey -b publicKey` your pair will be written in credentials file in the root folder.

To remove your private/public pair run `proximax clear` or just delete `credentials` file from your computer. 
#### SEARCH

```
>proximax help search
NAME
        proximax search - Search using Keywords

SYNOPSIS
        proximax search [ {-k | --keyword} <keyword> ]
                [ -key <meta data key/value> ] [ {-n | --name} <name> ]
                [ -value <meta data key/value> ]

OPTIONS
        -k <keyword>, --keyword <keyword>
            Search by keyword

        -key <meta data key/value>
            Key to search

        -n <name>, --name <name>
            Search by name

        -value <meta data key/value>
            Value to search
```

#### DOWNLOAD

```
>proximax help download
NAME
     proximax download - Download the file

SYNOPSIS
     proximax download [ {-b | --binary} ] [ {-f | --file} ]
             [ {-s | --secure} ] [ {-t | --text} ]

OPTIONS
     -b, --binary
         Download binary file

     -f, --file
         Download file

     -s, --secure
         Apply NEM keys privacy strategy

     -t, --text
         Download text file
```

You can set the NEM hash at `configs/download.json`.

#### UPLOAD

```
>proximax help upload
NAME
        proximax upload - Upload the file

SYNOPSIS
        proximax upload [ {-b | --binary} ] [ {-f | --file} ]
                [ {-m | --multiple} ] [ {-p | --path} ] [ {-t | --text} ]
                [ {-u | --url} ] [ {-z | --zip} ]

OPTIONS
        -b, --binary
            Upload binary file

        -f, --file
            Upload plain file

        -m, --multiple
            Upload multiple files

        -p, --path
            Upload the file using path

        -t, --text
            Upload text file

        -u, --url
            Upload the file using url

        -z, --zip
            Upload ZipFile

```

There are 7 types of upload:
1. Binary file.
2. Plain file.
3. Text file.
4. Multiple files.
5. Zip archive file.
6. Path upload.
7. Url upload.

You can use `-p` flag to set the absolute path of your file in your Linux machine or you can just add your file in `files` folder. 
For each of them you have to provide a particular data.

###
- `Binary` file at `configs/uploadBinary.json`
```
{
  "data": "",
  "name": "",
  "contentType": "",
  "keywords": "",
  "metadata": {
    "": ""
  }
}
```

- `Plain` file at `configs/uploadPlain.json`
```
{
  "file": "",
  "keywords": "",
  "metadata": {
    "": ""
  }
}
```

- `Text` file at `configs/uploadText.json`
```
{
  "data": "",
  "name": "",
  "contentType": "",
  "encoding": "",
  "keywords": "",
  "metadata": {
    "": ""
  }
}
```

- `Multiple` files at `configs/uploadMultiple.json`
```
{
  "file": [
    ""
  ],
  "keywords": "",
  "metadata": {
    "": ""
  }
}
```

- `Zip` files at `configs/uploadZip.json`
```
{
  "zipFileName": "",
  "file": [
    ""
  ],
  "keywords": "",
  "metadata": {
    "": ""
  }
}
```

- `Path` files at `configs/uploadPath.json`
```
{
  "path": "",
  "keywords": "",
  "metadata": {
    "": ""
  }
}

```

- `Url` files at `configs/uploadUrl.json`
```
{
  "url": "",
  "name": "",
  "keywords": "",
  "metadata": {
    "": ""
  }
}
```