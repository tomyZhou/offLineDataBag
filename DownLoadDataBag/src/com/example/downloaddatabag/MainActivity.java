package com.example.downloaddatabag;

import java.io.File;
import java.util.ArrayList;
import com.alibaba.fastjson.JSON;
import com.example.downloaddatabag.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String TAG = "MainActivity";

	private TextView tv_download;

	private ListView lv_news;

	private ArrayList<News> newsList = new ArrayList<News>();

	protected ReceiveBroadCast receiveBroadCast;
	
	private NewsAdapter newsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_download = (TextView) findViewById(R.id.tv_download);
		lv_news = (ListView) findViewById(R.id.lv_news);
		// Log.d(TAG,"Environment.getExternalStorageDirectory()="+Environment.getExternalStorageDirectory());
		// Log.d(TAG, "getCacheDir().getAbsolutePath()="+getCacheDir().getAbsolutePath());
		
		newsAdapter = new NewsAdapter();
		lv_news.setAdapter(newsAdapter);

		tv_download.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				receiveBroadCast = new ReceiveBroadCast();
				IntentFilter filter = new IntentFilter();
				filter.addAction("load_data"); // 只有持有相同的action的接受者才能接收此广播
				registerReceiver(receiveBroadCast, filter);

				startService(new Intent(MainActivity.this, MyService.class));
			}
		});
	}

	public class ReceiveBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent.getStringExtra("data");
			Toast.makeText(MainActivity.this, message, 0).show();
			Log.e("处理数据", "处理数据");
			
			String json = FileIOUtil.readFile(message+"/json.txt");
			
			ArrayList <News> list = (ArrayList<News>) JSON.parseArray(json,News.class);
			newsList.addAll(list);
			newsAdapter.notifyDataSetChanged();
		}

	}

	class NewsAdapter extends BaseAdapter {
		private ImageView iv_news_image;
		private TextView tv_news_content;

		@Override
		public int getCount() {
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			return newsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.item_news, null);
			iv_news_image = (ImageView) view.findViewById(R.id.iv_news_image);
			tv_news_content = (TextView) view.findViewById(R.id.tv_news_content);

			tv_news_content.setText(newsList.get(position).content);
			String imagePath = "/storage/emulated/legacy/data/"+newsList.get(position).image;
			Picasso.with(MainActivity.this).load(new File(imagePath)).into(iv_news_image);
			return view;
		}

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiveBroadCast);
	}
}