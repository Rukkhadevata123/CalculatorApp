public class HotOrCold {
    private boolean isItHotter(int current, int previous, int target) {
        if (current == target) {
            System.out.println("Found it!");
            return true;
        } else {
            return Math.abs(target - current) < Math.abs(target - previous);
        }
    }

    private int binarySearch(int target, int current, int low, int high) {
        return -1;
    }
}
