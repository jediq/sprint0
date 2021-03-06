package io.sprint0.cli.configuration;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConfigurationStoreTest {

    public static final String USER_HOME = "user.home";

    @Test
    public void testSaveLoadRemove() {
        String host = "my_test_host";
        ConfigurationStore manager = new ConfigurationStore();
        manager.setConfigurationProvider(new DefaultConfigurationProvider());

        Installation original = new Installation();
        original.setHost(host);
        manager.saveInstallation(host, original);

        Installation fromFile = manager.loadInstallation(host);
        assertThat(fromFile.getHost(), is(host));

        manager.removeInstallation(host);
    }

    @Test
    public void testSaveLoadRemoveConfig() {

        String orig = System.getProperty(USER_HOME);
        System.setProperty(USER_HOME, "/tmp/config");
        try {
            String host = "my_test_host";

            ConfigurationStore manager = new ConfigurationStore();
            manager.setConfigurationProvider(new DefaultConfigurationProvider());

            Configuration original = new Configuration();
            original.setCurrentDockerHost(host);
            manager.saveConfiguration(original);

            Configuration fromFile = manager.loadConfiguration();
            assertThat(fromFile.getCurrentDockerHost(), is(host));

            manager.removeConfiguration();
        } finally {
            System.setProperty(USER_HOME, orig);
        }
    }


    @Test
    public void testLoadConfigNonExistant() {
        String orig = System.getProperty(USER_HOME);
        System.setProperty(USER_HOME, "/tmp/unknown_location");
        try {

            ConfigurationStore manager = new ConfigurationStore();
            manager.setConfigurationProvider(new DefaultConfigurationProvider());
            Configuration config = manager.loadConfiguration();

            assertThat(config.getCurrentDockerHost(), is("192.168.99.100"));

        } finally {
            System.setProperty(USER_HOME, orig);
        }
    }


    @Test(expected=IllegalStateException.class)
    public void testLoadNonExistant() {
        String host = "testLoadNonExistant_host";

        ConfigurationStore manager = new ConfigurationStore();
        manager.setConfigurationProvider(new DefaultConfigurationProvider());
        manager.loadInstallation(host);
    }

    @Test(expected=IllegalStateException.class)
    public void testRemoveNonExistant() {
        String host = "testRemoveNonExistant_host";
        ConfigurationStore manager = new ConfigurationStore();
        manager.setConfigurationProvider(new DefaultConfigurationProvider());
        manager.removeInstallation(host);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveNull_installation() {
        String host = "testSaveNull_installation";
        ConfigurationStore manager = new ConfigurationStore();
        manager.setConfigurationProvider(new DefaultConfigurationProvider());
        manager.saveInstallation(host, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveNull_host() {
        ConfigurationStore manager = new ConfigurationStore();
        manager.setConfigurationProvider(new DefaultConfigurationProvider());
        manager.saveInstallation(null, new Installation());
    }
}
