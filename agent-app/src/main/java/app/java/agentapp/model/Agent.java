package app.java.agentapp.model;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Agent extends User{

	@ColumnDefault("false")
	private boolean hasApiToken;
}
