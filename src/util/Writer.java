package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Writer {
    public static ObjectInputStream getReadStream(String fileName) {
        try {
            return new ObjectInputStream(Files.newInputStream(getPath(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObjectOutputStream getWriteStream(String fileName) {
        try {
            return new ObjectOutputStream(Files.newOutputStream(getPath(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Path getPath(String fileName) {
        return Paths.get("resources", fileName);
    }
}