package app.java.agentapp.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductToBuy {

	@Id
	@SequenceGenerator(name = "productToBuySeqGen", sequenceName = "productToBuySeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productToBuySeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(nullable=false)
	private int quantity;
	
	@Column(nullable=false)
	private boolean isDeleted;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private ShoppingCart shoppingCart;
}
