package nma.konay.moview;

	import java.io.BufferedReader;
	import java.io.InputStream;
	import java.io.InputStreamReader;
//
//	import org.apache.http.HttpEntity;
//	import org.apache.http.HttpResponse;
//	import org.apache.http.client.HttpClient;
//	import org.apache.http.client.methods.HttpPost;
//	import org.apache.http.impl.client.DefaultHttpClient;
//
	import android.util.Log;
import java.net.*;
import java.io.*;
	

	public class JSONDownloader {

	public static String download(String url){
		StringBuffer result = new StringBuffer();
		try
		{
			HttpURLConnection connection = ((HttpURLConnection) new URL(url).openConnection());
			BufferedReader reader = new BufferedReader(
									new InputStreamReader(connection.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null)
				result.append(line).append("\n");
				
				reader.close();
				connection.disconnect();
				
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "";
		}

		return result.toString();
	}
	
}

/*
Android Pie Update (10)
========

HttpClient library
========

Android 6 (sdk 23) ကစပြီး HttpClient library က android sdk မှာ မပါတော့ပါဘူး။ ဒါကြောင့် targetSdkVersion 28 ထားရင် HttpClient ပြဿနာ ရှိပါမယ်။

ဖြေရှင်းနည်း
========

၁။ ကိုယ့် Project ထဲမှာ HttpClient အစား HttpURLConnection ကို ပြောင်းသုံးသင့်ပါတယ်။ ဒါမှသာ ကိုယ့် App ကို minimum sdk version ကနေ target sdk version ကြားထဲက device အားလုံးမှာ အသုံးပြုနိုင်မှာ ဖြစ်ပါတယ်။

Networking သင်တန်းမှာ HttpClient သုံးတဲ့ JSONDownloader.java ကို အောက်ကလို ပြင်ပါ။

JSONDownloader.java
========
import java(.)net.*;
import java(.)io.*;

public class JSONDownloader
{
 public static String download(String url){
 StringBuffer result=new StringBuffer();
 try
 {
 HttpURLConnection httpConn = ((HttpURLConnection)new URL(url).openConnection());
 BufferedReader reader = new BufferedReader(
 new InputStreamReader(httpConn.getInputStream()));
 String line = null;
 while ((line = reader.readLine()) != null)
 result.append(line).append("\n");

 reader.close();
 httpConn.disconnect();
 
 }
 catch (IOException e)
 {
 e.printStackTrace();
 return "";
 }

 return result.toString();
 }
}
========

၂။ HttpClient ကို သုံးကိုသုံးချင်တယ် ဆိုရင်တော့ အောက်ကလို လုပ်ပါ။

၂ (က)။ app/build.gradle မှာ အောက်က lib add ပါ။
========
compile 'org.jbundle.util.osgi.wrapped:org.jbundle.util.osgi.wrapped.org.apache.http.client:4.1.2'
========

၂ (ခ)။ AndroidManifest.xml မှာ အောက်ကလို ရေးထည့်ပါ။
========
<uses-library android:name="org.apache.http.legacy" android:required="false"/>
========

😊

*/
