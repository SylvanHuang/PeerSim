package peersim.core;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import peersim.cdsim.CDProtocol;
import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.vector.SingleValueHolder;

public class GnutellaProtocol extends SingleValueHolder implements CDProtocol, Linkable {
	/**
	 * A protocol that stores links. It does nothing apart from that.
	 * It is useful to model a static link-structure
	 * (topology). The only function of this protocol is to serve as a source of
	 * neighborhood information for other protocols.
	 */

	// --------------------------------------------------------------------------
	// Parameters
	// --------------------------------------------------------------------------

	/**
	 * Default init capacity
	 */
	private static final int DEFAULT_INITIAL_CAPACITY = 10;

	/**
	 * Initial capacity. Defaults to {@value #DEFAULT_INITIAL_CAPACITY}.
	 * @config
	 */
	private static final String PAR_INITCAP = "capacity";
	
	/**
	 * Max Load. Defaults to {@value #DEFAULT_INITIAL_CAPACITY}.
	 * @config
	 */	
	private static final int MAX_LOAD = 100;

	// --------------------------------------------------------------------------
	// Fields
	// --------------------------------------------------------------------------

	protected Node[] neighbors;						// Neighbors
	protected int len;								// Actual number of neighbors in the array
	protected double fileQuota;						// Amount of bandwidth that can be transferred in each cycle
<<<<<<< HEAD
	public ArrayList<GnuFile> fileList;  			// File objects have id, size, requests
	protected int numFiles;							// Number of files for each node to carry
=======
	public HashMap<UUID, Integer> fileMap;  		// Hash with key=unique ID, value = filesize in MB
	protected int numFiles = 5;							// Number of files for each node to carry
>>>>>>> branch 'master' of https://github.com/giangttpham/PeerSim.git

	// --------------------------------------------------------------------------
	// Initialization
	// --------------------------------------------------------------------------

	public GnutellaProtocol(String s)
	{
		super(s);				// value in the superclass represents current load on the node.
//		neighbors = new Node[Configuration.getInt(s + "." + PAR_INITCAP,
//				DEFAULT_INITIAL_CAPACITY)];
//		len = 0;
//		createFileMap();
//		fileQuota = 30;
		numFiles = 20;
		fileList = new ArrayList<GnuFile>();
	}

	//--------------------------------------------------------------------------

	public Object clone()
	{
		GnutellaProtocol gp = null;
		gp = (GnutellaProtocol) super.clone(); // parent handles exceptions
		gp.neighbors = new Node[neighbors.length];
		System.arraycopy(neighbors, 0, gp.neighbors, 0, len);
		gp.len = len;
		return gp;
	}

	// --------------------------------------------------------------------------
	// Methods
	// --------------------------------------------------------------------------

	public boolean contains(Node n)
	{
		for (int i = 0; i < len; i++) {
			if (neighbors[i] == n)
				return true;
		}
		return false;
	}

	// --------------------------------------------------------------------------

	/** Adds given node if it is not already in the network. There is no limit
	 * to the number of nodes that can be added. */
	public boolean addNeighbor(Node n)
	{
		for (int i = 0; i < len; i++) {
			if (neighbors[i] == n)
				return false;
		}
		if (len == neighbors.length) {
			Node[] temp = new Node[3 * neighbors.length / 2];
			System.arraycopy(neighbors, 0, temp, 0, neighbors.length);
			neighbors = temp;
		}
		neighbors[len] = n;
		len++;
		return true;
	}

	// --------------------------------------------------------------------------

	public Node getNeighbor(int i)
	{
		return neighbors[i];
	}

	// --------------------------------------------------------------------------

	public int degree()
	{
		return len;
	}

	// --------------------------------------------------------------------------

	public void pack()
	{
		if (len == neighbors.length)
			return;
		Node[] temp = new Node[len];
		System.arraycopy(neighbors, 0, temp, 0, len);
		neighbors = temp;
	}

	// --------------------------------------------------------------------------

	public String toString()
	{
		if( neighbors == null ) return "DEAD!";
		StringBuffer buffer = new StringBuffer();
		buffer.append("len=" + len + " maxlen=" + neighbors.length + " [");
		for (int i = 0; i < len; ++i) {
			buffer.append(neighbors[i].getIndex() + " ");
		}
		return buffer.append("]").toString();
	}

	// --------------------------------------------------------------------------

	public void onKill()
	{
		neighbors = null;
		len = 0;
	}

	@Override
	public void nextCycle(Node node, int protocolID) {
		int linkableID = FastConfig.getLinkable(protocolID);
        Linkable linkable = (Linkable) node.getProtocol(linkableID);
        if (this.fileQuota == 0) {
            return; // quota is exceeded
        }
        // this takes the most distant neighbor based on local load
        GnutellaProtocol neighbor = null;
        double maxdiff = 0;
        for (int i = 0; i < linkable.degree(); ++i) {
            Node peer = linkable.getNeighbor(i);
            // The selected peer could be inactive
            if (!peer.isUp())
                continue;
            GnutellaProtocol n = (GnutellaProtocol) peer.getProtocol(protocolID);
            if (n.fileQuota == 0.0)
                continue;
            double d = Math.abs(value - n.value);
            if (d > maxdiff) {
                neighbor = n;
                maxdiff = d;
            }
        }
        if (neighbor == null) {
            return;
        }
        replicateFile(neighbor);
	}
	
	public void replicateFile(GnutellaProtocol neighbor) {
		// TODO: finishing implementing file transfer
		GnuFile randomFile = getRandomFile();
		neighbor.fileList.add(randomFile);
		if (this.value < MAX_LOAD && neighbor.value < MAX_LOAD) {
			this.value += randomFile.size;
			neighbor.value += randomFile.size;
		}		
	}
	
	public  void requestFile() {
		
	}
	
	public GnuFile getRandomFile() {
		Random generator = new Random();
		return fileList.get(generator.nextInt(fileList.size()));
	}
	
	public void createFileMap() {
		for (int i = 0; i < numFiles; i++) {
			UUID fileID = UUID.randomUUID();
			int fileSize = (int) Math.floor(Math.random() * 1000 + 1);	// Max filesize for transfer = 1GB, min = 1MB
			//System.out.println("UUID: " + fileID.toString() + " File size: " + fileSize);
			GnuFile newFile = new GnuFile(fileID, fileSize);
			fileList.add(newFile);
		}
	}



}
