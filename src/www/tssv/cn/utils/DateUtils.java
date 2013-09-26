package www.tssv.cn.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtils {
	
	/**
	 * 获取当前日期是周几
	 */
	public static int getTodayWeekDay(){
		Calendar calendar = Calendar.getInstance();
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		if (weekDay == 1) {
			return 7;
		}else {
			return (weekDay - 1);
		}
	}
	
	/**
	 * 获取当前的日期yyyy-MM-dd HH:mm:ss
	 */
	public String getCurrenDate_YMDHMS(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	/**
	 * 获取当前的日期HH:mm
	 */
	public String getCurrentDate_HM(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(date);
	}
	
	/**
	 * 将当前时间转化为int HHmm
	 */
	public static int getDateToInt(Date time){
		SimpleDateFormat format = new SimpleDateFormat("HHmm");
		return Integer.parseInt(format.format(time));
	}
	
	public static int getDateToInt(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HHmm");
		return Integer.parseInt(format.format(date));
	}
	
	public static int getStringToInt(String s){
		return Integer.parseInt(s);
	}
	
	/**
	 * 字符串转化为日期 HHmm
	 */
	public static Date getStringToDate(String s){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		try {
			date = format.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
}
