package com.kingsnest.kneconomy;

import java.util.HashMap;

import net.minecraftforge.common.config.Configuration;

public interface Serializeable {
	
	public void serialize(Configuration config);
	
	/**
	 * De-serialize this object.
	 * 
	 * @param	data	A map of keys to their values in the config.
	 * */
	public void deserialize(HashMap<String, Object> data);

}
