package app.java.agentapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCartDTO {

	public Long id;
	public Long campaignId;
	public Long customerId;
	public LocalDateTime timestamp;
	public double totalPrice;
}
