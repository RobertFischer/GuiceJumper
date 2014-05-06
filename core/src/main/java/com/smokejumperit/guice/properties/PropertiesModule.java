package com.smokejumperit.guice.properties;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * This module makes system and environment variables available using the {@link Named} annotation
 * on any primitive type, or close relatives. For example: <br>
 *
 * <pre>
 *   @Inject @Named("user.home") String userHome // Using raw variable
 *   @Inject @Named("system.user.home") String userHome // Using alias for system variables
 *   @Inject @Named("env.HOME") String userHome // Using the HOME environment variable
 *   @Inject @Named("file.separator") char fileSeperator // Single-character values exposed as char
 *   @Inject @Named("env.NUMBER_OF_PROCESSORS") int processorCount // int-parseable values exposed as int
 *   @Inject @Named("user.home") File userHome // Value exposed as file
 *   @Inject @Named("file.encoding") Charset fileEncoding // If in Charset.availableCharsets(), exposed as Charset
 * </pre>
 * <p>
 * System variables are available with the {@code system.} prefix; environment variables are
 * available with the {@code env.} prefix. Unprefixed keys (eg: {@code user.home}) are set by
 * finding the first element in this list:
 * <ol>
 * <li>An override pair. (see below for more on overrides)</li>
 * <li>A system property.</li>
 * <li>An environment variable.</li>
 * <li>A {@code system.} alias for a system property.</li>
 * <li>An {@code env.} alias for a environment variable.</li>
 * <li>A default pair.</li>
 * </ol>
 * <p>
 * Beyond the system and environment variables, the user may also specify <i>override</i> and
 * <i>default</i> key/value pairs. Any <i>default</i> key-value pairs are applied at the end only if
 * there does not already exist a system property or environment variable. Any <i>override</i>
 * key-value pairs are applied at the end whether or not there exists a key.
 */
public class PropertiesModule extends AbstractModule {

	/**
	 * Defaults for raw keys that are not otherwise specified; never {@code null}.
	 */
	protected final Properties defaults = new Properties();

	/**
	 * Pairs that will override values whether or not keys exist; never {@code null}.
	 */
	protected final Properties overrides = new Properties();

	/**
	 * Constructs a generic instance of this class that provides no defaults and has no overrides.
	 */
	public PropertiesModule() {
		this(null, null);
	}

	/**
	 * Constructs an instance with the given defaults and no overrides.
	 *
	 * @param defaults
	 *          The defaults to use; passing {@code null} is equivalent to passing an empty map.
	 */
	public PropertiesModule(Properties defaults) {
		this(defaults, null);
	}

	/**
	 * Constructs an instance with the given defaults and overrides.
	 *
	 * @param defaults
	 *          The defaults to use; passing {@code null} is equivalent to passing an empty map.
	 * @param overrides
	 *          The overrides to use; passing {@code null} is equivalent to passing an empty map.
	 */
	public PropertiesModule(Properties defaults, Properties overrides) {
		addDefaults(defaults);
		addOverrides(overrides);
	}

	/**
	 * Adds multiple pairs to the defaults set.
	 *
	 * @param newDefaults
	 *          The defaults to add; passing {@code null} is equivalent to passing an empty map.
	 * @return {@code this} for invocation chaining.
	 */
	public PropertiesModule addDefaults(Map<?, ?> newDefaults) {
		if (newDefaults != null) this.defaults.putAll(newDefaults);
		return this;
	}

	/**
	 * Adds a single pair to the defaults set.
	 *
	 * @param key
	 *          The key of the default pair to add; may not be {@code null}.
	 * @param value
	 *          The value of the default pair to add; may not be {@code null}.
	 * @return {@code this} for invocation chaining.
	 */
	public PropertiesModule addDefault(String key, Object value) {
		Objects.requireNonNull(key, "default pair key (value: " + value + ")");
		Objects.requireNonNull(value, "default pair value (key: " + key + ")");
		this.defaults.put(key, value);
		return this;
	}

	/**
	 * Adds multiple pairs to the overrides set.
	 *
	 * @param newOverrides
	 *          The overrides to add; passing {@code null} is equivalent to passing an empty map.
	 * @return {@code this} for invocation chaining.
	 */
	public PropertiesModule addOverrides(Map<?, ?> newOverrides) {
		if (newOverrides != null) this.overrides.putAll(newOverrides);
		return this;
	}

	/**
	 * Adds a single pair to the overrides set.
	 *
	 * @param key
	 *          The key of the override pair to add; may not be {@code null}.
	 * @param value
	 *          The value of the override pair to add; may not be {@code null}.
	 * @return {@code this} for invocation chaining.
	 */
	public PropertiesModule addOverride(String key, Object value) {
		Objects.requireNonNull(key, "default pair key (value: " + value + ")");
		Objects.requireNonNull(value, "default pair value (key: " + key + ")");
		this.defaults.put(key, value);
		return this;
	}

	@Override
	protected void configure() {
		// Construct an entry for properties to load
		final Properties toLoad = new Properties();
		toLoad.putAll(defaults);
		for (Map.Entry<String, String> env : System.getenv().entrySet()) {
			toLoad.put("env." + env.getKey(), env.getValue());
		}
		for (Map.Entry<?, ?> prop : System.getProperties().entrySet()) {
			toLoad.put("system." + prop.getKey(), prop.getValue());
		}
		toLoad.putAll(System.getenv());
		toLoad.putAll(System.getProperties());
		toLoad.putAll(overrides);

		// Bind the string values
		Names.bindProperties(this.binder(), toLoad);

		// Register all the fancypants conversions
		for (final String key : toLoad.stringPropertyNames()) {
			final String value = toLoad.getProperty(key);
			PropertyConversionProvider<?>[] providers = new PropertyConversionProvider[] {
					new PropertyToChar(key, value),
					new PropertyToInt(key, value),
					new PropertyToShort(key, value),
					new PropertyToLong(key, value),
					new PropertyToByte(key, value),
					new PropertyToCharset(key, value),
					new PropertyToFile(key, value),
					new PropertyToFloat(key, value),
					new PropertyToDouble(key, value),
					new PropertyToBigDecimal(key, value),
					new PropertyToBigInteger(key, value)
			};
			for (PropertyConversionProvider<?> provider : providers) {
				Class clazz = provider.valueClass();
				Named name = Names.named(key);
				Key guiceKey = Key.get(clazz, name);
				bind(guiceKey).toProvider(provider);
			}
		}
	}
}
