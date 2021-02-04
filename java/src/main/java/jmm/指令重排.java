package jmm;

public class 指令重排 {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

//指令重排可能会持续循环下去，因为读线程可能永远都看不到ready的值。甚至指令重排可能会输出0，因为读线程可能看到了写入ready的值，
//但却没有看到之后写入number的值，这种现象被称为“重排序”。只要在某个线程中无法检测到重排序情况（即使在其他线程中可以明显地看到该线程中的重排序），
//那么就无法确保线程中的操作将按照程序中指定的顺序来执行。当主线程首先写入number，然后在没有同步的情况下写入ready，
//那么读线程看到的顺序可能与写入的顺序完全相反。在没有同步的情况下，编译器、处理器以及运行时等都可能对操作的执行顺序进
//行一些意想不到的调整。在缺乏足够同步的多线程程序中，要想对内存操作的执行春旭进行判断，无法得到正确的结论。这个看上去
//像是一个失败的设计，但却能使JVM充分地利用现代多核处理器的强大性能。例如，在缺少同步的情况下，

// Java内存模型允许编译器对操作顺序进行重排序，并将数值缓存在寄存器中。此外，它还允许CPU对操作顺序进行重排序，并将数值缓存在处理器特定的缓存中。
    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
