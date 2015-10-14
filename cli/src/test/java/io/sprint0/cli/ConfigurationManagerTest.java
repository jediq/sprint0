package io.sprint0.cli;

import io.sprint0.cli.installation.Configuration;
import io.sprint0.cli.installation.Installation;
import io.sprint0.cli.installation.ConfigurationManager;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConfigurationManagerTest {

    public static final String USER_HOME = "user.home";

    @Test
    public void testSaveLoadRemove() {
        String host = "my_test_host";
        ConfigurationManager manager = new ConfigurationManager();

        Installation original = new Installation();
        original.setHost(host);
        manager.saveInstallation(host, original);

        Installation fromFile = manager.loadInstallation(host);
        assertThat(fromFile.getHost(), is(host));

        manager.removeInstallation(host);
    }

    @Test
    public void testSaveLoadRemoveConfig() {
        String host = "my_test_host";
        ConfigurationManager manager = new ConfigurationManager();

        Configuration original = new Configuration();
        original.setCurrentHost(host);
        manager.saveConfiguration(original);

        Configuration fromFile = manager.loadConfiguration();
        assertThat(fromFile.getCurrentHost(), is(host));

        manager.removeConfiguration();
    }


    @Test(expected=IllegalStateException.class)
    public void testLoadConfigNonExistant() {
        String orig = System.getProperty(USER_HOME);
        System.setProperty(USER_HOME, "/tmp/unknown_location");
        try {
            ConfigurationManager manager = new ConfigurationManager();
            manager.loadConfiguration();
        } finally {
            System.setProperty(USER_HOME, orig);
        }
    }


    @Test(expected=IllegalStateException.class)
    public void testLoadNonExistant() {
        String host = "testLoadNonExistant_host";
        ConfigurationManager manager = new ConfigurationManager();
        manager.loadInstallation(host);
    }

    @Test(expected=IllegalStateException.class)
    public void testRemoveNonExistant() {
        String host = "testRemoveNonExistant_host";
        ConfigurationManager manager = new ConfigurationManager();
        manager.removeInstallation(host);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveNull_installation() {
        String host = "testSaveNull_installation";
        ConfigurationManager manager = new ConfigurationManager();
        manager.saveInstallation(host, null);
    }
    @Test(expected=IllegalArgumentException.class)
    public void testSaveNull_host() {
        String host = "testSaveNull_host";
        ConfigurationManager manager = new ConfigurationManager();
        manager.saveInstallation(null, new Installation());
    }
}
