package sample.aad.aad;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class UserPrincipalManager {
    private static final Logger LOG = LoggerFactory.getLogger(UserPrincipalManager.class);

    private final ConfigurableJWTProcessor<SecurityContext> validator;
    private final ResourceRetriever resourceRetriever;

    public UserPrincipalManager(ResourceRetriever resourceRetriever) {
        this.validator = getAadJwtTokenValidator();
        this.resourceRetriever = resourceRetriever;
    }

    public UserPrincipal buildUserPrincipal(String idToken) throws ParseException, JOSEException, BadJOSEException {
        final JWTClaimsSet jwtClaimsSet = validator.process(idToken, null);
        final JWTClaimsSetVerifier<SecurityContext> verifier = validator.getJWTClaimsSetVerifier();
        verifier.verify(jwtClaimsSet, null);
        final JWSObject jwsObject = JWSObject.parse(idToken);

        return new UserPrincipal(jwsObject, jwtClaimsSet);
    }

    private ConfigurableJWTProcessor<SecurityContext> getAadJwtTokenValidator() {
        final ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        final JWKSource<SecurityContext> keySource;

        try {
            keySource = new RemoteJWKSet<>(new URL(ServiceEndpointsConst.aadKeyDiscoveryUri), resourceRetriever);
        } catch (MalformedURLException e) {
            LOG.error("Failed to parse active directory key discovery uri.", e);
            throw new IllegalStateException("Failed to parse active directory key discovery uri.", e);
        }

        final JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;
        final JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(expectedJWSAlg, keySource);
        jwtProcessor.setJWSKeySelector(keySelector);

        jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier<SecurityContext>() {
            @Override
            public void verify(JWTClaimsSet claimsSet, SecurityContext ctx) throws BadJWTException {
                super.verify(claimsSet, ctx);
                final String issuer = claimsSet.getIssuer();
                if (issuer == null || !issuer.contains("https://sts.windows.net/")
                        && !issuer.contains("https://sts.chinacloudapi.cn/")) {
                    throw new BadJWTException("Invalid token issuer");
                }
            }
        });
        return jwtProcessor;
    }
}
