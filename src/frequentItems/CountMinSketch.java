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

public class CountMinSketch {

	/**
	 * 
	 * @param path
	 * @param encoding
	 * @return a string which includes complete article in the text file
	 * @throws IOException
	 */
	public static String readFile(String path, Charset encoding) 
	throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static Set<String>  readwords(String path)
	{
		String line;
		String[] result = null;

		try{
		    InputStream fis = new FileInputStream(path);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		    while ((line = br.readLine()) != null)
			{
		    	result = line.split(" ");
			}
		    
		    br.close();
		    
		    //
		    Set<String> set = new HashSet<String>();
		    String space="";
		    int count2 = 0;
		    int previndex = -1;
		    
		    //2-grams
		    for(int i=0;i<result.length;i++)
		    {
		    	if(!result[i].equals(space))
		    	{
		    		if(previndex==-1)
		    			previndex = i;
		    		count2++;
		    	}
		    	if(count2==2)
		    	{
		    		String word_2=new String(result[previndex]+" "+result[i]);
		    		if(!set.contains(word_2))
		    			set.add(word_2);
		    		previndex = i;
		    		count2= 1;
		    	}
		    }
		    return set;
		    
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * 
	 * @param path to txt file
	 * @return 2-gram result
	 */
	public static Set<String> kGram(String path, int k)
	{
		//Open file
		String article = new String();
		try {
			article = readFile(path, Charset.defaultCharset());
			//return article;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> set = new HashSet<String>();
		char[] charArray = article.toCharArray();
		
		//System.out.println("The start");
		int end = charArray.length - (k-1);
		for(int i=0;i<end;i++) 
		{		
			String tempS = new String(charArray,i,k);
			//System.out.println("two char:"+tempS);
			if(!set.contains(tempS))
			{
				set.add(tempS);
				//System.out.println(tempS+":set added");
			}
			//else
				//System.out.println(tempS+": no need to add");
			
		}
		//for(String s:set)
		//System.out.println("The end");
		return set;
	}
	
	public static float JaccardS(Set<String> set1, Set<String> set2)
	{
		float intersection=0;
		for(String temp : set1)
		{
			if(set2.contains(temp))
				intersection++;
		}
		float union = set1.size()+set2.size()-intersection;
		return intersection/union; 
	}
	
	public static float MinHashing( final Set<String> set1,  final Set<String> set2,int t)
	{
		//System.out.println("set1 size:"+set1.size());
		//System.out.println("set2 size:"+set2.size());
		List<String> union= new ArrayList<String>(set1.size()+set2.size());
		for(String stemp:set1)
		{
			if(!union.contains(stemp))
				union.add(stemp);
		}
		for(String stemp:set2)
		{
			if(!union.contains(stemp))
				union.add(stemp);
		}
		int domain=union.size(); 
		//System.out.println("domain size:"+domain);
		float JSsum = 0;
		Random ran = new Random();
		int r=0;
		
		for(int i= 0;i<t;i++)
		{
			r=ran.nextInt(domain);
			//if(r>=size_s1)
			//{
				String temp = union.get(r);
				if(set1.contains(temp)&&set2.contains(temp))
					JSsum++;
			//}
		}		
		return JSsum/t;
	}
	public static void main(String[] args) throws IOException
	{
		
		//Q1-A
		//G1
		
		Set<String> G1D1 = kGram("txt/D1.txt",2);
		System.out.println("set size of 2-Grams:"+G1D1.size()+" from file D1");
		
		Set<String> G1D2 = kGram("txt/D2.txt",2);
		System.out.println("set size of 2-Grams:"+G1D2.size()+" from file D2");
		Set<String> G1D3 = kGram("txt/D3.txt",2);
		System.out.println("set size of 2-Grams:"+G1D3.size()+" from file D3");
		Set<String> G1D4 = kGram("txt/D4.txt",2);
		System.out.println("set size of 2-Grams:"+G1D4.size()+" from file D4");
		
		
		System.out.println("JS D1&D2:"+JaccardS(G1D1,G1D2));
		System.out.println("JS D1&D3:"+JaccardS(G1D1,G1D3));
		System.out.println("JS D1&D4:"+JaccardS(G1D1,G1D4));
		System.out.println("JS D2&D3:"+JaccardS(G1D2,G1D3));
		System.out.println("JS D2&D4:"+JaccardS(G1D2,G1D4));
		System.out.println("JS D3&D4:"+JaccardS(G1D3,G1D4));
		//G2
		
		Set<String> G2D1 = kGram("txt/D1.txt",3);
		System.out.println("set size of 3-Grams:"+G2D1.size()+" from file D1");
		/*
		Set<String> G2D2 = kGram("txt/D2.txt",3);
		//System.out.println("set size of 3-Grams:"+G2D2.size()+" from file D2");
		Set<String> G2D3 = kGram("txt/D3.txt",3);
		//System.out.println("set size of 3-Grams:"+G2D3.size()+" from file D3");
		Set<String> G2D4 = kGram("txt/D4.txt",3);
		//System.out.println("set size of 3-Grams:"+G2D4.size()+" from file D4");
		
		/*
		float js=JaccardS(G2D1,G2D2); //domain 1715
		int [] a= {20, 60, 150, 300, 600};
		for(int t : a)
		{
			System.out.println("JS of D1&D2:"+MinHashing(G2D1,G2D2,t)+", t="+ t);
		}
		
		final XYSeries series = new XYSeries("data");
		List<Double> sum = new ArrayList<Double>(Collections.nCopies(195, 0.0));
		int tsize = 0;
		int condition = 0;
		tsize = 0;
		while (condition<300) {
			for (int i = 600; i <= 20000; i = i + 100,tsize++) {
				sum.set(tsize, sum.get(tsize)+Math.abs(js - MinHashing(G2D1, G2D2, i)) );
			} 
			condition++;
			tsize = 0;
		}
		tsize = 0;
		for (int i = 600; i <= 20000; i = i + 100,tsize++) {
			series.add(i, sum.get(tsize)/condition);
		} 
		final XYSeriesCollection data = new XYSeriesCollection(series);
		
	    kgrams chart = new kgrams(data);
	      chart.pack( );          
	      RefineryUtilities.centerFrameOnScreen( chart );          
	      chart.setVisible( true );
	    
	    System.out.println("END");  
		/*
		//G3
		Set<String> TwoWordsSetD1 = readwords("txt/D1.txt");
		System.out.println("size of 2-Grams words:"+TwoWordsSetD1.size()+" from file D1");
		Set<String> TwoWordsSetD2 = readwords("txt/D2.txt");
		System.out.println("size of 2-Grams words:"+TwoWordsSetD2.size()+" from file D2");
		Set<String> TwoWordsSetD3 = readwords("txt/D3.txt");
		System.out.println("size of 2-Grams words:"+TwoWordsSetD3.size()+" from file D3");
		Set<String> TwoWordsSetD4 = readwords("txt/D4.txt");
		System.out.println("size of 2-Grams words:"+TwoWordsSetD4.size()+" from file D4");
		//System.out.print(TwoWord);
		*/
		
		
		
	}

}
