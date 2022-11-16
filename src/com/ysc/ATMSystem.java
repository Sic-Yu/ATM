package com.ysc;

/**
 * Created with IntelliJ IDEA.
 * User: yuyuyu
 * Date: 2022/4/12.
 * Time: 22:31.
 * To change this template use File | Settings | File Templates.
 * @author 86182
 */

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 实现ATM系统的类
 */
public class ATMSystem {
    public static void main(String[] args) {
        //1.定义账户类
        //2.定义一个集合容器，负责以后存储全部的账户对象，进行相关的业务操作。
        ArrayList<Account> accounts = new ArrayList<Account>();
        Scanner sc = new Scanner(System.in);
        //3.展示系统的首页
        while (true) {
            System.out.println("===============================欢迎使用ATM系统===============================");
            System.out.println("1.账户登录");
            System.out.println("2.账户开户");

            System.out.println("请选择操作：");
            int command = sc.nextInt();

            switch (command) {
                case 1:
                    //账户登录
                    login(accounts, sc);
                    break;
                case 2:
                    //账户开户
                    register(accounts, sc);
                    break;
                default:
                    System.out.println("输入错误，请重新输入！");
            }
        }
    }

    /**
     * 账户登录
     *
     * @param accounts 全部账户的集合
     * @param sc 扫描器
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("======================系统登录操作=========================");
        //1.判断账户集合中是否存在账户，如果不存在，提示用户。
        if (accounts.size() == 0) {
            System.out.println("抱歉，没有任何账户，请先开户！");
            return;//结束方法
        }

        //2.登录操作
        while (true) {
            System.out.println("请输入账户名：");
            String cardId = sc.next();
            //3.判断卡号是否存在，根据卡号查询账户
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc != null) {
                while (true) {
                    //卡号存在，登录成功
                    //4.输入密码
                    System.out.println("请输入密码：");
                    String password = sc.next();
                    //判断密码是否正确
                    if (acc.getPassword().equals(password)) {
                        //密码正确，登录成功
                        System.out.println(acc.getUserName()+"登录成功！您的卡号是："+acc.getCardId());
                        // ```` 查询，收支明细，转账，取款，退出
                        //展示登陆后的主页面
                        showUserCommand(sc, acc,accounts);
                        return;//干掉登录方法
                    }else{
                        System.out.println("密码错误，请重新输入！");
                    }
                }
            }else {
                System.out.println("抱歉，没有该账户，请重新输入！");
            }
        }
    }

    /**
     * 展示登陆后的主页面
     */
    private static void showUserCommand(Scanner sc, Account acc, ArrayList<Account> accounts) {
        while (true) {
            System.out.println("======================用户操作页===========================");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.修改密码");
            System.out.println("6.退出");
            System.out.println("7.注销账户");
            System.out.println("请选择操作：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //查询账户(展示当前登录账户信息)
                    showAccount(acc);
                    break;
                case 2:
                    //存款
                    depositMoney(sc, acc);
                    break;
                case 3:
                    //取款
                    drawMoney(sc, acc);
                    break;
                case 4:
                    //转账
                    transferMoney(sc, acc, accounts);
                    break;
                case 5:
                    //修改密码
                    updatePassword(sc, acc);
                    return;//密码修改成功，退出主页面重新登录
                case 6:
                    //退出
                    System.out.println("您已退出登录！");
                    return;//结束方法,返回到登录方法
                case 7:
                    //注销账户
                    deleteAccount(sc, acc, accounts);
                default:
                    System.out.println("输入错误，请重新输入！");
            }
        }
    }

    /**
     * 注销账户
     * @param sc
     * @param acc
     * @param accounts
     */
    private static void deleteAccount(Scanner sc, Account acc, ArrayList<Account> accounts) {
        System.out.println("======================注销账户页===========================");
        System.out.println("您确定要注销账户吗？(y/n)");
        String confirm = sc.next();
        if (acc.getMoney() > 0) {
            System.out.println("您的账户金额不为0，不能注销！");
        } else if ("y".equals(confirm)) {
            accounts.remove(acc);
            System.out.println("您已注销账户！");
        } else {
            System.out.println("您已取消注销！");
        }
    }

    /**
     * 注销账户
     * @param sc
     * @param acc
     */
    private static void updatePassword(Scanner sc, Account acc) {
        System.out.println("================用户修改页================");
        while (true) {
            System.out.println("请输入当前密码：");
            String oldPassword = sc.next();
            //1.判断密码是否正确
            if (acc.getPassword().equals(oldPassword)) {
                while (true) {
                    //密码正确，修改密码
                    //2.输入新密码
                    System.out.println("请输入新密码：");
                    String newPassword = sc.next();

                    System.out.println("请再次输入新密码：");
                    String okPassword = sc.next();

                    //3.判断两次密码是否一致
                    if (newPassword.equals(okPassword)) {
                        //一致，修改密码
                        acc.setPassword(newPassword);
                        System.out.println("密码修改成功！");
                        return;//结束修改密码方法
                    }else {
                        System.out.println("两次密码不一致，请重新输入！");
                    }
                }
            } else {
                System.out.println("密码错误，请重新输入！");
            }
        }
    }

