package com.paradigmcreatives.ccl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.ContentConnection;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;


public class Download implements Runnable{

	String url;
	String id;
	String title;
	private CCL MIDlet;
	private Image image = null;
	static int i = 0;
	//List mList = new List("Photo Albums", List.IMPLICIT);
	//Form mForm = new Form("Photos");
	Vector vector = new Vector(5, 1);
	public Download(String url, String id, String title, CCL MIDlet) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.id = id;
		this.title = title;
		this.MIDlet = MIDlet;
	}
	public void run() {
		// TODO Auto-generated method stub
		try
	    {
	      getImage(url);
	     // Thread.sleep(10);
	    }
	    catch (Exception e)
	    { 
	    	getimage1();
	      System.err.println("Msg: " + e.toString());
	     
	    }   
	}
	private void getimage1() {
		// TODO Auto-generated method stub
		try {
			image = Image.createImage("/ThumbnailNoData3.png");
			vector.addElement(image);
			//Display.getDisplay(MIDlet).setCurrent(mList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public void start()
	  {
	    Thread thread = new Thread(this);
	    try
	    {
	      thread.start();
	    }
	    catch (Exception e)
	    {
	    }
	  }
	private void getImage(String url) throws IOException
	  {
	    ContentConnection connection = (ContentConnection) Connector.open(url);
	    
	    
	    DataInputStream iStrm = connection.openDataInputStream();    
	    
	    System.out.println("Datainput stream"+"  "+iStrm);
	    
	    ByteArrayOutputStream bStrm = null;
	   
	    Image im = null;

	    try
	    {
	      byte imageData[];      
	      int length = (int) connection.getLength();
	      if (length != -1)
	      {
	        imageData = new byte[length];      
	        iStrm.readFully(imageData);
	       
	      }
	      else  
	      {       
	        bStrm = new ByteArrayOutputStream();
	        
	        int ch;
	        while ((ch = iStrm.read()) != -1)
	         
	        	bStrm.write(ch);
	        
	        imageData = bStrm.toByteArray();
	      }
	 
	      im = Image.createImage(imageData, 0, imageData.length);        
	    }catch (OutOfMemoryError e) {
			// TODO: handle exception
	    	int size = vector.size();

            if (size > 0) {
                
            	vector.setSize(size - 1);
           
            }
	    	System.err.println("In out of memory error"+"  : "+e.toString());
	    	
		}
	    finally
	    {
	      // Clean up
	      if (connection != null)
	        connection.close();      
	      if (iStrm != null)
	        iStrm.close();
	      if (bStrm != null)
	        bStrm.close();                        
	    }
	    if (im == null)
		      getimage1();
		    else
		    {		
		    	vector.addElement(im);  	
		    }
	    MIDlet.showImage(vector);
	  }

}
