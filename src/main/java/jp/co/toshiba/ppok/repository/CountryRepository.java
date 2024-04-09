package jp.co.toshiba.ppok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import jp.co.toshiba.ppok.entity.Country;

/**
 * 国家リポジトリ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public interface CountryRepository extends JpaRepository<Country, String>, JpaSpecificationExecutor<Country> {

	/**
	 * 国名によって国家コードを抽出する
	 *
	 * @param nationVal 国名
	 * @return String
	 */
	String findNationCode(@Param("nation") String nationVal);
}
