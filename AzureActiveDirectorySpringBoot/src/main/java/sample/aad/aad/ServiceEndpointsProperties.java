/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package sample.aad.aad;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class ServiceEndpointsProperties {
    private Map<String, ServiceEndpoints> endpoints = new HashMap<>();

    public ServiceEndpointsProperties() {
        ServiceEndpoints serviceEndpoints  = new ServiceEndpoints(ServiceEndpointsConst.aadSigninUri,
                                                                  ServiceEndpointsConst.aadGraphApiUri,
                                                                  ServiceEndpointsConst.aadKeyDiscoveryUri,
                                                                  ServiceEndpointsConst.aadMembershipRestUri);
        this.endpoints.put("global", serviceEndpoints);
    }

    public Map<String, ServiceEndpoints> getEndpoints() {
        return endpoints;
    }

    /**
     * Get ServiceEndpoints data for the given environment.
     *
     * @param environment the environment of the cloud service
     * @return The ServiceEndpoints data for the given azure service <code>environment</code>
     */
    public ServiceEndpoints getServiceEndpoints(String environment) {
        Assert.notEmpty(endpoints, "No service endpoints found");

        if (!endpoints.containsKey(environment)) {
            throw new IllegalArgumentException(environment + " is not found in the configuration," +
                    " only following environments are supported: " + endpoints.keySet());
        }

        return endpoints.get(environment);
    }
}
