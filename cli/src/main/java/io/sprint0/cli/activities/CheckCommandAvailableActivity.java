package io.sprint0.cli.activities;

import io.sprint0.cli.jobs.Job;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;

/**
 *
 */
public class CheckCommandAvailableActivity implements Activity {

    private final String commandName;

    public CheckCommandAvailableActivity(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public ActivityResult go(CommandLine commandLine) {
        try {
            Runtime.getRuntime().exec(commandName);
        } catch (IOException e) {
            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }

    @Override
    public String toString() {
        return "Check command available activity : " + commandName;
    }

    @Override
    public void setJob(Job job) {
        // we don't really care about the job here
    }
}
