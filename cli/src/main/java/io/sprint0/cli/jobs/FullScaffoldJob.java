package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.CheckCommandAvailableActivity;

/**
 *
 */
public class FullScaffoldJob extends Job {

    public FullScaffoldJob() {
        addActivity(new CheckCommandAvailableActivity("docker"));
    }
}
