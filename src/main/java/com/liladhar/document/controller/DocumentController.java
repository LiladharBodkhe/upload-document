package com.liladhar.document.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.liladhar.document.entities.Document;
import com.liladhar.document.repos.DocumentRepository;

@Controller
public class DocumentController {

	@Autowired
	DocumentRepository documentRepository;
	
	@RequestMapping("/displayUpload")
	//@ResponseBody
	public String displayUpload(ModelMap modelMap) {
		List<Document> documents=documentRepository.findAll();
		System.out.println(documents.size());
		modelMap.addAttribute("documents", documents);
		return "documentUpload";
	}
	
	@RequestMapping(value="/upload")
	//@ResponseBody
	public String uploadDocument(@RequestParam("document")MultipartFile multipartFile,@RequestParam("id") long id,ModelMap modelMap) {
		Document document=new Document();
		document.setId(id);
		document.setName(multipartFile.getOriginalFilename());
		try {
			document.setData(multipartFile.getBytes());
		}catch(IOException e) {
			e.printStackTrace();
		}
		documentRepository.save(document);
		
		
		List<Document> documents=documentRepository.findAll();
		System.out.println(documents.size());
		modelMap.addAttribute("documents", documents);
		return "documentUpload";
	}
	
	@RequestMapping("/download")
	public StreamingResponseBody download(@RequestParam("id") long id,HttpServletResponse response) {
		Document document=documentRepository.findById(id).get();
		byte[] data =document.getData();
		String name=document.getName();
		response.setHeader("Content-Disposition", "attachment;filename=document");
		
		return outputStream->{
			outputStream.write(data);
		};
	}
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "hello";
	}
}
