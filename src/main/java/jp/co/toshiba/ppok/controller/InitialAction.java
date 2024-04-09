package jp.co.toshiba.ppok.controller;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 初期表示処理ハンドラ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Namespace("/")
@Result(name = "success", location = "/WEB-INF/cities.jsp")
@Controller
public class InitialAction extends ActionSupport {

	private static final long serialVersionUID = -8455008138473489275L;

	/**
	 * 画面初期表示する
	 *
	 * @return String
	 */
	@Action("index")
	public String initial() {
		return SUCCESS;
	}
}
