package amir.markovAPI.services.Parser;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;

public class TextFileParser extends Parser {

    public String fileToString(String fileName) throws IOException {
        byte[] bytes;
        try{
            Path path = Paths.get(fileName);
            bytes= Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            bytes = new byte[0];
        }
        return new String(bytes).trim();
    }
}
