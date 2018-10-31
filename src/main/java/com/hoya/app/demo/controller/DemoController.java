package com.hoya.app.demo.controller;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hoya.app.demo.entity.User;
import com.hoya.app.exception.BadRequestException;
import com.hoya.app.exception.Success;

class DateTest {

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	//不包含年月日
	@JsonFormat(pattern="yyyy-MM-dd")
	Date date;
	
	//JacksonConf控制格式
	Date datetime;
}

@Controller
@RequestMapping("/demo")
public class DemoController {
	
	private Log log = LogFactory.getLog(DemoController.class);
	
	/*
	 * 日志输出等级配置
	 */
	@GetMapping(path="/log")
	@ResponseBody
	public Success log() {
		log.trace("trace");
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
		return new Success();
	}

	@GetMapping(path = "/index")
	public String index(Model model) {
		return "/demo/demo";
	}

	/*
	 * 重定向页面
	 */
	@GetMapping(path = "/redirect-demo")
	public String redirect() {
		return "redirect:/demo/redirect";
	}

	@GetMapping(path = "/redirect")
	public String redirectPage() {
		return "/demo/redirect";
	}
	
	/*
	 * foward
	 */
	@GetMapping(path="forward-demo")
	public String forward() {
		System.out.println("forward");
		return "forward:/demo/forward";
	}

	@GetMapping(path="foward")
	public String forwardPage() {
		System.out.println("forward page");
		return "/demo/forward";
	}

	/*
	 * 请求格式
	 */
	@ResponseBody
	@RequestMapping(value = "/consumes/sample.json", method = RequestMethod.POST, consumes = "application/json")
	public User postUser(@RequestBody User user) {
		return user;
	}

	/*
	 * 返回格式
	 */
	@ResponseBody
	@RequestMapping(value = "/produces/sample.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User getUser() {
		User user = new User();
		user.setName("test-name");
		user.setPassword("test-password");
		return user;
	}

	/*
	 * URL路径匹配 支持Ant路径表达式，Ant用符号 “ * ”表示匹配任意字符，用 “ ** ”表示统配任意路径，用 “ ？ ” 匹配单个字符
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id}.json")
	public String pathVariable(@PathVariable("id") Long id) {
		return "path variable:" + id;
	}

	/*
	 * params匹配 下面匹配/update.json?action=save"
	 */
	@ResponseBody
	@PostMapping(path = "/update.json", params = "action=save")
	public void updateUserSave() {
		System.out.println("call save");
	}

	@ResponseBody
	@PostMapping(path = "/update.json", params = "action=update")
	public void updateUserUpdate() {
		System.out.println("call update");
	}

	/*
	 * headers 匹配 下面匹配/update.json, HTTP 头action=update
	 */
	@ResponseBody
	@GetMapping(path = "/update.json", headers = "action=update")
	public void updateUserHeaders() {
		System.out.println("call update(headers)");
	}

	/*
	 * HTTP提交映射的方法参数 下面匹配/update2.json?name=abc&id=1
	 */
	@ResponseBody
	@GetMapping(path = "/update-param")
	public String updateUserParam(Integer id,
			@RequestParam(value = "name", required = true, defaultValue = "name") String name) {
		System.out.println("id:" + id + " name:" + name);
		return "success";
	}

	/*
	 * HTTP参数转换为JavaBean,非json格式,form或者url参数
	 */
	@ResponseBody
	@GetMapping(path = "/update.json")
	public String updateUserForm(User user) {
		return "username:" + user.getName() + " password:" + user.getPassword();
	}

	/*
	 * @RequestBody接受JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/savejson.json", method = RequestMethod.POST, consumes = "application/json")
	public User saveUserByJson(@RequestBody User user) {
		user.setIgnore("ignore");
		return user;
	}

	/*
	 * 处理文件上传
	 */
	@ResponseBody
	@PostMapping("/form")
	public String handleUpload(String name, MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			// file.transferTo(new File("D://"+file.getOriginalFilename()));//保存到文件系统
			byte[] content = file.getBytes();
			if(content.equals(FILE_UPLOAD_CONTENT))
				return "success";
		}

		return "failure";
	}
	
	public static final byte[] FILE_UPLOAD_CONTENT = "content".getBytes();

	/*
	 * ModelAttribute 用于Controllder
	 * 某个方法上，该方法会首先调用，并将方法结果作为Model属性，然后再调用对于的controller方法
	 */

	@ModelAttribute
	private void findUserById(@PathVariable(required = false) Long id, Model model) {
		model.addAttribute("user", id);
	}

	@ResponseBody
	@GetMapping(path = "/var/{id}/get.json")
	public String getUser1(Model model, @PathVariable String id) {
		System.out.println(model.containsAttribute("user"));
		return "success" + id;
	}

	@ResponseBody
	@PostMapping(path = "/date", consumes = "application/json")
	public DateTest date(@RequestBody DateTest date) {
		System.out.println("date:" + date.getDate() + " datetime:"+date.getDatetime());
		return date;
	}
	
	/*
	 * 异常处理
	 */
	@ResponseBody
	@GetMapping(path="/error")
	public Success error() throws Exception {
		if(0.5 < Math.random())
			throw new BadRequestException();
		
		return new Success("error");
	}
}