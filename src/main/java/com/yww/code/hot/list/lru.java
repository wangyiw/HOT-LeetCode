package com.yww.code.hot.list;

import java.util.HashMap;
import java.util.Map;
public class lru {
    /**
     * 实现LRU缓存
     * LRU缓存算法是一种缓存淘汰策略，
     * 当缓存满时，淘汰最近最少使用的数据
     * 输入
        ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
        [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
     * 输出
        [null, null, null, 1, null, -1, null, -1, 3, 4]
     */
    class DLinkedNode{
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode(){
            
        }
        public DLinkedNode(int key,int value){
            this.key = key;
            this.value = value;
        }
    }
    private Map<Integer,DLinkedNode> cache = new HashMap<>();
    private int size;
    private int capacity;
    private DLinkedNode head;
    private DLinkedNode tail;
    
    public lru(int capacity){
        this.capacity = capacity;
        this.size = 0;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }
    public int get(int key){
        DLinkedNode node = cache.get(key);
        if(node == null){
            return -1;
        }
        moveToHead(node);
        return node.value;
    }
    public void put(int key , int value){
        DLinkedNode node = cache.get(key);
        if(node==null){
            DLinkedNode newNode = new DLinkedNode(key,value);
            cache.put(key,newNode);
            addToHead(newNode);
            size++;
            if(size>capacity){
                DLinkedNode tail = removeTail();
                cache.remove(tail.key);
                size--;
            }
        }else{
            node.value = value;
            moveToHead(node);
        }
    }
    public DLinkedNode removeTail(){
        DLinkedNode node = tail.prev;
        remove(node);
        return node;
    }
    public void moveToHead(DLinkedNode node){
        remove(node);
        addToHead(node);
    }
    public void addToHead(DLinkedNode node){
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    public void remove(DLinkedNode node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    public static void main(String[] args) {
        lru lru = new lru(2);
        lru.put(1,1);
        lru.put(2,2);
        System.out.println(lru.get(1));
        lru.put(3,3);
        System.out.println(lru.get(2));
        lru.put(4,4);
        System.out.println(lru.get(1));
        System.out.println(lru.get(3));
        System.out.println(lru.get(4));
    }
}
