package com.zetapp.zet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class GennieUtils {
	public static Class<?> getClassFromName(String packagename){
		Class<?> c = null;
        if(packagename != null) {
            try {
            	c = Class.forName(packagename);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return c;
	}
	
	public static String getPackageName(Context ctx){
		return ctx.getPackageName();
	}
	
	public static int getIDResource(Context ctx,String folder,String filename){
		String fn = null;
		if(filename.contains(".")){
			fn = filename.split("\\.")[0];
		}else{
			fn = filename;
		}
		return ctx.getResources().getIdentifier(fn,folder, GennieUtils.getPackageName(ctx));
	}
	
	public static int[] getIntArrayResource(Context context,String folder,String filename){
		int[] x = null;
		try {
			Class clazz = Class.forName(getPackageName(context)+".R$"+folder);
			try {
				String fn = null;
				if(filename.contains(".")){
					fn = filename.split("\\.")[0];
				}else{
					fn = filename;
				}
				if(clazz != null)
					x = (int[])clazz.getDeclaredField(fn).get(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return x;
	}
	
	
	public static int getIntResource(Context context,String folder,String filename){
		int x = 0;
		try {
			Class clazz = Class.forName(getPackageName(context)+".R$"+folder);
			try {
				String fn = null;
				if(filename.contains(".")){
					fn = filename.split("\\.")[0];
				}else{
					fn = filename;
				}
				if(clazz != null)
					x = (int)clazz.getDeclaredField(fn).getInt(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return x;
	}
	
	public static boolean getBooleanResource(Context context,String folder,String filename){
		boolean x = false;
		try {
			Class clazz = Class.forName(getPackageName(context)+".R$"+folder);
			try {
				String fn = null;
				if(filename.contains(".")){
					fn = filename.split("\\.")[0];
				}else{
					fn = filename;
				}
				if(clazz != null)
					x = (boolean)clazz.getDeclaredField(fn).getBoolean(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return x;
	}
	
	
	public static View getViewFromClass(String namewithpackage,Context ctx,Object contsec){
		View v = null;
		try {
			v = (View)getClassFromName(namewithpackage).getDeclaredConstructor(Context.class,Object.class).newInstance(ctx,contsec);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return v;
	}
	
	public static void runStaticMethod(String namewithpackage,Context ctx,RelativeLayout content,Object contsec){
			try {
				Class c = getClassFromName(namewithpackage);
				if(c != null){
					Method m = c.getDeclaredMethod("getContent", Context.class, RelativeLayout.class,Object.class);
					if(m != null){
						m.invoke(null, ctx,content,contsec);
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
	}
	public static Object runStaticMethodModularGetLayout(String classname,Context ctx,Node node){
		Object obj = null;
		try {
			Class c = getClassFromName(classname);
			if(c != null){
				Method m = c.getDeclaredMethod("getLayout",Context.class,Node.class);
				if(m != null){
					obj = (Object)m.invoke(null,ctx,node);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public static void runStaticMethodModularGetContent(String classname,Object parseObj,Context ctx,Bundle userdata,RelativeLayout root){
		try {
			Class c = getClassFromName(classname);
			if(c != null){
				Method m = c.getDeclaredMethod("getContent", Context.class, RelativeLayout.class,Object.class,Bundle.class);
				if(m != null){
					m.invoke(null,ctx,root,parseObj,userdata);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void Toast(Context context,String msg){
		Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
	}

	public static AlertDialog ShowInfo(Context context,String title,String msg){
			AlertDialog alert = null;
		    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	     	builder.setMessage(msg)
	     	       .setCancelable(false)
	     	       .setNegativeButton("Close", new DialogInterface.OnClickListener() {
	     	           public void onClick(DialogInterface dialog, int id) {
	     	                dialog.cancel();
	     	           }
	     	       });
	     	alert = builder.create();
	     	alert.setTitle(title);
	     	alert.show();
	     	return alert;
	}
	  
  	//get String from intent
	 public static String getStringIntent(Intent intent,String name){
		 String retval = null;
		 if(intent != null){
			 if(intent.hasExtra(name))
				 retval = intent.getStringExtra(name);
		 }
		 return retval;
	 }
	 
	//get Array String from intent
	 public static String[] getArrayStringIntent(Intent intent,String name){
		 String[] retval = null;
		 if(intent != null){
			 if(intent.hasExtra(name))
				 retval = intent.getStringArrayExtra(name);
		 }
		 return retval;
	 }
	 
	//get int from intent
	 public static int getIntIntent(Intent intent,String name,int defaultvalue){
		 int retval = defaultvalue;
		 if(intent != null){
			 if(intent.hasExtra(name))
				 retval = intent.getIntExtra(name, 0);
		 }
		 return retval;
	 }
	 
	//get int from intent
	 public static Bundle getBundleIntent(Intent intent,String name){
		 Bundle retval = null;
		 if(intent != null){
			 if(intent.hasExtra(name))
				 retval = intent.getBundleExtra(name);
		 }
		 return retval;
	 }
	 public static int GetScreenWidth(Context context){
		 Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
		 return display.getWidth();
	 }

	 
	 public static int GetScreenHeight(Context context){
		 Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
		 return display.getHeight();
	 }
	 public static int GetAppHeight(Context context){
		 return (GetScreenHeight(context)-getStatusBarHeight(context));
	 }
	 
	 public static int getStatusBarHeight(Context ctx) {
		  int result = 0;
		  int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
		  if (resourceId > 0) {
		      result = ctx.getResources().getDimensionPixelSize(resourceId);
		  }
		  return result;
	}

	 
	 public static int getPixels(Context ctx,int dipValue){
         Resources r = ctx.getResources();
         int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,r.getDisplayMetrics());
         return px;
	 }
	 
	 public static float getFontHeight(String text){
		/*Paint tp = new Paint();
		Rect bounds = new Rect();
		//this will just retrieve the bounding rect for text
		tp.getTextBounds(text, 0, text.length(), bounds);
		int textHeight = bounds.height();
		Paint.FontMetrics metrics = tp.getFontMetrics();
		int totalHeight = (int) (metrics.top - metrics.bottom);*/
		return 40;
	 }
	 
	 public static float getTextLength(String text,float fontsize,Typeface tf){
		Paint p = new Paint();
		p.setTextSize(fontsize);
		p.setTypeface(tf);
		return p.measureText(text);
	 }
		 
 	public static int getHeightSPercent(Context ctx,float percent){
		float pixel = 0;
		int screenheight = GetScreenHeight(ctx);
		pixel = (screenheight*percent)/100;
		return (int) pixel;
	}
	
	public static int getWidthSPercent(Context ctx,float percent){
		float pixel = 0;
		int screenwidth = GetScreenWidth(ctx);
		pixel = (screenwidth*percent)/100;
		return (int) pixel;
	}
	
	public static int getHeightPercent(Context ctx,int height,float percent){
		float pixel = 0;
		pixel = (height*percent)/100;
		return (int) pixel;
	}
	
	public static int getWidthPercent(Context ctx,int width,float percent){
		float pixel = 0;
		pixel = (width*percent)/100;
		return (int) pixel;
	}
	
	
}
