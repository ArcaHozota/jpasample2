package jp.co.toshiba.ppok.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 国家テーブルのエンティティ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "country")
@NamedQuery(name = "Country.findNationCode", query = "select cty.code from Country as cty "
		+ "where cty.deleteFlg = 'visible' and cty.name =:nation")
public final class Country implements Serializable {

	private static final long serialVersionUID = 6762395398373991166L;

	/**
	 * This field corresponds to the database column CODE
	 */
	@Id
	private String code;

	/**
	 * This field corresponds to the database column NAME
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * This field corresponds to the database column CONTINENT
	 */
	@Column(nullable = false)
	private String continent;

	/**
	 * This field corresponds to the database column REGION
	 */
	@Column(nullable = false)
	private String region;

	/**
	 * This field corresponds to the database column SURFACE_AREA
	 */
	@Column(nullable = false, precision = 23, scale = 5)
	private BigDecimal surfaceArea;

	/**
	 * This field corresponds to the database column INDEPENDENCE_YEAR
	 */
	private Short independenceYear;

	/**
	 * This field corresponds to the database column POPULATION
	 */
	@Column(nullable = false)
	private Integer population;

	/**
	 * This field corresponds to the database column LIFE_EXPECTANCY
	 */
	@Column(precision = 5, scale = 2)
	private BigDecimal lifeExpectancy;

	/**
	 * This field corresponds to the database column GNP
	 */
	@Column(precision = 23, scale = 5)
	private BigDecimal gnpUsd;

	/**
	 * This field corresponds to the database column GNP_OLD
	 */
	@Column(precision = 23, scale = 5)
	private BigDecimal gnpUsdOld;

	/**
	 * This field corresponds to the database column LOCAL_NAME
	 */
	@Column(nullable = false)
	private String localName;

	/**
	 * This field corresponds to the database column GOVERNMENT_FORM
	 */
	@Column(nullable = false)
	private String governmentForm;

	/**
	 * This field corresponds to the database column HEAD_OF_STATE
	 */
	private String headOfState;

	/**
	 * This field corresponds to the database column CAPITAL
	 */
	private Integer capital;

	/**
	 * This field corresponds to the database column CODE2
	 */
	@Column(nullable = false)
	private String code2;

	/**
	 * This field corresponds to the database column LOGIC_DELETE_FLG
	 */
	@Column(nullable = false)
	private String deleteFlg;

	/**
	 * This field corresponds to the database table city
	 */
	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	private List<City> cities;
}
