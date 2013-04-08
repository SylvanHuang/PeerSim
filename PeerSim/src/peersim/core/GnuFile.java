package peersim.core;

import java.util.UUID;

public class GnuFile {

	protected int size;
	protected int requests;
	protected UUID id;
	
	public GnuFile(UUID theID, int theSize) {
		id = theID;
		size = theSize;
		requests = 0;
	}
}
