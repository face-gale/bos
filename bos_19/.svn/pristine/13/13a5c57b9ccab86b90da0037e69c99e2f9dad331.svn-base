package com.itheima.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.itheima.bos.utils.PageBean;
import com.itheima.bos.utils.PinYin4jUtils;

public class UploadTest1 {
	
	
	@Test
	public void test1() throws FileNotFoundException, IOException{
		//使用poi 解析 xls
		
		//获取excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("F:\\abc.xls") ));
		//得到excel的sheet
		HSSFSheet sheet = workbook.getSheetAt(0);
		//遍历sheet里的内容
		for (Row row : sheet) {
			//获取第一列的数据
			String v1 = row.getCell(0).getStringCellValue();
			String v2 = row.getCell(1).getStringCellValue();
			String v3 = row.getCell(2).getStringCellValue();
			String v4 = row.getCell(3).getStringCellValue();
			String v5 = row.getCell(4).getStringCellValue();
			System.out.println(v1+" "+v2+" "+v3+" "+v4+" "+v5);
		}
		
	
	
	}
	@Test
	public void test2() {
		String province = "河北省";
		String city = "石家庄市";
		String district = "长安区";
		//城市编码---shijiazhuang
		city = city.substring(0, city.length()-1);
		String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
		String citycode = StringUtils.join(stringToPinyin, "");
		System.out.println(citycode);
		
		
		//简码--HBSJZCA
		province = province.substring(0, province.length()-1);
		district = district.substring(0, district.length()-1);
		String info = province+city+district;//河北石家庄长安
		String[] headByString = PinYin4jUtils.getHeadByString(info);
		
		String shortcode = StringUtils.join(headByString, "");
		System.out.println(shortcode);
		
		
		
		
		
	}

		
		
	
}
