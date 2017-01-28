package utwente.ss.connect.common;

/**
 * Programming Project module 2 Software Systems 2016-2017
 * Interface Protocol with the made agreements during the practical of 11-01-2017 
 * @author Niek Khasuntsev
 * @version 1.0.3 (26-01-2017)
 * 
 * Changelog:
 * 
 * v1.0.3:
 * 	Deleted whisper commands
 * 
 * v1.0.2:
 *  Added standart port number
 *  Made all strings "public static final" (just to be sure that you cannot override them)
 * 	Added example to the challengelist command
 * 	Added commands for whisper messages
 * 
 * v1.0.1:
 * 	Added command to request the leaderboard. 
 * 
 * v1.0.0:
 * 	Initial version of the protocol
 * 
 */

public interface Protocol {
	
	/**
	 * PAY ATTENTION: 
	 * 
	 * All arguments must be separated by a space. This also implies that the individual arguments are not allowed to contain spaces. The only exception to 
	 * this rule will be the chat function.
	 */
	
	// --------------- Pre game ---------------
	
	/**
	 * Value for the standart port 
	 */
	public static final int PORTNUMBER = 1337;
	
	
	/**
	 * Used for joining a server (or a.k.a. "lobby")
	 * 
	 * Arguments:
	 * - name = Name of the client
	 * - chat = Whether the client supports chat or not
	 * - challenge = Whether the client supports challenge or not
	 * - leaderboard = Whether the client supports leaderboard or not
	 * - security = Whether the client supports security functionalities or not
	 * 
	 * Requirements for arguments:
	 * - chat = 0 || 1
	 * - challenge = 0 || 1
	 * - leaderboard = 0 || 1
	 * - security = 0 || 1
	 * 
	 * Example:
	 * 
	 * My client, niek, wants to join and only support the chat feature
	 * Code: "joinrequest niek 1 0 0 0"
	 * 
	 * Direction: Client -> Server
	 */
	public static final String CLIENT_JOINREQUEST = "joinrequest";
	
	/**
	 * Used for accepting the client request to join the server  and notify the client of the features that the server supports
	 * 
	 * Arguments:
	 * - name = Name of the client
	 * - chat = Whether the client supports chat or not
	 * - challenge = Whether the client supports challenge or not
	 * - leaderboard = Whether the client supports leaderboard or not
	 * - security = Whether the client supports security functionalities or not
	 * 
	 * Requirements for arguments:
	 * - chat = 0 || 1
	 * - challenge = 0 || 1
	 * - leaderboard = 0 || 1
	 * - security = 0 || 1
	 * 
	 * Example:
	 *  Following up on the before mentioned joinrequest, the server only support the challenge feature and the leaderboard
	 *  
	 *  code: "acceptrequest niek 0 1 1 0
	 *  
	 * Direction: Server -> Client
	 */
	public static final String SERVER_ACCEPTREQUEST = "acceptrequest";
	
	/**
	 * Used to deny the client if the name is already in use 
	 * 
	 * Argument: 
	 * name : Name of the client 
	 * 
	 * Requirements for arguments:
	 *  - name is not allowed to contain spaces
	 *  
	 * Example:
	 *  If there is already a client named 'niek'
	 * 
	 *  code: "denyrequest niek"
	 *  
	 * Direction: Server -> Client 
	 */
	public static final String SERVER_DENYREQUEST  = "denyrequest";
	
	/**
	 * Used to let the server know that the client wants to start a game
	 * 
	 * Direction: Client -> Server
	 */
	public static final String CLIENT_GAMEREQUEST = "gamerequest";
	
	/**
	 * Used to let the client know that he has to wait for a second client to join the game
	 * 
	 * Direction: Server -> Client
	 */
	public static final String SERVER_WAITFORCLIENT = "waitforclient";
	
