package jp.co.toshiba.ppok.repository;

import java.util.List;

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
	 * 大陸の集合を取得する
	 *
	 * @return List<String>
	 */
	List<String> findAllContinents();

	/**
	 * 国名によって国家コードを抽出する
	 *
	 * @param nationVal 国名
	 * @return String
	 */
	String findNationCode(@Param("nation") String nationVal);

	/**
	 * 選択された大陸の上にすべての国の情報を取得する
	 *
	 * @param continent 大陸名
	 * @return List<String>
	 */
	List<String> findNationsByCnt(@Param("continent") String continent);
}
