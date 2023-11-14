package eu.deltasource;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber-report"},
    features = {"classpath:features"},
    snippets = CAMELCASE,
    glue = {"cucumber.api.spring", "eu.deltasource.steps"},
    tags = "not @Ignore"
)
public class CucumberIT {
}
