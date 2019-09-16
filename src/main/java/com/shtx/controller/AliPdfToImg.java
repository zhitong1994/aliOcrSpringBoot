package com.shtx.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shtx.utils.PdfToImg;

/**
 * @author tom
 * Description: 上传pdf转jpg
 */
@CrossOrigin
@RestController
public class AliPdfToImg {
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@RequestMapping("/uploadpdf")
	public Map getFile(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		Map map = new HashMap();
		String fileName = file.getOriginalFilename();
		String path = request.getServletContext().getRealPath("upload");
		File foder = new File(path);
		if(!foder.exists()) {
			foder.mkdirs();
		}
		 //文件上传
		File upfile = new File(path+"/"+fileName);
		byte[] fileByte = file.getBytes();
		OutputStream out = new FileOutputStream(upfile);
		out.write(fileByte);
		out.flush();
		out.close();
	    
		String pdfPath = path + "/" + fileName;
		System.out.println(pdfPath);
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File com=fsv.getHomeDirectory(); //读取桌面路径		    
		String Homepath = com.getPath();
		String imgPath = Homepath + "/" + fileName.substring(0,fileName.lastIndexOf("."));
		File folder = new File(imgPath);
		if(!folder.exists()){
		    folder.mkdirs();
		  }
	    	
		 PdfToImg pti = new PdfToImg();
		 pti.pdfToImage(pdfPath, imgPath);
		 
		 map.put(pdfPath, imgPath);
		 return map;
	} 

}
