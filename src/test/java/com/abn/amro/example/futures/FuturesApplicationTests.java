package com.abn.amro.example.futures;

import com.abn.amro.example.futures.config.TransactionsConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { TransactionsConfig.class }, locations = {"/context.xml"})
public class FuturesApplicationTests {

	private static final String INPUT_FILE = "src/test/resources/input/input.txt";
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void contextLoads() throws Exception {
		JobParameters param = new JobParametersBuilder().addString("inputfile", INPUT_FILE).toJobParameters();
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(param);
		Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}

}
