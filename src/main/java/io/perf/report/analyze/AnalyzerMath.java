package io.perf.report.analyze;//package com.java2s;

public class AnalyzerMath {
//    public static void main(String[] argv) throws Exception {
//        double[] v = new double[] {153.0, 251.0, 257.0, 260.0, 267.0, 268.0, 280.0, 310.0, 321.0, 328.0, 337.0, 387.0, 400.0, 405.0, 408.0, 411.0, 424.0, 455.0, 474.0 };
//
//
//        double p = 0.99;
//        double res = percentile(v, p);
//        System.out.println();
//    }// w  w  w. j  a v  a2s.c  om

    /**
     * Gives the percentile of an array
     *
     * @param p percentile, must be between 0 and 1
     * @throws IllegalArgumentException if parameter.
     *                                  p is not between 0 and 1.
     */
    public static double percentile(double[] v, double p) {
        if ((p < 0) || (p > 1)) {
            throw new IllegalArgumentException(
                    "Percentile must be between 0 and 1 : " + p);
        }
        double[] ans = sortMinToMax(v);
        int pos = (int) Math.floor(p * (ans.length - 1));
        double dif = p * (ans.length - 1)
                - Math.floor(p * (ans.length - 1));
        if (pos == (ans.length - 1))
            return (ans[ans.length - 1]);
        else
            return (ans[pos] * (1.0 - dif) + ans[pos + 1] * dif);
    }

    /**
     * Gives the percentile of an array.
     *
     * @param p percentile, must be between 0 and 1
     * @throws IllegalArgumentException if parameter
     *                                  p is not between 0 and 1.
     */
    public static double percentile(int[] v, double p) {
        if ((p < 0) || (p > 1)) {
            throw new IllegalArgumentException(
                    "Percentile must be between 0 and 1 : " + p);
        }
        int[] ans = sortMinToMax(v);
        int pos = (int) Math.floor(p * (ans.length - 1));
        double dif = p * (ans.length - 1)
                - Math.floor(p * (ans.length - 1));
        if (pos == (ans.length - 1))
            return (ans[ans.length - 1]);
        else
            return (ans[pos] * (1.0 - dif) + ans[pos + 1] * dif);
    }

    /**
     * Returns a sorted array from the minimum to the maximum value.
     */
    public static double[] sortMinToMax(double[] v) {
        double[] ans = copy(v);
        quickSortMinToMax(ans, 0, ans.length - 1);
        return (ans);
    }

    /**
     * Returns a sorted array from the minimum to the maximum value.
     */
    public static int[] sortMinToMax(int[] v) {
        int[] ans = copy(v);
        quickSortMinToMax(ans, 0, ans.length - 1);
        return (ans);
    }

