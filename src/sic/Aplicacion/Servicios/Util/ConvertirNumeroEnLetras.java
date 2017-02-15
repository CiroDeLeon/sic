/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Aplicacion.Servicios.Util;


import java.text.NumberFormat;


/**
 *
 * @author usuario
 */
public class ConvertirNumeroEnLetras{
   public static String getNumero_en_letras(double x) {
       return ConvertirNumeroEnLetras.getLetras(x)
               .replace("ochenta y uno mil","ochenta y un mil")
               .replace("veinti y uno mil","veinti y un mil")
               .replace("treinta y uno mil","treinta y un mil")
               .replace("cuarenta y uno mil","cuarenta y un mil")
               .replace("cincuenta y uno mil","cincuenta y un mil")
               .replace("sesenta y uno mil","sesenta y un mil")
               .replace("setenta y uno mil","setenta y un mil")
               .replace("noventa y uno mil","noventa y un mil")
               .replace("ochenta y uno millones","ochenta y un millones")
               .replace("veinti y uno millones","veinti y un millones")
               .replace("treinta y uno millones","treinta y un millones")
               .replace("cuarenta y uno millones","cuarenta y un millones")
               .replace("cincuenta y uno millones","cincuenta y un millones")
               .replace("sesenta y uno millones","sesenta y un millones")
               .replace("setenta y uno millones","setenta y un millones")
               .replace("noventa y uno millones","noventa y un millones");
   }
   public static String getLetras(double x){
       if(x==0){
           return "";
       }
       if(x==1){
           return "uno";
       }
       if(x==2){
           return "dos";
       }
       if(x==3){
           return "tres";
       }
       if(x==4){
           return "cuatro";
       }
       if(x==5){
           return "cinco";
       }
       if(x==6){
           return "seis";
       }
       if(x==7){
           return "siete";
       }
       if(x==8){
           return "ocho";
       }
       if(x==9){
           return "nueve";
       }
       if(x==10){
           return "diez";
       }
       if(x==11){
           return "once";
       }
       if(x==12){
           return "doce";
       }
       if(x==13){
           return "trece";
       }
       if(x==14){
           return "catorce";
       }
       if(x==15){
           return "quince";
       }
       if(x>15 && x<20){
        return "dieci"+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==20){
           return "veinte";
       }
       if(x>20 && x<30){
        return "veinti"+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==30){
           return "treinta";
       }
       if(x>30 && x<40){
        return "treinta y "+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==40){
           return "cuarenta";
       }
       if(x>40 && x<50){
        return "cuarenta y "+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==50){
           return "cincuenta";
       }
        if(x>50 && x<60){
        return "cincuenta y "+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==60){
           return "sesenta";
       }
        if(x>60 && x<70){
        return "sesenta y "+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==70){
           return "setenta";
       }
        if(x>70 && x<80){
        return "setenta y "+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==80){
           return "ochenta";
       }
        if(x>80 && x<90){
        return "ochenta y "+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==90){
           return "noventa";
       }
        if(x>90 && x<100){
           return "noventa y "+ConvertirNumeroEnLetras.getNumero_en_letras(x%10);
       }
       if(x==100){
           return "cien";
       }
       if(x>100 && x<200){
           return "ciento "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }
       if(x>=200 && x<300){
           return "doscientos "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }
       if(x>=300 && x<400){
           return "trecientos "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }
       if(x>=400 && x<500){
           return "cuatrocientos "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }
       if(x>=500 && x<600){
           return "quinientos "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }
       if(x>=600 && x<700){
           return "seicientos "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }
       if(x>=700 && x<800){
           return "setecientos "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }
       if(x>=800 && x<900){
           return "ochocientos "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }
       if(x>=900 && x<1000){
           return "novecientos "+ConvertirNumeroEnLetras.getNumero_en_letras(x%100);
       }

       if(x>=1000 && x<10000){
           String a=""+x;
           double p=Double.parseDouble(a.substring(0,1));
           if(p==1)
               p=0;
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" mil "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000);
       }
       if(x>=10000 && x<100000){
           String a=""+x;
           double p=Double.parseDouble(a.substring(0,2).trim());
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" mil "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000);
       }
       if(x>=100000 && x<1000000){
           String a=""+x;
           double p=Double.parseDouble(a.substring(0,3).trim());
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" mil "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000);
       }
       if(x==1000000)
           return "un millon";
       if(x>1000000 && x<10000000){
           String a=""+x;
           double p=Double.parseDouble(a.substring(0,1).trim());
           String m="millones";
           if(p==1){
               p=0;
               m="un millon";
           }
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" "+m+" "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000000);
       }
       if(x>=10000000 && x<100000000){
           String a=""+NumberFormat.getInstance().format(x);
           double p=Double.parseDouble(a.substring(0,2).trim().replace(".","").replace(',','.'));
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" millones "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000000);
       }
       if(x>=100000000 && x<1000000000){
           String a=""+NumberFormat.getInstance().format(x);
           double p=Double.parseDouble(a.substring(0,3).trim().replace(".","").replace(',','.'));
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" millones "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000000);
       }
       if(x>=1000000000 && x<10000000000.0){
           String a=""+NumberFormat.getInstance().format(x);
           double p=Double.parseDouble(a.substring(0,5).trim().replace(".","").replace(',','.'));
           //JOptionPane.showMessageDialog(null,p);
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" millones "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000000);
       }
       if(x>=10000000000.0 && x<100000000000.0){
           String a=""+NumberFormat.getInstance().format(x);
           double p=Double.parseDouble(a.substring(0,6).trim().replace(".","").replace(',','.'));
           //JOptionPane.showMessageDialog(null,p);
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" millones "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000000);
       }
       if(x>=100000000000.0 && x<1000000000000.0){
           String a=""+NumberFormat.getInstance().format(x);
           double p=Double.parseDouble(a.substring(0,7).trim().replace(".","").replace(',','.'));
           //JOptionPane.showMessageDialog(null,p);
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" millones "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000000);
       }
       if(x>=1000000000000.0 && x<10000000000000.0){
           String a=""+NumberFormat.getInstance().format(x);
           double p=Double.parseDouble(a.substring(0,1).trim());
           String m="billones";
           if(p==1){
               p=0;
               m="un billon";
           }
           return ConvertirNumeroEnLetras.getNumero_en_letras(p)+" "+m+" "+ConvertirNumeroEnLetras.getNumero_en_letras(x%1000000000000.0);
       }
       return "";
   }
}
