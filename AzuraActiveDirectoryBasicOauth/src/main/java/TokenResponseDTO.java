import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author MehmetAlpGuler
 */
@XmlRootElement
public class TokenResponseDTO {

	private String token_type;
	private String scope;
	private String expires_in;
	private String ext_expires_in;
	private String expires_on;
	private String not_before;
	private String resource;
	private String access_token;
	private String refresh_token;

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getExt_expires_in() {
		return ext_expires_in;
	}

	public void setExt_expires_in(String ext_expires_in) {
		this.ext_expires_in = ext_expires_in;
	}

	public String getExpires_on() {
		return expires_on;
	}

	public void setExpires_on(String expires_on) {
		this.expires_on = expires_on;
	}

	public String getNot_before() {
		return not_before;
	}

	public void setNot_before(String not_before) {
		this.not_before = not_before;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}



