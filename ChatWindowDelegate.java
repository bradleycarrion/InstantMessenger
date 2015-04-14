/**
 * @author Bert Heinzelman
 * @date 4/13/15
 * @edited na
 */
public interface ChatWindowDelegate {
	/**
	 * this function is called from ChatWindow when exit is pressed
	 * it is used to remove window from list of open windows
	 * @param theOther user to be removed
	 */
	public void didExitWindow(String theOther);
}
