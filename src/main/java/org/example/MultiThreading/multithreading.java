package org.example.MultiThreading;

import lombok.Synchronized;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Thread Lifecycle
//NEW	Thread is created but not started (new Thread())
//RUNNABLE	Thread is ready to run but may not be running yet
//RUNNING	Thread scheduler has picked the thread to run
//BLOCKED	Thread is waiting to enter a synchronized block
//WAITING	Thread is waiting indefinitely for another thread to notify
//TIMED_WAITING	Thread is waiting for a specified amount of time (sleep, join, wait(timeout))
//TERMINATED	Thread has completed execution or has been stopped

class LifecycleThread extends Thread {
    public void run() {
        System.out.println("Running...");
    }
    public static void main(String[] args) throws InterruptedException {
        LifecycleThread t = new LifecycleThread();
        System.out.println("State before start: " + t.getState());
        t.start();
        Thread.sleep(100);
        System.out.println("State after run: " + t.getState());
    }
}

//Inter-Thread Communication
class Shared {
    synchronized void printMsg() {
            try {
                System.out.println("Waiting...");
                wait();
                System.out.println("Resumed!");
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }

    synchronized void trigger() {
        notify();
    }
}

class WaitNotifyExample {
    public static void main(String[] args) {
        Shared s = new Shared();
        Runnable runnable = () -> s.printMsg();
        new Thread(runnable).start();
        try { Thread.sleep(1000); } catch (Exception e) {}
        new Thread(() -> s.trigger()).start();
    }
}


//Daemon Thread and Thread Priority
class DaemonPriorityDemo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("Daemon thread running...");
                try { Thread.sleep(500); } catch (Exception e) {}
            }
        });
        t.setDaemon(true);
        t.setPriority(Thread.MIN_PRIORITY);
        System.out.println(t.getPriority());
        t.start();
        try { Thread.sleep(2000); } catch (Exception e) {}
        System.out.println("Main thread ends.");
    }
}

//Callable Interface

class CallableExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> task = () -> 123;
        Future<Integer> future = executor.submit(task);
        System.out.println("Result: " + future.get());
        executor.shutdown();
    }
}


//Runnable vs Callable
class RunnableTask implements Runnable {
    public void run() {
        System.out.println("Runnable: no return, no exception throw");
    }
}
class CallableTask implements Callable<String> {
    public String call() {
        return "Callable: returns result and can throw exception";
    }
}

//t1;
//queue [1,2,3]

class RunnableCallableDiff {
    public static void main(String[] args) throws Exception {
        new Thread(new RunnableTask()).start();
        ExecutorService es = Executors.newSingleThreadExecutor();
        System.out.println(es.submit(new CallableTask()).get());
        es.shutdown();
    }
}


//Locks and Condition
class LockExample {
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Waiting...");
                condition.await();
                System.out.println("Woken up!");
            } catch (InterruptedException e) {
            } finally {
                lock.unlock();
            }
        }).start();
        try { Thread.sleep(1000); } catch (Exception e) {}
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}


class Increment implements Runnable{
    static int counter=0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter++;
        }
    }
}
//Lock-Free Concurrency
class AtomicCounter {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) counter.incrementAndGet();
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("Count: " + counter.get());

       /* Increment increment = new Increment();
        Thread t1 = new Thread(increment);
        t1.start();
        Thread t2 = new Thread(increment);
        t2.start();
        t1.join();
        t2.join();
        System.out.println(increment.getCounter());*/
    }
}

//1.
//SingleThreadExecutor
//How it works: One thread is reused for all tasks. Tasks are executed sequentially.
//Use case: Logging service, task queues where order matters.
//ExecutorService executor = Executors.newSingleThreadExecutor();

//2. FixedThreadPool
//How it works: A fixed number of threads execute tasks. If all are busy, tasks wait in a queue.
//Use case: Handling a known number of parallel tasks, like DB queries or I/O ops.
//ExecutorService executor = Executors.newFixedThreadPool(4);


//3. CachedThreadPool
//How it works: Creates new threads as needed, but reuses previously constructed ones when available.
//Use case: Lightweight short tasks, unpredictable load (e.g., HTTP request handlers).
//ExecutorService executor = Executors.newCachedThreadPool();

//4. ScheduledThreadPool
//How it works: Like FixedThreadPool, but adds scheduling capabilities (delayed/periodic execution).
//Use case: Background jobs, cron replacement, retry queues.
//ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
//scheduler.schedule(() -> System.out.println("Delayed"), 5, TimeUnit.SECONDS);

//Thread Pools
class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            final int task = i;
            service.execute(() -> {
                System.out.println("Running task " + task);
            });
        }
        service.shutdown();
    }
}

//ThreadPoolExecutor
class CustomThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 4, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
        for (int i = 0; i < 5; i++) {
            final int task = i;
            executor.execute(() -> System.out.println("Executing task " + task));
        }
        executor.shutdown();
    }
}

//Volatile Keyword
class VolatileDemo {
    volatile static boolean running = true;
    public static void main(String[] args) {
        new Thread(() -> {
            while (running) {}
            System.out.println("Stopped!");
        }).start();
        try { Thread.sleep(1000); } catch (Exception e) {}
        running = false;
    }
}

//Interrupting Threads
class InterruptExample {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted!");
            }
        });
        t.start();
        t.interrupt();
    }
}

//Producer-Consumer using wait/notify
class SharedBuffer {
    private int data;
    private boolean empty = true;

    public synchronized void produce(int value) throws InterruptedException {
        while (!empty) {
            wait();
        }
        data = value;
        empty = false;
        System.out.println("Produced: " + value);
        notify();
    }

    public synchronized int consume() throws InterruptedException {
        while (empty) {
            wait();
        }
        empty = true;
        System.out.println("Consumed: " + data);
        notify();
        return data;
    }

    public static void main(String[] args) {
        SharedBuffer sb = new SharedBuffer();
        Thread t1 = new Thread(()->{
           for(int i=1; i<=5; i++){
               try {
                   sb.produce(i);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });

        Thread t2 = new Thread(()->{
            for(int i=1; i<=5; i++){
                try {
                    sb.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t2.start();
        t1.start();

    }
}


//Producer-Consumer using BlockingQueue
class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    int val = queue.take();
                    System.out.println("Consumed: " + val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();
        consumer.start();
    }
}

public class multithreading {
}
