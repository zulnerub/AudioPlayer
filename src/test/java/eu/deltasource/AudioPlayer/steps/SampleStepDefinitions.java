package eu.deltasource.AudioPlayer.steps;

import static java.lang.Boolean.TRUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;

public class SampleStepDefinitions {

    @Value("${flag.value}")
    private boolean flag;

    @Given("^we prepare data -mocked$")
    public void wePrepareData_mocked() {

    }

    @When("data is mutated -mocked")
    public void dataIsMutatedMocked() {

    }

    @Then("the data is changed -mocked")
    public void theDataIsChangedMocked() {
        assertThat("Test failed as expected - mocked fail", flag, is(TRUE));
    }
}
