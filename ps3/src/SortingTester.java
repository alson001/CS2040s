public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        // TODO: implement this
        KeyValuePair[] temp = new KeyValuePair[size];
        for(int i = 0; i < size; i++){
            int min = 0;
            int max = 10000;
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            temp[i] = new KeyValuePair(random_int, 10);
        }
        sorter.sort(temp);
        for(int i = 0; i < size - 1; i++){
            if(temp[i].compareTo(temp[i + 1]) == 1){
                return false;
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        // TODO: implement this
        KeyValuePair[] temp = new KeyValuePair[size];
        for(int i = 0; i < size; i++){
            int min = 0;
            int max = 30;
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            temp[i] = new KeyValuePair(random_int , i);
        }
        sorter.sort(temp);
        for(int i = 0; i < size - 1; i++){
            if(temp[i].compareTo(temp[i + 1]) == 0){
                if(temp[i].getValue() > temp[i + 1].getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // TODO: implement this
        // Create three key value pairs
        ISort sortingObjectA = new SorterA();
        ISort sortingObjectB = new SorterB();
        ISort sortingObjectC = new SorterC();
        ISort sortingObjectD = new SorterD();
        ISort sortingObjectE = new SorterE();
        ISort sortingObjectF = new SorterF();

        /*
        System.out.println(SortingTester.checkSort(sortingObjectA, 10000));
        System.out.println(SortingTester.checkSort(sortingObjectB, 10000));
        System.out.println(SortingTester.checkSort(sortingObjectC, 10000));
        System.out.println(SortingTester.checkSort(sortingObjectD, 10000));
        System.out.println(SortingTester.checkSort(sortingObjectE, 10000));
        System.out.println(SortingTester.checkSort(sortingObjectF, 10000));
         */
        // output: true, false, true, true, true, true. SorterB is Dr.Evil



        System.out.println(SortingTester.isStable(sortingObjectA, 100));
        System.out.println(SortingTester.isStable(sortingObjectC, 100));
        System.out.println(SortingTester.isStable(sortingObjectD, 100));
        System.out.println(SortingTester.isStable(sortingObjectE, 100));
        System.out.println(SortingTester.isStable(sortingObjectF, 100));

        // output: true, true, false, false, true. SorterD and SorterE are the only unstable sorting algorithms
        // among the 5. This means one of them is Quicksort and one of them is Selection sort.


        KeyValuePair[] temp = new KeyValuePair[10000];
        for(int i = 0; i < 10000; i++){
            int min = 0;
            int max = 10000;
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            temp[i] = new KeyValuePair(random_int, 10);
        }

        /*
        StopWatch watchD = new StopWatch();
        watchD.start();
        sortingObjectD.sort(temp);
        watchD.stop();
        System.out.println("Time: " + watchD.getTime()); */
        // output 0.064632

        /*
        StopWatch watchE = new StopWatch();
        watchE.start();
        sortingObjectE.sort(temp);
        watchE.stop();
        System.out.println("Time: " + watchE.getTime()); */
        // output 0.0244436

        // E is Quicksort as it took lesser time while D is Selection sort. This is because E has a lower order of growth
        // of n log n while selection sort is n^2.

        /*
        StopWatch watchA = new StopWatch();
        watchA.start();
        sortingObjectA.sort(temp);
        watchA.stop();
        System.out.println("Time: " + watchA.getTime()); */
        // output 0.023771

        /*
        StopWatch watchC = new StopWatch();
        watchC.start();
        sortingObjectC.sort(temp);
        watchC.stop();
        System.out.println("Time: " + watchC.getTime()); */
        // output 10.788464


        /*
        StopWatch watchF = new StopWatch();
        watchF.start();
        sortingObjectF.sort(temp);
        watchF.stop();
        System.out.println("Time: " + watchF.getTime()); */
        // output 0.12793049


    }
}
