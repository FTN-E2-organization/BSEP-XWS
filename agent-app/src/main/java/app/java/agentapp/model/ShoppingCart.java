package app.java.agentapp.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShoppingCart {

	@Id
	@SequenceGenerator(name = "shoppingCartSeqGen", sequenceName = "shoppingCartSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shoppingCartSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(nullable=false)
	private double totalPrice;
	
	@Column(nullable=false)
	private Long campaignId;
	
	@Column(nullable=false)
	private boolean isFinished;
	
	@Column(nullable=false)
	private boolean isDeleted;
	
	@Column
	@CreationTimestamp
	private LocalDateTime timestamp;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private Customer customer;
}
