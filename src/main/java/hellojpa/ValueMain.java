package hellojpa;

public class ValueMain {

    public static void main(String[] args) {

        /**
         *  기본 값 타입은 공유 x
         */
//        int a = 10;
//        int b = a;
        Integer a= new Integer(10);
        Integer b = a;

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
