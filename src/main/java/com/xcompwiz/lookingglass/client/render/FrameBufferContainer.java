package com.xcompwiz.lookingglass.client.render;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentMap;

import net.minecraft.client.shader.Framebuffer;

import com.google.common.collect.MapMaker;
import com.xcompwiz.lookingglass.client.proxyworld.WorldView;
import com.xcompwiz.lookingglass.log.LoggerUtils;

public class FrameBufferContainer {
	/**
	 * Using this map we can detect which FBOs should be freed. The map will delete any entry where the world view object is garbage collected. It unfortunately
	 * can't detect that the world view is otherwise leaked, though, just when it's gone.
	 */
	private static final ConcurrentMap<WorldView, Framebuffer>	weakfbomap		= new MapMaker().weakKeys().makeMap();
	private static final Collection<Framebuffer>					framebuffers	= new HashSet<>();

	public static Framebuffer createNewFramebuffer(WorldView view, int width, int height) {
		Framebuffer fbo = new Framebuffer(width, height, true);
		weakfbomap.put(view, fbo);
		framebuffers.add(fbo);
		return fbo;
	}

	public static void removeWorldView(WorldView view) {
		weakfbomap.remove(view);
	}

	public static synchronized void detectFreedWorldViews() {
		Collection<Framebuffer> unpairedFBOs = new HashSet<>(framebuffers);
		unpairedFBOs.removeAll(weakfbomap.values());
		if (unpairedFBOs.isEmpty()) return;
		LoggerUtils.info("Freeing %d loose framebuffers from expired world views", unpairedFBOs.size());
		for (Framebuffer fbo : unpairedFBOs) {
			fbo.deleteFramebuffer();
		}
		framebuffers.removeAll(unpairedFBOs);
	}
}
