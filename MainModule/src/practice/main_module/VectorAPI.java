package practice.main_module;

import jdk.incubator.vector.*;

/**
 * To enable: include "--add-modules jdk.incubator.vector" in VM options
 *
 * Notes:
 * The vector API allows for SIMD operations (see computer architecture).
 * --> Allows a single CPU instruction to process multiple data elements in parallel
 * --> Brings Java to lower level performance while maintaining portability
 *
 * Traditional Java loops work on one integer at a time
 *
 * TODO: Explore API further
 */

public class VectorAPI {

    // Defines the vector width. I.e how many values can fit into a single vector register.
    // ^^^^ This depends on the underlying CPU architecture

    static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;

    static void main() {

        int[] a = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        int[] b = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        int[] c = new int[a.length];
        // Upper bound value using loop bound. This returns the largest index up to which vector operations can be safely performed.
        // --> This is required because the number of element processed in each iteration must exactly match the vector size.
        int upperbound = SPECIES.loopBound(a.length);
        // get the register capacity and use it as the step size to increment the loop index in each iteration.
        int registerCapacity = SPECIES.length();
        int i=0;

        System.out.println("Register Capacity: " + registerCapacity);
        System.out.println("Max values process by Vector API: " + upperbound);
        System.out.println();

        for(; i<upperbound; i+=registerCapacity) {

            System.out.println("Vectorized loop with index: " + i);

            //Load in CPU vector register
            var va = IntVector.fromArray(SPECIES, a, i);
            //Load in CPU vector register
            var vb = IntVector.fromArray(SPECIES, b, i);
            // Perform the SIMD vector add
            var vc = va.add(vb);
            //Store result back into output array
            vc.intoArray(c, i);
        }

        System.out.println();

        // This loops handles elements that were not handled in the vectorized loop
        // If the array szie is not an exact multiplication of the vector size, a few elements may remain.....🤔
        for(; i<a.length; i++) {
            System.out.println("Normal loop with index: " + i);
            c[i] = a[i] + b[i];
        }

        System.out.println();
        System.out.print("Result: ");

        for (int val : c) {
            System.out.print(val + " ");
        }

    }


}
