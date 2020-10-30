package testJUnit;

import org.junit.Test;
import static org.junit.Assert.*;

import game.MovedPieceData;
import game.GamePieceCode;

public class TestMovedPieceData{
	MovedPieceData data1 = new MovedPieceData(GamePieceCode.BLACK, 0, 0);
	MovedPieceData data2 = new MovedPieceData(GamePieceCode.WHITE, 0, 1);
	
	@Test
	public void testGetGamePieceCode(){
		assertEquals(data1.getGamePieceCode(), GamePieceCode.BLACK);
		assertNotEquals(data1.getGamePieceCode(), data2.getGamePieceCode());
		assertEquals(data2.getGamePieceCode(), GamePieceCode.WHITE);
	}
	
	@Test
	public void testGetInitialPathPosition(){
		assertEquals(data1.getInitialPathPosition(), 0);
		assertEquals(data1.getInitialPathPosition(), data2.getInitialPathPosition());
		assertNotEquals(data2.getInitialPathPosition(), 2);
	}
	
	@Test
	public void testGetFinalPathPosition(){
		assertEquals(data1.getFinalPathPosition(), 0);
		assertNotEquals(data1.getFinalPathPosition(), data2.getFinalPathPosition());
		assertEquals(data2.getFinalPathPosition(), 1);
	}
}