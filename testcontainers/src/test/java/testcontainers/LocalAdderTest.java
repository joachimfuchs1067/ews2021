package testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import simpleapp.IntegerArgument;
import simpleapp.LocalAdder;

public class LocalAdderTest {

	@Test
	public void test() {
		
		LocalAdder instance = new LocalAdder();
		
		int a = 1;
		int b = 2;
		int expected = a + b;
		
		assertEquals(expected, instance.solve(IntegerArgument.of(a, b)).get());
	}
	
}
