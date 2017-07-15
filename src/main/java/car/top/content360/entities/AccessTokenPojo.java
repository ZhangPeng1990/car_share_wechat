package car.top.content360.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccessTokenPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String requestHost;
	private String requestQuery;
	private String token;
	private Date expiresTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRequestHost() {
		return requestHost;
	}
	public void setRequestHost(String requestHost) {
		this.requestHost = requestHost;
	}
	public String getRequestQuery() {
		return requestQuery;
	}
	public void setRequestQuery(String requestQuery) {
		this.requestQuery = requestQuery;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpiresTime() {
		return expiresTime;
	}
	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}
}
