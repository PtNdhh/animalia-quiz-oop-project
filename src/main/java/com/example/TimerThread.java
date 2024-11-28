package com.example;

public class TimerThread extends Thread {
    private int timeLimit; // Dalam detik
    private boolean running = true;

    public TimerThread(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void stopTimer() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running && timeLimit > 0) {
                System.out.println("Time remaining: " + timeLimit + " seconds");
                Thread.sleep(1000);
                timeLimit--;
            }
            if (timeLimit == 0) {
                System.out.println("Time's up!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getTimeRemaining() {
        return timeLimit;
    }
}
