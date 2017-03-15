package org.sonarsource.plugins.rules;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.config.Settings;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.MessageException;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.plugins.parser.Antipattern;
import org.sonarsource.plugins.parser.ChronosParser;
import org.sonarsource.plugins.parser.File;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by cezarcoca.
 */
public class ChronosSensor implements Sensor {

    public static final String CHRONOS_SERVER_PATH = "chronos.server.path";
    private static final Logger LOGGER = Loggers.get(ChronosSensor.class);
    private final FileSystem fileSystem;
    private URL url;
    private SensorContext context;

    public ChronosSensor(final Settings settings, final FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        try {
            url = new URL(settings.getString(CHRONOS_SERVER_PATH));
        } catch (MalformedURLException e) {
            LOGGER.error("Invalid Chronos Server URL '{}'. Please correct the configuration and retry.",
                    settings.getString(CHRONOS_SERVER_PATH));
        }
        LOGGER.info("Chronos Plugin started successfully. The Chronos Server URL is {}", url);
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("Chronos Issues Loader Sensor");
    }

    @Override
    public void execute(SensorContext context) {
        if (url == null) {
            return;
        }
        this.context = context;
        new ChronosParser().parse(url).getFiles().stream()
                .filter(file -> !file.getAntipatterns().isEmpty())
                .forEach(this::getResourceAndSaveIssue);
    }

    private void getResourceAndSaveIssue(final File issue) {
        LOGGER.debug("Processing file {}", issue);
        Optional<InputFile> resource = getInputFile(issue);
        LOGGER.debug("File found ? {}", resource.isPresent());
        resource.ifPresent(file -> issue.getAntipatterns().forEach(a -> saveIssue(file, a)));
    }

    private Optional<InputFile> getInputFile(final File file) {
        for (String path : file.getPaths()) {
            LOGGER.debug("Check if relative path {} is valid", path);
            InputFile resource = fileSystem.inputFile(
                    fileSystem.predicates().hasRelativePath(path));
            if (resource != null) {
                return Optional.of(resource);
            }
        }
        return Optional.empty();
    }

    private void saveIssue(final InputFile inputFile, final Antipattern antipattern) {
        RuleKey ruleKey = RuleKey.of(ChronosRulesDefinition.REPO_KEY, antipattern.getName());

        NewIssue newIssue = context.newIssue().forRule(ruleKey);
        NewIssueLocation primaryLocation = newIssue.newLocation().on(inputFile);
        newIssue.at(primaryLocation);
        newIssue.gap(Double.valueOf(antipattern.getSeverity()));

        try {
            newIssue.save();
        } catch (MessageException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
