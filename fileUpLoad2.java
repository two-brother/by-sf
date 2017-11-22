package com.jobservice.controller.File;

import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

@Controller//扫描
public class FileUpLoadController {
	@RequestMapping("/addPictrueToService.do")
	@ResponseBody
	public boolean execute(HttpServletRequest request, 
			HttpServletResponse response,
			String image,
			String currentTime){
		// 只允许jpg
		String header = "data:image/jpeg;base64,";
		image = image.substring(header.length());
		// 写入磁盘
		boolean success = false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] decodedBytes = decoder.decodeBuffer(image);

//工程原始路径
//			String imgFilePath_Project = "D://Java//myWeekWork//jobservice2017//WebRoot//upload//"+currentTime+".jpg";

//服务器路径
			String imgFilePath_Service  =request.getSession().getServletContext().getRealPath("/");
			FileOutputStream out = new FileOutputStream(imgFilePath_Service+"upload/"+currentTime+".jpg");
//			FileOutputStream out1 = new FileOutputStream(imgFilePath_Project);
			out.write(decodedBytes);
//			out1.write(decodedBytes);
			out.close();
//			out1.close();
			success = true;
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return success;
		
	}
}
