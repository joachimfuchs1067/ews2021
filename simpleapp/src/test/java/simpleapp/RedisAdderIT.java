package simpleapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static simpleapp.RedisAdder.A;
import static simpleapp.RedisAdder.B;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import redis.clients.jedis.Jedis;

@Testcontainers
public class RedisAdderIT {

	@Container
	public GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
	
	private IAdder<Integer> sut;
	private Random random;
	
	@BeforeEach
	public void setUp() {
		
		sut = new RedisAdder(redis.getHost(), redis.getFirstMappedPort());
		random = new Random();
	}
	
	@AfterEach
	public void tearDown() {
		
		sut = null;
		random = null;
	}
	
	@Test
	public void test() {
		
		int a = random.nextInt() / 2;
		int b = random.nextInt() / 2;
		int expected = a + b;
		
		int result = sut.add(a, b);
		
		assertEquals(expected, result);
		
		checkRedis(a, b);
	}

	private void checkRedis(int a, int b) {
		Jedis jedis = new Jedis(redis.getHost(), redis.getFirstMappedPort());
		
		Object redisA = jedis.get(A);
		Object redisB = jedis.get(B);
		
		assertEquals(String.valueOf(a), redisA);
		assertEquals(String.valueOf(b), redisB);		
	}
}
