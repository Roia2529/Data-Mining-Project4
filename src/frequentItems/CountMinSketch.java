package frequentItems;

import java.util.List;
import java.util.Map;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import java.io.IOException;

public class CountMinSketch {
private char[] chararray = null;
	
	public CountMinSketch(){
		this.chararray = null;
	}
	public CountMinSketch(String path){
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
	
	public void CountMin(int nofhash, int k,char[] query)
	{
		int[][] counter = new int[nofhash][k];
		int[][] result = new int[query.length][nofhash];
		for(int round=0;round<nofhash; round++)
		{
			//generate hash table
			Map<Character,Integer> AtoZ=this.charLUT(k);
			
			int hashresult;
			for(char new_c: this.chararray )
			{
				hashresult = AtoZ.get(new_c);
				counter[round][hashresult]++;
			}
			for(int q=0;q<query.length;q++)
			{
				hashresult = AtoZ.get(query[q]);
				result[q][round] = counter[round][hashresult]; 
			}
		}
		for(int q=0;q<query.length;q++)
		{
			System.out.print(query[q]+" result:");
			for(int round=0;round<nofhash; round++)
			{
				System.out.print(" "+result[q][round]); 
			}
			System.out.println();
		}
	}
	
	private Map<Character,Integer> charLUT(int k)
	{
		Random ran = new Random();
		Map<Character,Integer> AtoZ = new HashMap<Character,Integer>();
		for(int i=97;i<123;i++)
		{
			char t=(char) i;
			//System.out.println(t);
			AtoZ.put(t, ran.nextInt(k));
		}
		return AtoZ;
	}

}
