package peersim.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import peersim.core.GnuFile;
import peersim.core.GnutellaProtocol;

public class GnutellaTest {

	GnutellaProtocol gp;
	GnutellaProtocol gp2;
	
	@Before
	public void setup() {
		gp = new GnutellaProtocol("0");
		gp.createFileMap();
		gp2 = new GnutellaProtocol("0");
	}
	
	@Test
	public void testCreateFileMap() {
		//System.out.println(gp.fileMap.toString());
		assertEquals("20 files created", 20, gp.fileList.size());
	}
	
	@Test
	public void testGetRandomFile() {
		GnuFile randomFile = gp.getRandomFile();
		assertTrue("Random file is from list", gp.fileList.contains(randomFile));
	}
	
	@Test
	public void testReplicateFile() {
		assertTrue("Neighbor is empty", gp2.fileList.isEmpty());
		gp.replicateFile(gp2);
		assertEquals("Neighbor has one entry", 1, gp2.fileList.size());
	}

}
