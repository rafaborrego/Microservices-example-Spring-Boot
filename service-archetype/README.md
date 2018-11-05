This project allows to generate new microservices just running one command.

Instructions:

1) Run this command so the project generator gets copied to your Maven repository:
mvn clean install


2) Run the following command to generate a project:

mvn archetype:generate -DarchetypeGroupId=com.rafaborrego -DarchetypeArtifactId=service-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId={groupId} -DartifactId={artifact-id} -Dversion={version} -Ddomain={domain}

You can replace groupId, artifact-id and version by whatever you want, for example:

mvn archetype:generate -DarchetypeGroupId=com.rafaborrego -DarchetypeArtifactId=service-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId=com.myCompany -DartifactId=invoice-service -Dversion=1.0.0-RELEASE -Ddomain=invoice

