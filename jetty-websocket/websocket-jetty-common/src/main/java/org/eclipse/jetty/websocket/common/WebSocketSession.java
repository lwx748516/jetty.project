//
// ========================================================================
// Copyright (c) 1995-2020 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under
// the terms of the Eclipse Public License 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0
//
// This Source Code may also be made available under the following
// Secondary Licenses when the conditions for such availability set
// forth in the Eclipse Public License, v. 2.0 are satisfied:
// the Apache License v2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

package org.eclipse.jetty.websocket.common;

import java.io.IOException;
import java.net.SocketAddress;
import java.time.Duration;
import java.util.Objects;

import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.SuspendToken;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.core.FrameHandler;

public class WebSocketSession implements Session, SuspendToken, Dumpable
{
    private static final Logger LOG = Log.getLogger(WebSocketSession.class);
    private final FrameHandler.CoreSession coreSession;
    private final JettyWebSocketFrameHandler frameHandler;
    private final JettyWebSocketRemoteEndpoint remoteEndpoint;
    private final UpgradeRequest upgradeRequest;
    private final UpgradeResponse upgradeResponse;

    public WebSocketSession(FrameHandler.CoreSession coreSession, JettyWebSocketFrameHandler frameHandler)
    {
        this.frameHandler = Objects.requireNonNull(frameHandler);
        this.coreSession = Objects.requireNonNull(coreSession);
        this.upgradeRequest = frameHandler.getUpgradeRequest();
        this.upgradeResponse = frameHandler.getUpgradeResponse();
        this.remoteEndpoint = new JettyWebSocketRemoteEndpoint(coreSession, frameHandler.getBatchMode());
    }

    @Override
    public void close()
    {
        remoteEndpoint.close();
    }

    @Override
    public void close(CloseStatus closeStatus)
    {
        remoteEndpoint.close(closeStatus.getCode(), closeStatus.getPhrase());
    }

    @Override
    public void close(int statusCode, String reason)
    {
        remoteEndpoint.close(statusCode, reason);
    }

    @Override
    public WebSocketBehavior getBehavior()
    {
        switch (coreSession.getBehavior())
        {
            case CLIENT:
                return WebSocketBehavior.CLIENT;
            case SERVER:
                return WebSocketBehavior.SERVER;
            default:
                return null;
        }
    }

    @Override
    public Duration getIdleTimeout()
    {
        return coreSession.getIdleTimeout();
    }

    @Override
    public int getInputBufferSize()
    {
        return coreSession.getInputBufferSize();
    }

    @Override
    public int getOutputBufferSize()
    {
        return coreSession.getOutputBufferSize();
    }

    @Override
    public long getMaxBinaryMessageSize()
    {
        return coreSession.getMaxBinaryMessageSize();
    }

    @Override
    public long getMaxTextMessageSize()
    {
        return coreSession.getMaxTextMessageSize();
    }

    @Override
    public long getMaxFrameSize()
    {
        return coreSession.getMaxFrameSize();
    }

    @Override
    public boolean isAutoFragment()
    {
        return coreSession.isAutoFragment();
    }

    @Override
    public void setIdleTimeout(Duration duration)
    {
        coreSession.setIdleTimeout(duration);
    }

    @Override
    public void setInputBufferSize(int size)
    {
        coreSession.setInputBufferSize(size);
    }

    @Override
    public void setOutputBufferSize(int size)
    {
        coreSession.setOutputBufferSize(size);
    }

    @Override
    public void setMaxBinaryMessageSize(long size)
    {
        coreSession.setMaxBinaryMessageSize(size);
    }

    @Override
    public void setMaxTextMessageSize(long size)
    {
        coreSession.setMaxTextMessageSize(size);
    }

    @Override
    public void setMaxFrameSize(long maxFrameSize)
    {
        coreSession.setMaxFrameSize(maxFrameSize);
    }

    @Override
    public void setAutoFragment(boolean autoFragment)
    {
        coreSession.setAutoFragment(autoFragment);
    }

    @Override
    public String getProtocolVersion()
    {
        return upgradeRequest.getProtocolVersion();
    }

    @Override
    public JettyWebSocketRemoteEndpoint getRemote()
    {
        return remoteEndpoint;
    }

    @Override
    public boolean isOpen()
    {
        return remoteEndpoint.getCoreSession().isOutputOpen();
    }

    @Override
    public boolean isSecure()
    {
        return upgradeRequest.isSecure();
    }

    @Override
    public void disconnect()
    {
        coreSession.abort();
    }

    @Override
    public SocketAddress getLocalAddress()
    {
        return coreSession.getLocalAddress();
    }

    @Override
    public SocketAddress getRemoteAddress()
    {
        return coreSession.getRemoteAddress();
    }

    @Override
    public UpgradeRequest getUpgradeRequest()
    {
        return this.upgradeRequest;
    }

    @Override
    public UpgradeResponse getUpgradeResponse()
    {
        return this.upgradeResponse;
    }

    @Override
    public SuspendToken suspend()
    {
        frameHandler.suspend();
        return this;
    }

    @Override
    public void resume()
    {
        frameHandler.resume();
    }

    public FrameHandler.CoreSession getCoreSession()
    {
        return coreSession;
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException
    {
        Dumpable.dumpObjects(out, indent, this, upgradeRequest, coreSession, remoteEndpoint, frameHandler);
    }

    @Override
    public String dumpSelf()
    {
        return String.format("%s@%x[behavior=%s,idleTimeout=%dms]",
            this.getClass().getSimpleName(), hashCode(),
            getPolicy().getBehavior(),
            getIdleTimeout().toMillis());
    }

    @Override
    public String toString()
    {
        return String.format("WebSocketSession[%s,to=%s,%s,%s]", getBehavior(), getIdleTimeout(), coreSession, frameHandler);
    }
}
