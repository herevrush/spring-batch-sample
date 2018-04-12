package com.abn.amro.example.futures.writer;

import com.abn.amro.example.futures.model.ReportItem;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


public class TransactionsWriter implements ItemWriter<List<ReportItem>>, ItemStream, InitializingBean {

    private static final Logger logger = Logger.getLogger(TransactionsWriter.class);

    private FlatFileItemWriter<ReportItem> csvFileWriter;
    public static String EXPORT_FILE_NAME = "Output.csv";

    public void setCsvFileWriter(FlatFileItemWriter<ReportItem> csvFileWriter) {
        logger.info( " Adding CSV file Writer to Transaction writer class");
        this.csvFileWriter = csvFileWriter;
    }


    @Override
    public void write(List<? extends List<ReportItem>> items) throws Exception {

        if(items != null){
            final List<ReportItem> consolidatedList = new ArrayList<>();

            items.forEach(consolidatedList::addAll);
            logger.info( " Writing " + items.size() + " messages to summary report " + TransactionsWriter.EXPORT_FILE_NAME);
            try {
                csvFileWriter.write(consolidatedList);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        csvFileWriter.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        csvFileWriter.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        csvFileWriter.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(csvFileWriter, "You must set a csvFileWriter!");
    }
}
