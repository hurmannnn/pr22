import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
//я не розібралась з часом(

public class Main {
    //сортування бульбашкою
    public static void bubbleSort(int[] array, boolean count) {//масив з чисел які треба відсортувати за зростанням або спаданням
        for (int i = 0; i < array.length - 1; i++) {//прохід по елементам масиву
            for (int j = 0; j < array.length - 1 - i; j++) {//порівняння елементів

                //зростання
                if (count && array[j] > array[j + 1]) {//якщо лівий елемент більший за правий - вони міняються
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }

                //спадання
                if (!count && array[j] < array[j + 1]) {//якщо правий елемент більший за лівий - вони міняються
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    //сортування вставками
    public static void insertionSort(int[] array, boolean count) {
        for (int i = 1; i < array.length; i++) {//перший елемент вже відосртований

            int el = array[i];//елемент який треба вставити у потрібне місце
            int j = i - 1;//попередній ідекс

            //за зростанням
            if (count) {
                while (j >= 0 && array[j] > el) {//поки елемент більший за за el треба зсунути його вправо
                    array[j + 1] = array[j];
                    j--;//сам зсув елементів
                }
            }
            //за спаданням
            else {
                while (j >= 0 && array[j] < el) {//
                    array[j + 1] = array[j];
                    j--;
                }
            }
            array[j + 1] = el;//ставлення el на потрібне місце
        }
    }

    //сортування за вибіркою
    public static void selectionSort(int[] array, boolean count) {
        for (int i = 0; i < array.length - 1; i++) {

            int index = i;

            for (int j = i + 1; j < array.length; j++) {

                //за зростанням
                if (count && array[j] < array[index]) {//пошук найменшего елементу
                    index = j;
                }
                //за спаданням
                if (!count && array[j] > array[index]) {//пошук найбільшого елементу
                    index = j;
                }
            }

            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;//зміна місцями елемент
        }
    }

    // сортування злиттям
    public static void mergeSort(int[] array, boolean count) {

        if (array.length < 2) {
            return;
        }//якщо у масиві меньше за 2 елементи - немає чого сортувати

        int mid = array.length / 2;//знаходження середини масиву
        int[] left = Arrays.copyOfRange(array, 0, mid);//новий масив від 0 до серелини зліва
        int[] right = Arrays.copyOfRange(array, mid, array.length);//новий масив від 0 до середини справа
        mergeSort(left, count);
        mergeSort(right, count);//виклик функцій

        merge(array, left, right, count);//виклик функції для порівнювання елементів і збору їх у у правильному порядку
    }
    public static void merge(int[] array, int[] left, int[] right, boolean count) {
        int i = 0;
        int j = 0;
        int k = 0;//пусті індекси для лівої частини, правої та готового маисву

        while (i < left.length && j < right.length) {//цикл який працює поки у лівому та правому масиві є елемнети
            //порівнювання
            if ((count && left[i] <= right[j]) ||//якщо сортування по зростанню - найменший елемент
                    (!count && left[i] >= right[j])) {//якщо по спаданню - найбільший
                array[k] = left[i];//запис елементу у готовий масив
                i++;
            }
            else {
                array[k] = right[j];//якщо елемент справа більший - ставиться він
                j++;
            }
            k++;
        }
        while (i < left.length) {//додавання у лівий
            array[k] = left[i];//
            i++;
            k++;
        }
        while (j < right.length) {//додавання у правий
            array[k] = right[j];
            j++;
            k++;
        }
    }

    //сортування підрахунком
    public static void countingSort(int[] array, boolean count, int min, int max) {

        int range = max - min + 1;//знаходження всіх можливих чисел

        int[] countArray = new int[range];//масив для рохунку

        for (int i = 0; i < array.length; i++) {
            countArray[array[i] - min]++;
        }

        int index = 0;

        //за зростанням
        if (count) {
            for (int i = 0; i < countArray.length; i++) {//прохід зліва на право

                while (countArray[i] > 0) {//поки число є
                    array[index] = i + min;//записуємо в масив
                    index++;//слідуюче місце в масиву
                    countArray[i]--;//зменшення повторювань числе
                }
            }
        }
        //за спаданням
        else {
            for (int i = countArray.length - 1; i >= 0; i--) {//прохід справа на ліво
                while (countArray[i] > 0) {
                    array[index] = i + min;
                    index++;
                    countArray[i]--;
                }
            }
        }
    }
    //швидке сортування
    public static void quickSort(int[] array, int left, int right, boolean count) {
        if (left < right) {//перевірка чи є ще що треба сортувати
            int index = partition(array, left, right, count);//index стає одним з елементів від якого відштовхуються подальші дії, він ділить масив на дві частини за допомогою partition

            quickSort(array, left, index - 1, count);//виклик лівої частини масиву
            quickSort(array, index + 1, right, count);//виклик правлї частини масиву
        }
    }

    public static int partition(int[] array, int left, int right, boolean count) {//сам розподіл елементів, щоб меньші вліво, більші вправо
        int index = array[right];//індекс це останній елемент масиву

        int i = left - 1;//елементи які вже підходять за умовою
        for (int j = left; j < right; j++) {//цикл проходить по всім елементам масиву окрім індекс
            //за зростанням
            if (count && array[j] <= index) {//якщо елемент меньший за елемент під назвою індекс або рівний йому - вліво
                i++;

                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
            //за спаданням
            if (!count && array[j] >= index) {//якщо більший - вправо
                i++;

                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];//зберження елементу після і
        array[i + 1] = array[right];//підстановка на правильне місце
        array[right] = temp;//підстановка старого елементу на місце елементу під назвоню індекс
        return i + 1;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введіть розмір масиву: ");
        int size = scanner.nextInt();

        System.out.print("Введіть мінімальне значення: ");
        int min = scanner.nextInt();

        System.out.print("Введіть максимальне значення: ");
        int max = scanner.nextInt();

        int choice;

        while (true) {
            try {
                System.out.print("Оберіть спосіб сортування: 1 - за зростанням, 2 - за спаданням: ");
                choice = scanner.nextInt();

                if (choice != 1 && choice != 2) {
                    throw new IllegalArgumentException("Потрібно ввести 1 або 2");
                }

                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Введіть число 1 або 2");
                scanner.nextLine();
            }
        }

        boolean count = (choice == 1);//якщо вибір 1 - це сортування по зростанню
        int[] array = new int[size];//створення основного масиву

        for (int i = 0; i < size; i++) {//прохід по кожному елементу
            array[i] = random.nextInt(max - min + 1) + min;//генерування чисел від мін до макс
        }
        //створення копій усіх масивів щоб кожний раз сортувались однакові масиви
        int[] bubbleArray = Arrays.copyOf(array, array.length);
        int[] insertionArray = Arrays.copyOf(array, array.length);
        int[] selectionArray = Arrays.copyOf(array, array.length);
        int[] mergeArray = Arrays.copyOf(array, array.length);
        int[] countingArray = Arrays.copyOf(array, array.length);
        int[] quickArray = Arrays.copyOf(array, array.length);

        System.out.println("\nПочатковий масив:");
        System.out.println(Arrays.toString(array));

        bubbleSort(bubbleArray, count);//сортування бульбашкове
        System.out.println("\nМасив після сортування бульбашкою:");
        System.out.println(Arrays.toString(bubbleArray));

        insertionSort(insertionArray, count);//сортування вставкою
        System.out.println("\nМасив після сортування вставками:");
        System.out.println(Arrays.toString(insertionArray));

        selectionSort(selectionArray, count);//сортування вибіркою
        System.out.println("\nМасив після сортування вибіркою:");
        System.out.println(Arrays.toString(selectionArray));
        mergeSort(mergeArray, count);

        System.out.println("\nМасив після сортування злиттям:");
        System.out.println(Arrays.toString(mergeArray));

        System.out.println("\nМасив після сортування підрахунком:");
        System.out.println(Arrays.toString(countingArray));
        countingSort(countingArray, count, min, max);

        quickSort(quickArray, 0, quickArray.length - 1, count);
        System.out.println("\nМасив після швидкого сортування:");
        System.out.println(Arrays.toString(quickArray));

        scanner.close();
    }
}