	/**
	 * Used to let the clients know that a game has been started
	 * 
	 * Arguments:
	 * - name1 = Name of the first player
	 * - name2 = Name of the second player
	 *  
	 * Example: 
	 *  A game is being started for the clients 'niek' and 'victor'
	 * 
	 *  code: "startgame niek victor"
	 * 
	 * Direction: Server -> Client 
	 */
	public static final String SERVER_STARTGAME = "startgame";
	
	// --------------- In game ---------------
	
	/**
	 * Used by the server to let the client know that he has to do a move
	 * 
	 * Arguments:
	 * - name = name of the player whose turn it is 
	 * 
	 * Example: 
	 *  It is my turn to do a move 
	 *  
	 *  code: "moverequest niek"
	 *  
	 * Direction: Server -> Client
	 */
	public static final String SERVER_MOVEREQUEST = "moverequest";
	
	/**
	 * Used by the client to do a move 
	 * 
	 * Arguments:
	 * - x = x Coordinate on the board
	 * - z = z Coordinate on the board
	 * 
	 * Note:
	 * See image in the repo for the directions of the x,y,z axis
	 * 
	 * Extra:
	 * - The individual implementation of the game has to calculate the y coordinate (in the form of a gravityMethod)  
	 *
	 *  Example: 
	 *  I want to place a move on the position x = 0 && z = 1
	 *  
	 *  code: "setmove 0 1"
	 *  
	 * Direction: Client -> Server
	 */
	public static final String CLIENT_SETMOVE = "setmove";
	
	/**
	 * Used to deny a move if the move is not valid. 
	 *
	 * Arguments 
	 * 
	 * Direction: Server -> Client
	 */
	public static final String SERVER_DENYMOVE = "denymove";
	
	/** 
	 * Used to let the clients know that a move has been made
	 * 
	 * Arguments:
	 * - name = Name of the client that performed the move //TODO check if necessary 
	 * - x = x Coordinate on the board
	 * - y = y Coordinate on the board
	 * - z = z Coordinate on the board
	 * 
	 * Example:
	 *  niek placed a move on x = 1 && y = 0 && z = 0
	 * 
	 *  code: "notifymove niek 1 0 0"
	 *  
	 * Direction: Server -> Client
	 */
	public static final String SERVER_NOTIFYMOVE = "notifymove";
	
	/**
	 * Used to announce that the game is over 
	 * 
	 * Arguments:
	 * - name = Name of the client that won the game
	 * 
	 * Example:
	 * victor wins the game 
	 * 
	 * code: "gameover victor
	 * Direction: Server -> Client		
	 */
	public static final String SERVER_GAMEOVER = "gameover";
	
	// --------------- Error ---------------
	
	/**
	 * Used when one client disconnects from the server 
	 * 
	 * Arguments:
	 * - name = name of the player who disconnected 
	 * 
	 * Example: victor rage quites because he is losing
	 * 
	 * code: "connectionlost victor"
	 * 
	 * Direction: Server -> Client
	 */
	public static final String SERVER_CONNECTIONLOST = "connectionlost";
	
	/**
	 * Used everytime a client tries to perform an invalid command
	 * 
	 * Direction: Server -> Client
	 */
	public static final String SERVER_INVALIDCOMMAND = "invalidcommand";
	
	// --------------- Extra features ---------------
	
	/**
	 * Used to send chat messages to the server
	 * 
	 * Argument:
	 * - message = The message that you want to send to the server
	 * 
	 * Example:
	 * 
	 *  code: sendmessage "this is a test message"
	 *  
	 * Direction: Client -> Server
	 */
	public static final String CLIENT_SENDMESSAGE = "sendmessage";

	
	/**
	 * Used to broadcast the message to all the clients that support the chat feature
	 * 
	 * Argument:
	 * - message = The message that you want to send to the server
	 * 
	 * Example:
	 * 
	 *  code: broadcastmessage "this is a test message"
	 * 
	 * Direction: Server -> Client
	 */
	public static final String SERVER_BROADCASTMESSAGE = "broadcastmessage";

	
	/**
	 * Used to request a list of all the clients that support the challenge feature
	 * 
	 * Direction: Client -> Server
	 */
	public static final String CLIENT_REQUESTCHALLENGELIST = "requestchallengelist";
	
