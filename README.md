A CLI for a ProximaX Java SDK
=============================

INSTALLATION
------------

1. `mvn package`

2. Get the full path for the /target/xpx-cli-1.0.0-SNAPSHOT.jar and add it in `proximax` script:

    ```
    #!/bin/bash
    # proximax
    # {$} is a directory where you store the project
    java -jar "{$}/xpx-cli/target/xpx-cli-1.0.0-SNAPSHOT.jar" "$@"
    ```
3. `chmod+x proximax`
 
4. Copy the script to your local bin to execute 

    ```cp proximax /usr/local/bin```

5. Run `proximax` from anywhere