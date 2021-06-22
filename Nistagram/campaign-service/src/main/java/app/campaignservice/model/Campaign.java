package app.campaignservice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import app.campaignservice.enums.CampaignType;
import app.campaignservice.enums.ContentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Campaign {

	@Id
	@SequenceGenerator(name = "campaignSeqGen", sequenceName = "campaignSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaignSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;	
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private CampaignType campaignType;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private ContentType contentType;
	
	@Column(nullable=false)
	private LocalDate startDate;
	
	@Column(nullable=false)
	private LocalDate endDate;	
	
	@Column
	private boolean isDeleted;	
	
	@Column(nullable = false)
	private LocalDateTime lastUpdateTime;	
	
	@Column(nullable = false)
	private String agentUsername;	
	
	@Column
	private int placementFrequency;	
	
	@ElementCollection(targetClass = String.class)
	private Collection<LocalTime> dailyFrequency;	
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "campaigns_ads", joinColumns = @JoinColumn(name = "campaign_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ad_id", referencedColumnName = "id"))
    private Set<Ad> ads = new HashSet<Ad>();
		
	
	
	
}
