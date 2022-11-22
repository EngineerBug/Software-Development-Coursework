/*
 * Running the test suite:
 * - Open the project in intellij.
 * - Run the TestSuite.java file.
 * - If all tests pass and no unexpected exceptions are thrown, intellij will return exit code 0.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestCard.class, TestPlayer.class, TestCardGame.class})
public class TestSuite {
}