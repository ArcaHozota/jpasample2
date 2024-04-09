package jp.co.toshiba.ppok.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import jp.co.toshiba.ppok.utils.LanguageId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 言語テーブルのエンティティ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@IdClass(LanguageId.class)
@Table(name = "language")
public final class Language implements Serializable {

	private static final long serialVersionUID = -8085659909634431823L;

	/**
	 * This field corresponds to the database column COUNTRY_CODE
	 */
	@Id
	private String countryCode;

	/**
	 * This field corresponds to the database column LANGUAGE
	 */
	@Id
	@Column(name = "language")
	private String name;

	/**
	 * This field corresponds to the database column IS_OFFICIAL
	 */
	@Column(nullable = false)
	private String isOfficial;

	/**
	 * This field corresponds to the database column PERCENTAGE
	 */
	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal percentage;

	/**
	 * This field corresponds to the database column LOGIC_DELETE_FLG
	 */
	@Column(nullable = false)
	private String deleteFlg;
}
