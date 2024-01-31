package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootBoard.command.BExecuteCommand;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BController {
	private static final Logger logger=LoggerFactory.getLogger(BController.class);
	
	private final BExecuteCommand bExecuteService;
	@Autowired
	public BController(BExecuteCommand bExecuteService) {
		this.bExecuteService=bExecuteService;
	}
	
	@RequestMapping("list") //이건 구식이고 getMapping, postMapping을 써라
	public String list(Model model) {
		logger.info("list start...");
		bExecuteService.bListCmd(model);
		return "list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		logger.info("write_view start..");
		return "write_view";
	}
	
	@PostMapping(value="/write")
	public String write(HttpServletRequest request, Model model) {
		logger.info("write start..");
		model.addAttribute("request", request);
		bExecuteService.bWriteCmd(model);
		return "redirect:list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");
		model.addAttribute("request", request);
		bExecuteService.bContentCmd(model);
		return "content_view";
	}
	
}
