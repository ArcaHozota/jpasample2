package jp.co.toshiba.ppok.repository;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import jp.co.toshiba.ppok.entity.CityInfo;

/**
 * 都市情報リポジトリ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public interface CityInfoRepository extends JpaRepository<CityInfo, Integer>, JpaSpecificationExecutor<CityInfo> {

	/**
	 * ビューリフレッシュ
	 */
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	@Query(value = "refresh materialized view city_info", nativeQuery = true)
	void refresh();
}
