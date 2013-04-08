package peersim.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import peersim.core.GnutellaProtocol;

public class GnutellaTest {

	GnutellaProtocol gp;
	
	@Before
	public void setup() {
		gp = new GnutellaProtocol("0");
	}
	
	@Test
	public void testCreateFileMap() {
		gp.createFileMap();
		System.out.println(gp.fileMap.toString() + " test");
		
	}

}
