package com.lib.common.eventbus.matchpolicy;

import com.lib.common.eventbus.EventType;

import java.util.List;

/**
 * @author mrsimple
 */
public interface MatchPolicy {
    List<EventType> findMatchEventTypes(EventType type, Object aEvent);
}
