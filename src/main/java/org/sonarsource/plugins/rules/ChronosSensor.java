package org.sonarsource.plugins.rules;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.config.Settings;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

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
    parse().stream().forEach(issue -> getResourceAndSaveIssue(issue));
  }

  private void getResourceAndSaveIssue(final ChronosIssue error) {
    LOGGER.debug(error.toString());

    InputFile inputFile = fileSystem.inputFile(
        fileSystem.predicates().and(
            fileSystem.predicates().hasRelativePath(error.getFilePath()),
            fileSystem.predicates().hasType(InputFile.Type.MAIN)));

    LOGGER.debug("inputFile null ? " + (inputFile == null));

    if (inputFile != null) {
      saveIssue(inputFile, error.getType());
    } else {
      LOGGER.error("Not able to find a InputFile with " + error.getFilePath());
    }
  }

  private void saveIssue(final InputFile inputFile, final String externalRuleKey) {
    RuleKey ruleKey = RuleKey.of(ChronosRulesDefinition.REPO_KEY, externalRuleKey);

    NewIssue newIssue = context.newIssue().forRule(ruleKey);
    NewIssueLocation primaryLocation = newIssue.newLocation().on(inputFile);
    newIssue.at(primaryLocation);

    newIssue.save();
  }

  private List<ChronosIssue> parse() {
    List<ChronosIssue> result = new ArrayList<>();
    result.add(new ChronosIssue(ChronosRulesDefinition.ACCUMULATOR,
        "src/main/java/org/sonarsource/plugins/rules/ChronosSensor.java"));
    result.add(new ChronosIssue(ChronosRulesDefinition.BAZAAR,
        "src/main/java/org/sonarsource/plugins/rules/ChronosRulesDefinition.java"));
    result.add(new ChronosIssue(ChronosRulesDefinition.FLICKER,
        "src/main/java/org/sonarsource/plugins/rules/ChronosQualityProfile.java"));
    result.add(new ChronosIssue(ChronosRulesDefinition.ORPHAN,
        "src/main/java/org/sonarsource/plugins/rules/ChronosSensor.java"));
    result.add(new ChronosIssue(ChronosRulesDefinition.OWNER_CHURN,
        "src/main/java/org/sonarsource/plugins/rules/ChronosSensor.java"));
    result.add(new ChronosIssue(ChronosRulesDefinition.PULSAR,
        "src/main/java/org/sonarsource/plugins/rules/ChronosSensor.java"));
    result.add(new ChronosIssue(ChronosRulesDefinition.SOLITAIRE,
        "src/main/java/org/sonarsource/plugins/rules/ChronosSensor.java"));
    result.add(new ChronosIssue(ChronosRulesDefinition.SUPERNOVA,
        "src/main/java/org/sonarsource/plugins/rules/ChronosSensor.java"));
    result.add(new ChronosIssue(ChronosRulesDefinition.TEAM_CHURN,
        "src/main/java/org/sonarsource/plugins/rules/ChronosSensor.java"));
    return result;
  }

  private static class ChronosIssue {

    private String type;
    private String filePath;

    public ChronosIssue(String type, String filePath) {
      this.type = type;
      this.filePath = filePath;
    }

    public String getType() {
      return type;
    }

    public String getFilePath() {
      return filePath;
    }
  }
}
