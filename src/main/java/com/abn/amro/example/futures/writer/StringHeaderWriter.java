package com.abn.amro.example.futures.writer;

import org.apache.log4j.Logger;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

public class StringHeaderWriter implements FlatFileHeaderCallback{

    private final String header;
    private static final Logger logger = Logger.getLogger(StringHeaderWriter.class);

    public StringHeaderWriter(String header) {
        this.header = header;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        logger.info(" Adding header to the summary report:: " + header);
        writer.write(header);
    }
}