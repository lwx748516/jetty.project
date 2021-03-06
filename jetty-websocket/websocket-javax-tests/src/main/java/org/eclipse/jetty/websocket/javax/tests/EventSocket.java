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

package org.eclipse.jetty.websocket.javax.tests;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

@ServerEndpoint("/")
@ClientEndpoint()
public class EventSocket
{
    private static final Logger LOG = Log.getLogger(EventSocket.class);

    public Session session;
    public EndpointConfig endpointConfig;

    public BlockingQueue<String> messageQueue = new BlockingArrayQueue<>();
    public volatile Throwable error = null;
    public volatile CloseReason closeReason = null;

    public CountDownLatch openLatch = new CountDownLatch(1);
    public CountDownLatch closeLatch = new CountDownLatch(1);

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig)
    {
        this.session = session;
        this.endpointConfig = endpointConfig;
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onOpen(): {}", toString(), session);
        openLatch.countDown();
    }

    @OnMessage
    public void onMessage(String message) throws IOException
    {
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onMessage(): {}", toString(), message);
        messageQueue.offer(message);
    }

    @OnClose
    public void onClose(CloseReason reason)
    {
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onClose(): {}", toString(), reason);

        closeReason = reason;
        closeLatch.countDown();
    }

    @OnError
    public void onError(Throwable cause)
    {
        if (LOG.isDebugEnabled())
            LOG.debug("{}  onError(): {}", toString(), cause);
        error = cause;
    }
}
