/**
 * 
 */
package pl.grmdev.hitboard.requests.util;

import java.util.*;
/**
 * POST RESTful API methods
 * 
 * @author Levvy055
 */
public enum HbPost {
	MEDIA_CREATE_VOD("media/video/:channel"),
	CHANNEL_RUN_COMMERCIAL("ws/combreak/:channel/:adCount"),
	CHANNEL_EDIT_EDITORS("editors/:channel"),
	USER_DEFAULT_TEAM("user/:user/team/default"),
	FOLLOWERS_FOLLOW("follow"),
	TEAM_CREATE("team"),
	CHAT_EDIT_MODERATORS("chat/moderators/:channel"),
	CHAT_BLACKLIST_UPDATE("chat/blacklist/:channel"),
	CHAT_SETTINGS_UPDATE("chat/settings/:channel"),
	TOKEN_GET("auth/token", new Params().p("login").p("pass")),
	TOKEN_AUTH("auth/login", new Params().p("authToken"));
	
	private String cmd;
	private String[] objs;
	private Params params;
	
	private HbPost(String cmd, Params params) {
		this(cmd);
		this.params = params;
	}
	
	private HbPost(String cmd) {
		this.cmd = cmd;
		if (cmd.contains(":")) {
			int objCount = cmd.length() - cmd.replaceAll(":", "").length();
			objs = new String[objCount];
			int bI = 0, eI = 0;
			for (int i = 0; i < objCount; i++) {
				bI = cmd.indexOf(":", eI);
				eI = i < cmd.length() && cmd.indexOf("/", bI + 1) != -1
						? cmd.indexOf("/", bI + 1)
						: cmd.length() - 1;
				objs[i] = cmd.substring(bI, eI);
			}
		}
	}
	
	public String getCmd() {
		return cmd;
	}
	
	public String getCmd(String... args) throws Exception {
		if (args == null || args.length == 0) {
			return getCmd();
		}
		if (args.length != getObjects().length) {
			throw new Exception("Wrong amount of input parameters!\nShould be "
					+ getObjects().length + " but was " + args.length);
		}
		String result = getCmd();
		for (int i = 0; i < objs.length; i++) {
			result = result.replace(objs[i], args[i]);
		}
		return result;
	}
	
	public String[] getObjects() {
		return objs;
	}
	
	public boolean hasParams() {
		return (params == null || params.isEmpty()) ? false : true;
	}
	
	public Iterator<String> paramIterator() {
		return params.iterator();
	}
	
	public Object getParamObject(String key) {
		return params.get(key);
	}
	
	public Map<String, Object> getParams() {
		return params.getAll();
	}
}
