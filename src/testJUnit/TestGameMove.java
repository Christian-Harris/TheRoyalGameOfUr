package testJUnit;

import org.junit.Test;
import static org.junit.Assert.*;

import game.GameMove;
import game.PlayerCode;

public class TestGameMove{
	GameMove move1 = new GameMove(0, new String("abcd"), PlayerCode.BLACK);
	GameMove move2 = new GameMove(1, new String("xyz"), PlayerCode.WHITE);
	
	@Test
	public void testGetType(){
		assertEquals(move1.getType(), 0);
		assertEquals(move2.getType(), 1);
		assertNotEquals(move1.getType(), move2.getType());
	}
	
	@Test
	public void testGetData(){
		assertEquals((String)(move1.getData()), "abcd");
		assertEquals((String)(move2.getData()), "xyz");
		assertNotEquals((String)(move1.getData()), (String)(move2.getData()));
	}
	
	@Test
	public void testGetPlayerCode(){
		assertEquals(move1.getPlayerCode(), PlayerCode.BLACK);
		assertEquals(move2.getPlayerCode(), PlayerCode.WHITE);
		assertNotEquals(move1.getPlayerCode(), move2.getPlayerCode());
	}
}