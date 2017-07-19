package com.tangaoyu.gen.config;

import com.lorne.tx.bean.TxTransactionLocal;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;

import java.util.concurrent.Callable;


public class CustomHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    public CustomHystrixConcurrencyStrategy() {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return new HystrixContextWrapper<T>(callable);
    }

    public static class HystrixContextWrapper<V> implements Callable<V> {

        private TxTransactionLocal txTransactionLocal;
        private Callable<V> delegate;

        public HystrixContextWrapper(Callable<V> delegate) {
            this.txTransactionLocal = TxTransactionLocal.current();
            this.delegate = delegate;
        }

        @Override
        public V call() throws Exception {
            TxTransactionLocal existingState = TxTransactionLocal.current();
            try {
                TxTransactionLocal.setCurrent(this.txTransactionLocal);
                return this.delegate.call();
            } finally {
                TxTransactionLocal.setCurrent(existingState);
            }
        }
    }
}