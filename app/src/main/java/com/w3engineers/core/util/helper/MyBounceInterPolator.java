package com.w3engineers.core.util.helper;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/30/2018 at 4:42 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/30/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

public class MyBounceInterPolator implements android.view.animation.Interpolator{

    private double mAmplitude = 1;
    private double mFrequency = 10;

    public MyBounceInterPolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}
