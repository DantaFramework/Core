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
    * [AEM Base](https://github.com/DantaFramework/AEMBase)
    * [Parent](https://github.com/DantaFramework/Parent)
    * [API](https://github.com/DantaFramework/API)
    * [Core](https://github.com/DantaFramework/Core)
    * [AEM](https://github.com/DantaFramework/AEM)   
    * [AEM Demo](https://github.com/DantaFramework/AEMDemo)
    
    **Note: for fresh installation, make sure to install ACS Common before running the maven build command**

### Jahia

  * Clone the following repositories into the same folder (i.e. C:\workspace\danta or /User/{username}/workspace/danta) 
    then run the maven build command (refer to **Compile** section of README.md, for each repository) in the following order
    * [JahiaDF Demo](https://github.com/DantaFramework/JahiaDFDemo)
    * [Parent](https://github.com/DantaFramework/Parent)
    * [API](https://github.com/DantaFramework/API)
    * [Core](https://github.com/DantaFramework/Core)
    * [JahiaDF](https://github.com/DantaFramework/JahiaDF)

### Official documentation

  * Read our [official documentation](https://danta.tikaltechnologies.io/docs) for more information.

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
           <id>deploy-jahia</id>
            <properties>
                <jahia.username>jahia</jahia.username>
                <jahia.password>password</jahia.password
                <jahia.server>http://localhost:8080</jahia.server>
                <jahia.slingURL>${jahia.server}/tools/osgi/console/install</jahia.slingURL>
            </properties>
        </profile>

**Note: The properties username, password, and server correspond to the default settings after installing Jahia**

Make sure the properties above correspond to your Jahia instance.

Then you should be able to deploy your module using the following Maven command:

    mvn clean install -Pdeploy-jahia
    
## Credit

Special thanks to Jose Alvarez, who named Danta for the powerful ancient Mayan pyramid, La Danta. 
La Danta is the largest pyramid in El Mirador—the biggest Mayan city found in Petén, Guatemala.