import java.util.Arrays;

public class CourseScheduler {
    private static void swap(int[][] courses, int i , int j) {
        int[] temp = courses[i];
        courses[i] = courses[j];
        courses[j] = temp;
    }

    private static int partition(int[][] courses, int start, int end) {


        int[] pivot = courses[end];

        int smallerIndex = start - 1;

        for(int i = start; i < end ; i++) {
            if(courses[i][1] < pivot[1]) {
                    smallerIndex++;
                    swap(courses, i, smallerIndex);
            }
        }

        swap(courses, end, smallerIndex + 1);

        return smallerIndex + 1;
    }
    private static void quickSort(int[][] courses, int start, int end) {
        if(start <= end) {
            int pivot = partition(courses, start, end);

            quickSort(courses, start, pivot - 1);
            quickSort(courses, pivot + 1, end);
        }
    }

    public static int maxNonOverlappingCourses(int[][] courses) {
        if(courses.length == 0) {
            return 0;
        }

        quickSort(courses, 0, courses.length - 1);

        int res = 1;

        int lastHour = 0;
        for(int i = 1; i < courses.length; i++) {
            if(courses[i][0] >= courses[lastHour][1]) {
                res++;
                lastHour = i;
            }
        }

        return res;
    }


    public static void main(String[] args) {
        int[][] arr = {
                {8, 11},
                {9, 12},
                {12, 15},
                {13, 15},
                {1, 4},
                {2, 5},
                {3, 6},
                {1, 2},
                {2, 4},
                {1, 3},
                {2, 4},
                {3, 5},
                {7, 8},
                {0, 24},
                {1, 23},
                {3, 19},
                {4, 5},
                {5, 6},
                {8, 10},
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8},
                {1, 2},
                {1, 2},
                {1, 2},
                {1, 2},
                {8, 18},
                {9, 11},
                {10, 17},
                {17, 18}
        };


        System.out.println(maxNonOverlappingCourses(arr));
    }
}