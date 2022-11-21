import java.io.*;
public class FIFO{
public static void main(String[]args)throws IOException
{
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
int fr,rl,pt =0, hit=0,fault =0;
int buffer[];
int reference[];
int mem_layout[][];
System.out.println("\nENTER THE NUMBER OF FRAMES:");
fr= Integer.parseInt(br.readLine());
System.out.println("\nENTER THE LENGTH OF REFERENCE STRING:");
rl=Integer.parseInt(br.readLine());
reference = new int[rl];
mem_layout = new int[rl][fr];
buffer =new int[fr];
for(int j=0;j<fr;j++)
buffer[j] = -1;
System.out.println("\nENTER THE REFERENCE STRING:");for(int i =0; i <rl; i++)
{
reference[i]=Integer.parseInt(br.readLine());
}
System.out.println();
for(int i=0;i<rl;i++)
{
    int search=-1;
    for(int j =0; j <fr; j++)
{
if(buffer[j]==reference[i])
{
search=j;
hit++;
break;
}
}
if(search==-1)
{
buffer[pt]=reference[i];
fault++;
pt++;
if(pt == fr)pt = 0;
}
for(int j = 0; j < fr; j++)
mem_layout[i][j]=buffer[j];
}
for(int i =0; i< fr;i++)
{
for(int j = 0; j < rl; j++)
System.out.printf("%5d ",mem_layout[j][i]);System.out.println();
}
System.out.println("\nTOTAL NUMBER OF HITS: " + hit);
System.out.println("\nHIT RATIO IS: " +(float)((float)hit/rl));
System.out.println("\nTOTAL NUMBER OF PAGE FAULT:"+fault);
}
}