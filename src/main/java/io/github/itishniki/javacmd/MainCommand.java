package io.github.itishniki.javacmd;

import io.github.itishniki.javacmd.commands.GoogleSearchCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "search-for-me", mixinStandardHelpOptions = true,
        subcommands = {GoogleSearchCommand.class}
)
public class MainCommand implements Runnable{

    @Override
    public void run() {
    }

}
