package org.example.multidatasourcetrainning.utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@UtilityClass
public class DataUtils {
    public static <T> boolean nullField(T object) throws IllegalAccessException {
        if (object == null) return true;

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(object);

            if (value == null) return true;
            if (value instanceof String && Pattern.matches("\\s+", (String) value)) return true;
        }
        return false;
    }

    public static <T> T transferData(T from, T to) throws IllegalAccessException {
        if (from == null || to == null) throw new NullPointerException("Source or destination object is null.");

        // Map to store field values from 'from' object
        Map<String, Field> fromFieldMap = new HashMap<>();
        Class<?> fromClass = from.getClass();

        // Traverse all fields in 'from' object (including superclasses)
        while (fromClass != null) {
            for (Field fromField : fromClass.getDeclaredFields()) {
                fromField.setAccessible(true); // Allow access to private fields
                fromFieldMap.put(fromField.getName(), fromField);
            }
            fromClass = fromClass.getSuperclass();
        }

        Class<?> toClass = to.getClass();

        // Traverse all fields in 'to' object (including superclasses)
        while (toClass != null) {
            for (Field toField : toClass.getDeclaredFields()) {
                toField.setAccessible(true); // Allow access to private fields
                Field fromField = fromFieldMap.get(toField.getName());
                if (fromField.getName().equalsIgnoreCase("serialVersionUID")) continue;
                // Check if 'from' object has the field with the same name and compatible type
                if (fromField != null && fromField.getType().equals(toField.getType())) {
                    Object value = fromField.get(from);

                    // Only transfer non-null values
                    if (value != null) {
                        toField.set(to, value);
                    }
                }
            }
            toClass = toClass.getSuperclass();
        }

        return to;
    }

    public static File zip(String zipFileName, Path csvFilePath) throws IOException {
        Path zipPath = Paths.get(zipFileName);
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            // Add CSV file to ZIP archive
            try (InputStream inputStream = Files.newInputStream(csvFilePath)) {
                ZipEntry zipEntry = new ZipEntry(csvFilePath.getFileName().toString());
                zipOut.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    zipOut.write(buffer, 0, length);
                }
                zipOut.closeEntry();
            }
            Files.deleteIfExists(csvFilePath);
            return zipPath.toFile();
        }
    }
}
