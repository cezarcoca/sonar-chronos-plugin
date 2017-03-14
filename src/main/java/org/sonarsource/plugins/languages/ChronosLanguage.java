package org.sonarsource.plugins.languages;

import org.sonar.api.resources.AbstractLanguage;

/**
 * Created by cezarcoca.
 */
public class ChronosLanguage extends AbstractLanguage {

  public static final String NAME = "Chronos";
  public static final String KEY = "chronos";

  public ChronosLanguage() {
    super(KEY, NAME);
  }

  @Override
  public String[] getFileSuffixes() {
    return new String[0];
  }
}
