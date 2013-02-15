package com.paradigmcreatives.ccl;

import java.io.IOException;
import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

public class PhotoAlbums 

{
	private Download mDownload;
	ServerData mServerData;
	static int i = 0;
	CCL Midlet;
	Vector vector;
	String photoAlbum = "http://ec2-54-234-80-86.compute-1.amazonaws.com/restv2/albums";

	public PhotoAlbums(CCL Midlet) {
		
		this.Midlet = Midlet;
		mServerData = new ServerData();
		String response = mServerData.data(photoAlbum);
		try {
			vector = new Vector(3, 2);
			JSONArray mArray = new JSONArray(response);
			for(int i = 0 ; i < mArray.length() ; i++) {
	
				PhotoAlbumsData mData = new PhotoAlbumsData();
				JSONObject object = mArray.getJSONObject(i);
				mData.setAlbum_id(object.getString("album_id"));
				mData.setAlbum_title(object.getString("album_title"));
				mData.setAlbum_thumb(object.getString("album_thumb"));
				vector.addElement(mData);	
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e) {
			
			System.out.println("Array index bound exception");
		}
		try {
		   for(int i = 28 ; i < 33 ; i++){
				
				PhotoAlbumsData mData = (PhotoAlbumsData) vector.elementAt(i);
				String thumb = mData.getAlbum_thumb();
				String image = thumb.replace('c', 't');
				System.out.println(image);
				System.out.println("thumb"+"  : "+thumb);
				//Midlet.showAlert("Downloading", false, tbMain);
				mDownload = new Download(thumb,mData.getAlbum_id(),mData.getAlbum_title(),Midlet);
				mDownload.start();
				
				
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
