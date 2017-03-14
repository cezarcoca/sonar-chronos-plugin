/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.plugins.rules;

import org.sonar.api.rule.RuleKey;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.rule.Severity;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.plugins.languages.ChronosLanguage;

public class ChronosRulesDefinition implements RulesDefinition {

  public static final String REPO_KEY = "Chronos";
  public static final String REPO_NAME = "Chronos Analyzer";

  // anti-patterns
  public static final String ACCUMULATOR = "accumulator";
  public static final String SUPERNOVA = "supernova";
  public static final String ORPHAN = "orphan";
  public static final String TEAM_CHURN = "team churn";
  public static final String PULSAR = "pulsar";
  public static final String OWNER_CHURN = "owner churn";
  public static final String FLICKER = "flicker";
  public static final String BAZAAR = "bazaar";
  public static final String SOLITAIRE = "solitaire";
  public static final String ORPHAN_DETAILS = "This is a file for which the main developer (a.k.a file owner) is not anymore involved in the project. This means that there is a risk that the knowledge about the file gets lost, which in turn might raise the maintenance costs and risks of the project.";
  public static final String TEAM_CHURN_DETAILS = "This is a file that was changed by many people over time, by a rather non-cohesive team in which there is a constant “come and go”: many new people join the team later, while some of the people stop working on the file. All this high team dynamics may lead to a lack of conceptual cohesion, and a degradation of the file’s initial conceptual integrity. As a result of different people with different styles, and different understandings of the file, bugs may easily creep in and the understandability of the file might be severely hampered.";
  public static final String PULSAR_DETAILS = "This is a file that goes through a large number of grow-shrink cycles. Size shrinking is usually a (good) sign that refactoring is systematically being performed. At the same time, the very frequent need for refactoring is a sign of instability. Usually the cause of this instability is that the file implements a volatile functionality/concept, either because it is not yet well understood by the development team, or because the functionality/concept itself is so important that the team wants to sharpen it. Such files are not necessarily bad, but they are important to understand, because they may contain key aspects of a system. Also, if currently the file is still “pulsating” there is a high chance that it’s still in an unstable state and will continue to change in a similar way in the future.";
  public static final String OWNER_CHURN_DETAILS = "This is a file on which numerous committers that made major contributions, namely at some point in time they became “file owners”, i.e. at a particular moment they had the most lines of code having been edited by them. A large number of file owners is a sign the the file had suffered many significant changes over time, and these significant changes were driven by different people. These files are interesting for at least two reasons: (i) the many major changes indicate its importance/centrality in the project; (ii) the fact that the changes were driven by different people brings in the risk that the conceptual integrity of the file might be compromised, which in turn leads to higher maintenance costs";
  public static final String FLICKER_DETAILS = "This is a file that is very often changed, whereby each of these changes is only a very small refinement, which is usually induced by the need to adapt to another change that occurred in the system. The cause is often that the file has too many responsibilities and/or dependencies on too many other files in the system.";
  public static final String BAZAAR_DETAILS = "This is a file that has been changed by many different committers during the same period of time. A large number of committers may lead to a lack of uniformity and coherence of the functionality contained in the file. This in turn makes the file harder to understand, and increases its risk of having hidden bugs, due to the more difficult synchronisation among developers. Thus, Bazaar files require a special attention in terms of testing, and are candidates for a refactoring, especially if they are sufficiently large and complex. Bazaar files may also be a sign of a non-cohesive functionality, or a very central part of a system, that requires changes from developers working on different sides of that system.";
  public static final String SOLITAIRE_DETAILS = "This is a file that has currently a single committer that is still active in the project. In other words, these are files with a “Bus/Truck Factor” of one. This means that if the only committer that has an understanding of that file leaves the project, the entire knowledge about that file gets lost, which in turn might raise the costs (and risks) of the further maintenance of the project.";
  // anti-patterns details
  private static final String ACCUMULATOR_DETAILS = "This is a file that has significantly grown in size over time without being cleaned up by refactoring commits. Such a file does constantly accumulate complexity, and becomes over time harder to understand and to maintain.";
  private static final String SUPERNOVA_DETAILS = "This is a file that has one or more “leaps” of massive growth over short periods of time. This sudden massive growth can be a sign of unclean design, or a hasty implementation that might introduce new bugs, especially if is not followed by any cleanup actions.";
  private NewRepository repository;

  @Override
  public void define(Context context) {
    repository = context.createRepository(REPO_KEY, ChronosLanguage.KEY).setName(REPO_NAME);

    registerRule(ACCUMULATOR, ACCUMULATOR_DETAILS, Severity.MAJOR);
    registerRule(SUPERNOVA, SUPERNOVA_DETAILS, Severity.MAJOR);
    registerRule(ORPHAN, ORPHAN_DETAILS, Severity.MAJOR);
    registerRule(TEAM_CHURN, TEAM_CHURN_DETAILS, Severity.MAJOR);
    registerRule(PULSAR, PULSAR_DETAILS, Severity.MAJOR);
    registerRule(OWNER_CHURN, OWNER_CHURN_DETAILS, Severity.MAJOR);
    registerRule(FLICKER, FLICKER_DETAILS, Severity.MAJOR);
    registerRule(BAZAAR, BAZAAR_DETAILS, Severity.MAJOR);
    registerRule(SOLITAIRE, SOLITAIRE_DETAILS, Severity.MAJOR);
    repository.done();
  }

  private void registerRule(String key, String descriptions, String severity) {
    RuleKey ruleKey = RuleKey.of(REPO_KEY, key);
    repository.createRule(ruleKey.rule())
        .setName(Character.toUpperCase(key.charAt(0)) + key.substring(1))
        .setHtmlDescription(descriptions)
        .setStatus(RuleStatus.READY)
        .setSeverity(severity);
  }
}
