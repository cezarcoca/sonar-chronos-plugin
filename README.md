## Chronos Plugin for SonarQube ##

**Chronos Sonar Plugin** provides an integration with the version control systems data mining tool **Chronos**. The deployment diagram is depicted below.

![Chronos/SonarQube deployment diagram](https://drive.google.com/uc?id=0B9tMA3RbZ5P_ekJreC1Qc0Zma3c)

**How to build**

Clone a copy of the main **Chronos Plugin for SonarQube** git repository by running:

    git clone https://github.com/cezarcoca/sonar-chronos-plugin.git

Enter the *sonar-chronos-plugin* directory

    cd sonar-chronos-plugin
 
Run the build script:

    mvn clean package

**Installation**

1. Copy the plugin into the *SONARQUBE_HOME/extensions/plugins directory*
2. Restart the [SonarQube](https://docs.sonarqube.org/display/SONAR/Documentation) server (version **5.6+**)

**Usage**

To feed [SonarQube](https://docs.sonarqube.org/display/SONAR/Documentation) with Chronos support, use below command

    mvn sonar:sonar