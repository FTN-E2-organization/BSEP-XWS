package app.authservice.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "codeToken")
public class CodeToken {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "token_id")
		private long tokenid;

		@Column(name = "code")
		private String code;

		@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
		@JoinColumn(nullable = false, name = "user_id")
		private User user;

		@Column(nullable = false, name = "token_time")
		private LocalDateTime expirationTime;

		public CodeToken() {
		}

		public CodeToken(User user) {
			this.user = user;
			code = UUID.randomUUID().toString().replace("-","").substring(0,6);
		}

		public long getTokenid() {
			return tokenid;
		}

		public void setTokenid(long tokenid) {
			this.tokenid = tokenid;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public LocalDateTime getExparationTime() {
			return expirationTime;
		}

		public void setExparationTime(LocalDateTime exparationTime) {
			this.expirationTime = exparationTime;
		}
}
