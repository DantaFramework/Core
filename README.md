# Danta - Core Project

Danta is the agnostic multi-platform templating engine. enables developers and IT teams to use technologies they already know, expediting the creation and leveraging of reusable technical assets.

Danta - Core Project is the core maven project contained source codes which implements the API and all platform specific maven project (i.e. Danta - AEM Project) depend on.

## Prerequisites

 * [Danta - Parent Project](https://github.com/DantaFramework/Parent)
 * [Danta - API Project](https://github.com/DantaFramework/API)
 * Java 8
 * AEM 6.2 or later (for integration with AEM)
 * Jahia 7.2 or later (for integration with Jahia)

## Documentation

### Installation

#### Adobe Experience Manager (AEM)

  * Via AEM Package Manager, install [ACS AEM Commons 3.9.0](https://github.com/Adobe-Consulting-Services/acs-aem-commons/releases/tag/acs-aem-commons-3.9.0) or later
  * Clone the following repositories into the same folder (i.e. C:\workspace\danta or /User/{username}/workspace/danta) 
  then run the maven build command (refer to **Compile** section of README.md, for each repository) in the following order
    * [Parent](https://github.com/DantaFramework/Parent)
    * [API](https://github.com/DantaFramework/API)
    * [Core](https://github.com/DantaFramework/Core)
    * [AEM](https://github.com/DantaFramework/AEM)
    * [AEM Demo](https://github.com/DantaFramework/AEMDemo)   
    
    **Note: for fresh installation, make sure to install ACS Common before running the maven build command**

### Jahia

  * Clone the following repositories into the same folder (i.e. C:\workspace\danta or /User/{username}/workspace/danta) 
    then run the maven build command (refer to **Compile** section of README.md, for each repository) in the following order
    * [Parent](https://github.com/DantaFramework/Parent)
    * [API](https://github.com/DantaFramework/API)
    * [Core](https://github.com/DantaFramework/Core)
    * [JahiaDF](https://github.com/DantaFramework/JahiaDF)
    * [JahiaDF Demo](https://github.com/DantaFramework/JahiaDFDemo)

### Official documentation

* Read our [official documentation](https://danta.tikaltechnologies.io/docs/aem/index.html) for more information.

## License

Read [License](LICENSE) for more licensing information.

## Contribute

Read [here](CONTRIBUTING.md) for more information.

## Compile

    mvn clean install

## Deploy to AEM

    mvn clean install -Pdeploy-aem

## Deploy to Jahia

Edit your Maven settings.xml (usually in ~/.m2/settings.xml) to add the following profile :

        <profile>
            <id>jahia-local</id>
            <properties>
                <jahia.deploy.targetServerType>tomcat</jahia.deploy.targetServerType>
                <jahia.deploy.targetServerVersion>7</jahia.deploy.targetServerVersion>
                <jahia.deploy.targetServerDirectory><path_to_your_digital_factory_folder>/tomcat</jahia.deploy.targetServerDirectory>
            </properties>
        </profile>

Make sure you modify the jahia.deploy.targetServerDirectory to point to the /tomcat directory inside your Jahia installation.

Then you should be able to deploy your module using the following Maven command:

    mvn clean install jahia:deploy -P jahia-local
    
## Credit

Special thanks to Jose Alvarez, who named Danta for the powerful ancient Mayan pyramid, La Danta. 
La Danta is the largest pyramid in El Mirador—the biggest Mayan city found in Petén, Guatemala.