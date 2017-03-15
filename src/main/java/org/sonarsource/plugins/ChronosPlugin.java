package org.sonarsource.plugins;

import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonarsource.plugins.languages.ChronosLanguage;
import org.sonarsource.plugins.rules.ChronosQualityProfile;
import org.sonarsource.plugins.rules.ChronosRulesDefinition;
import org.sonarsource.plugins.rules.ChronosSensor;

import static java.util.Arrays.asList;

/**
 * Created by cezarcoca.
 */
public class ChronosPlugin implements Plugin {

    @Override
    public void define(Context context) {
        context.addExtension(ChronosSensor.class);
        context.addExtension(ChronosRulesDefinition.class);
        context.addExtension(ChronosLanguage.class);
        context.addExtension(ChronosQualityProfile.class);

        context.addExtensions(asList(
                PropertyDefinition.builder(ChronosSensor.CHRONOS_SERVER_PATH)
                        .name("Chronos Server")
                        .description("HTTP URL of the Chronos server, such as http://yourhost.yourdomain/chronos. "
                                + "This value is used to trigger Chronos Analysis.")
                        .category("Chronos")
                        .defaultValue("")
                        .build()));
    }
}
