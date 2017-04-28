package com.lib.common.eventbus.matchpolicy;

import com.lib.common.eventbus.EventType;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mrsimple
 */
public class StrictMatchPolicy implements MatchPolicy {

    @Override
    public List<EventType> findMatchEventTypes(EventType type, Object aEvent) {
        List<EventType> result = new LinkedList<EventType>();
        result.add(type);
        return result;
    }
}
