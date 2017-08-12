/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Rosyproject1;

/**
 * Java program on Project 1: Cracking a Vigenere cipher
 * @author ROSY
 */   

import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;



public class Rosyproject1
{
    public static void main(String[] args) throws FileNotFoundException
    {
        File file;
        file = new File("C:\\\\Users\\\\Hp\\\\Desktop\\\\Data security and Cryptography Notes\\\\myciphertext.txt");    // accessing ciphertext from a file 
        String ctxt = new Scanner(file).useDelimiter(" ").next(); 
        System.out.println("Cipher Text :" + ctxt); 
		int kl= KeyLength(ctxt); 
        System.out.println("Length of the key is- "+kl);
		String  plaintext= decrypt(ctxt,kl);
		System.out.println("Plain Text : " + plaintext);
    }

    public static int KeyLength(String ct) 
    { 
        int kl,count,max;
		kl=0;
		count=0;
		max=0;
		char [] ct1=ct.toUpperCase().toCharArray();
        String ctfinal=ct;   
		for(int i=1;i<=25;i++)
		{
            ctfinal =' '+ctfinal;                                              //Shift Ciphertext by one position
            char [] ctfinal1=ctfinal.toUpperCase().toCharArray();
            for(int j=0;j<ct.length()-i;j++)
			{ 
                if(ct1[j+i]==ctfinal1[j+i])
                count++;
                          
			}

            if(count-5>=max) 
			{ 
                max=count;
                kl=i;
			}

            System.out.println("coincidence for shift no: "+i+" -is- " + count); 
            count=0;
        }
        return kl;
    }

	public static String decrypt(String ct,int keylen)
	{ 
		String pt;
		char[] ctd=ct.toUpperCase().toCharArray();

		StringBuilder[] sb=new StringBuilder[keylen];                                //String builder to split ciphertext
		for(int i=0;i<keylen;i++) 
			sb[i]=new StringBuilder();

		StringBuilder[] sb_pt=new StringBuilder[keylen];
		for(int i=0;i<keylen;i++) 
			sb_pt[i]=new StringBuilder();
	
		for(int i=0;i<ctd.length;i++) 
			sb[i%keylen].append(ctd[i]);


		for(int i=0;i<keylen;i++) 
		{
                                                                                  
			pt=shiftDecode(sb[i].toString());
			sb_pt[i].append(pt);
		}
		pt="";

		for(int i=0;i<sb_pt[4].length();i++) 
		{ 
			for(int j=0;j<keylen;j++) 
			{ 
				pt+=sb_pt[j].charAt(i);
			}
		}
		return pt;
	}
	public static double[] Shift(double[] A)
	{ 
		double tmp = A[0];
		for(int j = 0;j<A.length-1;j++)
            A[j]=A[j+1];
		A[A.length-1]=tmp; 
        return A;
    }
 
	// Finding the Key by using the English letter frequencies
	static String ALPHABET ="abcdefghijklmnopqrstuvwxyz";
	public static String shiftDecode(String ct) 
	{
		double[] A={0.08167,0.01492,0.02782,0.04253,0.12702,0.02228,0.02015,0.06094,0.06966,0.00153, 0.00772,0.04025,0.02406,0.06749,0.07507,0.01929,0.00095,0.05987,0.06327,0.09056,
					0.02758,0.00978,0.02360,0.00150,0.01974,0.00074};
		double[] W=new double[26];
		char c,ch;
		int i=ct.length(),j,k=0; 
		for(c='A';c<='Z';c++,k++) 
		{
			for(j=0;j<i;j++)
			{

				ch=ct.charAt(j); 
				if(Character.toUpperCase(ch)==c)
				W[k]++;
			}
			W[k]=W[k]/i;
		}
		double k1=0,max=k1; 
		int key=0;

		for(int n=0;n<25;n++) 
		{ 
			k1=0;
			for(i=0;i<=25;i++)
			k1+=W[i]*A[i];   
			if(k1>=max)
				{ 
				//Find maximum value of k1 to find the KEY
				max = k1;
				key =26 - n;
				}
			A=Shift(A);  // Left shift A by one position
		}
		System.out.println("key character: "+ALPHABET.charAt(key)); 
		String ptxt=getPlainText(ct, key);  
		return ptxt;
	}


	public static String getPlainText(String ct,int key) // Decrypting the ciphertext using the key found
	{
 
		ALPHABET=ALPHABET.toUpperCase();
		String ptxt="";
		for(int i=0;i<ct.length();i++) 
		{
			int position=ALPHABET.indexOf(ct.charAt(i)); int keyval=(position-key)%26;
			if(keyval<0) keyval+=ALPHABET.length();
			char replace=ALPHABET.charAt(keyval);
			ptxt+=replace;
		}
		return ptxt; 
	}


	
}