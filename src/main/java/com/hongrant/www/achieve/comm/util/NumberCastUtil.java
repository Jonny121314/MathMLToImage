package com.hongrant.www.achieve.comm.util;

public class NumberCastUtil {
	/// <summary>
	        /// 阿拉伯數字轉中文
	        /// </summary>
	        /// <param name="intInput"></param>
	        /// <returns></returns>
	        public static String ToConvtZH(Integer intInput)
	        {
	            String sd = ToCH(intInput);
	            String sd2 = sd;
	            if (intInput== 10)
	            {
	                //sd2 = sd.insert(sd.lastIndexOf('十'), "一");
	                sd2 = "十";
	            }
	            return sd2;
	        }

	        public static String ToCH(Integer intInput)
	        {
	            String si = intInput.toString();
	            String sd = "";
	            if (si.length() == 1)     //個
	            {
	                sd += GetCH(intInput);
	                return sd;
	            }
	            else if (si.length() == 2)//十
	            {
	                if (si.substring(0, 1) == "1")
	                    sd += "十";
	                else
	                    sd += (GetCH(intInput / 10) + "十");
	                sd += ToCH(intInput % 10);
	            }
	            else if (si.length() == 3)//百
	            {
	                sd += (GetCH(intInput / 100) + "百"); 
	                if (String.valueOf(intInput % 100).length() < 2 && (intInput % 100) != 0)
	                    sd += "零";
	                sd += ToCH(intInput % 100);
	            }
	            else if (si.length() == 4)//千
	            {
	                sd += (GetCH(intInput / 1000) + "千");
	                if (String.valueOf(intInput % 1000).length() < 3 && (intInput % 1000) != 0)
	                    sd += "零";
	                sd += ToCH(intInput % 1000);
	            }
	            else if (si.length() == 5)//萬
	            {
	                sd += (GetCH(intInput / 10000) + "万");
	                if (String.valueOf(intInput % 10000).length() < 4 && (intInput % 10000) != 0)
	                    sd += "零";
	                sd += ToCH(intInput % 10000);
	            }

	            return sd;
	        }

	        private static String GetCH(int input)
	        {
	            String sd = "";
	            switch (input)
	            {
	                case 1:
	                    sd = "一";
	                    break;
	                case 2:
	                    sd = "二";
	                    break;
	                case 3:
	                    sd = "三";
	                    break;
	                case 4:
	                    sd = "四";
	                    break;
	                case 5:
	                    sd = "五";
	                    break;
	                case 6:
	                    sd = "六";
	                    break;
	                case 7:
	                    sd = "七";
	                    break;
	                case 8:
	                    sd = "八";
	                    break;
	                case 9:
	                    sd = "九";
	                    break;
	                default:
	                    break;
	            }
	            return sd;
	        }
	        
	        
	        public static void main(String[] args) {
				Integer aa = 10;
				String _number = NumberCastUtil.ToConvtZH(aa);
	        	System.out.println(_number);
			}
}