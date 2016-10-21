package lab;
//lab1 lab1 lab1

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab1 {
	public String str;
	public ArrayList<String[]> list;

	public Lab1() {
		list = new ArrayList<String[]>();
	}

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int t;
		Lab1 lab = new Lab1();

		do {
			str = br.readLine();
			t = lab.expression(str);

		} while (t == 1);
		long endTime = System.nanoTime(); 
		System.out.println("程序运行时间： "+(endTime-startTime)+"ns");
	}

	public int expression(String str) {
		//Pattern p1 = Pattern.compile("(\\d+|[a-zA-Z]+)([\\+\\*](\\d+|[a-zA-Z]+))*");
		Pattern p1 = Pattern.compile("(\\d*|[a-zA-Z]*(\\*\\d*|[a-zA-Z]*)*\\+)*(\\d*|[a-zA-Z]*(\\*\\d*|[a-zA-Z]*)*)");
		Pattern p2 = Pattern.compile("!simplify((\\s)+[a-zA-Z]*=\\d*)*");
		Pattern p3 = Pattern.compile("!d/d([a-zA-Z]*)");
		Matcher m1 = p1.matcher(str);
		Matcher m2 = p2.matcher(str);
		Matcher m3 = p3.matcher(str);
		if (m1.matches()) {
			this.str = str;
			System.out.println("True1");
			System.out.println(str);
			String[] add = str.split("\\+");
			list.clear();
			for (int i = 0; i < add.length; i++) {
				// System.out.println(add[i]);
				list.add(add[i].split("\\*"));
			}
			
			 for(int i=0; i < add.length; i++) { for(int j=0; j <
			 list.get(i).length; j++) { System.out.println(list.get(i)[j]); }
			  }
			 
			return 1;
		} else if (m2.matches()) {   
			if (list.isEmpty()) {
				System.out.println("Error");
				return 0;
			} else {
				long startTime = System.nanoTime();
				ArrayList<String> x = new ArrayList<String>();
				ArrayList<String> value = new ArrayList<String>();
				Pattern p4 = Pattern.compile("([a-zA-Z]*)=(\\d*)");
				Matcher m4 = p4.matcher(str);
				while (m4.find()) {
					x.add(m4.group(1));
					value.add(m4.group(2));
				}
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < list.get(i).length; j++) {
						for (int k = 0; k < x.size(); k++) {
							if (x.get(k).equals(list.get(i)[j])) {
								list.get(i)[j] = value.get(k);
							}
						}
					}
				}

				simplify(list);
				long endTime = System.nanoTime(); 
				System.out.println("求值、化简时间： "+(endTime-startTime)+"ns");
			}
			//System.out.println("True2");
			return 1;
		} else if (m3.matches()) {
			long startTime = System.nanoTime();
			derivative(m3.group(1));
			long endTime = System.nanoTime(); 
			System.out.println("求导时间： "+(endTime-startTime)+"ns");
			//System.out.println("True3");
			return 0;
		} else {
			System.out.println("Error");
			return 0;
		}
	}

	public void simplify(ArrayList<String[]> list) {
		Pattern p5 = Pattern.compile("[a-zA-Z]*");
		Matcher m5;
		int n = 1;
		int sum = 0;
		StringBuffer sb = new StringBuffer();
		StringBuffer strsb = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			n = 1;
			for (int j = 0; j < list.get(i).length; j++) {
				m5 = p5.matcher(list.get(i)[j]);
				if (m5.matches()) {
					if (sb.length() == 0)
						sb.append(list.get(i)[j]);
					else {
						sb.append("*");
						sb.append(list.get(i)[j]);
					}
				} else {
					n *= Integer.parseInt(list.get(i)[j]);
				}
			}
			if (sb.length() == 0) {
				sum += n;
			}
			else {
				if (n != 1){
					sb.append("*");
					sb.append(String.valueOf(n));
				}
				sb.append("+");
			}
			/*
			 * System.out.println(sb.toString()); 
			 * System.out.println(n);
			 * System.out.println(sum);
			 */
			strsb.append(sb.toString());
			sb.setLength(0);
		}
		if (sum != 0) {
			strsb.append(String.valueOf(sum));
		}
		else
			strsb.deleteCharAt(strsb.length()-1);
		System.out.println(strsb.toString());
	}
	public void derivative(String x)
	{
		Pattern p6 = Pattern.compile("[\\+\\*]?" + x + "[\\+\\*]?");
		Matcher m6;
		String[] add = str.split("\\+");
		int count;
		boolean flag = false;
		for (int i = 0; i < add.length; i++){
			count = 0;
			m6 = p6.matcher(add[i]);
			while (m6.find()) {
				count++;
			}
			if (count != 0){
				if (flag == false){
					flag = true;
				}
				else
					System.out.print("+");
				System.out.print(add[i].replaceFirst(x, String.valueOf(count)));
			}
		}
		System.out.println();
	}
}
