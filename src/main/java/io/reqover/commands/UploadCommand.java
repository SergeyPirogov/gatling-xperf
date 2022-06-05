package io.reqover.commands;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import picocli.CommandLine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommandLine.Command(
        name = "upload", mixinStandardHelpOptions = true,
        description = "upload files"
)
public class UploadCommand implements Runnable {

    @CommandLine.Option(names = {"-d", "--dir"}, paramLabel = "DIRECTORY", description = "the source dir",
            defaultValue = "reqover-results")
    private File directory;

    @CommandLine.Option(names = {"-s", "--server"}, paramLabel = "URL", description = "server url")
    private String serverUrl;

    @CommandLine.Option(names = {"-t", "--token"}, paramLabel = "TOKEN", description = "build token")
    private String token;

    @Override
    public void run() {
        listFiles(directory).forEach(it -> {
            String file = readFile(it);
            post(String.format("%s/%s/results", serverUrl, token), file);
        });
    }


    public Set<String> listFiles(File dir) {
        return Stream.of(Objects.requireNonNull(dir.listFiles()))
                .filter(file -> !file.isDirectory() && file.getName().endsWith(".json"))
                .map(File::getAbsolutePath)
                .collect(Collectors.toSet());
    }

    public void post(String url, String inputJson) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFile(String name) {
        Path filePath = Path.of(name);

        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
