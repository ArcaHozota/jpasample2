package jp.co.toshiba.ppok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.toshiba.ppok.entity.Language;
import jp.co.toshiba.ppok.utils.LanguageId;

/**
 * 言語リポジトリ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public interface LanguageRepository extends JpaRepository<Language, LanguageId>, JpaSpecificationExecutor<Language> {
}
