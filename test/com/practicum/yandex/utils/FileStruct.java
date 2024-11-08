package test.com.practicum.yandex.utils;

public class FileStruct {
    private final String name;

    private String format;

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public FileStruct(String name, String format) {
        this.name = name;
        this.format = format;
    }
}
