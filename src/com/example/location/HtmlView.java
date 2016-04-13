package com.example.location;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class HtmlView extends Activity {

	private TextView tView;

	private Handler handler;
	String sText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.html_view);
		tView = (TextView) findViewById(R.id.htmlViewId);
		// setData();
		// sText = "测试图片信息：<br><img src=\"http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg\" />";
		 sText = "测试自定义标签：<br><h1><mxgsa>测试自定义标签</mxgsa></h1><br>"
		+"<img src=\"http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg\" />"
				 +"<table><tr><td>dfdf</td></tr><td><tr>df</tr></td></table>";
		 new Yibu(tView).execute(sText); 
	}

	class Yibu extends AsyncTask<String, String, CharSequence> 
	{ 
		TextView textView; 
		public Yibu( TextView textView ) {  
	        this.textView = textView;  
	    } 
	  @Override 
	  protected CharSequence doInBackground(String... params) { 
		   ImageGetter imageGetter = new ImageGetter() {
				@Override
				public Drawable getDrawable(String source) {
					// TODO Auto-generated method stub
					URL url;
					Drawable drawable = null;
					try {
						url = new URL(source);
						drawable = Drawable.createFromStream(
								url.openStream(), null);
						drawable.setBounds(0, 0,
								drawable.getIntrinsicWidth(),
								drawable.getIntrinsicHeight());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return drawable;
				}
			};
			CharSequence test = Html.fromHtml(params[0], imageGetter, null);
			return test;
	  } 
	  @Override 
	  protected void onPostExecute(CharSequence result) { 
	   // TODO Auto-generated method stub 
		  textView.setText(result); 
	  } 
	} 
	
	private void setData2(){
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x101) {
					tView.setText((CharSequence) msg.obj);
				}
				super.handleMessage(msg);
			}
		};
		// 因为从网上下载图片是耗时操作 所以要开启新线程
		Thread t = new Thread(new Runnable() {
			Message msg = Message.obtain();

			@Override
			public void run() {
				// TODO Auto-generated method stub
				/**
				 * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
				 * fromHtml (String source, Html.ImageGetterimageGetter,
				 * Html.TagHandler
				 * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
				 * (String source)方法中返回图片的Drawable对象才可以。
				 */
				ImageGetter imageGetter = new ImageGetter() {
					@Override
					public Drawable getDrawable(String source) {
						// TODO Auto-generated method stub
						URL url;
						Drawable drawable = null;
						try {
							url = new URL(source);
							drawable = Drawable.createFromStream(
									url.openStream(), null);
							drawable.setBounds(0, 0,
									drawable.getIntrinsicWidth(),
									drawable.getIntrinsicHeight());
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return drawable;
					}
				};
				CharSequence test = Html.fromHtml(sText, imageGetter, null);
				msg.what = 0x101;
				msg.obj = test;
				handler.sendMessage(msg);
			}
		});
		t.start();
	}
	
	private void setData() {
		// TODO Auto-generated method stub
		/*
		 * final String sText =
		 * "测试自定义标签：<br><h1><mxgsa>测试自定义标签</mxgsa></h1><table><tr><td>ww</td></tr></table>"
		 * ; tView.setText(Html.fromHtml(sText, null, new
		 * MxgsaTagHandler(this))); tView.setClickable(true);
		 * tView.setMovementMethod(LinkMovementMethod.getInstance());
		 */

		final String sText = "测试图片信息：<br><img src=\"http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg\" />";
		// tView.setText(Html.fromHtml(sText, new
		// URLImageGetter(getApplicationContext(), textView), null));
		new URLImageGetter(HtmlView.this, tView);
		Spanned text = Html.fromHtml(sText, imageGetter, null);
		tView.setText(text);
	}

	final Html.ImageGetter imageGetter = new Html.ImageGetter() {

		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			URL url;
			try {
				url = new URL(source);
				drawable = Drawable.createFromStream(url.openStream(), "");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			return drawable;
		}
	};
}
