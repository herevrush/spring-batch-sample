package com.abn.amro.example.futures.config;

import com.abn.amro.example.futures.model.ReportItem;
import com.abn.amro.example.futures.model.Transaction;
import com.abn.amro.example.futures.writer.StringHeaderWriter;
import com.abn.amro.example.futures.processor.TransactionProcessor;
import com.abn.amro.example.futures.reader.TransactionReader;
import com.abn.amro.example.futures.writer.TransactionsWriter;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@Configuration
@EnableBatchProcessing

public class TransactionsConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;



    @BeforeStep
    public void beforeStep(final StepExecution stepExecution) {
        JobParameters parameters = stepExecution.getJobExecution().getJobParameters();
        System.out.println(parameters.getParameters().toString());
        //use your parameters
    }




    @Bean
    @StepScope
    public TransactionReader transactionFileItemReader(FlatFileItemReader<Transaction> fileItemReader){

        TransactionReader transactionReader = new TransactionReader();
        transactionReader.setItemReader(fileItemReader);
        return transactionReader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Transaction> fileItemReader(@Value("#{jobParameters[inputfile]}") String pathToFile){
        FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<Transaction>();
        lineMapper.setLineTokenizer(transactionTokenizer());
        flatFileItemReader.setResource(new FileSystemResource(pathToFile));

        FieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<Transaction>() {{
            setTargetType(Transaction.class);
        }};
        lineMapper.setFieldSetMapper(fieldSetMapper);


        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }


    @Bean
    public TransactionProcessor processor() {
        return new TransactionProcessor();
    }

    @Bean
    public TransactionsWriter writer(FlatFileItemWriter<ReportItem> csvItemWriter){
        TransactionsWriter writer = new TransactionsWriter();
        writer.setCsvFileWriter(csvItemWriter);
        return writer;
    }

    @Bean
    public FlatFileItemWriter<ReportItem> csvItemWriter() {
        FlatFileItemWriter<ReportItem> csvFileWriter = new FlatFileItemWriter<>();

        String exportFileHeader = "Client_Information,Product_Information,Total_Transaction_Amount";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        csvFileWriter.setHeaderCallback(headerWriter);
        csvFileWriter.setResource(new FileSystemResource(TransactionsWriter.EXPORT_FILE_NAME));

        DelimitedLineAggregator lineAggregator = new DelimitedLineAggregator();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<ReportItem> extractor = new BeanWrapperFieldExtractor<ReportItem>();
        extractor.setNames(new String[]{"clientInfo","productInfo","transactionAmount"});
        lineAggregator.setFieldExtractor(extractor);
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    @Bean
    public FixedLengthTokenizer transactionTokenizer(){
        FixedLengthTokenizer tokenizer =  new FixedLengthTokenizer();
        String[] names = new String[]{
                "clientType","clientNumber", "accountNumber","subAccountNumber",
                "exchangeCode", "productGroupCode","symbol","expirationDate",
                "quantityLong","quantityShort"
        };
        tokenizer.setNames(names);
        Range[] ranges = new Range[]{
                new Range(4,7),new Range(8,11), new Range(12,15),new Range(16,19),
                new Range(28,31),new Range(26,27), new Range(32,37),new Range(38,45),
                new Range(53,62),new Range(64,73)

        };
        tokenizer.setColumns(ranges);
        tokenizer.setStrict(false);
        return  tokenizer;
    }


    @Bean
    public Step step1(TransactionReader reader, FlatFileItemReader<Transaction> fileItemReader,TransactionsWriter writer,
                      FlatFileItemWriter<ReportItem> flatFileItemWriter) {
        return stepBuilderFactory.get("step1")
                .<List<Transaction>, List<ReportItem>> chunk(1)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }


    @Bean
    protected Job futuresJobs(Step step1) {
        return jobBuilderFactory.get("futuresJobs")
                .incrementer(new RunIdIncrementer())
//                .listener(listener)
                .flow(step1)
                .end()
                .build();

    }
}
