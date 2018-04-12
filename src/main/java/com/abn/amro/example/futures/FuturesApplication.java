package com.abn.amro.example.futures;

import com.abn.amro.example.futures.config.TransactionsConfig;
import com.abn.amro.example.futures.reader.TransactionReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.Logger;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application.
 */
@SpringBootApplication
public class FuturesApplication {

    private static final Logger logger = Logger.getLogger(FuturesApplication.class);

	public static void main(String[] args) {
        if(args.length > 0){
            logger.info( " arguments : " + args[0]);
            try {
                CommandLineJobRunner.main(
                        new String[]{TransactionsConfig.class.getName(),
                                "futuresJobs", args[0]});
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        else{
            logger.info(" Please provide input file as inputfile argument in format inputfile=<input file full path>.");
            System.out.println(" Please provide input file as inputfile argument in format inputfile=<input file full path>.");
        }
    }

}
