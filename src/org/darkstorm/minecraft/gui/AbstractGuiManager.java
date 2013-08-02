/*
 * Copyright (c) 2013, DarkStorm (darkstorm@evilminecraft.net)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.darkstorm.minecraft.gui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.theme.Theme;

/**
 * Minecraft GUI API
 * 
 * @author DarkStorm (darkstorm@evilminecraft.net)
 */
public abstract class AbstractGuiManager implements GuiManager {
	private final List<Frame> frames;

	private Theme theme;

	public AbstractGuiManager() {
		frames = new CopyOnWriteArrayList<Frame>();
	}

	@Override
	public abstract void setup();

	@Override
	public void addFrame(Frame frame) {
		frame.setTheme(theme);
		frames.add(0, frame);
	}

	@Override
	public void removeFrame(Frame frame) {
		frames.remove(frame);
	}

	@Override
	public Frame[] getFrames() {
		return frames.toArray(new Frame[frames.size()]);
	}

	@Override
	public void bringForward(Frame frame) {
		if(frames.remove(frame))
			frames.add(0, frame);
	}

	@Override
	public Theme getTheme() {
		return theme;
	}

	@Override
	public void setTheme(Theme theme) {
		this.theme = theme;
		for(Frame frame : frames)
			frame.setTheme(theme);
		resizeComponents();
	}

	protected abstract void resizeComponents();

	@Override
	public void render() {
		Frame[] frames = getFrames();
		for(int i = frames.length - 1; i >= 0; i--)
			frames[i].render();
	}

	@Override
	public void renderPinned() {
		Frame[] frames = getFrames();
		for(int i = frames.length - 1; i >= 0; i--)
			if(frames[i].isPinned())
				frames[i].render();
	}

	@Override
	public void update() {
		Frame[] frames = getFrames();
		for(int i = frames.length - 1; i >= 0; i--)
			frames[i].update();
	}
}
