package org.jaaps.webview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends AppCompatActivity {

	private WebView webView;
	private Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        toolbar = (Toolbar) findViewById(R.id.app_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        
		webView = (WebView) findViewById(R.id.webView);
		startWebView("https://en.wikipedia.org/wiki/A._P._J._Abdul_Kalam");
		
         
    }
     
    @SuppressLint("NewApi")
	private void startWebView(String url) {
         
        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        toolbar = (Toolbar) findViewById(R.id.app_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        webView.setWebViewClient(new WebViewClient() {      
            ProgressDialog progressDialog;
          
            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {              
                view.loadUrl(url);
                return true;
            }
        
            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }else{
                	progressDialog.dismiss();
                }
            }
            
            public void onPageFinished(WebView view, String url) {
                try{
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode,
            		String description, String failingUrl) {
            	// TODO Auto-generated method stub
//            	super.onReceivedError(view, errorCode, description, failingUrl);
            	webView.loadUrl("file:///android_asset/errorPage.html");
            }
             
        }); 
        WebSettings webSettings = webView.getSettings();
        
         // Javascript inabled on webview  
        webSettings.setJavaScriptEnabled(true); 
         
        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);*/
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true);
        
         
        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null); 
         */
         
        //Load url in webview
        webView.loadUrl(url);
          
          
    }	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressLint("NewApi") @Override
    public void onBackPressed() {
	 if(webView.canGoBack()) {
            webView.goBack();
        }else{
        	AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        	builder.setTitle(R.string.exit_title);
        	builder.setMessage(R.string.exit_msg);
        	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        	{
        		@Override
        		public void onClick(DialogInterface dialog, int which) {
//                finish();    
        			android.os.Process.killProcess(android.os.Process.myPid());
        		}
        	});
//    	builder.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        	builder.setNegativeButton("No", null);
        	builder.show();
        }
    }

}
