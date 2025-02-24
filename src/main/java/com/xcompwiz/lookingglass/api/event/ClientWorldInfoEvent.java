package com.xcompwiz.lookingglass.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Called on the server side to allow mods to send dimension information to clients
 * @author xcompwiz
 */
public class ClientWorldInfoEvent extends Event {

	public final int			dim;
	public final EntityPlayerMP	player;

	public ClientWorldInfoEvent(int dim, EntityPlayerMP player) {
		this.dim = dim;
		this.player = player;
	}

}
