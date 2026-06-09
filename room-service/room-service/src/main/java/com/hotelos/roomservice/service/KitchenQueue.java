package com.hotelos.roomservice.service;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Oshxona buyurtmalar navbati - FIFO (First In, First Out) ma'lumotlar tuzilmasi.
 * LinkedList Queue interfeysini amalga oshiradi; kelgan tartibda qayta ishlanadi.
 */
@Component
public class KitchenQueue {

    private final Queue<Long> queue = new LinkedList<>();

    public synchronized void enqueue(Long orderId) {
        queue.offer(orderId);
    }

    public synchronized Long pollNext() {
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }
}