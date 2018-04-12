package com.abn.amro.example.futures.writer;

import com.abn.amro.example.futures.config.TransactionsConfig;
import com.abn.amro.example.futures.model.ReportItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.WriterNotOpenException;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TransactionWriterTest {

    private TransactionsWriter transactionsWriter;
    private static final int EXPECTED_COUNT = 20;


    private List<ReportItem> reportItems;


    @Before
    public void setUp() throws Exception {
        reportItems = new ArrayList<>();
        ReportItem item = new ReportItem("CL-1234-00020001","SGX(FU)-NK-20100910",-52);
        reportItems.add(item);
        item = new ReportItem("CL-4321-00020001","SGX(FU)-NK-20100910",46);
        reportItems.add(item);

        transactionsWriter = new TransactionsWriter();
        TransactionsConfig config = new TransactionsConfig();
        FlatFileItemWriter<ReportItem> writer= config.csvItemWriter();
        transactionsWriter.setCsvFileWriter(writer);
    }

//    @Test (expected = WriterNotOpenException.class)
//    public void testWriteWithException() throws Exception {
//        List<List<ReportItem>> list = new ArrayList<>();
//        list.add(reportItems);
//        transactionsWriter.write(list);
//    }

    @Test
    public void testWrite() throws Exception {
        List<List<ReportItem>> list = new ArrayList<>();
        list.add(reportItems);

        ExecutionContext executionContext = new ExecutionContext();
        transactionsWriter.open(executionContext);
        transactionsWriter.write(list);

        Resource resource = new FileSystemResource(TransactionsWriter.EXPORT_FILE_NAME);
        Assert.assertEquals(true, resource.exists());
        Assert.assertEquals(TransactionsWriter.EXPORT_FILE_NAME,resource.getFilename());

        Assert.assertEquals( "Client_Information,Product_Information,Total_Transaction_Amount",
                Files.lines(resource.getFile().toPath()).findFirst().get());
        String[] lines = Files.lines(resource.getFile().toPath()).skip(1).toArray(String[]::new);
        Assert.assertEquals( "CL-1234-00020001,SGX(FU)-NK-20100910,-52",
               lines[0]);
        Assert.assertEquals( "CL-4321-00020001,SGX(FU)-NK-20100910,46",
                lines[1]);

    }


    @After
    public void afterCleanup(){

        Resource resource = new FileSystemResource(TransactionsWriter.EXPORT_FILE_NAME);
        if(resource.exists()){
            try {
                resource.getFile().deleteOnExit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
