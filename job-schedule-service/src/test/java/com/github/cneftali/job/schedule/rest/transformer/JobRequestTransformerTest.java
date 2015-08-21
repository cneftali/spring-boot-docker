package com.github.cneftali.job.schedule.rest.transformer;

import static org.fest.assertions.Assertions.assertThat;

import com.github.cneftali.job.commons.batch.JobLaunchRequest;
import com.github.cneftali.job.schedule.rest.domain.JobRequest;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JobRequestTransformerTest {

    private static final Long ID = null;
    private static final String JOB_NAME = "job1";
    private static final String JOB_PARAMETER = "{\"toto\":1, \"tata\" : \"encule\"}";
    private static final long POD_ID = 1L;
    private static final long CLIENT_ID = 801L;

    private JobRequestTransformer transformer;

    @Before
    public void setUp() throws Exception {
        transformer = new JobRequestTransformer();
    }

    @Test
    public void when_transform_then_ok() throws Exception {
        // GIVEN
        final JSONObject jsonObject = new JSONObject(JOB_PARAMETER);
        final JobRequest jobRequest = new JobRequest(JOB_NAME, jsonObject, POD_ID, CLIENT_ID, DateTime.now());
        // WHEN
        final JobLaunchRequest launchRequest = transformer.transform(jobRequest);

        // THEN
        assertThat(launchRequest.getJobParameters().getLong("podId")).isEqualTo(POD_ID);
        assertThat(launchRequest.getJobParameters().getLong("clientId")).isEqualTo(CLIENT_ID);
        assertThat(launchRequest.getJobName()).isEqualTo(JOB_NAME);
        assertThat(launchRequest.getScheduleId()).isEqualTo(ID);
    }
}