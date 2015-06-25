/**
 * 
 */
package com.grm.hitboard.config;

/**
 * @author Levvy055
 *
 */
public enum ConfigId {
						LAST_SAVE_PATH("");

	private final String defValue;

	private ConfigId(String defValue) {
		this.defValue = defValue;
	}

	public static ConfigId getFromString(String name) throws IllegalArgumentException {
		return Enum.valueOf(ConfigId.class, name);
	}

	public String getDefValue() {
		return defValue;
	}
}
