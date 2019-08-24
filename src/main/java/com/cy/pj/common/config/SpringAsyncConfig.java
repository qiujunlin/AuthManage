package com.cy.pj.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
@Configuration
public class SpringAsyncConfig implements AsyncConfigurer{
	private int corePoolSize=10;
	private int maximumPoolSize =20;
	private int keepAliveTime =20;
	private LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
	ThreadFactory threadFactory = new ThreadFactory() {

		@Override
		public Thread newThread(Runnable r) {
			AtomicLong atomicLong = new AtomicLong(100);

			return new Thread(r,"async-thread"+atomicLong.getAndIncrement());
		}
	};
	@Bean("newThreadPoolExecutor")
	public ThreadPoolExecutor newThreadPoolExecutor() {
		ThreadPoolExecutor threadPoolExecutor =
				new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, threadFactory);
		return threadPoolExecutor;
	}
	@Override
	public Executor getAsyncExecutor() {
		System.out.println("==getAsyncExeutor");
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(corePoolSize);
		pool.setMaxPoolSize(maximumPoolSize);
		pool.setKeepAliveSeconds(keepAliveTime);
		pool.setQueueCapacity(20);
		pool.initialize();

		return pool;
	}
}
