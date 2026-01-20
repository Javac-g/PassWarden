package View;

import Model.SecurityManager;

import java.util.Scanner;

public class View {

    private static final Scanner scanner = new Scanner(System.in);
    private static SecurityManager securityManager = new SecurityManager();
    public   void print_message(String msg){
        System.out.println(msg);
    }

    public String getStr(){

        return scanner.next();
    }
    public Integer getNum(){

        return scanner.nextInt();
    }
    public void print_menu(){
        System.out.println("  ___PASS_WARDEN___");
        System.out.println("1. add credentials");
        System.out.println("2. Look credentials");
        System.out.println("3. Log out");
    }

    public boolean authorisation(String pass){
        return securityManager.getToken().equals(pass);
    }






























}
