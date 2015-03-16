package com.kingsnest.kneconomy;

import net.minecraftforge.common.config.Configuration;

public interface Serializeable {
	
	/**
	 * Serialize this object.
	 * 
	 * @param	config	The configuration file.
	 * */
	public void serialize(Configuration config);
	
	/**
	 * De-serialize this object.
	 * 
	 * @param	config	The configuration file.
	 * @param	key		The property key belonging to this.
	 * */
	public void deserialize(Configuration config, String key);

}
