package www.tssv.cn;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class TSSV_Exit extends Application
{
	private List<Activity> mList = new LinkedList<Activity>();
	private static TSSV_Exit instance;
	public static boolean isExit = false;
	private TSSV_Exit(){
		
	}
	public synchronized static TSSV_Exit getInstance(){
		if (instance == null)
		{
			instance = new TSSV_Exit();
		}
		return instance;
	}
	
	//add activity
	public void addActivity(Activity activity){
		mList.add(activity);
	}
	
	public void exitAll(){
		try
		{
			 isExit = true;
			for (Activity activity : mList)
			{
				if (activity != null)
				{
					activity.finish();
					activity = null;
				}
			}
		} catch (Exception e)
		{
			
			e.printStackTrace();
		}finally{
			instance = null;
			System.gc();
			System.exit(0);
		}
	}
	public void exit(){
		try
		{
			isExit = true;
			for (Activity activity : mList)
			{
				if (activity != null)
				{
					activity.finish();
					activity = null;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			instance = null;
			System.gc();
		}
	}
	@Override
	public void onLowMemory()
	{
		super.onLowMemory();
		System.gc();
	}

}
