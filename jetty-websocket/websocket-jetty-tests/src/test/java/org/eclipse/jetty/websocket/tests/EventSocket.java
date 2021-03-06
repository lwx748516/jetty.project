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

package org.eclipse.jetty.websocket.tests;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class EventSocket
{
    private static final Logger LOG = Log.getLogger(EventSocket.class);

    public Session session;
    private String behavior;

    public BlockingQueue<String> messageQueue = new BlockingArrayQueue<>();
    public BlockingQueue<ByteBuffer> binaryMessageQueue = new BlockingArrayQueue<>();
    public volatile int statusCode = StatusCode.UNDEFINED;
    public volatile String reason;
    public volatile Throwable error = null;

    public CountDownLatch openLatch = new CountDownLatch(1);
    public CountDownLatch errorLatch = new CountDownLatch(1);
    public CountDownLatch closeLatch = new CountDownLatch(1);

    @OnWebSocketConnect
    public void onOpen(Session session)
    {
        this.session = session;
        behavior = session.getPolicy().getBehavior().name();
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onOpen(): {}", toString(), session);
        openLatch.countDown();
    }

    @OnWebSocketMessage
    public void onMessage(String message) throws IOException
    {
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onMessage(): {}", toString(), message);
        messageQueue.offer(message);
    }

    @OnWebSocketMessage
    public void onMessage(byte[] buf, int offset, int len)
    {
        ByteBuffer message = ByteBuffer.wrap(buf, offset, len);
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onMessage(): {}", toString(), message);
        binaryMessageQueue.offer(message);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onClose(): {}:{}", toString(), statusCode, reason);
        this.statusCode = statusCode;
        this.reason = reason;
        closeLatch.countDown();
    }

    @OnWebSocketError
    public void onError(Throwable cause)
    {
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onError(): {}", toString(), cause);
        error = cause;
        errorLatch.countDown();
    }

    @Override
    public String toString()
    {
        return String.format("[%s@%s]", behavior, Integer.toHexString(hashCode()));
    }
}
