package net.masonliu.decompileapk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Result {

	public static void handleResult(String[] args) {   

	   File file=new File(args[0]);   
	  
	   String a=file2String(file,"UTF-8");
	   
	   System.out.println(pattern("android:versionCode=\"\\d*\"",a));; 

       System.out.println(pattern("android:versionName=\"[\\S^\"]*\"",a));; 
       
       System.out.println(pattern("package=\"[\\S^\"]*\"",a));
       
       String c[]=a.split("\n");
       for(int i=0;i<c.length;i++){
    	   if(c[i].contains("UMENG_APPKEY")){
    		   int e =i;
    		   String f=c[i];
    		   while(!c[e].contains(">")){
    			   e++;
    			   f=f+c[e];
    		   }
    		   System.out.println("umeng id:"+pattern("android:value=\"[\\S^\"]*\"",f)); 
    	   }else{
    		   if(c[i].contains("UMENG_CHANNEL")){
    			   int e =i;
        		   String f=c[i];
        		   while(!c[e].contains(">")){
        			   e++;
        			   f=f+c[e];
        		   }
        		   System.out.println("umeng channel name:"+pattern("android:value=\"[\\S^\"]*\"",f)); 
    		   }
    	   }
       }
       
	    File file2=new File(args[1]);
		if(file2==null){
			System.out.println("check sign failed！"); 
		}else{
			String d=file2String(file2,"UTF-8");
			if(d.contains("certificate is valid")){
				System.out.println("\n"+"certificate verified:"); 
				String e[]=d.split("X.509");
				System.out.println(e[1]);
			}else{
				System.out.println("no sign！"); 
			}
			
			
		}
	}
	private static String pattern(String b,String a) {
		Pattern p2 = Pattern.compile(b); 
		   Matcher m2 = p2.matcher(a); 
		   while (m2.find()) { 
		           String s0 = m2.group(); 
		           return s0;
		   }
		   return "";
	}  

    public static String file2String(File file, String encoding) { 
            InputStreamReader reader = null; 
            StringWriter writer = new StringWriter(); 
            try { 
                    if (encoding == null || "".equals(encoding.trim())) { 
                            reader = new InputStreamReader(new FileInputStream(file), encoding); 
                    } else { 
                            reader = new InputStreamReader(new FileInputStream(file)); 
                    } 
                    char[] buffer = new char[2048]; 
                    int n = 0; 
                    while (-1 != (n = reader.read(buffer))) { 
                            writer.write(buffer, 0, n); 
                    } 
            } catch (Exception e) { 
                    e.printStackTrace(); 
                    return null; 
            } finally { 
                    if (reader != null) 
                            try { 
                                    reader.close(); 
                            } catch (IOException e) { 
                                    e.printStackTrace(); 
                            } 
            } 
            if (writer != null) 
                    return writer.toString(); 
            else return null; 
    }
	       
}
