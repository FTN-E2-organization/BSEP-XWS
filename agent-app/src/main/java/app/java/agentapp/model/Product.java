package app.java.agentapp.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

	@Id
	@SequenceGenerator(name = "productSeqGen", sequenceName = "productSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private double price;
	
	@Column(nullable=false)
	private int availableQuantity;
	
	@Column(nullable=false)
	private boolean isDeleted;
	
	@Column(nullable=false)
	private String imagePath;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private Agent agent;
}
