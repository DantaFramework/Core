# Danta - Core Project

Danta - Core Project is the core maven project contained source codes which implements the API and all platform specific maven project (i.e. Danta - AEM Project) depend on.

## Documentation

 * Read our [official documentation](http://danta.tikaltechnologies.io/docs) for more information.

## Prerequisites

 * [Danta - Parent Project](https://github.com/DataFramework/Parent)
 * [Danta - API Project](https://github.com/DataFramework/API)
 * Java 8
 * AEM 6.2 or later (for integration with AEM)
 * Jahia 7.2 or later (for integration with Jahia)

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