/**
 * 
 */
package org.example;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.jgotesting.rule.JGoTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.aro_tech.extended_mockito.ExtendedMockito;
import com.github.aro_tech.tdd_mixins.AllAssertions;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

/**
 * @author aro_tech
 *
 */
@RunWith(ZohhakRunner.class)
public class ExampleTest implements ExtendedMockito, AllAssertions {

	static interface MyService {
		String retrieveInfo(String key);
	}

	class UnderTest {
		List<String> makeList(String... args) {
			return null;
		}
	}

	@Rule
	public JGoTestRule t = new JGoTestRule();
	
	private MyService mock = this.mock(MyService.class);

	@TestWith({ "2, 1,   3", 
		        "3, 5,   8",
		        "100, 100, 100"})
	public void should_compute_numbers(int addend1, int addend2, int result) {

		t.check("Addition", addend1 + addend2, x -> x == result);
		t.check("Subtraction", result - addend1, x -> x == addend2);
		t.check("Identity", addend1, x -> x == addend2);

	}

	@Test
	public void test_with_mockito_failure() {
		t.check("Bogus", false);
		this.verify(mock).retrieveInfo("5");
	}
	
	@Test
	public void test_with_multiple_assertj_soft_failures_and_jgo_checks() {
		t.check("Bouncing check 1", false);
		SoftAssertions multi = new SoftAssertions();
		
		multi.assertThat(Boolean.TRUE).isFalse();
		multi.assertThat(Boolean.FALSE).isTrue();
		t.check("Bouncing check 2", false);

		multi.assertAll();
		
		t.check("Bouncing check 3", false);
	}
	
	@Test
	public void test_with_multiple_assertj_soft_failures_and_no_jgo_checks() {
		SoftAssertions multi = new SoftAssertions();
		
		multi.assertThat(Boolean.TRUE).isFalse();
		multi.assertThat(Boolean.FALSE).isTrue();

		multi.assertAll();
		
	}
	
	@Test
	public void failing_test_with_log() {
		t.log("This should appear in the log");
		t.check("Bogus", false);
	}

	@Test
	public void passing_test_with_log() {
		t.log("This should NOT appear in the log");
		t.check("Ok", true);
	}
	
	@Test
	public void failing_mockito_test_with_log() {
		t.log("This should appear in the log");
		this.verify(mock).retrieveInfo("6");
	}

	
}
