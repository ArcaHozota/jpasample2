package jp.co.toshiba.ppok.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import jp.co.toshiba.ppok.dto.CityDto;
import jp.co.toshiba.ppok.entity.City;
import jp.co.toshiba.ppok.service.CentreLogicService;
import jp.co.toshiba.ppok.utils.Messages;
import jp.co.toshiba.ppok.utils.Pagination;
import jp.co.toshiba.ppok.utils.RestMsg;

/**
 * グローバル都市情報処理ハンドラ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Namespace("/ssmcrud2")
@Results({ @Result(name = "error", location = "/WEB-INF/failure.jsp"),
		@Result(name = "success", type = "json", params = { "root", "jsonfiedResult" }) })
@ParentPackage("json-default")
@Controller
public class GlobalCityAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = -5336245503597367061L;

	/**
	 * 中央処理サービスインターフェス
	 */
	@Resource
	private CentreLogicService centreLogicService;

	/**
	 * リクエスト
	 */
	private HttpServletRequest request;

	/**
	 * JSONリスポンス
	 */
	private RestMsg jsonfiedResult;

	/**
	 * This field corresponds to the database column ID
	 */
	private Integer id;

	/**
	 * This field corresponds to the database column NAME
	 */
	private String name;

	/**
	 * This field corresponds to the database column CONTINENT
	 */
	private String continent;

	/**
	 * This field corresponds to the database column NATION
	 */
	private String nation;

	/**
	 * This field corresponds to the database column DISTRICT
	 */
	private String district;

	/**
	 * This field corresponds to the database column POPULATION
	 */
	private Integer population;

	/**
	 * This field corresponds to the database column LANGUAGE
	 */
	private String language;

	/**
	 * ページング検索
	 *
	 * @return String
	 */
	@Action("checklist")
	public String checkDuplicated() {
		final String cityName = this.request.getParameter("cityName");
		final List<City> checkDuplicate = this.centreLogicService.checkDuplicate(cityName);
		if (checkDuplicate.isEmpty()) {
			this.setJsonfiedResult(RestMsg.success());
			return SUCCESS;
		}
		this.setJsonfiedResult(RestMsg.failure().add("validatedMsg", Messages.MSG004));
		return SUCCESS;
	}

	/**
	 * 都市情報を削除する
	 *
	 * @return String
	 */
	@Action("cityDel")
	public String deleteCityInfo() {
		final String cityId = this.request.getParameter("cityId");
		this.centreLogicService.removeById(Integer.parseInt(cityId));
		this.setJsonfiedResult(RestMsg.success(Messages.MSG013));
		return SUCCESS;
	}

	/**
	 * 国家名称を取得する
	 *
	 * @return String
	 */
	@Action("city")
	public String getCityInfoById() {
		final String rawId = this.request.getParameter("id");
		final CityDto cityInfoById = this.centreLogicService.getCityInfoById(Integer.parseInt(rawId));
		this.setJsonfiedResult(RestMsg.success().add("citySelected", cityInfoById));
		return SUCCESS;
	}

	public String getContinent() {
		return this.continent;
	}

	/**
	 * 大陸名称を取得する
	 *
	 * @return String
	 */
	@Action("continents")
	public String getContinents() {
		final List<String> allContinentNames = this.centreLogicService.findAllContinents();
		this.setJsonfiedResult(RestMsg.success().add("continents", allContinentNames));
		return SUCCESS;
	}

	public String getDistrict() {
		return this.district;
	}

	public Integer getId() {
		return this.id;
	}

	/**
	 * getter for jsonfiedResult
	 *
	 * @return RestMsg
	 */
	public RestMsg getJsonfiedResult() {
		return this.jsonfiedResult;
	}

	public String getLanguage() {
		return this.language;
	}

	/**
	 * 公用語を取得する
	 *
	 * @return String
	 */
	@Action("language")
	public String getLanguageByCty() {
		final String nationVal = this.request.getParameter("nationVal");
		final String languageByCty = this.centreLogicService.findLanguageByCty(nationVal);
		this.setJsonfiedResult(RestMsg.success().add("languageByCty", languageByCty));
		return SUCCESS;
	}

	public String getName() {
		return this.name;
	}

	public String getNation() {
		return this.nation;
	}

	/**
	 * 国家名称を取得する
	 *
	 * @return String
	 */
	@Action("countries")
	public String getNations() {
		final String continentVal = this.request.getParameter("continentVal");
		final List<String> nationNames = this.centreLogicService.findNationsByCnt(continentVal);
		this.setJsonfiedResult(RestMsg.success().add("nations", nationNames));
		return SUCCESS;
	}

	public Integer getPopulation() {
		return this.population;
	}

	/**
	 * ページング検索
	 *
	 * @return String
	 */
	@Action("cities")
	public String pagination() {
		final String pageNum = this.request.getParameter("pageNum");
		final String keyword = this.request.getParameter("keyword");
		final Pagination<CityDto> pagination = this.centreLogicService.getPagination(Integer.valueOf(pageNum), keyword);
		this.setJsonfiedResult(RestMsg.success().add("pageInfo", pagination));
		return SUCCESS;
	}

	/**
	 * 都市情報を保存する
	 *
	 * @return String
	 */
	@Action(value = "citySave", interceptorRefs = { @InterceptorRef(value = "json") })
	public String saveCityInfo() {
		final CityDto cityDto = new CityDto();
		cityDto.setId(this.id);
		cityDto.setName(this.name);
		cityDto.setContinent(this.continent);
		cityDto.setDistrict(this.district);
		cityDto.setPopulation(this.population);
		cityDto.setNation(this.nation);
		cityDto.setLanguage(this.language);
		this.centreLogicService.save(cityDto);
		this.setJsonfiedResult(RestMsg.success(Messages.MSG011));
		return SUCCESS;
	}

	public void setContinent(final String continent) {
		this.continent = continent;
	}

	public void setDistrict(final String district) {
		this.district = district;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * setter of jsonfiedResult
	 *
	 * @param jsonfiedResult JSONリスポンスデータ
	 */
	public void setJsonfiedResult(final RestMsg jsonfiedResult) {
		this.jsonfiedResult = jsonfiedResult;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setNation(final String nation) {
		this.nation = nation;
	}

	public void setPopulation(final Integer population) {
		this.population = population;
	}

	@Override
	public void setServletRequest(final HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 都市情報を変更する
	 *
	 * @return String
	 */
	@Action(value = "cityUpd", interceptorRefs = { @InterceptorRef(value = "json") })
	public String updateCityInfo() {
		final CityDto cityDto = new CityDto();
		cityDto.setId(this.id);
		cityDto.setName(this.name);
		cityDto.setContinent(this.continent);
		cityDto.setDistrict(this.district);
		cityDto.setPopulation(this.population);
		cityDto.setNation(this.nation);
		cityDto.setLanguage(this.language);
		final RestMsg updateMsg = this.centreLogicService.update(cityDto);
		this.setJsonfiedResult(updateMsg);
		return SUCCESS;
	}
}
