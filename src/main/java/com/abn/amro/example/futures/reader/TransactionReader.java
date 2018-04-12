package com.abn.amro.example.futures.reader;

import com.abn.amro.example.futures.model.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Reader which reads whole file and send data for processing
 */
public class TransactionReader implements ItemReader<List<Transaction>> , ItemStream{
    private static final Logger logger = Logger.getLogger(TransactionReader.class);

    private FlatFileItemReader<Transaction> itemReader;


    public void setItemReader(FlatFileItemReader<Transaction> itemReader) {
        this.itemReader = itemReader;
    }

    private boolean process(Transaction transaction,List<Transaction> records) {
        // if transaction is null stop processing
        if (transaction == null) {
            logger.debug("Exhausted ItemReader");
            return false;
        }

        // add a simple record to the current collection
        logger.debug("Mapping: " + transaction);
        records.add(transaction);
        return true;
    }

    @Override
    public List<Transaction> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        List<Transaction> records = new ArrayList<Transaction>();
        while (process(itemReader.read(), records)) {
            continue;
        }
        logger.info( " reader read " + records.size() +" records.");
        if(records.size() > 0){
            return records;
        }

        return null;

    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        itemReader.open(executionContext);
        logger.info(" Opening reader.");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        itemReader.update(executionContext);
        logger.info(" Updating reader.");
    }

    public FlatFileItemReader<Transaction> getItemReader() {
        return itemReader;
    }

    @Override
    public void close() throws ItemStreamException {
        logger.info( " reader is closing.");
        itemReader.close();
    }
}
