How to test and run

gradle test run

By default, ./data is working directory.
Application reads/write reports to this directory.

To change working dir, change settings in build.gradle

run {
    args = ["./data"]
}

Application generates 4 reports according the requirements.














