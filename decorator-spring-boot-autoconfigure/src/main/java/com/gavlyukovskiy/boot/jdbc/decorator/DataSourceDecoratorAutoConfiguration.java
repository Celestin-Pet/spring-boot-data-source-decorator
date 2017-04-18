/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gavlyukovskiy.boot.jdbc.decorator;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.gavlyukovskiy.boot.jdbc.decorator.metadata.DecoratedDataSourcePoolMetadataProvider;
import com.p6spy.engine.spy.P6DataSource;
import com.p6spy.engine.spy.P6SpyLoadableOptions;
import com.p6spy.engine.spy.P6SpyOptions;
import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Proxy DataSource configurations imported by {@link DataSourceAutoConfiguration}.
 *
 * @author Arthur Gavlyukovskiy
 */
@Configuration
@EnableConfigurationProperties(DataSourceDecoratorProperties.class)
@ConditionalOnProperty(prefix = "spring.datasource.decorator", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@Import({ DataSourceDecoratorAutoConfiguration.FlexyPool.class,
          DataSourceDecoratorAutoConfiguration.DataSourceProxy.class,
          DataSourceDecoratorAutoConfiguration.P6Spy.class })
public class DataSourceDecoratorAutoConfiguration {

	@Bean
	@ConditionalOnBean(DataSourceDecorator.class)
	public DataSourceDecoratorBeanPostProcessor dataSourceDecoratorBeanPostProcessor(
		ApplicationContext applicationContext,
		DataSourceDecoratorProperties properties) {
		return new DataSourceDecoratorBeanPostProcessor(applicationContext, properties);
	}

	@Bean
	public DataSourcePoolMetadataProvider proxyDataSourcePoolMetadataProvider() {
		return new DecoratedDataSourcePoolMetadataProvider();
	}

	@ConditionalOnClass(FlexyPoolDataSource.class)
	static class FlexyPool {

		@Bean
		public DataSourceDecorator flexyPoolDataSourceDecorator() {
			return new DataSourceDecorator() {
				@Override
				public DataSource decorate(DataSource dataSource) {
					try {
						return new FlexyPoolDataSource<DataSource>(dataSource);
					}
					catch (Exception e) {
						return dataSource;
					}
				}
			};
		}

	}

	@ConditionalOnClass(ProxyDataSource.class)
	static class DataSourceProxy {

		@Bean
		public DataSourceDecorator proxyDataSourceDecorator() {
			return new DataSourceDecorator() {
				@Override
				public DataSource decorate(DataSource dataSource) {
					return ProxyDataSourceBuilder.create(dataSource)
						.logQueryBySlf4j()
						.logSlowQueryBySlf4j(10, TimeUnit.MINUTES)
						.build();
				}
			};
		}

	}

	@ConditionalOnClass(P6DataSource.class)
	static class P6Spy {

		@Bean
		public DataSourceDecorator p6SpyDataSourceDecorator() {
			return new DataSourceDecorator() {
				@Override
				public DataSource decorate(DataSource dataSource) {
					P6SpyLoadableOptions options = P6SpyOptions.getActiveInstance();
					options.setLogMessageFormat("com.p6spy.engine.spy.appender.MultiLineFormat");
					return new P6DataSource(dataSource);
				}
			};
		}

	}
}
