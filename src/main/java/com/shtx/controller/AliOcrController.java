package com.shtx.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.shtx.utils.HttpUtils;


/**
 * @author tom
 * Description: 文件上传
 */

@CrossOrigin
@RestController
public class AliOcrController{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/aliocr")
	public Map getFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		Map map = new HashMap();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        //从前端拿到image的64编码
        String imgs = request.getParameter("image");
        String img = imgs.substring(1, imgs.length()-1);
        //System.out.println(imgs);
        //从前端拿到template_id
        String temids = request.getParameter("temid");
        String temid = temids.substring(1, temids.length()-1);
        //System.out.println(temids);
        
		// 调用阿里云api
		String host = "http://ocrdiy.market.alicloudapi.com";
        String path = "/api/predict/ocr_sdt";
        String method = "POST";
        String appcode = "**********************自己的appcode";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();

        String bodys = null;
        bodys =  "{\"image\":\""+ img +"\",\"configure\": \"{\\\"template_id\\\":\\\""+ temid +"\\\"}\"}";
     
		try {
			HttpResponse response1 = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			//System.out.println(response1);
			String message = String.valueOf(response1).substring(9, 12);
			//System.out.println("返回的状态码："+message);
			if(message.equals("200")) {
				//得到返回的数据的字符串
		        String result = EntityUtils.toString(response1.getEntity()); 
			    System.out.println("返回的字符串:" + result);
			    //处理json字符串，提取值
			    
			    JSONObject jsonObject = JSONObject.parseObject(result);						    
			    //System.out.println("json:" + jsonObject);
			    //取出json中需要的items值
			    JSONObject json = jsonObject.getJSONObject("items");
			    System.out.println("items:" + json);
			    
			    map.putAll(json);
			}else {
				Map<String,String> msg = new HashMap<String,String>();
				msg.put("error", message);
				map.putAll(msg);
			}
	        

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		
		 return map;
	} 

}
