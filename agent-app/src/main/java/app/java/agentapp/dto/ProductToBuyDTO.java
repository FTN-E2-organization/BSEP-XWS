package app.java.agentapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductToBuyDTO {

	public int quantity;
	public Long productId;
	public Long shoppingCartId;
}