    /**
     * 转账功能
     * @param sc 接收输入的扫描器
     * @param acc 自己的账户
     * @param accounts 全部账户
     */
    private static void transferMoney(Scanner sc, Account acc, ArrayList<Account> accounts) {
        System.out.println("======================转账页===========================");
        //1.判断是否有两个账户
        if (accounts.size() < 2) {
            System.out.println("抱歉，当前系统中没有足够的账户，无法转账！");
            return;//结束方法
        }

        //2.判断自己的账户是否有足够的余额
        if (acc.getMoney() == 0) {
            System.out.println("抱歉，您的账户余额为0，无法转账！");
            return;//结束方法
        }

        while (true) {
            //3.开始转账操作
            System.out.println("请输入对方的账户卡号：");
            String cardId = sc.next();
            //判断卡号是否是自己的卡号
            if (cardId.equals(acc.getCardId())) {
                System.out.println("抱歉，不能给自己转账！");
                continue;//结束本次循环
            }

            //判断对方的账户是否存在，根据卡号查询对方账号对象
            Account account = getAccountByCardId(cardId, accounts);
            if (account == null) {
                System.out.println("抱歉，没有该账户，无法转账！");
            }else{
                //账户存在，认证Ta的姓氏
                String userName = account.getUserName();
                String tip = "*"+userName.substring(1);
                System.out.println("请输入["+tip+"]的姓氏：");
                String preName = sc.next();

                //判断姓氏是否正确
                if (userName.startsWith(preName)) {
                    while (true) {
                        //姓氏正确，开始转账
                        System.out.println("请输入转账金额：");
                        double money = sc.nextDouble();
                        //判断转账金额是否足够
                        if (money > acc.getMoney()) {
                            System.out.println("抱歉，您的账户余额不足,最多可以转账"+acc.getMoney()+"元！");
                        }else{
                            //转账金额足够，开始转账
                            acc.setMoney(acc.getMoney()-money);
                            account.setMoney(account.getMoney()+money);
                            System.out.println("转账成功！您的账户余额为："+acc.getMoney());
                            return;//直接结束转账方法
                        }
                    }
                }else{
                    System.out.println("抱歉，您输入的姓氏不正确，无法转账！");
                }
            }
        }
    }

    /**
     * 存款
     * @param sc
     * @param acc
     */
    private static void drawMoney(Scanner sc, Account acc) {
        System.out.println("======================取款操作==============================");
        //1.判断是否足够100元
        if (acc.getMoney() < 100) {
            System.out.println("抱歉，您的余额不足100元，请前往柜台！");
            return;
        }

        while (true) {
            //2.提示用户输入取款金额
            System.out.println("请输入取款金额：");
            double money = sc.nextDouble();


            //3.判断取款金额是否大于100
            if (money > acc.getQuotaMoney()){
                System.out.println("抱歉，您的取款金额大于取款额度，每次最多可取："+acc.getQuotaMoney()+"请前往柜台！");
            }else{
                //没有超过取款额度。
                //4.判断是否超过账户余额
                if (money > acc.getMoney()){
                    System.out.println("抱歉，您的余额不足！当前余额："+acc.getMoney());
                }else{
                    //5.执行取款操作
                    System.out.println("取款成功！"+money+"元已经从您的账户中取出！");
                    //更新账户余额
                    acc.setMoney(acc.getMoney()-money);
                    //取钱结束
                    showAccount(acc);
                    return;//结束取钱方法
                }
            }
        }
    }

    /**
     * 存钱
     * @param sc
     * @param acc
     */
    private static void depositMoney(Scanner sc, Account acc) {
        System.out.println("==========================用户存钱操作=======================");
        System.out.println("请输入存款金额：");
        double money = sc.nextDouble();

        //更新账户余额
        acc.setMoney(acc.getMoney()+money);
        System.out.println("存款成功！当前账户信息如下：");
        showAccount(acc);
    }

    /**
     * 展示账户信息
     * @param acc
     */
    private static void showAccount(Account acc) {
        System.out.println("======================账户信息===========================");
        System.out.println("账户名："+acc.getUserName());
        System.out.println("卡号："+acc.getCardId());
        System.out.println("余额："+acc.getMoney());
        System.out.println("限额："+acc.getQuotaMoney());
    }

    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("======================系统开户操作=========================");
        //1.创建一个账户对象，用于后期封装账户信息
        Account account = new Account();

        //2.录入账户信息，注入到账户对象中
        System.out.println("请输入账户名：");
        String username = sc.next();
        account.setUserName(username);

        while (true) {
            System.out.println("请输入账户密码：");
            String password = sc.next();
            System.out.println("请再次输入账户密码：");
            String okPassword = sc.next();
            if (okPassword.equals(password)) {
                //密码输入正确,可以注入给账户对象
                account.setPassword(password);
                break;//密码录入成功，跳出循环
            } else {
                System.out.println("对不起，你输入的两次密码不一致，请重新输入！ ");
            }
        }

        System.out.println("请输入账户当次限额：");
        double quotaMoney = sc.nextDouble();
        account.setQuotaMoney(quotaMoney);

        //为账户随机一个8位且不重复的的账户号（独立功能，可以做成方法）
        String cardId = getRandomCardId(accounts);
        account.setCardId(cardId);

        //3.将账户对象添加到集合中
        accounts.add(account);
        System.out.println("恭喜你，" + username + "先生/女士开户成功！您的卡号是：" + cardId);
    }

    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random random = new Random();
        while (true) {
            //1.创建一个随机数对象
            String cardId = "";
            for (int i = 0; i < 8; i++) {
                cardId += random.nextInt(10);
            }

            //2.判断随机数是否重复
            Account acc = getAccountByCardId(cardId, accounts);
            if(acc == null){
                return cardId;
            }
        }
    }

    /**
     *
     * @param cardId
     * @param accounts
     * @return
     */
    private static Account getAccountByCardId(String cardId, ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (cardId.equals(acc.getCardId())) {
                return acc;
            }
        }
        return null;
    }

}

