

https://docs.openrewrite.org/running-recipes/running-rewrite-on-a-maven-project-without-modifying-the-build#running-a-recipe-without-configuration-parameters

How to run from cmd

mvn -U org.openrewrite.maven:rewrite-maven-plugin:5.20.0:run \
-Drewrite.recipeArtifactCoordinates=org.apache.camel:camel-tooling-updates:4.7.0-SNAPSHOT \
-DactiveRecipes=org.apache.camel.updates.camel44.CamelQuarkusMigrationRecipe
-Drewrite.recipeFile=/org.apache.camel.updates/4.0.yaml


todo no quarkus word