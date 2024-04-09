package jp.co.toshiba.ppok.utils;

import java.io.Serializable;

import lombok.Data;

/**
 * 言語テーブル複数プライマリーキーの永続化するクラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Data
public class LanguageId implements Serializable {

	private static final long serialVersionUID = 4470395347286329942L;

	/**
	 * This field corresponds to the database column COUNTRY_CODE
	 */
	private String countryCode;

	/**
	 * This field corresponds to the database column LANGUAGE
	 */
	private String name;
}
