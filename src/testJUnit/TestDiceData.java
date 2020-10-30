package testJUnit;

import org.junit.Test;
import static org.junit.Assert.*;

import game.DiceData;

public class TestDiceData{
	DiceData data1 = new DiceData(1, 2, 3, 4);
	DiceData data2 = new DiceData(1, 2, 4, 8);
	
	@Test
	public void testGetRoll1(){
		assertEquals(data1.getRoll1(), data2.getRoll1());
		assertNotEquals(data1.getRoll1(), 2);
		assertNotEquals(data2.getRoll1(), -1);
	}
	
	@Test
	public void testGetRoll2(){
		assertEquals(data1.getRoll2(), data2.getRoll2());
		assertNotEquals(data1.getRoll2(), 1);
		assertNotEquals(data2.getRoll2(), -1);
	}
	
	@Test
	public void testGetRoll3(){
		assertEquals(data1.getRoll3(), 3);
		assertNotEquals(data1.getRoll3(), 2);
		assertEquals(data2.getRoll3(), 4);
	}
	
	@Test
	public void testGetRoll4(){
		assertNotEquals(data1.getRoll4(), data2.getRoll4());
		assertEquals(data1.getRoll4(), 4);
		assertEquals(data2.getRoll4(), 8);
	}
}