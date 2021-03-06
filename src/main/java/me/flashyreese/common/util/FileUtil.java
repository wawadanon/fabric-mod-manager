package me.flashyreese.common.util;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileUtil {
    public static <T> T readJson(File file, Type type) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<T> jsonAdapter = moshi.adapter(type);
        FileReader fileReader = null;
        BufferedReader buffered = null;
        try {
            fileReader = new FileReader(file);
            buffered = new BufferedReader(fileReader);
            return jsonAdapter.fromJson(buffered.lines().collect(Collectors.joining()));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                assert buffered != null;
                buffered.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void writeJson(File file, Object object, Type type) {
        try {
            Moshi moshi = new Moshi.Builder().build();
            String json = moshi.adapter(type).toJson(object);
            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static File changeExtension(File file, String extension) {
        String filename = file.getName();

        if (filename.contains(".")) {
            filename = filename.substring(0, filename.lastIndexOf('.'));
        }
        filename += "." + extension;

        File newFile = file;
        if (file.renameTo(new File(file.getParentFile(), filename))) {
            newFile = new File(file.getParentFile(), filename);
        }
        return newFile;
    }

    public static String getFileNameWithExtension(File file) {
        return file.getName();
    }

    public static String getFileName(File file) {
        String fileNameWithExtension = getFileNameWithExtension(file);
        if (fileNameWithExtension.contains(".")) {
            return fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));
        }
        return fileNameWithExtension;
    }

    public static String sanitizeFileName(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]", "");
    }

    public static List<String> readLines(File file) {
        List<String> lines = new ArrayList<>();
        if (file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                while (line != null) {
                    lines.add(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lines;
        } else {
            return null;
        }
    }

    public static boolean removeFileDirectory(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                removeFileDirectory(file);
            }
            if (!file.delete()) return false;
        }
        return true;
    }
}
