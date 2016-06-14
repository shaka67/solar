package com.ouer.solar.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ProfileSpringConfig {

    private final Logger       LOG                     = LoggerFactory.getLogger(ProfileSpringConfig.class);

    /** 线上环境的profile名称 */
    public static final String PROFILE_NAME_PROD       = "prod";
    public static final String PROFILE_NAME_PREPROD    = "preprod";
    public static final String PROFILE_NAME_TEST       = "test";
    public static final String PROFILE_NAME_DEV        = "dev";
    public static final String PROFILE_NAME_UCLOUDPROD = "ucloudprod";
    public static final String PROFILE_NAME_BETA       = "beta";
    public static final String PROFILE_NAME_GAMMA       = "gamma";

    public static final String PROFILE_NAME_SINGLETON = "singleton";

    /**
     * dev profile
     */
    @Profile(PROFILE_NAME_DEV)
    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurerDev() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("env/config-dev.properties"));
        LOG.warn("env/config-dev.properties loaded");
        return ppc;
    }

    @Profile(PROFILE_NAME_TEST)
    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurerTest() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("env/config-test.properties"));
        LOG.warn("env/config-test.properties loaded");
        return ppc;
    }

    @Profile(PROFILE_NAME_PREPROD)
    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurerPreProd() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("env/config-preprod.properties"));
        LOG.warn("env/config-preprod.properties loaded");
        return ppc;
    }

    @Profile(PROFILE_NAME_PROD)
    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurerProd() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("env/config-prod.properties"));
        LOG.warn("env/config-prod.properties loaded");
        return ppc;
    }

    @Profile(PROFILE_NAME_UCLOUDPROD)
    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurerUCloudProd() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("env/config-ucloudProd.properties"));
        LOG.warn("env/config-ucloudProd.properties loaded");
        return ppc;
    }

    @Profile(PROFILE_NAME_BETA)
    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurerBeta() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("env/config-beta.properties"));
        LOG.warn("env/config-beta.properties loaded");
        return ppc;
    }

    @Profile(PROFILE_NAME_GAMMA)
    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurerGamma() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("env/config-gamma.properties"));
        LOG.warn("env/config-gamma.properties loaded");
        return ppc;
    }

    @Profile(PROFILE_NAME_SINGLETON)
    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurerSingle() {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("env/config-singleton.properties"));
        LOG.warn("env/config-singleton.properties loaded");
        return ppc;
    }
}