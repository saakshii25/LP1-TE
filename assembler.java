import java.util.*;
import java.io.*;
class Main
{
    static String mnt[][]=new String[6][3]; //assuming 5 macros in 1 program
    static String ala[][]=new String[10][2]; //assuming 2 arguments in each macro
     static String alax[][]=new String[10][3];
    static String mdt[][]=new String[20][1]; //assuming 4 LOC for each macro
    static int mntc=0,mdtc=0,alac=0,cnt=0;
    
    public static void main(String args[])
    {    
        pass1();
        System.out.println("\n*********PASS-1 MACROPROCESSOR***********\n");
        System.out.println("MACRO NAME TABLE (MNT)\n");
        System.out.println("Sr. Macro  Loc\n");
        display(mnt,mntc,3);
        System.out.println("\n");
        System.out.println("ARGUMENT LIST ARRAY(ALA) for Pass1\n");
        display(alax,cnt,3);
        System.out.println("\n");
        System.out.println("MACRO DEFINITION TABLE (MDT)\n");
        System.out.println("Sr. \t MDT");
        display(mdt,mdtc,1);
        System.out.println("\n");
    }

    static void pass1()
    {
        int index=0,i;
        String s,prev="",substring;
        try
        {
            BufferedReader inp = new BufferedReader(new FileReader("input.txt"));
            File op = new File("pass1_output.txt");
            if (!op.exists())
            op.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(op.getAbsoluteFile()));
            while((s=inp.readLine())!=null)
            {
                if(s.equalsIgnoreCase("MACRO"))
                {   int flag=0;//FLAG PLAYS A MAJOR ROLE IN SELECTING MACRO
                    prev=s;
                    for(prev=s;!(s=inp.readLine()).equalsIgnoreCase("MEND");mdtc++)
                    {   
                        if(prev.equalsIgnoreCase("MACRO"))
                        {   
                            
                            StringTokenizer st=new StringTokenizer(s);
                            String str[]=new String[st.countTokens()];
                            for(i=0;i<str.length;i++)
                            str[i]=st.nextToken();
                            if(flag==0)
                            
                            {mnt[mntc][0]=(mntc+1)+" "; //mnt formation
                            mnt[mntc][1]=str[flag]+" ";
                            mnt[mntc++][2]=(mdtc)+" ";
                            
                            ALA(str[1],cnt);
                            /*cnt variable is used for counting macro 
                            for 1st macro it will take cnt=0
                            for 2nd macro it will take cnt=1
                            */
                            flag=1;
                            }
                        }
                        if(flag==1)                             //automatically eliminates tagging of arguments in definition
                        {  
                            
                            index=s.indexOf("&");               //finds index string starting with &
                            
                            substring=s.substring(index);       //extracts that substring
                        
                            if(!(substring.length()>11))         // length(CREG,&ARG4) ==11 and same for all other operations
                            {   
                                String tokens1[] = substring.split("[,]");      // tokens will be t1=&ARG1 t2=&ARG4
                            
                                for(i=0;i<tokens1.length;i++)                   // scans each element in alax with tokens1 element
                                {
                                     for(int m=0;m<3;m++)                       //here m is 3 bcoz alax's column size is 3 for better comparison in ALA table
                                       {
                                         if(alax[cnt][m].equalsIgnoreCase(tokens1[i]) )
                                         s=s.replaceAll(tokens1[i],"#"+m);
                                       }
                            
                                }  
                            }
                        }
                       
                        mdt[mdtc][0]=Integer.toString(mdtc)+"  "+s; 
                    }
                    mdt[mdtc++][0]=Integer.toString(mdtc-1)+"  "+s;
                    cnt+=1;  
                }
                else
                {
                 output.write(s);
                 output.newLine();
                }
            }
         output.close();
        }
        catch(FileNotFoundException ex)
        {
        System.out.println("UNABLE TO END FILE ");
        }
        catch(IOException e)
        {
        e.printStackTrace();
        }
    }
    static void ALA(String str,int count)
    
    {   int index=0;
        
        StringTokenizer st=new StringTokenizer(str,",");       //tokenizing the arguments or splitting them.
        String string[]=new String[st.countTokens()];
        
        for(int i=0;i<string.length;i++)
        {//ala table formation
            string[i]=st.nextToken();                         //inserts string tokens 
            index=string[i].indexOf("=");
            if(index!=-1)
            alax[count][i]=string[i].substring(0,index);     //enters the tokens into [arg1,arg2,arg3] format as column size of alax is 3
            
            else
            alax[count][i]=string[i]; 
        }  
        if(alax[count][2]==null)                             //if any column in a row is not filld then replace it with null
        alax[count][2]=" ";
    }
   
    static void display(String a[][],int n,int m)
    {
        int i,j;
        for(i=0;i<n;i++)
        {
            for(j=0;j<m;j++)
            if(a[i][j]==null)   //we dont want to show null in ALA
            {a[i][j]=" ";}
            else
            System.out.print(" "+a[i][j]+" ");
            System.out.println();
        }
    }
}   