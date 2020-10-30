package testJUnit;

import org.junit.Test;
import static org.junit.Assert.*;

import game.CoordinatePair;

public class TestCoordinatePair{
	CoordinatePair pair1 = new CoordinatePair(0, 1);
	CoordinatePair pair2 = new CoordinatePair(1, 2);
	
	@Test
	public void testGetColumn(){
		assertEquals(pair1.getColumn(), 0);
		assertEquals(pair2.getColumn(), 1);
		assertNotEquals(pair1.getColumn(), pair2.getColumn());
	}
	
	@Test
	public void testGetRow(){
		assertEquals(pair1.getRow(), 1);
		assertEquals(pair2.getRow(), 2);
		assertNotEquals(pair1.getRow(), pair2.getRow());
	}
}