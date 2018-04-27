package com.w3engineers.core.util.helper;

import java.text.NumberFormat;

/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/17/2018 at 10:55 AM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/17/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */
public class NumberUtil {

    /*
    * Md. Hasnain
    * ashik.pstu.cse@gmail.com
    * on 04/08/18 12.03AM
    */
    public static String formatFloatNumber(Float floatNum, int minFractionPoint, int maxFractionPoint){
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        numberFormat.setMinimumFractionDigits(minFractionPoint);
        numberFormat.setMaximumFractionDigits(maxFractionPoint);

        return numberFormat.format(floatNum);
    }
}
