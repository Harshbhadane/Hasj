package pass1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
/*
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
*/
public class Assembler
{
	HashMap<String,Integer> MOT = new HashMap<>();
	HashMap<String,Integer> RT = new HashMap<>();
	HashMap<String,Integer> DST = new HashMap<>();
	LiteralTable LT[] =new LiteralTable[10];
	SymbolTable ST[] =new SymbolTable[10];
	int STP=0,LTP=0;
	int location_counter=0;
	String row="";
	public Assembler()
	{
		MOT.put("STOP",00);
		MOT.put("ADD",01);
		MOT.put("SUB",02);
		MOT.put("MULT",03);
		MOT.put("MOVER",04);
		MOT.put("MOVEM",05);
		MOT.put("COMP",06);
		MOT.put("BC",07);
		MOT.put("DIV",8);
		MOT.put("READ",9);
		MOT.put("PRINT",10);

		RT.put("AREG",1);
		RT.put("BREG",2);
		RT.put("CREG",3);
		RT.put("DREG",4);

		DST.put("DC",11);
		DST.put("DS",22);
}
	public void pass1() throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yashk\\OneDrive\\other\\PRACTICAL\\LP1\\pass1\\input.txt"));
		FileWriter fw = new FileWriter("C:\\Users\\yashk\\OneDrive\\other\\PRACTICAL\\LP1\\pass1\\out.txt");
		BufferedWriter bw =new BufferedWriter(fw);
		String line="",statment="";
		while((line=br.readLine())!=null)
		{
			statment = parse_Line(line);
			System.out.println(statment+" ");
			bw.write(statment+" \n");
		}
		bw.close();
		fw.close();
		for(int i=0;i<STP;i++)
		{
			System.out.println(i+" "+ST[i].name+" , "+ST[i].add);
		}
		for(int i=0;i<LTP;i++)
		{
			//System.out.println(LT[i].name+" , "+LT[i].add);
		}
	}
	public String parse_Line(String line)
	{
		//row = row+location_counter;
		row = "";
		String split[]=line.split(" ");
		if(split.length>4)
		{
			System.out.println("Invalid Statement");
		}
		else 
		{
			row =row+location_counter;
			if(split[0].equalsIgnoreCase("Start"))
			{
				location_counter =Integer.parseInt(split[1]);
				return location_counter+"";
			}
			else if(split[0].equalsIgnoreCase("End"))
			{
				return "";
			}
			else if(split[0].equalsIgnoreCase("LTORG"))
			{
				return "";
			}
			else if(split[0].equalsIgnoreCase("ORIGIN"))
			{
				return "";
			}
			else if(split[0].equalsIgnoreCase("EQU"))
			{
				return "";
			}
			else
			{ 
				for(int i=0;i<split.length;i++)
				{
					int opcode = getOpcode(split[i]);
					if(opcode != 9999)
					{
						row = row+" "+opcode;
					}
					else
					{
						if((split[i].charAt(0))=='=')
						{
							LT[LTP]=new LiteralTable(""+split[i],0);
							row = row+"  L"+LTP;
							LTP++;

						}
						else
						{
							int len = split[i].length();
							if(len > 3) {
								System.out.println("Dead Code");
							}
							else {
									if(split[1]!="DC")
									{
										ST[STP]=new SymbolTable(""+split[i],0);
										row = row+" S"+STP;
										STP++;
									}
									else
									{
										System.out.print("--------------------");

										int index = 0;
										if((index=searchST(split[i]))!=0)
										{
											row =row+" S"+index;
											System.out.print("--------------------");
										}
										else
										{
											row =row+" "+split[i];
										}
									}
							}
						}
					}
				}
			}
			location_counter++;
		}
		return row;

	}
	
	public int getOpcode(String key) {
		if(MOT.containsKey(key))
		{
			return MOT.get(key);
		}
		else if(RT.containsKey(key))
		{
			return RT.get(key);
		}
		else if(DST.containsKey(key))
		{
			return DST.get(key);
		}
		
		return 9999;
	}
	
	public int searchST(String key)
	{	int ret=0;
		for(int i=0;i<STP;i++)
		{
			if(ST[i].name.equalsIgnoreCase(key) )
			{
				System.out.print(" KEy :"+ST[i].name+" Value :"+i);
				ret=i;
				return ret;
			}
			else 
				return ret;
		}
		return ret;
	}
	public static void main(String args[]) throws Exception
	{
		Assembler A =new Assembler();
		A.pass1();
		

	}


}