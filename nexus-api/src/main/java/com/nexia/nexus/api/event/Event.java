package com.nexia.nexus.api.event;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final List<Runnable> runAfterwards = new ArrayList<>();

    public void runAfterwards(Runnable runAfterwards) {
        this.runAfterwards.add(runAfterwards);
    }

    public List<Runnable> getAfterEventFunctions() {
        return this.runAfterwards;
    }
}
