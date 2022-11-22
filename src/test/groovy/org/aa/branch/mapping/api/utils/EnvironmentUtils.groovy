package org.aa.branch.mapping.api.utils

final class EnvironmentUtils {
    private static final String APP_DOMAIN_KEY = 'api.tests.env.url'

    private static final String DEFAULT_DOMAIN = 'http://local.env'

    private EnvironmentUtils() {}

    static URL getAppUrl() {
        getSystemValueOrDefault(APP_DOMAIN_KEY, DEFAULT_DOMAIN).toURL()
    }

    /**
     * Work similar to System.getProperty(key, defaultValue),
     * but current implementation will return default value even when key exists with empty or null value
     * @param key - system variable key
     * @param defaultValue - default value if there will be the undefined value
     * @return variable value
     */
    private static String getSystemValueOrDefault(String key, String defaultValue) {
        System.getProperty(key) ?: defaultValue
    }
}