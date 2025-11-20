package com.yww.code.hot.others;
import java.util.*;

/**
 * 简单内存分配器实现
 * 使用首次适应算法（First Fit）进行内存分配
 */
public class MemoryAllocator {
    
    // 内存块类
    static class MemoryBlock {
        int start;      // 起始地址
        int size;       // 块大小
        boolean isFree; // 是否空闲
        
        public MemoryBlock(int start, int size, boolean isFree) {
            this.start = start;
            this.size = size;
            this.isFree = isFree;
        }
        
        @Override
        public String toString() {
            return String.format("[%d-%d, %s, %dB]", 
                start, start + size - 1, 
                isFree ? "FREE" : "USED", 
                size);
        }
    }
    
    private final int totalSize;                    // 总内存大小
    private final List<MemoryBlock> memoryBlocks;   // 内存块列表
    private final Map<Integer, MemoryBlock> allocatedBlocks; // 已分配块映射 (ID -> Block)
    private int nextId = 1;                         // 下一个分配ID
    
    /**
     * 构造函数
     * @param totalSize 总内存大小（字节）
     */
    public MemoryAllocator(int totalSize) {
        this.totalSize = totalSize;
        this.memoryBlocks = new ArrayList<>();
        this.allocatedBlocks = new HashMap<>();
        
        // 初始化：整个内存都是空闲的
        memoryBlocks.add(new MemoryBlock(0, totalSize, true));
    }
    
    /**
     * 分配内存
     * @param size 需要分配的大小（字节）
     * @return 分配的内存ID，失败返回-1
     */
    public int allocate(int size) {
        if (size <= 0) {
            System.out.println("❌ 分配失败: 大小必须大于0");
            return -1;
        }
        
        // 首次适应算法：找到第一个足够大的空闲块
        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            
            if (block.isFree && block.size >= size) {
                // 找到合适的空闲块
                int allocId = nextId++;
                
                if (block.size == size) {
                    // 完全匹配，直接标记为已使用
                    block.isFree = false;
                    allocatedBlocks.put(allocId, block);
                } else {
                    // 分割块：前部分分配，后部分保持空闲
                    MemoryBlock allocatedBlock = new MemoryBlock(block.start, size, false);
                    MemoryBlock remainingBlock = new MemoryBlock(
                        block.start + size, 
                        block.size - size, 
                        true
                    );
                    
                    memoryBlocks.set(i, allocatedBlock);
                    memoryBlocks.add(i + 1, remainingBlock);
                    
                    allocatedBlocks.put(allocId, allocatedBlock);
                }
                
                System.out.printf("✓ 分配成功: ID=%d, 大小=%dB, 地址=[%d-%d]\n", 
                    allocId, size, block.start, block.start + size - 1);
                return allocId;
            }
        }
        
