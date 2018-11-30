package sample.aad.aad;

import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ClassUtils;
import sample.aad.utils.telemetry.TelemetryData;
import sample.aad.utils.telemetry.TelemetryProxy;

import java.util.HashMap;


public class AADAuthenticationFilterAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(AADAuthenticationProperties.class);

    private final TelemetryProxy telemetryProxy;

    public AADAuthenticationFilterAutoConfiguration() {
        this.telemetryProxy = new TelemetryProxy(true);
    }


    public AADAuthenticationFilter azureADJwtTokenFilter() {
        LOG.info("AzureADJwtTokenFilter Constructor.");
        trackCustomEvent();
        return new AADAuthenticationFilter();
    }


    public final static ResourceRetriever getJWTResourceRetriever() {
        return new DefaultResourceRetriever(RemoteJWKSet.DEFAULT_HTTP_CONNECT_TIMEOUT, RemoteJWKSet.DEFAULT_HTTP_READ_TIMEOUT,
                RemoteJWKSet.DEFAULT_HTTP_SIZE_LIMIT);
    }

    private void trackCustomEvent() {
        final HashMap<String, String> customTelemetryProperties = new HashMap<String, String>();
        final String[] packageNames = this.getClass().getPackage().getName().split("\\.");

        if (packageNames.length > 1) {
            customTelemetryProperties.put(TelemetryData.SERVICE_NAME, packageNames[packageNames.length - 1]);
        }
        telemetryProxy.trackEvent(ClassUtils.getUserClass(this.getClass()).getSimpleName(), customTelemetryProperties);
    }
}
