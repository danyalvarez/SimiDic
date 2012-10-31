package com.ketanolab.simidic.util;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.ketanolab.simidic.DescargaActivity;
import com.ketanolab.simidic.R;

public class Util {

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	// public static boolean hayInternet(Context contexto) {
	// ConnectivityManager cm = (ConnectivityManager) contexto
	// .getSystemService(Context.CONNECTIVITY_SERVICE);
	// if (cm.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
	// || cm.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
	// || cm.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING
	// || cm.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
	// return true;
	// }
	// return false;
	// }

	public static boolean isDownloaded(String file) {
		File directory = new File(Constants.PATH_DICTIONARIES);
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().equals(file)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean checkFilenameDictionary(String name) {
		if (name.length() == 11 && name.contains(".db")) {
			return true;
		}
		return false;
	}

	public static boolean checkExternalStorageAvailable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public static void showAlertNoExternalStorage(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.sd_required);
		builder.setMessage(R.string.you_dont_have_sd);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}

	public static void showAlertNoDictionaries(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.download_dictionaries);
		builder.setMessage(R.string.you_dont_have_dictionaries);
		builder.setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(context,
								DescargaActivity.class);
						context.startActivity(intent);
					}
				});
		builder.setNegativeButton(android.R.string.no,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}

	public static void showAlertNoInternet(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.no_internet);
		builder.setMessage(R.string.you_dont_have_conection);
		builder.setCancelable(false);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static String[] getNameAndAuthorDictionary(SQLiteDatabase db) {
		Cursor cursor = db.rawQuery("SELECT name, author FROM info", null);
		if (cursor.moveToFirst()) {
			String name = cursor.getString(0);
			String author = cursor.getString(1);
			cursor.close();
			return new String[] { name, author };
		}
		cursor.close();
		return null;
	}

}
