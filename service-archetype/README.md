This project allows to generate new microservices just running one command.

Instructions:

1) Run this command so the project generator gets copied to your Maven repository:
mvn clean install


2) Run this command to generate a project:

mvn archetype:generate -DarchetypeGroupId=com.rafaborrego -DarchetypeArtifactId=service-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId={groupId} -DartifactId={artifact-id} -Dversion={version} -Ddomain={domain} -B

You can replace groupId, artifact-id, version and domain by what you need. For example:

mvn archetype:generate -DarchetypeGroupId=com.rafaborrego -DarchetypeArtifactId=service-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId=com.myCompany -DartifactId=invoice-service -Dversion=1.0.0-RELEASE -Ddomain=invoice -B