	/**
	 * Used to send the clients that support the challenge feature
	 * 
	 * Direction: Server -> Client
	 * 
	 * Example: 
	 *  Niek request the challenge list. victor, steen and arthur support the feature
	 *  
	 *  code: challengelist victor steen arthur
	 */
	public static final String SERVER_CHALLENGELIST = "challengelist";
	
	/**
	 * Used to send a challenge to the other client
	 * 
	 * Arguments:
	 * - name = Name of the client that you want to challenge
	 * 
	 * Example:
	 *  I (niek) want to challenge victor to a game
	 * 
	 *  code: "requestchallenge victor"
	 *  
	 *  Direction: Client -> Server
	 */
	public static final String CLIENT_REQUESTCHALLENGE = "requestchallenge";
	
	/**
	 * Used to notify the client that someone else is challenging him
	 * 
	 * Arguments:
	 * - name = Name of the client that is challenging you
	 * 
	 * Example:
	 *  victor challenges me to a game
	 *  
	 *  code: "notifychallenge victor"
	 *  
	 * Direction: Server -> Client
	 *
	 */
	public static final String SERVER_NOTIFYCHALLENGE = "notifychallenge";
	
	/**
	 * Used by the client that got challenged to answer the challenge
	 * 
	 * Arguments:
	 * - answer = The answer to the challenge 
	 * - name = Name of the client that challenged
	 * 
	 * Requirements for arguments:
	 * - answer = 0 || 1
	 * 
	 * Example:
	 * I am responding to the challenge that victor send me, and I accept it
	 * 
	 * code: "answerchallenge 1 victor"
	 *
	 * Direction: Client -> Server
	 */
	public static final String CLIENT_ANSWERCHALLENGE = "answerchallenge";
	
	/**
	 * Used to notify the other client whether the challenge has been accepted or not 
	 * 
	 * Arguments:
	 * - answer = The answer to the challenge 
	 * - name = Name of the client that answered the challenge
	 * 
	 * Requirements for arguments:
	 * - answer = 0 || 1
	 * 
	 * Example:
	 * My previous response that is being broadcasted to the challenger (victor)
	 * 
	 * code: "resultchallenge 1 niek"
	 * 
	 * Direction: Server -> Client
	 */
	public static final String SERVER_RESULTCHALLENGE = "resultchallenge";
	
	/**
	 * Used by the client to request the leaderboard from the server
	 *  
	 * Direction: Client -> Server
	 */
	public static final String CLIENT_REQUESTLEADERBOARD = "requestleaderboard";
	
	/**
	 * Used to send the data of the game to the leaderboad
	 * 
	 * Arguments:
	 * - name  = Name of the player
	 * - score = The score of the game
	 * - time = date and time when the game was played
	 * 
	 * Requirements for arguments:
	 * - score = 0 || 1 (also  the client that disconnects, loses the game
	 * - time = UNIX Timestamp
	 * 
	 * Example:
	 * I won my last game 
	 * 
	 * code: setleaderboard niek 1 1484670803
	 * 
	 * Direction: Client -> Server
	 */
	public static final String CLIENT_SETLEADERBOARD = "setleaderboard";
	
	/**
	 * Used to broadcast the leaderboard to the clients
	 * 
	 * Arguments:
	 * - name  = Name of the player
	 * - score = The score of the game
	 * - time = date and time when the game was played
	 * 
	 * Note:
	 * This is done as often as needed. Untill all the entries in the leaderboard are returned
	 * 
	 * Example: 
	 * There are 2 entries in the leaderbaord:
	 * 
	 * code: "broadcastleaderboard niek 1 1484670803 niek 0 1484671115
	 * 
	 * Direction: Server -> Client
	 * 
	 */
	public static final String SERVER_BROADCASTLEADERBOARD = "broadcastleaderboard";

}
