package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Writer {
    public static <T> T readObject(String fileName) {
        try {
            return (T) new ObjectInputStream(Files.newInputStream(getPath(fileName))).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeObject(String fileName, Object object) {
        try {
            new ObjectOutputStream(Files.newOutputStream(getPath(fileName))).writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(String fileName) {
        try {
            List<String> read = Files.readAllLines(getPath(fileName));
            for (String r : read)
                System.out.println(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String fileName, String string) {
        try {
            Files.write(getPath(fileName), string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getPath(String fileName) {
        return Paths.get("resources", fileName);
    }
}