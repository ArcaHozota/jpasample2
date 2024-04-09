package jp.co.toshiba.ppok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.toshiba.ppok.entity.City;

/**
 * 都市リポジトリ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public interface CityRepository extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {

	/**
	 * 採番を行います
	 *
	 * @return 採番値
	 */
	Integer saiban();
}
