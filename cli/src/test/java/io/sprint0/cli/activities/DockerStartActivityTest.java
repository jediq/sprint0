package io.sprint0.cli.activities;

import com.google.common.collect.ImmutableList;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.messages.Image;
import io.sprint0.cli.IntegrationTest;
import io.sprint0.cli.configuration.Configuration;
import io.sprint0.cli.configuration.ConfigurationStore;
import io.sprint0.cli.jobs.Job;
import io.sprint0.cli.tools.Jenkins;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;

/**
 *
 */
public class DockerStartActivityTest {

    @Test
    public void testHaveNotPulled() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = new DockerStartActivity(new Jenkins()) {
            @Override
            public DockerClient getDocker() throws DockerCertificateException {
                return docker;
            }
        };
        Image image = mock(Image.class);
        when(image.repoTags()).thenReturn(ImmutableList.of("test_jenkinsXXXX:123456"));
        when(docker.listImages()).thenReturn(ImmutableList.of(image));

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.FAILURE));
        assertThat(activityResult.getMessage(), is("We haven't pulled image : jenkins"));

        verify(docker, never()).startContainer(eq("test_jenkins"));

    }


    @Test
    @Category(IntegrationTest.class)
    public void testKnownImageIT() {
        DockerActivity dockerActivity = new DockerStartActivity(new Jenkins());
        Job job = new Job();
        job.setConfigurationStore(new ConfigurationStore());
        job.addActivity(dockerActivity);


        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));
    }

    @Test
    public void test() {
        ConfigurationStore configurationStore = new ConfigurationStore();
        Configuration configuration = new Configuration();
        configurationStore.saveConfiguration(configuration);
    }
}
