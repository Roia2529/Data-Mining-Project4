package frequentItems;

import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import java.io.IOException;

public class MisraGries /*extends ApplicationFrame*/{
	private char[] chararray = null;
	
	public MisraGries(){
		this.chararray = null;
	}
	public MisraGries(String path){
		String article = new String();
	try {
		article = this.readFile(path, Charset.defaultCharset());
		//return article;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.chararray = article.toCharArray();

	System.out.println("This file include "+ this.chararray.length+" characters.");
		
	}
	public char[] getdata(){
		return this.chararray;
	}
	/**
	 * 
	 * @param path
	 * @param encoding
	 * @return a string which includes complete article in the text file
	 * @throws IOException
	 */
	private String readFile(String path, Charset encoding) 
	throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public void Run(final int k)
	{
		char[] label = new char[k];
		int[] counter = new int[k];
		//final char null_c = '\u0000';//the initial value of char
		int find=0;
		boolean replace = false;
		
		//initialize counter
		for(int i=0;i<k;i++)
		{
			counter[i]=0;
		}
		
		for(char new_c: this.chararray )
		{
			//int notFind = 0;
			//put character into label
			for(int i=0;i<k;i++)
			{
				if(new_c==label[i])
				{
					counter[i]++;
					find = 1;
					break;
				}
			}
			
			if(find==0)
			{
				for(int i=0;i<k;i++)
				{
					if(counter[i]==0)
					{
						label[i]=new_c;
						counter[i]=1;
						replace = true;
						break;
					} 
				}
				if(!replace)
				{
					for(int j=0;j<k;j++)
					{
						counter[j]--;
					}
				}
			}
			find= 0;
			replace = false;
			
		}
		
		for(int i=0;i<k;i++)
		{
		System.out.println("Label: "+label[i]+"  Counter is "+counter[i]);
		}
		
	}

}
