# Migration Utility

This migration utility is designed to transition the product's relational database from Oracle to PostgreSQL.

## Project Structure
.gitignore lib/ ojdbc6.jar pom.xml README.md src/ main/ java/ com/ ca/ migration/ constants/ MigrationQueryConstants.java dao/ ... dto/ ... exception/ ... MigrationScript.java service/ MigrationService.java PostgresService.java utility/ PrettySqlFormat.java MigrationReaderUtility.java vo/ resources/ .gitignore database.properties log4j.properties Spring-Module.xml spy.properties target/ classes/ .gitignore log4j.properties Spring-Module.xml spy.properties test-classes/

## Configuration

### Database Properties

Configure your database connection in `src/main/resources/database.properties`:

```properties
oracle.hostname=your_oracle_hostname
oracle.port=your_oracle_port
oracle.db=your_oracle_db
oracle.username=your_oracle_username
oracle.password=your_oracle_password

This [README.md](http://_vscodecontentref_/12) provides an overview of the project structure, configuration, services, running instructions, logging, and building the project.