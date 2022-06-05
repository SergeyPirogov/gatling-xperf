package io.reqover;

import io.reqover.commands.UploadCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "reqover", mixinStandardHelpOptions = true,
        subcommands = {UploadCommand.class}
)
public class MainCommand implements Runnable{

    @Override
    public void run() {
    }

}
