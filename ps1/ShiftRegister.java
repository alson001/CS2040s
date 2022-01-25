///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    int tap1;
    int[] arr;
    int size1;

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        tap1 = tap;
        size1 = size;
        arr = new int[size];
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        if(size1 == seed.length){
            for(int i = 0; i < size1; i++){
                if(seed[i] == 0 || seed[i] == 1){
                    arr[i] = seed[size1 - 1 - i];
                }
                else {
                    System.out.println("Error. Seed is not binary.");
                }

            }
        }
        else{
            System.out.println("Wrong size.");
        }
    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        // TODO:
        int temp = arr[0];
        int temp2 = arr[size1 - 1 - tap1];
        for(int i = 0; i < size1 - 1; i++){
            arr[i] = arr[i+1];
        }
        arr[size1 - 1] = temp2 ^ temp;
        return arr[size1 - 1];
    }

    /**
     * generate
     * @param k
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        // TODO:
        int temp = 0;
        for(int i = k - 1; i >= 0; i--){
            temp = shift() * (int)Math.pow(2, i) + temp;
        }
        return temp;
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toBinary(int[] array) {
        // TODO:
        return 0;
    }
}
