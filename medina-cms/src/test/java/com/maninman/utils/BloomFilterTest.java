package com.maninman.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BloomFilterTest {

	BloomFilter<String> bf =  new BloomFilter<String>(0.1, 3);
	@Before
	public void setUp() throws Exception {
		bf.add("https://github.com/MagnusS/Java-BloomFilter1");
		bf.add("https://github.com/MagnusS/Java-BloomFilter2");
		bf.add("https://github.com/MagnusS/Java-BloomFilter3");
		bf.add("https://github.com/MagnusS/Java-BloomFilter4");
		bf.add("https://github.com/MagnusS/Java-BloomFilter5");
	}

	@Test
	public void testContainsE() {
		assertTrue(bf.contains("https://github.com/MagnusS/Java-BloomFilter1322"));
	}

}
