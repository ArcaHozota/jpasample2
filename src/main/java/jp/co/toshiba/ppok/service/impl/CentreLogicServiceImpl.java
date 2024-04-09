package jp.co.toshiba.ppok.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import jp.co.toshiba.ppok.dto.CityDto;
import jp.co.toshiba.ppok.entity.City;
import jp.co.toshiba.ppok.entity.CityInfo;
import jp.co.toshiba.ppok.entity.Country;
import jp.co.toshiba.ppok.repository.CityInfoRepository;
import jp.co.toshiba.ppok.repository.CityRepository;
import jp.co.toshiba.ppok.repository.CountryRepository;
import jp.co.toshiba.ppok.service.CentreLogicService;
import jp.co.toshiba.ppok.utils.CommonException;
import jp.co.toshiba.ppok.utils.Messages;
import jp.co.toshiba.ppok.utils.Pagination;
import jp.co.toshiba.ppok.utils.RestMsg;
import jp.co.toshiba.ppok.utils.SecondBeanUtils;
import jp.co.toshiba.ppok.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 中央処理サービス実装クラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CentreLogicServiceImpl implements CentreLogicService {

	/**
	 * ナビゲーションのページ数
	 */
	private static final Integer NAVIGATION_NUMBER = 7;

	/**
	 * ページサイズ
	 */
	private static final Integer PAGE_SIZE = 8;

	/**
	 * デフォルトソート値
	 */
	private static final Integer SORT_NUMBER = 100;

	/**
	 * 都市リポジトリ
	 */
	private final CityRepository cityRepository;

	/**
	 * 都市情報リポジトリ
	 */
	private final CityInfoRepository cityInfoRepository;

	/**
	 * 国家リポジトリ
	 */
	private final CountryRepository countryRepository;

	@Override
	public List<City> checkDuplicate(final String cityName) {
		final City city = new City();
		city.setName(StringUtils.toHankaku(cityName));
		city.setDeleteFlg(Messages.MSG007);
		final Example<City> example = Example.of(city, ExampleMatcher.matchingAll());
		return this.cityRepository.findAll(example);
	}

	@Override
	public List<String> findAllContinents() {
		final Specification<Country> specification = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), Messages.MSG007);
		return this.countryRepository.findAll(specification).stream().map(Country::getContinent).distinct()
				.sorted(Comparator.naturalOrder()).collect(Collectors.toList());
	}

	@Override
	public String findLanguageByCty(final String nationVal) {
		final Specification<CityInfo> specification = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("nation"), nationVal);
		return this.cityInfoRepository.findAll(specification).stream().map(CityInfo::getLanguage)
				.collect(Collectors.toList()).get(0);
	}

	@Override
	public List<String> findNationsByCnt(final String continentVal) {
		final Specification<Country> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), Messages.MSG007);
		if (StringUtils.isDigital(continentVal)) {
			final Integer id = Integer.parseInt(continentVal);
			final List<String> nations = Lists.newArrayList();
			final CityInfo cityInfo = this.cityInfoRepository.findById(id).orElseGet(CityInfo::new);
			nations.add(cityInfo.getNation());
			final Specification<Country> where2 = (root, query, criteriaBuilder) -> criteriaBuilder
					.equal(root.get("continent"), cityInfo.getContinent());
			final Specification<Country> specification = Specification.where(where1).and(where2);
			final List<String> list = this.countryRepository.findAll(specification).stream().map(Country::getName)
					.filter(a -> StringUtils.isNotEqual(a, cityInfo.getNation())).distinct()
					.sorted(Comparator.naturalOrder()).collect(Collectors.toList());
			nations.addAll(list);
			return nations;
		}
		final Specification<Country> specification = Specification.where(where1);
		return this.countryRepository.findAll(specification).stream().map(Country::getName).distinct()
				.sorted(Comparator.naturalOrder()).collect(Collectors.toList());
	}

	@Override
	public CityDto getCityInfoById(final Integer id) {
		final CityDto cityDto = new CityDto();
		final CityInfo cityInfo = this.cityInfoRepository.findById(id).orElseGet(CityInfo::new);
		SecondBeanUtils.copyNullableProperties(cityInfo, cityDto);
		return cityDto;
	}

	@Override
	public Pagination<CityDto> getPagination(final Integer pageNum, final String keyword) {
		// ページングコンストラクタを宣言する；
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PAGE_SIZE, Sort.by(Direction.ASC, "id"));
		// キーワードの属性を判断する；
		if (StringUtils.isNotEmpty(keyword)) {
			final String hankakuKeyword = StringUtils.toHankaku(keyword);
			final int pageMin = PAGE_SIZE * (pageNum - 1);
			final int pageMax = PAGE_SIZE * pageNum;
			int sort = CentreLogicServiceImpl.SORT_NUMBER;
			if (hankakuKeyword.startsWith("min(pop)")) {
				final int indexOf = hankakuKeyword.indexOf(")");
				final String keisan = hankakuKeyword.substring(indexOf + 1);
				if (StringUtils.isNotEmpty(keisan)) {
					sort = Integer.parseInt(keisan);
				}
				// 人口数量昇順で最初の15個都市の情報を吹き出します；
				final List<CityDto> minimumRanks = this.cityInfoRepository.findAll().stream().map(item -> {
					final CityDto cityDto = new CityDto();
					SecondBeanUtils.copyNullableProperties(item, cityDto);
					return cityDto;
				}).sorted(Comparator.comparing(CityDto::getPopulation)).collect(Collectors.toList()).subList(0, sort);
				if (pageMax >= sort) {
					return Pagination.of(minimumRanks.subList(pageMin, sort), minimumRanks.size(), pageNum, PAGE_SIZE,
							NAVIGATION_NUMBER);
				}
				return Pagination.of(minimumRanks.subList(pageMin, pageMax), minimumRanks.size(), pageNum, PAGE_SIZE,
						NAVIGATION_NUMBER);
			}
			if (hankakuKeyword.startsWith("max(pop)")) {
				final int indexOf = hankakuKeyword.indexOf(")");
				final String keisan = hankakuKeyword.substring(indexOf + 1);
				if (StringUtils.isNotEmpty(keisan)) {
					sort = Integer.parseInt(keisan);
				}
				// 人口数量降順で最初の15個都市の情報を吹き出します；
				final List<CityDto> maximumRanks = this.cityInfoRepository.findAll().stream().map(item -> {
					final CityDto cityDto = new CityDto();
					SecondBeanUtils.copyNullableProperties(item, cityDto);
					return cityDto;
				}).sorted(Comparator.comparing(CityDto::getPopulation).reversed()).collect(Collectors.toList())
						.subList(0, sort);
				if (pageMax >= sort) {
					return Pagination.of(maximumRanks.subList(pageMin, sort), maximumRanks.size(), pageNum, PAGE_SIZE,
							NAVIGATION_NUMBER);
				}
				return Pagination.of(maximumRanks.subList(pageMin, pageMax), maximumRanks.size(), pageNum, PAGE_SIZE,
						NAVIGATION_NUMBER);
			}
			// ページング検索；
			final CityInfo cityInfo = new CityInfo();
			final String nationCode = this.countryRepository.findNationCode(hankakuKeyword);
			if (StringUtils.isNotEmpty(nationCode)) {
				cityInfo.setNation(hankakuKeyword);
				final Example<CityInfo> example = Example.of(cityInfo, ExampleMatcher.matching());
				final Page<CityInfo> pages = this.cityInfoRepository.findAll(example, pageRequest);
				final List<CityDto> list = pages.getContent().stream().map(item -> {
					final CityDto cityDto = new CityDto();
					SecondBeanUtils.copyNullableProperties(item, cityDto);
					return cityDto;
				}).collect(Collectors.toList());
				return Pagination.of(list, pages.getTotalElements(), pageNum, PAGE_SIZE, NAVIGATION_NUMBER);
			}
			cityInfo.setName(hankakuKeyword);
			final ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",
					GenericPropertyMatchers.contains());
			final Example<CityInfo> example = Example.of(cityInfo, matcher);
			final Page<CityInfo> pages = this.cityInfoRepository.findAll(example, pageRequest);
			final List<CityDto> list = pages.getContent().stream().map(item -> {
				final CityDto cityDto = new CityDto();
				SecondBeanUtils.copyNullableProperties(item, cityDto);
				return cityDto;
			}).collect(Collectors.toList());
			return Pagination.of(list, pages.getTotalElements(), pageNum, PAGE_SIZE, NAVIGATION_NUMBER);
		}
		// ページング検索；
		final Page<CityInfo> pages = this.cityInfoRepository.findAll(pageRequest);
		final List<CityDto> list = pages.getContent().stream().map(item -> {
			final CityDto cityDto = new CityDto();
			SecondBeanUtils.copyNullableProperties(item, cityDto);
			return cityDto;
		}).collect(Collectors.toList());
		return Pagination.of(list, pages.getTotalElements(), pageNum, PAGE_SIZE, NAVIGATION_NUMBER);
	}

	@Override
	public void removeById(final Integer id) {
		final City city = this.cityRepository.findById(id).orElseThrow(() -> {
			throw new CommonException(Messages.MSG009);
		});
		city.setDeleteFlg(Messages.MSG008);
		this.cityRepository.saveAndFlush(city);
		this.cityInfoRepository.refresh();
	}

	@Override
	public void save(final CityDto cityDto) {
		final String countryCode = this.countryRepository.findNationCode(cityDto.getNation());
		final Integer saiban = this.cityRepository.saiban();
		final City city = new City();
		SecondBeanUtils.copyNullableProperties(cityDto, city);
		city.setId(saiban);
		city.setCountryCode(countryCode);
		city.setDeleteFlg(Messages.MSG007);
		this.cityRepository.saveAndFlush(city);
		this.cityInfoRepository.refresh();
	}

	@Override
	public RestMsg update(final CityDto cityDto) {
		final City originalEntity = new City();
		final City city = this.cityRepository.findById(cityDto.getId()).orElseGet(City::new);
		SecondBeanUtils.copyNullableProperties(city, originalEntity);
		SecondBeanUtils.copyNullableProperties(cityDto, city);
		final String countryCode = this.countryRepository.findNationCode(cityDto.getNation());
		city.setCountryCode(countryCode);
		if (originalEntity.equals(city)) {
			return RestMsg.failure().add("errorMsg", Messages.MSG012);
		}
		this.cityRepository.saveAndFlush(city);
		this.cityInfoRepository.refresh();
		return RestMsg.success();
	}
}
