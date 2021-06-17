package app.java.agentapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
	
	public Long id;
	public double price;
	public int availableQuantity;
	public Long agentId;
	public boolean isDeleted;
	public String path;

}