package sample.aad.aad;

/**
 * Pojo file to store the service urls for different azure services.
 */

public class ServiceEndpoints {
    public String aadSigninUri;
    public String aadGraphApiUri;
    public String aadKeyDiscoveryUri;
    public String aadMembershipRestUri;

    public ServiceEndpoints() {
    }

    public ServiceEndpoints(String aadSigninUri, String aadGraphApiUri, String aadKeyDiscoveryUri, String aadMembershipRestUri) {
        this.aadSigninUri = aadSigninUri;
        this.aadGraphApiUri = aadGraphApiUri;
        this.aadKeyDiscoveryUri = aadKeyDiscoveryUri;
        this.aadMembershipRestUri = aadMembershipRestUri;
    }

    public String getAadSigninUri() {
        return aadSigninUri;
    }

    public void setAadSigninUri(String aadSigninUri) {
        this.aadSigninUri = aadSigninUri;
    }

    public String getAadGraphApiUri() {
        return aadGraphApiUri;
    }

    public void setAadGraphApiUri(String aadGraphApiUri) {
        this.aadGraphApiUri = aadGraphApiUri;
    }

    public String getAadKeyDiscoveryUri() {
        return aadKeyDiscoveryUri;
    }

    public void setAadKeyDiscoveryUri(String aadKeyDiscoveryUri) {
        this.aadKeyDiscoveryUri = aadKeyDiscoveryUri;
    }

    public String getAadMembershipRestUri() {
        return aadMembershipRestUri;
    }

    public void setAadMembershipRestUri(String aadMembershipRestUri) {
        this.aadMembershipRestUri = aadMembershipRestUri;
    }
}
