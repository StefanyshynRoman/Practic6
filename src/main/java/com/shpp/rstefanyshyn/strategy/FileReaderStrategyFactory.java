package com.shpp.rstefanyshyn.strategy;

public class FileReaderStrategyFactory {
    public static FileReaderStrategy fabricMethodCreateReaderStrategy(String file) {
        if (file.endsWith(".csv")) {
            return new CsvFileReaderStrategy();
        } else if (file.endsWith(".pdf")) {
            return new PdfFileReaderStrategy();
        } else if (file.endsWith(".txt")) {
            return new TxtFileReaderStrategy();
        } else {
            // Обробка випадку, коли тип файлу не підтримується
            throw new IllegalArgumentException("Unsupported file type: " + file);
        }
    }
}
