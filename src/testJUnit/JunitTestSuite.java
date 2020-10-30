package testJUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   TestMovedPieceData.class,
   TestDiceData.class,
   TestGameMove.class,
   TestCoordinatePair.class
})

public class JunitTestSuite {   
} 