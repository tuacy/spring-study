package com.tuacy.uid.config;

import com.tuacy.uid.impl.UidGenerator;
import com.tuacy.uid.impl.CachedUidGenerator;
import com.tuacy.uid.worker.DisposableWorkerIdAssigner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name: IdGeneratorConfiguration
 * @author: tuacy.
 * @date: 2020/1/10.
 * @version: 1.0
 * @Description:
 */
@Configuration
public class IdGeneratorConfiguration {

    @Bean(name = "disposableWorkerIdAssigner")
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Bean(name = "cacheUidGenerator")
    public UidGenerator cacheUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        return cachedUidGenerator;
    }

}
