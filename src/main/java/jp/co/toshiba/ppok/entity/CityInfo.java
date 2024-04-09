package jp.co.toshiba.ppok.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 都市情報マテリアライズドビューのエンティティ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "city_info")
public final class CityInfo implements Serializable {

	private static final long serialVersionUID = -6905329688598071268L;

	/**
	 * 都市ID
	 */
	@Id
	private Integer id;

	/**
	 * 都市名
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 大陸
	 */
	@Column(nullable = false)
	private String continent;

	/**
	 * 国家
	 */
	@Column(nullable = false)
	private String nation;

	/**
	 * 地域
	 */
	@Column(nullable = false)
	private String district;

	/**
	 * 人口
	 */
	@Column(nullable = false)
	private Integer population;

	/**
	 * 公用語
	 */
	@Column(nullable = false)
	private String language;
}
