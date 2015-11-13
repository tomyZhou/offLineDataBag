package com.example.downloaddatabag;

import com.example.downloaddatabag.DownLoaderTask.onDownLoadCompleteListener;
import com.example.downloaddatabag.ZipExtractorTask.onUnZipCompleteListener;

import android.app.Service;

import android.content.Intent;

import android.os.IBinder;

import android.util.Log;

public class MyService extends Service {

	boolean isStop = false;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("TAG", "绑定");
		return null;

	}

	public void onCreate() {

		Log.i("TAG", "Services onCreate");
		super.onCreate();
	}

	public void onStart(Intent intent, int startId) {
		Log.i("TAG", "Services onStart");
		super.onStart(intent, startId);
		doDownLoadWork();
	}

	
	private void doDownLoadWork(){
		DownLoaderTask task = new DownLoaderTask("http://huanxinjia.cn/data.zip", "/storage/emulated/legacy/", this,
			new onDownLoadCompleteListener() {
				@Override
				public void onComplete() {
					doZipExtractorWork();
				}
			}
	    );
		task.execute();
	}
	
	
	public void doZipExtractorWork(){
		ZipExtractorTask task = new ZipExtractorTask("/storage/emulated/legacy/data.zip", "/storage/emulated/legacy/", this, true,new onUnZipCompleteListener() {
			@Override
			public void onComplete() {
				Log.e("TAG", "Service 你的任务完成了");
				notifyActivity();
				stopSelf();
			}
		});
		task.execute();
	}
	
	/**
	 * 通知界面
	 */
	protected void notifyActivity() {
		Intent intent = new Intent();  //Itent就是我们要发送的内容
		intent.putExtra("data", "/storage/emulated/legacy/data");
        intent.setAction("load_data");   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        sendBroadcast(intent);   //
	}

	@Override
	public void onDestroy() {
		Log.e("TAG", "Services onDestory");
		isStop = true;// 即使service销毁线程也不会停止，所以这里通过设置isStop来停止线程
		super.onDestroy();
	}

}