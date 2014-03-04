/**
 * Copyright 2014 Coinport Inc. All Rights Reserved.
 * Author: c@coinport.com (Chao Ma)
 */

package com.coinport.f1.output_event;

import static org.fusesource.leveldbjni.JniDBFactory.*;

import java.util.concurrent.atomic.AtomicBoolean;

import com.esotericsoftware.kryo.*;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import org.iq80.leveldb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coinport.f1.*;

public final class OutputEventAgent implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(OutputEventAgent.class);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private volatile boolean alert = false;

    DB db = null;
    Kryo kryo = null;

    public OutputEventAgent(final DB db) {
        this.db = db;

        kryo = new Kryo();
        FieldSerializer<?> serializer = new FieldSerializer<OutputEventImpl>(kryo, OutputEventImpl.class);
        kryo.register(OutputEventImpl.class, serializer);
    }

    public void halt() {
        alert = true;
        running.set(false);
    }

    boolean isRunning() {
        return running.get();
    }

    @Override
    public void run() {
        if (!running.compareAndSet(false, true)) {
            throw new IllegalStateException("Thread is already running");
        }
        try {
            while (true) {
                if (alert)
                    break;
                else {
                    Thread.yield();
                }
            }
        } finally {
            running.set(false);
        }
    }
}
