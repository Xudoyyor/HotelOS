package com.hotelos.dashboardservice.state;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Mehmonxonaning real vaqtdagi yig'ma holati (in-memory projeksiya).
 * Thread-safe tuzilmalar: ConcurrentHashMap (xona bo'yicha oxirgi holat) va
 * ConcurrentLinkedDeque (oxirgi faoliyat tasmasi).
 */
@Component
public class DashboardState {

    private static final int MAX_ACTIVITY = 30;

    private final Map<String, Map<String, Object>> roomsByNumber = new ConcurrentHashMap<>();
    private final Deque<Map<String, Object>> recentActivity = new ConcurrentLinkedDeque<>();

    /** Kelgan hodisani holatga qo'llaydi. */
    public void apply(Map<String, Object> event) {
        if (event == null) {
            return;
        }
        Object roomNumber = event.get("roomNumber");
        if (roomNumber != null) {
            roomsByNumber.put(String.valueOf(roomNumber), new HashMap<>(event));
        }
        recentActivity.offerFirst(new HashMap<>(event));
        while (recentActivity.size() > MAX_ACTIVITY) {
            recentActivity.pollLast();
        }
    }

    /** Joriy holatning to'liq tasviri (WebSocket va REST uchun). */
    public Map<String, Object> snapshot() {
        Map<String, Object> snapshot = new HashMap<>();
        snapshot.put("rooms", new ArrayList<>(roomsByNumber.values()));
        snapshot.put("activity", new ArrayList<>(recentActivity));
        snapshot.put("totalRooms", roomsByNumber.size());
        snapshot.put("updatedAt", new Date().toString());
        return snapshot;
    }
}