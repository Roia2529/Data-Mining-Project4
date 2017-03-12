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


public class Streaming{
	
	public static void main(String[] args) throws IOException
	{
		
		MisraGries M1 = new MisraGries("txt/S1.txt");
		int k=10;
		M1.Run(k-1);
		
		MisraGries M2 = new MisraGries("txt/S2.txt");
		M2.Run(k-1);
		//System.out.println("set size of 2-Grams:"+G1D1.size()+" from file D1");
		
		
	}

}