    /**
     * Returns a copy of the array.
     */
    //a call to array.clone() may also work although this is a primitive type. I haven't checked
    //it even may be faster
    public static int[] copy(int[] array) {
        int[] result;
        result = new int[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    /**
     * Returns a copy of the array.
     */
    //a call to array.clone() may also work although this is a primitive type. I haven't checked
    //it even may be faster
    public static long[] copy(long[] array) {
        long[] result;
        result = new long[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    /**
     * Returns a copy of the array.
     */
    //a call to array.clone() may also work although this is a primitive type. I haven't checked
    //it even may be faster
    public static float[] copy(float[] array) {
        float[] result;
        result = new float[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    /**
     * Returns a copy of the array.
     */
    //a call to array.clone() may also work although this is a primitive type. I haven't checked
    //it even may be faster
    public static double[] copy(double[] array) {
        double[] result;
        result = new double[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    /**
     * Returns a copy of the array.
     */
    public static double[][] copy(double[][] v) {
        double[][] ans = new double[v.length][];
        for (int k = 0; k < v.length; k++)
            ans[k] = copy(v[k]);
        return (ans);
    }

    /**
     * Returns a copy of the array.
     */
    public static int[][] copy(int[][] v) {
        int[][] ans = new int[v.length][];
        for (int k = 0; k < v.length; k++)
            ans[k] = copy(v[k]);
        return (ans);
    }

    /**
     * This is a generic version of C.A.R Hoare's Quick Sort
     * algorithm.  This will handle arrays that are already
     * sorted, and arrays with duplicate keys.<BR>
     * <p/>
     * If you think of a one dimensional array as going from
     * the lowest index on the left to the highest index on the right
     * then the parameters to this function are lowest index or
     * left and highest index or right.  The first time you call
     * this function it will be with the parameters 0, a.length - 1.
     * (taken out of a code by James Gosling and Kevin A. Smith provided
     * with Sun's JDK 1.1.7)
     *
     * @param a   an integer array
     * @param lo0 left boundary of array partition (inclusive).
     * @param hi0 right boundary of array partition (inclusive).
     * @deprecated
     */
    private static void quickSortMinToMax(int a[], int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;
        int mid;

        if (hi0 > lo0) {

            /* Arbitrarily establishing partition element as the midpoint of
             * the array.
             */
            mid = a[(int) Math.round((lo0 + hi0) / 2.0)];

            // loop through the array until indices cross
            while (lo <= hi) {
                /* find the first element that is greater than or equal to
                 * the partition element starting from the left Index.
                 */
                while ((lo < hi0) && (a[lo] < mid))
                    ++lo;

                /* find an element that is smaller than or equal to
                 * the partition element starting from the right Index.
                 */
                while ((hi > lo0) && (a[hi] > mid))
                    --hi;

                // if the indexes have not crossed, swap
                if (lo <= hi) {
                    swap(a, lo, hi);
                    ++lo;
                    --hi;
                }
            }

            /* If the right index has not reached the left side of array
             * must now sort the left partition.
             */
            if (lo0 < hi)
                quickSortMinToMax(a, lo0, hi);

            /* If the left index has not reached the right side of array
             * must now sort the right partition.
             */
            if (lo < hi0)
                quickSortMinToMax(a, lo, hi0);

        }
    }

    /**
     * This is a generic version of C.A.R Hoare's Quick Sort
     * algorithm.  This will handle arrays that are already
     * sorted, and arrays with duplicate keys.<BR>
     * <p/>
     * If you think of a one dimensional array as going from
     * the lowest index on the left to the highest index on the right
     * then the parameters to this function are lowest index or
     * left and highest index or right.  The first time you call
     * this function it will be with the parameters 0, a.length - 1.
     * (taken out of a code by James Gosling and Kevin A. Smith provided
     * with Sun's JDK 1.1.7)
     *
     * @param a   a double array
     * @param lo0 left boundary of array partition (inclusive).
     * @param hi0 right boundary of array partition (inclusive).
     * @deprecated
     */
    private static void quickSortMinToMax(double a[], int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;
        double mid;

        if (hi0 > lo0) {

            /* Arbitrarily establishing partition element as the midpoint of
             * the array.
             */
            mid = a[(int) Math.round((lo0 + hi0) / 2.0)];

            // loop through the array until indices cross
            while (lo <= hi) {
                /* find the first element that is greater than or equal to
                 * the partition element starting from the left Index.
                 */
                while ((lo < hi0) && (a[lo] < mid))
                    ++lo;

                /* find an element that is smaller than or equal to
                 * the partition element starting from the right Index.
                 */
                while ((hi > lo0) && (a[hi] > mid))
                    --hi;

                // if the indexes have not crossed, swap
                if (lo <= hi) {
                    swap(a, lo, hi);
                    ++lo;
                    --hi;
                }
            }

            /* If the right index has not reached the left side of array
             * must now sort the left partition.
             */
            if (lo0 < hi)
                quickSortMinToMax(a, lo0, hi);

            /* If the left index has not reached the right side of array
             * must now sort the right partition.
             */
            if (lo < hi0)
                quickSortMinToMax(a, lo, hi0);

        }
    }

    /**
     * Used by the quick sort and quick select algorithms.
     */
    private static void swap(final int a[], final int i, final int j) {
        final int T;
        T = a[i];
        a[i] = a[j];
        a[j] = T;
    }

    /**
     * Used by the quick sort and quick select algorithms.
     */
    private static void swap(final double a[], final int i, final int j) {
        final double T;
        T = a[i];
        a[i] = a[j];
        a[j] = T;

    }
}