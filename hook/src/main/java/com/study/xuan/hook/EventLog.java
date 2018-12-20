package com.study.xuan.hook;

/**
 * Author : xuan.
 * Date : 2018/12/20.
 * Description :the description of this file
 */
public class EventLog {
    public int eventId;
    public String other;

    public EventLog(int eventId, String other) {
        this.eventId = eventId;
        this.other = other;
    }

    public EventLog(int eventId) {
        this.eventId = eventId;
    }
}
