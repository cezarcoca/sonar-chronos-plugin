package org.sonarsource.plugins.rules;

import static org.sonarsource.plugins.rules.ChronosRulesDefinition.ACCUMULATOR;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.BAZAAR;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.FLICKER;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.ORPHAN;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.OWNER_CHURN;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.PULSAR;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.REPO_KEY;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.SOLITAIRE;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.SUPERNOVA;
import static org.sonarsource.plugins.rules.ChronosRulesDefinition.TEAM_CHURN;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.Rule;
import org.sonar.api.utils.ValidationMessages;
import org.sonarsource.plugins.languages.ChronosLanguage;

/**
 * Created by cezarcoca.
 */
public class ChronosQualityProfile extends ProfileDefinition {

  @Override
  public RulesProfile createProfile(ValidationMessages validation) {
    RulesProfile profile = RulesProfile.create("Chronos Rules", ChronosLanguage.KEY);

    profile.activateRule(Rule.create(REPO_KEY, ACCUMULATOR), null);
    profile.activateRule(Rule.create(REPO_KEY, BAZAAR), null);
    profile.activateRule(Rule.create(REPO_KEY, FLICKER), null);
    profile.activateRule(Rule.create(REPO_KEY, ORPHAN), null);
    profile.activateRule(Rule.create(REPO_KEY, OWNER_CHURN), null);
    profile.activateRule(Rule.create(REPO_KEY, PULSAR), null);
    profile.activateRule(Rule.create(REPO_KEY, SOLITAIRE), null);
    profile.activateRule(Rule.create(REPO_KEY, SUPERNOVA), null);
    profile.activateRule(Rule.create(REPO_KEY, TEAM_CHURN), null);

    return profile;
  }
}
