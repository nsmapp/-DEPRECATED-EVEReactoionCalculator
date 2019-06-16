package by.nepravsky.sm.domain.executors;


import io.reactivex.Scheduler;

public interface PostExecutionThread {

    Scheduler getScheduler();
}
