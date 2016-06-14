/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.mybatis;

import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.loader.ProxyFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class EsXMLConfigBuilder {

	protected final EsConfiguration configuration;
	protected final TypeAliasRegistry typeAliasRegistry;
	protected final TypeHandlerRegistry typeHandlerRegistry;

	private boolean parsed;
	private final XPathParser parser;
	private String environment;

	public EsXMLConfigBuilder(Reader reader) {
		this(reader, null, null);
	}

	public EsXMLConfigBuilder(Reader reader, String environment) {
		this(reader, environment, null);
	}

	public EsXMLConfigBuilder(Reader reader, String environment,
			Properties props) {
		this(
				new XPathParser(reader, true, props,
						new XMLMapperEntityResolver()), environment, props);
	}

	public EsXMLConfigBuilder(InputStream inputStream) {
		this(inputStream, null, null);
	}

	public EsXMLConfigBuilder(InputStream inputStream, String environment) {
		this(inputStream, environment, null);
	}

	public EsXMLConfigBuilder(InputStream inputStream, String environment,
			Properties props) {
		this(new XPathParser(inputStream, true, props,
				new XMLMapperEntityResolver()), environment, props);
	}

	public EsXMLConfigBuilder(XPathParser parser, String environment,
			Properties props) {
		this.configuration = new EsConfiguration();
		this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
		this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
		ErrorContext.instance().resource("SQL Mapper Configuration");
		this.configuration.setVariables(props);
		this.parsed = false;
		this.environment = environment;
		this.parser = parser;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	protected Boolean booleanValueOf(String value, Boolean defaultValue) {
		return value == null ? defaultValue : Boolean.valueOf(value);
	}

	protected Integer integerValueOf(String value, Integer defaultValue) {
		return value == null ? defaultValue : Integer.valueOf(value);
	}

	protected Set<String> stringSetValueOf(String value, String defaultValue) {
		value = (value == null ? defaultValue : value);
		return new HashSet<String>(Arrays.asList(value.split(",")));
	}

	protected JdbcType resolveJdbcType(String alias) {
		if (alias == null) {
			return null;
		}
		try {
			return JdbcType.valueOf(alias);
		} catch (final IllegalArgumentException e) {
			throw new BuilderException("Error resolving JdbcType. Cause: " + e,
					e);
		}
	}

	protected ResultSetType resolveResultSetType(String alias) {
		if (alias == null) {
			return null;
		}
		try {
			return ResultSetType.valueOf(alias);
		} catch (final IllegalArgumentException e) {
			throw new BuilderException("Error resolving ResultSetType. Cause: "
					+ e, e);
		}
	}

	protected ParameterMode resolveParameterMode(String alias) {
		if (alias == null) {
			return null;
		}
		try {
			return ParameterMode.valueOf(alias);
		} catch (final IllegalArgumentException e) {
			throw new BuilderException("Error resolving ParameterMode. Cause: "
					+ e, e);
		}
	}

	protected Object createInstance(String alias) {
		final Class<?> clazz = resolveClass(alias);
		if (clazz == null) {
			return null;
		}
		try {
			return resolveClass(alias).newInstance();
		} catch (final Exception e) {
			throw new BuilderException("Error creating instance. Cause: " + e,
					e);
		}
	}

	protected Class<?> resolveClass(String alias) {
		if (alias == null) {
			return null;
		}
		try {
			return resolveAlias(alias);
		} catch (final Exception e) {
			throw new BuilderException("Error resolving class. Cause: " + e, e);
		}
	}

	protected TypeHandler<?> resolveTypeHandler(Class<?> javaType,
			String typeHandlerAlias) {
		if (typeHandlerAlias == null) {
			return null;
		}
		final Class<?> type = resolveClass(typeHandlerAlias);
		if (type != null && !TypeHandler.class.isAssignableFrom(type)) {
			throw new BuilderException(
					"Type "
							+ type.getName()
							+ " is not a valid TypeHandler because it does not implement TypeHandler interface");
		}
		@SuppressWarnings("unchecked")
		final
		// already verified it is a TypeHandler
		Class<? extends TypeHandler<?>> typeHandlerType = (Class<? extends TypeHandler<?>>) type;
		return resolveTypeHandler(javaType, typeHandlerType);
	}

	protected TypeHandler<?> resolveTypeHandler(Class<?> javaType,
			Class<? extends TypeHandler<?>> typeHandlerType) {
		if (typeHandlerType == null) {
			return null;
		}
		// javaType ignored for injected handlers see issue #746 for full detail
		TypeHandler<?> handler = typeHandlerRegistry
				.getMappingTypeHandler(typeHandlerType);
		if (handler == null) {
			// not in registry, create a new one
			handler = typeHandlerRegistry
					.getInstance(javaType, typeHandlerType);
		}
		return handler;
	}

	protected Class<?> resolveAlias(String alias) {
		return typeAliasRegistry.resolveAlias(alias);
	}

	public Configuration parse() {
		if (parsed) {
			throw new BuilderException(
					"Each XMLConfigBuilder can only be used once.");
		}
		parsed = true;
		parseConfiguration(parser.evalNode("/configuration"));
		return configuration;
	}

	private void parseConfiguration(XNode root) {
		try {
			propertiesElement(root.evalNode("properties")); // issue #117 read
															// properties first
			typeAliasesElement(root.evalNode("typeAliases"));
			pluginElement(root.evalNode("plugins"));
			objectFactoryElement(root.evalNode("objectFactory"));
			objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
			settingsElement(root.evalNode("settings"));
			environmentsElement(root.evalNode("environments")); // read it after
																// objectFactory
																// and
																// objectWrapperFactory
																// issue #631
			databaseIdProviderElement(root.evalNode("databaseIdProvider"));
			typeHandlerElement(root.evalNode("typeHandlers"));
			mapperElement(root.evalNode("mappers"));
		} catch (final Exception e) {
			throw new BuilderException(
					"Error parsing SQL Mapper Configuration. Cause: " + e, e);
		}
	}

	private void typeAliasesElement(XNode parent) {
		if (parent != null) {
			for (final XNode child : parent.getChildren()) {
				if ("package".equals(child.getName())) {
					final String typeAliasPackage = child.getStringAttribute("name");
					configuration.getTypeAliasRegistry().registerAliases(
							typeAliasPackage);
				} else {
					final String alias = child.getStringAttribute("alias");
					final String type = child.getStringAttribute("type");
					try {
						final Class<?> clazz = Resources.classForName(type);
						if (alias == null) {
							typeAliasRegistry.registerAlias(clazz);
						} else {
							typeAliasRegistry.registerAlias(alias, clazz);
						}
					} catch (final ClassNotFoundException e) {
						throw new BuilderException(
								"Error registering typeAlias for '" + alias
										+ "'. Cause: " + e, e);
					}
				}
			}
		}
	}

	private void pluginElement(XNode parent) throws Exception {
		if (parent != null) {
			for (final XNode child : parent.getChildren()) {
				final String interceptor = child.getStringAttribute("interceptor");
				final Properties properties = child.getChildrenAsProperties();
				final Interceptor interceptorInstance = (Interceptor) resolveClass(
						interceptor).newInstance();
				interceptorInstance.setProperties(properties);
				configuration.addInterceptor(interceptorInstance);
			}
		}
	}

	private void objectFactoryElement(XNode context) throws Exception {
		if (context != null) {
			final String type = context.getStringAttribute("type");
			final Properties properties = context.getChildrenAsProperties();
			final ObjectFactory factory = (ObjectFactory) resolveClass(type)
					.newInstance();
			factory.setProperties(properties);
			configuration.setObjectFactory(factory);
		}
	}

	private void objectWrapperFactoryElement(XNode context) throws Exception {
		if (context != null) {
			final String type = context.getStringAttribute("type");
			final ObjectWrapperFactory factory = (ObjectWrapperFactory) resolveClass(
					type).newInstance();
			configuration.setObjectWrapperFactory(factory);
		}
	}

	private void propertiesElement(XNode context) throws Exception {
		if (context != null) {
			final Properties defaults = context.getChildrenAsProperties();
			final String resource = context.getStringAttribute("resource");
			final String url = context.getStringAttribute("url");
			if (resource != null && url != null) {
				throw new BuilderException(
						"The properties element cannot specify both a URL and a resource based property file reference.  Please specify one or the other.");
			}
			if (resource != null) {
				defaults.putAll(Resources.getResourceAsProperties(resource));
			} else if (url != null) {
				defaults.putAll(Resources.getUrlAsProperties(url));
			}
			final Properties vars = configuration.getVariables();
			if (vars != null) {
				defaults.putAll(vars);
			}
			parser.setVariables(defaults);
			configuration.setVariables(defaults);
		}
	}

	private void settingsElement(XNode context) throws Exception {
		if (context != null) {
			final Properties props = context.getChildrenAsProperties();
			// Check that all settings are known to the configuration class
			final MetaClass metaConfig = MetaClass.forClass(Configuration.class);
			for (final Object key : props.keySet()) {
				if (!metaConfig.hasSetter(String.valueOf(key))) {
					throw new BuilderException(
							"The setting "
									+ key
									+ " is not known.  Make sure you spelled it correctly (case sensitive).");
				}
			}
			configuration.setAutoMappingBehavior(AutoMappingBehavior
					.valueOf(props
							.getProperty("autoMappingBehavior", "PARTIAL")));
			configuration.setCacheEnabled(booleanValueOf(
					props.getProperty("cacheEnabled"), true));
			configuration.setProxyFactory((ProxyFactory) createInstance(props
					.getProperty("proxyFactory")));
			configuration.setLazyLoadingEnabled(booleanValueOf(
					props.getProperty("lazyLoadingEnabled"), false));
			configuration.setAggressiveLazyLoading(booleanValueOf(
					props.getProperty("aggressiveLazyLoading"), true));
			configuration.setMultipleResultSetsEnabled(booleanValueOf(
					props.getProperty("multipleResultSetsEnabled"), true));
			configuration.setUseColumnLabel(booleanValueOf(
					props.getProperty("useColumnLabel"), true));
			configuration.setUseGeneratedKeys(booleanValueOf(
					props.getProperty("useGeneratedKeys"), false));
			configuration.setDefaultExecutorType(ExecutorType.valueOf(props
					.getProperty("defaultExecutorType", "SIMPLE")));
			configuration.setDefaultStatementTimeout(integerValueOf(
					props.getProperty("defaultStatementTimeout"), null));
			configuration.setMapUnderscoreToCamelCase(booleanValueOf(
					props.getProperty("mapUnderscoreToCamelCase"), false));
			configuration.setSafeRowBoundsEnabled(booleanValueOf(
					props.getProperty("safeRowBoundsEnabled"), false));
			configuration.setLocalCacheScope(LocalCacheScope.valueOf(props
					.getProperty("localCacheScope", "SESSION")));
			configuration.setJdbcTypeForNull(JdbcType.valueOf(props
					.getProperty("jdbcTypeForNull", "OTHER")));
			configuration.setLazyLoadTriggerMethods(stringSetValueOf(
					props.getProperty("lazyLoadTriggerMethods"),
					"equals,clone,hashCode,toString"));
			configuration.setSafeResultHandlerEnabled(booleanValueOf(
					props.getProperty("safeResultHandlerEnabled"), true));
			configuration.setDefaultScriptingLanguage(resolveClass(props
					.getProperty("defaultScriptingLanguage")));
			configuration.setCallSettersOnNulls(booleanValueOf(
					props.getProperty("callSettersOnNulls"), false));
			configuration.setLogPrefix(props.getProperty("logPrefix"));
			configuration
					.setLogImpl(resolveClass(props.getProperty("logImpl")));
			configuration.setConfigurationFactory(resolveClass(props
					.getProperty("configurationFactory")));
		}
	}

	private void environmentsElement(XNode context) throws Exception {
		if (context != null) {
			if (environment == null) {
				environment = context.getStringAttribute("default");
			}
			for (final XNode child : context.getChildren()) {
				final String id = child.getStringAttribute("id");
				if (isSpecifiedEnvironment(id)) {
					final TransactionFactory txFactory = transactionManagerElement(child
							.evalNode("transactionManager"));
					final DataSourceFactory dsFactory = dataSourceElement(child
							.evalNode("dataSource"));
					final DataSource dataSource = dsFactory.getDataSource();
					final Environment.Builder environmentBuilder = new Environment.Builder(
							id).transactionFactory(txFactory).dataSource(
							dataSource);
					configuration.setEnvironment(environmentBuilder.build());
				}
			}
		}
	}

	private void databaseIdProviderElement(XNode context) throws Exception {
		DatabaseIdProvider databaseIdProvider = null;
		if (context != null) {
			String type = context.getStringAttribute("type");
			if ("VENDOR".equals(type))
			 {
				type = "DB_VENDOR"; // awful patch to keep backward
			}
									// compatibility
			final Properties properties = context.getChildrenAsProperties();
			databaseIdProvider = (DatabaseIdProvider) resolveClass(type)
					.newInstance();
			databaseIdProvider.setProperties(properties);
		}
		final Environment environment = configuration.getEnvironment();
		if (environment != null && databaseIdProvider != null) {
			final String databaseId = databaseIdProvider.getDatabaseId(environment
					.getDataSource());
			configuration.setDatabaseId(databaseId);
		}
	}

	private TransactionFactory transactionManagerElement(XNode context)
			throws Exception {
		if (context != null) {
			final String type = context.getStringAttribute("type");
			final Properties props = context.getChildrenAsProperties();
			final TransactionFactory factory = (TransactionFactory) resolveClass(type)
					.newInstance();
			factory.setProperties(props);
			return factory;
		}
		throw new BuilderException(
				"Environment declaration requires a TransactionFactory.");
	}

	private DataSourceFactory dataSourceElement(XNode context) throws Exception {
		if (context != null) {
			final String type = context.getStringAttribute("type");
			final Properties props = context.getChildrenAsProperties();
			final DataSourceFactory factory = (DataSourceFactory) resolveClass(type)
					.newInstance();
			factory.setProperties(props);
			return factory;
		}
		throw new BuilderException(
				"Environment declaration requires a DataSourceFactory.");
	}

	private void typeHandlerElement(XNode parent) throws Exception {
		if (parent != null) {
			for (final XNode child : parent.getChildren()) {
				if ("package".equals(child.getName())) {
					final String typeHandlerPackage = child
							.getStringAttribute("name");
					typeHandlerRegistry.register(typeHandlerPackage);
				} else {
					final String javaTypeName = child.getStringAttribute("javaType");
					final String jdbcTypeName = child.getStringAttribute("jdbcType");
					final String handlerTypeName = child
							.getStringAttribute("handler");
					final Class<?> javaTypeClass = resolveClass(javaTypeName);
					final JdbcType jdbcType = resolveJdbcType(jdbcTypeName);
					final Class<?> typeHandlerClass = resolveClass(handlerTypeName);
					if (javaTypeClass != null) {
						if (jdbcType == null) {
							typeHandlerRegistry.register(javaTypeClass,
									typeHandlerClass);
						} else {
							typeHandlerRegistry.register(javaTypeClass,
									jdbcType, typeHandlerClass);
						}
					} else {
						typeHandlerRegistry.register(typeHandlerClass);
					}
				}
			}
		}
	}

	private void mapperElement(XNode parent) throws Exception {
		if (parent != null) {
			for (final XNode child : parent.getChildren()) {
				if ("package".equals(child.getName())) {
					final String mapperPackage = child.getStringAttribute("name");
					configuration.addMappers(mapperPackage);
				} else {
					final String resource = child.getStringAttribute("resource");
					final String url = child.getStringAttribute("url");
					final String mapperClass = child.getStringAttribute("class");
					if (resource != null && url == null && mapperClass == null) {
						ErrorContext.instance().resource(resource);
						final InputStream inputStream = Resources
								.getResourceAsStream(resource);
						final XMLMapperBuilder mapperParser = new XMLMapperBuilder(
								inputStream, configuration, resource,
								configuration.getSqlFragments());
						mapperParser.parse();
					} else if (resource == null && url != null
							&& mapperClass == null) {
						ErrorContext.instance().resource(url);
						final InputStream inputStream = Resources.getUrlAsStream(url);
						final XMLMapperBuilder mapperParser = new XMLMapperBuilder(
								inputStream, configuration, url,
								configuration.getSqlFragments());
						mapperParser.parse();
					} else if (resource == null && url == null
							&& mapperClass != null) {
						final Class<?> mapperInterface = Resources
								.classForName(mapperClass);
						configuration.addMapper(mapperInterface);
					} else {
						throw new BuilderException(
								"A mapper element may only specify a url, resource or class, but not more than one.");
					}
				}
			}
		}
	}

	private boolean isSpecifiedEnvironment(String id) {
		if (environment == null) {
			throw new BuilderException("No environment specified.");
		} else if (id == null) {
			throw new BuilderException("Environment requires an id attribute.");
		} else if (environment.equals(id)) {
			return true;
		}
		return false;
	}
}
