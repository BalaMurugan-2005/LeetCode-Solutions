import java.util.Scanner;

class ParkingSystem {
    int big, medium, small;

    public ParkingSystem(int big, int medium, int small) {
        this.big = big;
        this.medium = medium;
        this.small = small;
    }

    public boolean addCar(int carType) {
        if (carType == 1) {
            if (big == 0) {
                return false;
            }
            big--;
        }
        else if (carType == 2) {
            if (medium == 0) {
                return false;
            }
            medium--;
        }
        else {
            if (small == 0) {
                return false;
            }
            small--;
        }
        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int big = sc.nextInt();
        int medium = sc.nextInt();
        int small = sc.nextInt();

        ParkingSystem obj = new ParkingSystem(big, medium, small);

        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            int carType = sc.nextInt();
            System.out.println(obj.addCar(carType));
        }

        sc.close();
    }
}