        System.out.printf("❌ 分配失败: 没有足够的连续空闲内存 (需要 %dB)\n", size);
        return -1;
    }
    
    /**
     * 释放内存
     * @param allocId 分配时返回的ID
     * @return 是否成功释放
     */
    public boolean free(int allocId) {
        MemoryBlock block = allocatedBlocks.get(allocId);
        
        if (block == null) {
            System.out.printf("❌ 释放失败: ID=%d 不存在\n", allocId);
            return false;
        }
        
        // 标记为空闲
        block.isFree = true;
        allocatedBlocks.remove(allocId);
        
        System.out.printf("✓ 释放成功: ID=%d, 大小=%dB, 地址=[%d-%d]\n", 
            allocId, block.size, block.start, block.start + block.size - 1);
        
        // 合并相邻的空闲块
        mergeAdjacentFreeBlocks();
        
        return true;
    }
    
    /**
     * 合并相邻的空闲块（碎片整理）
     */
    private void mergeAdjacentFreeBlocks() {
        for (int i = 0; i < memoryBlocks.size() - 1; i++) {
            MemoryBlock current = memoryBlocks.get(i);
            MemoryBlock next = memoryBlocks.get(i + 1);
            
            // 如果当前块和下一块都是空闲的，合并它们
            if (current.isFree && next.isFree) {
                current.size += next.size;
                memoryBlocks.remove(i + 1);
                i--; // 重新检查当前位置
            }
        }
    }
    
    /**
     * 获取内存使用统计
     */
    public void printStats() {
        int usedMemory = 0;
        int freeMemory = 0;
        int freeBlockCount = 0;
        int usedBlockCount = 0;
        
        for (MemoryBlock block : memoryBlocks) {
            if (block.isFree) {
                freeMemory += block.size;
                freeBlockCount++;
            } else {
                usedMemory += block.size;
                usedBlockCount++;
            }
        }
        
        System.out.println("\n========== 内存统计 ==========");
        System.out.printf("总内存: %dB\n", totalSize);
        System.out.printf("已使用: %dB (%.1f%%)\n", usedMemory, usedMemory * 100.0 / totalSize);
        System.out.printf("空闲: %dB (%.1f%%)\n", freeMemory, freeMemory * 100.0 / totalSize);
        System.out.printf("已使用块数: %d\n", usedBlockCount);
        System.out.printf("空闲块数: %d\n", freeBlockCount);
        System.out.println("==============================\n");
    }
    
    /**
     * 打印内存布局
     */
    public void printLayout() {
        System.out.println("\n========== 内存布局 ==========");
        for (MemoryBlock block : memoryBlocks) {
            System.out.println(block);
        }
        System.out.println("==============================\n");
    }
    
    /**
     * 测试示例
     */
    public static void main(String[] args) {
        System.out.println("===== 内存分配器测试 =====\n");
        
        // 创建1024字节的内存池
        MemoryAllocator allocator = new MemoryAllocator(1024);
        
        System.out.println("1. 初始状态:");
        allocator.printLayout();
        
        // 分配内存
        System.out.println("2. 分配内存:");
        int id1 = allocator.allocate(100);  // 分配100字节
        int id2 = allocator.allocate(200);  // 分配200字节
        int id3 = allocator.allocate(150);  // 分配150字节
        allocator.printLayout();
        allocator.printStats();
        
        // 释放内存
        System.out.println("3. 释放 ID=" + id2 + ":");
        allocator.free(id2);
        allocator.printLayout();
        allocator.printStats();
        
        // 再次分配
        System.out.println("4. 再次分配:");
        int id4 = allocator.allocate(50);   // 分配50字节（会使用释放的空间）
        int id5 = allocator.allocate(100);  // 分配100字节
        allocator.printLayout();
        allocator.printStats();
        
        // 释放所有
        System.out.println("5. 释放所有内存:");
        allocator.free(id1);
        allocator.free(id3);
        allocator.free(id4);
        allocator.free(id5);
        allocator.printLayout();
        allocator.printStats();
        
        // 测试碎片整理
        System.out.println("6. 测试内存碎片:");
        int[] ids = new int[5];
        for (int i = 0; i < 5; i++) {
            ids[i] = allocator.allocate(100);
        }
        allocator.printLayout();
        
        // 释放奇数位置的块
        allocator.free(ids[1]);
        allocator.free(ids[3]);
        System.out.println("释放部分块后:");
        allocator.printLayout();
        
        // 尝试分配大块（会失败，因为有碎片）
        System.out.println("尝试分配250字节:");
        allocator.allocate(250);
        
        // 释放所有后再分配（碎片已合并）
        allocator.free(ids[0]);
        allocator.free(ids[2]);
        allocator.free(ids[4]);
        System.out.println("释放所有后:");
        allocator.printLayout();
        
        System.out.println("再次尝试分配250字节:");
        allocator.allocate(250);
        allocator.printLayout();
        
        System.out.println("\n===== 测试完成 =====");
    }
}
