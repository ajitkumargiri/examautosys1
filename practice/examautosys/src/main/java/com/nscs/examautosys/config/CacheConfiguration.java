package com.nscs.examautosys.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.nscs.examautosys.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.nscs.examautosys.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.nscs.examautosys.domain.User.class.getName());
            createCache(cm, com.nscs.examautosys.domain.Authority.class.getName());
            createCache(cm, com.nscs.examautosys.domain.User.class.getName() + ".authorities");
            createCache(cm, com.nscs.examautosys.domain.University.class.getName());
            createCache(cm, com.nscs.examautosys.domain.University.class.getName() + ".colleges");
            createCache(cm, com.nscs.examautosys.domain.College.class.getName());
            createCache(cm, com.nscs.examautosys.domain.College.class.getName() + ".courses");
            createCache(cm, com.nscs.examautosys.domain.Course.class.getName());
            createCache(cm, com.nscs.examautosys.domain.Course.class.getName() + ".branches");
            createCache(cm, com.nscs.examautosys.domain.Branch.class.getName());
            createCache(cm, com.nscs.examautosys.domain.Branch.class.getName() + ".academicBatches");
            createCache(cm, com.nscs.examautosys.domain.AcademicBatch.class.getName());
            createCache(cm, com.nscs.examautosys.domain.AcademicBatch.class.getName() + ".sessions");
            createCache(cm, com.nscs.examautosys.domain.AcademicBatch.class.getName() + ".students");
            createCache(cm, com.nscs.examautosys.domain.Session.class.getName());
            createCache(cm, com.nscs.examautosys.domain.Session.class.getName() + ".exams");
            createCache(cm, com.nscs.examautosys.domain.Session.class.getName() + ".subjectPapers");
            createCache(cm, com.nscs.examautosys.domain.Student.class.getName());
            createCache(cm, com.nscs.examautosys.domain.Student.class.getName() + ".examApplicationForms");
            createCache(cm, com.nscs.examautosys.domain.Address.class.getName());
            createCache(cm, com.nscs.examautosys.domain.Exam.class.getName());
            createCache(cm, com.nscs.examautosys.domain.Exam.class.getName() + ".applicationForms");
            createCache(cm, com.nscs.examautosys.domain.Exam.class.getName() + ".examCenters");
            createCache(cm, com.nscs.examautosys.domain.ExamApplicationForm.class.getName());
            createCache(cm, com.nscs.examautosys.domain.SubjectPaper.class.getName());
            createCache(cm, com.nscs.examautosys.domain.ExamCenter.class.getName());
            createCache(cm, com.nscs.examautosys.domain.ExamCenter.class.getName() + ".examApplicationForms");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
